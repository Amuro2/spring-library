package fr.amurotakahashi.cefimtestcda2.repositories;

import fr.amurotakahashi.cefimtestcda2.entities.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReservationRepository extends JpaRepository<Reservation, Integer> {
}
