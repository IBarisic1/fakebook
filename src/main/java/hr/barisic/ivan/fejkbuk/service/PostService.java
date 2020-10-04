package hr.barisic.ivan.fejkbuk.service;

import hr.barisic.ivan.fejkbuk.entity.Person;
import hr.barisic.ivan.fejkbuk.entity.Post;
import hr.barisic.ivan.fejkbuk.exception.nonexistentresource.NonexistentPostException;
import hr.barisic.ivan.fejkbuk.repository.PostRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class PostService {

    private static final Logger LOGGER = LoggerFactory.getLogger(PostService.class);

    private PostRepository postRepository;

    private PersonService personService;

    public PostService(PostRepository postRepository, PersonService personService) {
        this.postRepository = postRepository;
        this.personService = personService;
    }

    public Post findById(Long id) {
        return postRepository.findById(id).orElseThrow(() -> new NonexistentPostException(id));
    }

    public List<Post> findAll() {
        return postRepository.findAll();
    }

    public void deleteById(Long id) {
        LOGGER.info("Deleting post with id: {}", id);

        postRepository.deleteById(id);

        LOGGER.info("Successfully deleted post with id: {}", id);
    }

    @Transactional
    public Post save(Post post) {
        Person poster = personService.findById(post.getPerson().getId());

        LOGGER.info("User {} {} (id: {}) is posting a new post: {}", poster.getFirstName(), poster.getLastName(), poster.getId(), post.getText());

        poster.addPost(post);
        post = postRepository.save(post);

        LOGGER.info("User {} {} (id: {}) successfully posted a new post: {}", poster.getFirstName(), poster.getLastName(), poster.getId(), post.getText());

        return post;
    }

    @Transactional
    public void modifyPost(Post modifiedPost) {
        Post oldPost = findById(modifiedPost.getId());

        LOGGER.info("Modifying post (id: {}) from \"{}\" to \"{}\"", oldPost.getId(), oldPost.getText(), modifiedPost.getText());

        oldPost.setText(modifiedPost.getText());

        LOGGER.info("Successfully modified post (id: {}) to \"{}\"", oldPost.getId(), oldPost.getText());
    }
}
