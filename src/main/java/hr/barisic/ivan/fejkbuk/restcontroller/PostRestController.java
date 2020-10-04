package hr.barisic.ivan.fejkbuk.restcontroller;

import hr.barisic.ivan.fejkbuk.entity.Post;
import hr.barisic.ivan.fejkbuk.service.PostService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/post")
public class PostRestController {

    private PostService postService;

    public PostRestController(PostService postService) {
        this.postService = postService;
    }

    @PostMapping
    public Post newPost(@RequestBody Post post) {
        return postService.save(post);
    }

    @DeleteMapping("{id}")
    public void deletePost(@PathVariable("id") long id) {
        postService.deleteById(id);
    }

    @PutMapping("{id}")
    public void modifyPost(@PathVariable("id") long id, @RequestBody Post post) {
        post.setId(id);
        postService.modifyPost(post);
    }
}
