package com.khaikin.qrest.table;

import com.khaikin.qrest.exception.ResourceNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RestaurantTableServiceImpl implements RestaurantTableService {
    private final RestaurantTableRepository restaurantTableRepository;

    @Override
    public List<RestaurantTable> getAllTables() {
        return restaurantTableRepository.findAll();
    }

    @Override
    public RestaurantTable getTableById(Long id) {
        return restaurantTableRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Table", "tableId", id));
    }

    @Override
    public List<RestaurantTable> getAvailableTablesAtTime(LocalDateTime time) {
        return restaurantTableRepository.findAvailableTablesAtTime(time);
    }

    @Override
    public RestaurantTable createTable(RestaurantTable restaurantTable) {
        return restaurantTableRepository.save(restaurantTable);
    }

    // Phương thức tạo nhiều bàn với tiền tố, tổng số bàn và số ghế
    @Transactional
    public List<RestaurantTable> createMultipleTables(MultipleTableRequestDto multipleTableRequest) {
        List<RestaurantTable> tables = new ArrayList<>();

        // Tạo các bàn theo yêu cầu
        for (int i = multipleTableRequest.getStartTableNumber(); i <= multipleTableRequest.getEndTableNumber(); i++) {
            // Tạo tên bàn theo tiền tố và số thứ tự (ví dụ: Bàn 1, Bàn 2, D1, D2...)
            String tableName = multipleTableRequest.getPrefix() + i;
            // Tạo bàn mới với số ghế và tên bàn
            RestaurantTable table = new RestaurantTable(tableName, multipleTableRequest.getCapacity(), RestaurantTableStatus.AVAILABLE); // mặc
            // định là có sẵn
            tables.add(table);
        }

        // Lưu tất cả bàn vào cơ sở dữ liệu
        return restaurantTableRepository.saveAll(tables);
    }

    @Override
    public RestaurantTable updateTable(Long id, RestaurantTable restaurantTable) {
        RestaurantTable existingRestaurantTable = restaurantTableRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Table", "tableId", id));

        existingRestaurantTable.setName(restaurantTable.getName());
        existingRestaurantTable.setCapacity(restaurantTable.getCapacity());
        existingRestaurantTable.setStatus(restaurantTable.getStatus());

        return restaurantTableRepository.save(existingRestaurantTable);
    }

    @Override
    public void deleteTableById(Long id) {
        RestaurantTable existingRestaurantTable = restaurantTableRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Table", "tableId", id));

        restaurantTableRepository.delete(existingRestaurantTable);
    }
}
