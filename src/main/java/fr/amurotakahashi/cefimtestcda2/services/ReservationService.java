package fr.amurotakahashi.cefimtestcda2.services;

import fr.amurotakahashi.cefimtestcda2.entities.Reservation;
import fr.amurotakahashi.cefimtestcda2.repositories.ReservationRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.InvalidParameterException;
import java.util.List;
import java.util.Optional;

@Service
public class ReservationService {
    @Autowired
    private ReservationRepository reservationRepository;

    public List<Reservation> getAllReservations() {
        return reservationRepository.findAll();
    }

    public Reservation getReservationById(Integer id) {
        return reservationRepository.findById(id).orElse(null);
    }

//    public List<Reservation> getReservationsByName(String name) {
//        return reservationRepository.findByName(name);
//    }

    public Reservation postReservation(Reservation reservation) {
        if(reservation == null) {
            throw new InvalidParameterException("Reservation must not be null");
        }

        Optional<Reservation> optionalReservation = reservationRepository.findById(reservation.getId());

        if(optionalReservation.isEmpty()) {
            return reservationRepository.save(reservation);
        } else {
            return optionalReservation.get();
        }
    }

//    public Reservation patchReservationName(Integer id, String name) {
//        Optional<Reservation> optionalReservation = reservationRepository.findById(id);
//
//        if(optionalReservation.isPresent()) {
//            Reservation reservation = optionalReservation.get();
//
//            reservation.setName(name);
//
//            return reservationRepository.save(reservation);
//        } else {
//            throw new EntityNotFoundException();
//        }
//    }

    public Reservation putReservation(Integer id, Reservation reservation) {
        if(reservation == null) {
            throw new InvalidParameterException("Reservation must not be null");
        }

        reservation.setId(id);

        Optional<Reservation> optionalReservation = reservationRepository.findById(id);

        if(optionalReservation.isPresent()) {
            return reservationRepository.save(reservation);
        } else {
            throw new EntityNotFoundException();
        }
    }

    public Reservation deleteReservationById(Integer id) {
        Optional<Reservation> optionalReservation = reservationRepository.findById(id);

        if(optionalReservation.isPresent()) {
            reservationRepository.deleteById(id);

            return optionalReservation.get();
        } else {
            throw new EntityNotFoundException();
        }
    }
}
