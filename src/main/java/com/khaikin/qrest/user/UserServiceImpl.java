package com.khaikin.qrest.user;

import com.khaikin.qrest.auth.CreateAccountRequest;
import com.khaikin.qrest.auth.CreateAccountResponse;
import com.khaikin.qrest.exception.BadRequestException;
import com.khaikin.qrest.exception.ConflictException;
import com.khaikin.qrest.exception.InvalidCredentialsException;
import com.khaikin.qrest.exception.ResourceNotFoundException;
import com.khaikin.qrest.staff.Staff;
import com.khaikin.qrest.staff.StaffRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.SecureRandom;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService, UserDetailsService {

    private final UserRepository userRepository;
    private final StaffRepository staffRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final ModelMapper modelMapper;

    @Override
    public User register(String username, String password, Role role) {
        if (userRepository.findByUsername(username).isPresent())
            throw new ConflictException("A user with username '" + username + "' already exists.");

        if (role == null) {
            role = Role.USER;
        }

        User user = User.builder()
                .username(username)
                .password(passwordEncoder.encode(password))
                .role(role)
                .build();
        return userRepository.save(user);
    }

    @Override
    public User authenticate(String username, String rawPassword) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("User", "username", username));

        if (!passwordEncoder.matches(rawPassword, user.getPassword())) {
            throw new InvalidCredentialsException("Invalid password");
        }
        return user;
    }

    @Override
    public void changePassword(String username, String oldPassword, String newPassword) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("User", "username", username));

        if (!passwordEncoder.matches(oldPassword, user.getPassword())) {
            throw new BadRequestException("Old password is incorrect");
        }

        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);
    }

    @Override
    public List<UserDto> getAllUsers() {
        return userRepository.findAll().stream()
                .map(user -> modelMapper.map(user, UserDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public UserDto getUserById(Long userId) {
        User user = userRepository.getReferenceById(userId);
        return modelMapper.map(user, UserDto.class);
    }

    @Transactional
    @Override
    public UserDto updateUserStaff(Long id, Long staffId) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User", "userId", id));
        Staff staff = staffRepository.findById(staffId)
                .orElseThrow(() -> new ResourceNotFoundException("Staff", "staffId", staffId));

        if (staff.getUser() != null) {
            staff.getUser().setStaff(null);  // user cũ gỡ staff
        }
        user.setStaff(staff);
        User savedUser = userRepository.save(user);
        return modelMapper.map(savedUser, UserDto.class);
    }

    @Override
    public UserDto updateUserStaff(String username, Long staffId) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("User", "username", username));
        Staff staff = staffRepository.findById(staffId)
                .orElseThrow(() -> new ResourceNotFoundException("Staff", "staffId", staffId));

        user.setStaff(staff);
        User savedUser = userRepository.save(user);
        return modelMapper.map(savedUser, UserDto.class);
    }

    @Transactional
    @Override
    public void deleteUserById(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "userId", userId));

        // Hủy liên kết staff trước khi xóa user
        Staff staff = user.getStaff();
        if (staff != null) {
            staff.setUser(null); // Xóa liên kết trong Staff (nếu Staff có user)
            user.setStaff(null); // Xóa liên kết trong User
            staffRepository.save(staff); // Lưu Staff (nếu muốn đảm bảo DB cập nhật)
        }

        userRepository.delete(user);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        return new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                user.getPassword(),
                List.of(new SimpleGrantedAuthority("ROLE_" + user.getRole()))
        );
    }

    @Override
    public CreateAccountResponse createAccount(CreateAccountRequest request) {
        Role role = request.getRole();
        String prefix = role.name().toLowerCase();
        String pattern = "^" + prefix + "[0-9]{3,}$"; // regex chuẩn

        Optional<User> userOpt = userRepository.findTopValidUsernameByRole(role.name(), pattern);

        long nextNumber = 1;
        if (userOpt.isPresent()) {
            String lastUsername = userOpt.get().getUsername();
            String numberPart = lastUsername.substring(prefix.length());
            try {
                nextNumber = Long.parseLong(numberPart) + 1;
            } catch (NumberFormatException e) {
//                nextNumber = 1; // fallback
            }
        }

        String username = prefix + String.format("%03d", nextNumber);
        String password = request.getPassword();

        register(username, password, role);

        return new CreateAccountResponse(username, password, role);
    }


    private String generateRandomPassword(int length) {
        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        SecureRandom random = new SecureRandom();
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < length; i++) {
            int index = random.nextInt(chars.length());
            sb.append(chars.charAt(index));
        }

        return sb.toString();
    }
}