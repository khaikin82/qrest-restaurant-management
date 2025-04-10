package com.khaikin.qrest.reservation;

import java.util.List;

public interface ReservationService {
    List<Reservation> getAllReservations();
    Reservation getReservationById(Long id);
    Reservation createReservation(ReservationRequest reservationRequest);
    Reservation updateReservation(Long id, Reservation reservation);
    void deleteReservationById(Long id);
}
