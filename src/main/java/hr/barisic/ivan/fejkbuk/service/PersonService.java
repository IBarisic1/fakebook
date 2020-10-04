package hr.barisic.ivan.fejkbuk.service;

import hr.barisic.ivan.fejkbuk.entity.Person;
import hr.barisic.ivan.fejkbuk.exception.nonexistentresource.NonexistentPersonException;
import hr.barisic.ivan.fejkbuk.repository.PersonRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class PersonService {

    private static final Logger LOGGER = LoggerFactory.getLogger(PersonService.class);

    private PersonRepository personRepository;

    public PersonService(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    public Person findById(Long id) {
        return personRepository.findById(id).orElseThrow(() -> new NonexistentPersonException(id));
    }

    public List<Person> findAll() {
        return personRepository.findAll();
    }

    public Person save(Person person) {
        LOGGER.info("Registering new Person: {}", person);

        return personRepository.save(person);
    }

    public void deleteById(Long id) {
        personRepository.deleteById(id);
    }

    @Transactional
    public void modifyPersonInfo(Person modifiedPerson) {
        Person oldPerson = findById(modifiedPerson.getId());

        LOGGER.info("Modifying personal info of user {} {} (id: {})", oldPerson.getFirstName(), oldPerson.getLastName(), oldPerson.getId());

        if (modifiedPerson.getFirstName() != null) {
            LOGGER.info("Changing first name of user with id={} from {} to {}", oldPerson.getId(), oldPerson.getFirstName(), modifiedPerson.getFirstName());
            oldPerson.setFirstName(modifiedPerson.getFirstName());
        }
        if (modifiedPerson.getLastName() != null) {
            LOGGER.info("Changing last name of user with id={} from {} to {}", oldPerson.getId(), oldPerson.getLastName(), modifiedPerson.getLastName());
            oldPerson.setLastName(modifiedPerson.getLastName());
        }
        if (modifiedPerson.getEmail() != null) {
            LOGGER.info("Changing email of user with id={} from {} to {}", oldPerson.getId(), oldPerson.getEmail(), modifiedPerson.getEmail());
            oldPerson.setEmail(modifiedPerson.getEmail());
        }
        if (modifiedPerson.getPassword() != null) {
            LOGGER.info("Changing password of user with id={}", oldPerson.getId());
            oldPerson.setPassword(modifiedPerson.getPassword());
        }
    }

    @Transactional
    public void deletePerson(long id) {
        Person personForRemoval = findById(id);

        LOGGER.info("Deleting user {} {} (id: {})", personForRemoval.getFirstName(), personForRemoval.getLastName(), personForRemoval.getId());

        personForRemoval.removeAllFriendshipRequestsAndFriendships();

        deleteById(personForRemoval.getId());

        LOGGER.info("Successfully deleted user {} {} (id: {})", personForRemoval.getFirstName(), personForRemoval.getLastName(), personForRemoval.getId());
    }

    @Transactional
    public void sendFriendshipRequest(long senderId, long receiverId) {
        Person sender = findById(senderId);
        Person receiver = findById(receiverId);

        LOGGER.info("User {} {} (id: {}) sending friendship request to user {} {} (id: {}).",
                sender.getFirstName(), sender.getLastName(), sender.getId(), receiver.getFirstName(), receiver.getLastName(), receiver.getId());

        sender.sendFriendshipRequest(receiver);

        LOGGER.info("User {} {} (id: {}) successfully sent friendship request to user {} {} (id: {}).",
                sender.getFirstName(), sender.getLastName(), sender.getId(), receiver.getFirstName(), receiver.getLastName(), receiver.getId());
    }

    @Transactional
    public void cancelFriendshipRequest(long senderId, long receiverId) {
        Person sender = findById(senderId);
        Person receiver = findById(receiverId);

        LOGGER.info("User {} {} (id: {}) cancelling friendship request sent to user {} {} (id: {}).",
                sender.getFirstName(), sender.getLastName(), sender.getId(), receiver.getFirstName(), receiver.getLastName(), receiver.getId());

        sender.cancelFriendshipRequest(receiver);

        LOGGER.info("User {} {} (id: {}) successfully cancelled friendship request sent to user {} {} (id: {}).",
                sender.getFirstName(), sender.getLastName(), sender.getId(), receiver.getFirstName(), receiver.getLastName(), receiver.getId());
    }

    @Transactional
    public void declineFriendshipRequest(long senderId, long receiverId) {
        Person sender = findById(senderId);
        Person receiver = findById(receiverId);

        LOGGER.info("User {} {} (id: {}) declining friendship request from user {} {} (id: {}).",
                receiver.getFirstName(), receiver.getLastName(), receiver.getId(), sender.getFirstName(), sender.getLastName(), sender.getId());

        receiver.declineFriendshipRequest(sender);

        LOGGER.info("User {} {} (id: {}) successfully declined friendship request from user {} {} (id: {}).",
                receiver.getFirstName(), receiver.getLastName(), receiver.getId(), sender.getFirstName(), sender.getLastName(), sender.getId());
    }

    @Transactional
    public void acceptFriendshipRequest(long senderId, long receiverId) {
        Person sender = findById(senderId);
        Person receiver = findById(receiverId);

        LOGGER.info("User {} {} (id: {}) accepting friendship request from user {} {} (id: {})",
                receiver.getFirstName(), receiver.getLastName(), receiver.getId(), sender.getFirstName(), sender.getLastName(), sender.getId());

        receiver.acceptFriendshipRequest(sender);

        LOGGER.info("User {} {} (id: {}) successfully accepted friendship request from user {} {} (id: {})",
                receiver.getFirstName(), receiver.getLastName(), receiver.getId(), sender.getFirstName(), sender.getLastName(), sender.getId());
    }

    @Transactional
    public void endFriendship(long senderId, long receiverId) {
        Person sender = findById(senderId);
        Person receiver = findById(receiverId);

        LOGGER.info("User {} {} (id: {}) ending friendship with user {} {} (id: {})",
                sender.getFirstName(), sender.getLastName(), sender.getId(), receiver.getFirstName(), receiver.getLastName(), receiver.getId());

        Person.endFriendship(sender, receiver);

        LOGGER.info("User {} {} (id: {}) successfully ended friendship with user {} {} (id: {})",
                sender.getFirstName(), sender.getLastName(), sender.getId(), receiver.getFirstName(), receiver.getLastName(), receiver.getId());
    }
}
