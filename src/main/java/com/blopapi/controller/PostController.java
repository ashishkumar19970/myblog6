package com.blopapi.controller;

import com.blopapi.entity.Post;
import com.blopapi.payload.PostDto;
import com.blopapi.service.PostService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.websocket.server.PathParam;
import java.util.List;

@RestController // REST controller responsible for handling HTTP requests and producing HTTP responses.
@RequestMapping("/api/posts")//API url
public class PostController {

    private PostService postService;

    public PostController(PostService postService) {

        this.postService = postService;
    }

    //http://localhost:8080/api/posts
    //http://localhost:8080/login?logout FOR LOGOUT.
    // create blog post rest api
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<?> createPost(@Valid @RequestBody PostDto postDto, BindingResult result) { // json to PostDto// Status code =201. // ResponseEntity It will Give the response in the Response section of POSTMAN.
        if (result.hasErrors()){
            return new ResponseEntity<>(result.getFieldError().getDefaultMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        }
        PostDto savedDto = postService.createPost(postDto);
        return new ResponseEntity<>(savedDto, HttpStatus.CREATED); //201-->HttpStatus.CREATED)


    }

    //http://localhost:8080/api/posts/1000
    @GetMapping("/{id}")
    public ResponseEntity<PostDto> getPostById(@PathVariable("id") long id) { //Status Code=200
        PostDto dto = postService.getPostById(id);
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }
    //http://localhost:8080/api/posts?pageNo=0&pageSize=3&sortBy=title
    //http://localhost:8080/api/posts?pageNo=0&pageSize=10&sortBy=description
    //http://localhost:8080/api/posts?pageNo=0&pageSize=3&sortBy

    @GetMapping
    public List<PostDto> getAllPosts(                                   //page 42 to page 45 It will take size and page from url or will call url from postMan
                                                                        // this method called as Handler Method.
            @RequestParam(value = "pageNo",   defaultValue = "0", required = false)int pageNo,
            @RequestParam(value = "pageSize", defaultValue = "5", required = false)int pageSize,
            @RequestParam(value = "sortBy", defaultValue = "id",required = false)String sortBy,
            @RequestParam(value="sortDir", defaultValue = "asc", required = false)String sortDir

    ) {
        List<PostDto> postDtos = postService.getAllPost(pageNo,pageSize,sortBy,sortDir,sortDir);// It will give as  ServiceImpl layer from url
        return postDtos;

    }

    // http://localhost:8080/api/posts
    @PreAuthorize("hasRole('ADMIN')")

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePost(@PathVariable("id") long id) { //Status Code =200
        postService.deletePost(id);
        return new ResponseEntity<>("Post is Deleted", HttpStatus.OK);
    }
    //http://localhost:8080/api/posts/20
    @PreAuthorize("hasRole('ADMIN')")

    @PutMapping("/{id}")
    public ResponseEntity<PostDto> updatePost(@PathVariable("id") long id ,@RequestBody PostDto postDto) {
        PostDto dto = postService.updatePost( id, postDto);
        return new ResponseEntity<>(dto,HttpStatus.OK);


    }
}
