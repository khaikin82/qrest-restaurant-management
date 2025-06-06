package com.khaikin.qrest.reservation;

import com.khaikin.qrest.exception.ResourceNotFoundException;
import com.khaikin.qrest.table.RestaurantTable;
import com.khaikin.qrest.table.RestaurantTableRepository;
import com.khaikin.qrest.tablereservation.TableReservation;
import com.khaikin.qrest.tablereservation.TableReservationRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReservationServiceImpl implements ReservationService {
    private final ReservationRepository reservationRepository;
    private final RestaurantTableRepository restaurantTableRepository;
    private final TableReservationRepository tableReservationRepository;
    private final ModelMapper modelMapper;

    @Override
    public List<Reservation> getAllReservations() {
        return reservationRepository.findAll();
    }

    @Override
    public Reservation getReservationById(Long id) {
        return reservationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Reservation", "id", id));
    }

    @Override
    @Transactional
    public Reservation createReservation(ReservationRequest reservationRequest) {
        Reservation reservation = modelMapper.map(reservationRequest, Reservation.class);
        List<TableReservation> tableReservations = new ArrayList<>();
        if (reservationRequest.getRestaurantTableNames() != null) {
            for (String restaurantTableName : reservationRequest.getRestaurantTableNames()) {
                RestaurantTable restaurantTable = restaurantTableRepository.findByName(restaurantTableName)
                        .orElseThrow(() -> new ResourceNotFoundException("Table", "name", restaurantTableName));
                TableReservation tableReservation = new TableReservation();
                tableReservation.setRestaurantTable(restaurantTable);
                tableReservation.setReservation(reservation);

                tableReservations.add(tableReservation);
            }
            tableReservationRepository.saveAll(tableReservations);
            reservation.setTableReservations(tableReservations);
        }

        reservation.setBookingTime(LocalDateTime.now());
        return reservationRepository.save(reservation);
    }

    @Override
    public Reservation updateReservation(Long id, Reservation reservation) {
        Reservation existingReservation = reservationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Reservation", "id", id));

        existingReservation.setBookingTime(reservation.getBookingTime());
        existingReservation.setArrivalTime(reservation.getArrivalTime());
        existingReservation.setNumberOfGuests(reservation.getNumberOfGuests());
        existingReservation.setReservationStatus(reservation.getReservationStatus());
        existingReservation.setDeposit(reservation.getDeposit());
        existingReservation.setCustomerName(reservation.getCustomerName());
        existingReservation.setCustomerPhone(reservation.getCustomerPhone());
        existingReservation.setTableReservations(reservation.getTableReservations());

        return reservationRepository.save(existingReservation);
    }

    @Override
    public Reservation updateReservationStatus(Long id, ReservationStatus reservationStatus) {
        Reservation existingReservation = reservationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Reservation", "id", id));
        existingReservation.setReservationStatus(reservationStatus);
        return reservationRepository.save(existingReservation);
    }


    @Override
    public void deleteReservationById(Long id) {
        Reservation existingReservation = reservationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Reservation", "id", id));
        reservationRepository.delete(existingReservation);
    }
}

