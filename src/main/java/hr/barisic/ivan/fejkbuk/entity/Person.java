package hr.barisic.ivan.fejkbuk.entity;

import hr.barisic.ivan.fejkbuk.exception.*;
import hr.barisic.ivan.fejkbuk.exception.nonexistentresource.NonexistentFriendshipException;
import hr.barisic.ivan.fejkbuk.exception.nonexistentresource.NonexistentFriendshipRequestException;
import hr.barisic.ivan.fejkbuk.exception.resourcealreadyexists.FriendshipAlreadyExistsException;
import hr.barisic.ivan.fejkbuk.exception.resourcealreadyexists.FriendshipRequestAlreadyReceivedException;
import hr.barisic.ivan.fejkbuk.exception.resourcealreadyexists.FriendshipRequestAlreadySentException;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "person")
public class Person {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "email")
    private String email;

    @Column(name = "password")
    private String password;

    @ManyToMany
    @JoinTable(
            name = "friendship",
            joinColumns = @JoinColumn(name = "first_person_id"),
            inverseJoinColumns = @JoinColumn(name = "second_person_id")
    )
    private List<Person> friends;

    @ManyToMany
    @JoinTable(
            name = "friendship_request",
            joinColumns = @JoinColumn(name = "sender_id"),
            inverseJoinColumns = @JoinColumn(name = "receiver_id")
    )
    private List<Person> sentFriendshipRequests;

    @ManyToMany(mappedBy = "sentFriendshipRequests")
    private List<Person> receivedFriendshipRequests;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "person")
    private List<Post> posts;

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getFirstName() {
        return this.firstName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<Person> getFriends() {
        return friends;
    }

    public void setFriends(List<Person> friends) {
        this.friends = friends;
    }

    public List<Post> getPosts() {
        return posts;
    }

    public void setPosts(List<Post> posts) {
        this.posts = posts;
    }

    public List<Person> getSentFriendshipRequests() {
        return sentFriendshipRequests;
    }

    public void setSentFriendshipRequests(List<Person> sentFriendshipRequests) {
        this.sentFriendshipRequests = sentFriendshipRequests;
    }

    public List<Person> getReceivedFriendshipRequests() {
        return receivedFriendshipRequests;
    }

    public void setReceivedFriendshipRequests(List<Person> receivedFriendshipRequests) {
        this.receivedFriendshipRequests = receivedFriendshipRequests;
    }

    public void sendFriendshipRequest(Person receiver) {
        if (this.equals(receiver)) {
            throw new FriendshipRequestToSelfException("Sending friendship request to self not allowed!");
        }

        if (areFriends(this, receiver)) {
            throw new FriendshipAlreadyExistsException();
        }

        if (this.hasSentFriendshipRequestTo(receiver)) {
            throw new FriendshipRequestAlreadySentException();
        }

        if (receiver.hasSentFriendshipRequestTo(this)) {
            throw new FriendshipRequestAlreadyReceivedException();
        }

        sentFriendshipRequests.add(receiver);
        receiver.receivedFriendshipRequests.add(this);
    }

    private void removeSentFriendshipRequest(Person person) {
        if (!sentFriendshipRequests.remove(person)) {
            throw new NonexistentFriendshipRequestException();
        }
    }

    private void removeReceivedFriendshipRequest(Person person) {
        if (!receivedFriendshipRequests.remove(person)) {
            throw new NonexistentFriendshipRequestException();
        }
    }

    public void cancelFriendshipRequest(Person receiver) {
        removeFriendshipRequest(this, receiver);
    }

    public void declineFriendshipRequest(Person sender) {
        removeFriendshipRequest(sender, this);
    }

    public void acceptFriendshipRequest(Person sender) {
        if (!sender.hasSentFriendshipRequestTo(this)) {
            throw new NonexistentFriendshipRequestException("Can't accept non existent friendship request!");
        }

        if (this.equals(sender)) {
            throw new FriendshipRequestToSelfException("Accepting friendship request to self not allowed!");
        }

        if (areFriends(sender, this)) {
            throw new FriendshipAlreadyExistsException();
        }

        removeFriendshipRequest(sender, this);
        becomeFriends(sender, this);
    }

    private static void becomeFriends(Person person1, Person person2) {
        person1.friends.add(person2);
        person2.friends.add(person1);
    }

    private static void removeFriendshipRequest(Person sender, Person receiver) {
        sender.removeSentFriendshipRequest(receiver);
        receiver.removeReceivedFriendshipRequest(sender);
    }

    public static void endFriendship(Person person1, Person person2) {
        person1.removeFriend(person2);
        person2.removeFriend(person1);
    }

    private void removeFriend(Person person) {
        if (!friends.remove(person)) {
            throw new NonexistentFriendshipException();
        }
    }

    public void addPost(Post post) {
        posts.add(post);
    }

    private static boolean areFriends(Person person1, Person person2) {
        return person1.getFriends().contains(person2);
    }

    private boolean hasSentFriendshipRequestTo(Person receiver) {
        return sentFriendshipRequests.contains(receiver);
    }

    public void removeAllFriendshipRequestsAndFriendships() {
        sentFriendshipRequests.forEach(requestedFriend -> requestedFriend.getReceivedFriendshipRequests().remove(this));
        receivedFriendshipRequests.forEach(friendshipRequester -> friendshipRequester.getSentFriendshipRequests().remove(this));
        friends.forEach(friend -> friend.getFriends().remove(this));

        sentFriendshipRequests.clear();
        receivedFriendshipRequests.clear();
        friends.clear();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Person)) return false;
        Person person = (Person) o;
        return email.equals(person.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(email);
    }

    @Override
    public String toString() {
        return "Person{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", friends=" + friends +
                ", sentFriendshipRequests=" + sentFriendshipRequests +
                ", receivedFriendshipRequests=" + receivedFriendshipRequests +
                ", posts=" + posts +
                '}';
    }
}
