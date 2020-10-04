package hr.barisic.ivan.fejkbuk.restcontroller;

import hr.barisic.ivan.fejkbuk.entity.Person;
import hr.barisic.ivan.fejkbuk.service.PersonService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("person")
public class PersonRestController {
    //TODO razmisli gdje bacati exceptione (u service-ima ili skroz duboko u metodama) i vezano uz to razmisli kad ces koristiti defaultni message, a kad ces dati specificni
    //TODO možda bi bilo dobro dodati detalje o userima i u exception message kao u logove. a mozda je i nepotrebno, pogotovo ako se ispisuju logovi koji prethode tom exceptionu, a sadrze informacije koje sam mislio dodati u exception

    private PersonService personService;

    public PersonRestController(PersonService personService) {
        this.personService = personService;
    }

    @PostMapping
    public Person registerNewPerson(@RequestBody Person newPerson) {
        Person person = personService.save(newPerson);
        return person;
    }

    @PutMapping("{id}")
    public void modifyPersonInfo(@PathVariable("id") long id, @RequestBody Person modifiedPerson) {
        modifiedPerson.setId(id);
        personService.modifyPersonInfo(modifiedPerson);
    }

    @DeleteMapping("{id}")
    public void deletePerson(@PathVariable("id") long id) {
        personService.deletePerson(id);
    }

    @PostMapping("/friendship-request")
    public void sendFriendshipRequest(@RequestParam("senderId") long senderId, @RequestParam("receiverId") long receiverId) {
        personService.sendFriendshipRequest(senderId, receiverId);
    }

    @DeleteMapping("/friendship-request")
    public void cancelFriendshipRequest(@RequestParam("senderId") long senderId, @RequestParam("receiverId") long receiverId) {
        personService.cancelFriendshipRequest(senderId, receiverId);
    }

    @DeleteMapping("/decline-friendship-request")
    public void declineFriendshipRequest(@RequestParam("senderId") long senderId, @RequestParam("receiverId") long receiverId) {
        personService.declineFriendshipRequest(senderId, receiverId);
    }

    @PostMapping("/accept-friendship")
    public void acceptFriendshipRequest(@RequestParam("senderId") long senderId, @RequestParam("receiverId") long receiverId) {
        personService.acceptFriendshipRequest(senderId, receiverId);
    }

    @DeleteMapping("/end-friendship")
    public void endFriendship(@RequestParam("senderId") long senderId, @RequestParam("receiverId") long receiverId) {
        personService.endFriendship(senderId, receiverId);
    }
}
