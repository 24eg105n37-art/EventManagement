package event_management.Service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import event_management.Model.Event;
import event_management.Repository.EventRepository;
import event_management.Repository.RegistrationRepository;

@Service
public class EventService {

    private final EventRepository eventRepository;
    private final RegistrationRepository registrationRepository;

    public EventService(
            EventRepository eventRepository,
            RegistrationRepository registrationRepository) {

        this.eventRepository = eventRepository;
        this.registrationRepository = registrationRepository;
    }

    public Event saveEvent(Event event) {
        return eventRepository.save(event);
    }

    public List<Event> getAllEvents() {
        return eventRepository.findAll();
    }

    public Event getEventById(int id) {
        return eventRepository.findById(id).orElse(null);
    }

    @Transactional
    public void deleteEvent(int id) {

        registrationRepository.deleteByEventId(id);

        eventRepository.deleteById(id);
    }
}