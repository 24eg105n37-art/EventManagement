package event_management.Controller;

import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import event_management.Model.Event;
import event_management.Service.EventService;

@RestController
@RequestMapping("/events")
@CrossOrigin
public class EventController {

    private final EventService eventService;

    public EventController(EventService eventService) {
        this.eventService = eventService;
    }

    @PostMapping
    public Event addEvent(@RequestBody Event event) {
        return eventService.saveEvent(event);
    }

    @GetMapping
    public List<Event> getAllEvents() {
        return eventService.getAllEvents();
    }

    @GetMapping("/{id}")
    public Event getEventById(@PathVariable int id) {
        return eventService.getEventById(id);
    }

    @PutMapping("/{id}")
    public Event updateEvent(@PathVariable int id, @RequestBody Event event) {

        Event existingEvent = eventService.getEventById(id);

        if (existingEvent == null) {
            return null;
        }

        existingEvent.setTitle(event.getTitle());
        existingEvent.setDescription(event.getDescription());
        existingEvent.setDate(event.getDate());
        existingEvent.setTime(event.getTime());
        existingEvent.setVenue(event.getVenue());
        existingEvent.setCapacity(event.getCapacity());

        return eventService.saveEvent(existingEvent);
    }

    @DeleteMapping("/{id}")
    public String deleteEvent(@PathVariable int id) {
        eventService.deleteEvent(id);
        return "Event deleted successfully";
    }
}