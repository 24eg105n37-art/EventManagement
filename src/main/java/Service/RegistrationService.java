
package event_management.Service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;

import event_management.Model.Event;
import event_management.Model.Registration;
import event_management.Repository.EventRepository;
import event_management.Repository.RegistrationRepository;

@Service
public class RegistrationService {

    private final RegistrationRepository registrationRepository;
    private final EventRepository eventRepository;

    public RegistrationService(
            RegistrationRepository registrationRepository,
            EventRepository eventRepository) {
        this.registrationRepository = registrationRepository;
        this.eventRepository = eventRepository;
    }

    public Registration saveRegistration(Registration registration) {

        Registration existing =
                registrationRepository.findByUserIdAndEventId(
                        registration.getUserId(),
                        registration.getEventId()
                );

        if (existing != null) {
            return null;
        }

        Event event = eventRepository
                .findById(registration.getEventId())
                .orElse(null);

        if (event == null) {
            return null;
        }

        List<Registration> registrations =
                registrationRepository.findByEventId(
                        registration.getEventId()
                );

        long registeredCount = registrations.stream()
                .filter(r -> !"CANCELLED".equalsIgnoreCase(r.getStatus()))
                .count();

        if (registeredCount >= event.getCapacity()) {
            return null;
        }

        if (registration.getRegistrationDate() == null) {
            registration.setRegistrationDate(LocalDate.now());
        }

        if (registration.getStatus() == null
                || registration.getStatus().isBlank()) {
            registration.setStatus("REGISTERED");
        }

        return registrationRepository.save(registration);
    }

    public List<Registration> getAllRegistrations() {
        return registrationRepository.findAll();
    }

    public Registration getRegistrationById(int id) {
        return registrationRepository.findById(id).orElse(null);
    }

    public void deleteRegistration(int id) {
        registrationRepository.deleteById(id);
    }

    public List<Registration> getRegistrationsByUserId(int userId) {
        return registrationRepository.findByUserId(userId);
    }

    public List<Registration> getRegistrationsByEventId(int eventId) {
        return registrationRepository.findByEventId(eventId);
    }
}
