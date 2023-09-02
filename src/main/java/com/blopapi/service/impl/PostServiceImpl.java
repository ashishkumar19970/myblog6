package com.blopapi.service.impl;

import com.blopapi.entity.Post;
import com.blopapi.exception.ResourceNotFound;
import com.blopapi.payload.PostDto;
import com.blopapi.repository.PostRepository;
import com.blopapi.service.PostService;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostServiceImpl implements PostService {

    private PostRepository postRepo;
    private ModelMapper modelMapper;


    public PostServiceImpl(PostRepository postRepo,ModelMapper modelMapper) {

        this.postRepo = postRepo;
        this.modelMapper= modelMapper;
    }

    @Override
    public PostDto createPost(PostDto postDto) {
        Post post = mapToEntity(postDto);

        Post savedPost = postRepo.save(post);

        PostDto dto = mapToDto(savedPost);

        return dto;
    }

    @Override
    public PostDto getPostById(long id) {
        Post post = postRepo.findById(id).orElseThrow(
                ()->new ResourceNotFound( id)
        );
        PostDto dto = mapToDto(post);
        return dto;
    }

    @Override
    public List<PostDto> getAllPost(int pageNo, int pageSize, String sortBy, String sortDir, String dir) {

        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();

        // create Pageable instance
        Pageable pageable = PageRequest.of(pageNo,pageSize,sort);//Gives Page no, page size from the url

        Page<Post> posts = postRepo.findAll(pageable); // provide the page NO and Page size which gives pageable object

        List<Post> content = posts.getContent();// All the Page Object will converted into the list

        List<PostDto> postDtos = content.stream().map(post -> mapToDto(post)).collect(Collectors.toList());
        return postDtos;
    }
    public  void deletePost(long id){
        Post post=postRepo.findById(id).orElseThrow(
                ()-> new ResourceNotFound(id)
        );
        postRepo.deleteById(id);

    }

    @Override
    public PostDto updatePost(long id, PostDto postDto) {

       Post post= postRepo.findById(id).orElseThrow(
                ()-> new ResourceNotFound(id)


        );
        Post updatedContent = mapToEntity(postDto);
        updatedContent.setId(post.getId());
        Post updateInfo = postRepo.save(updatedContent);
        return mapToDto(updateInfo);


    }

    PostDto mapToDto(Post post){// Taking Post Converting DTO

        PostDto dto = modelMapper.map(post, PostDto.class);
//         PostDto dto = new PostDto();
//        dto.setId(post.getId());
//        dto.setTitle(post.getTitle());
//        dto.setDescription(post.getDescription());
//        dto.setContent(post.getContent());
        return dto;
    }
    Post mapToEntity(PostDto postDto){
        Post post = modelMapper.map(postDto, Post.class);
//        Post post = new Post();
//        post.setTitle(postDto.getTitle());
//        post.setDescription(postDto.getDescription());
//        post.setContent(postDto.getContent());
        return post;
    }



}







