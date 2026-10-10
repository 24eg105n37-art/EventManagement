package event_management.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import event_management.Model.Registration;

public interface RegistrationRepository extends JpaRepository<Registration, Integer> {

    List<Registration> findByUserId(int userId);

    Registration findByUserIdAndEventId(int userId, int eventId);

    List<Registration> findByEventId(int eventId);

    void deleteByEventId(int eventId);
}