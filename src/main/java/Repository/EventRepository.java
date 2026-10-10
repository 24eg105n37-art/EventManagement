package event_management.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import event_management.Model.Event;

public interface EventRepository extends JpaRepository<Event, Integer> {
}