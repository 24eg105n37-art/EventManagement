package event_management.Controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import event_management.Model.Registration;
import event_management.Service.RegistrationService;

@RestController
@RequestMapping("/registrations")
@CrossOrigin
public class RegistrationController {

    private final RegistrationService registrationService;

    public RegistrationController(RegistrationService registrationService) {
        this.registrationService = registrationService;
    }

    @PostMapping
    public ResponseEntity<?> addRegistration(
            @RequestBody Registration registration) {

        Registration result =
                registrationService.saveRegistration(registration);

        if (result == null) {
    return ResponseEntity.badRequest()
            .body("Registration failed: You may already be registered, the event may be full, or the event may not exist.");
}
        return ResponseEntity.ok(result);
    }

    @GetMapping
    public List<Registration> getAllRegistrations() {
        return registrationService.getAllRegistrations();
    }

    @GetMapping("/{id}")
    public Registration getRegistrationById(@PathVariable int id) {
        return registrationService.getRegistrationById(id);
    }

    @DeleteMapping("/{id}")
    public String deleteRegistration(@PathVariable int id) {
        registrationService.deleteRegistration(id);
        return "Registration deleted successfully";
    }

    @GetMapping("/user/{userId}")
    public List<Registration> getRegistrationsByUserId(
            @PathVariable int userId) {

        return registrationService.getRegistrationsByUserId(userId);
    }

    @GetMapping("/event/{eventId}")
    public List<Registration> getRegistrationsByEventId(
            @PathVariable int eventId) {

        return registrationService.getRegistrationsByEventId(eventId);
    }
}