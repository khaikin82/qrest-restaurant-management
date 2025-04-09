package com.khaikin.qrest.reservation;

import com.khaikin.qrest.exception.ResourceNotFoundException;
import com.khaikin.qrest.restauranttable.RestaurantTableRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReservationServiceImpl implements ReservationService {
    private final ReservationRepository reservationRepository;
    private final RestaurantTableRepository restaurantTableRepository;

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
    public Reservation createReservation(Reservation reservation) {
        restaurantTableRepository.findById(reservation.getRestaurantTable().getId())
                .orElseThrow(() -> new ResourceNotFoundException("RestaurantTable", "id", reservation.getRestaurantTable().getId()));
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
        existingReservation.setConfirmed(reservation.isConfirmed());
        existingReservation.setDeposit(reservation.getDeposit());
        existingReservation.setCustomerName(reservation.getCustomerName());
        existingReservation.setCustomerPhone(reservation.getCustomerPhone());

        if (reservation.getRestaurantTable() != null) {
            existingReservation.setRestaurantTable(restaurantTableRepository.findById(reservation.getRestaurantTable().getId())
                                                           .orElseThrow(() -> new ResourceNotFoundException("RestaurantTable", "id", reservation.getRestaurantTable().getId())));
        }

        return reservationRepository.save(existingReservation);
    }

    @Override
    public void deleteReservationById(Long id) {
        Reservation existingReservation = reservationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Reservation", "id", id));
        reservationRepository.delete(existingReservation);
    }
}

