package com.example.blogapi.service.serviceImplementation;

import com.example.blogapi.dto.PostMapper;
import com.example.blogapi.model.*;
import com.example.blogapi.repository.*;
import com.example.blogapi.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

@Service
public class PostServiceImpl implements PostService {
    @Autowired
    PostRepository postRepository;

    @Autowired
    CommentRepository commentRepository;

    @Autowired
    PersonRepository personRepository;

    @Autowired
    LikeRepository likeRepository;

    @Autowired
    FollowRepository followRepository;

    /**
     * Performing CREATE operation on Post
     * @param file
     * @param body
     * @params title
     * @return boolean( the method returns true if the post was created succesfully, and false if otherwise)
     * */

    public boolean createPost(MultipartFile file, Person user, String body, String title) {
        boolean result = false;

        try {

            Person person = personRepository.findPersonByEmail(user.getEmail()).get();
            Post post = new Post();

            post.setBody(body);
            post.setTitle(title);
            post.setPerson(person);

            String fileName = StringUtils.cleanPath(file.getOriginalFilename());

            if(!fileName.isEmpty()){
                //Extract the image extension
                String ext = fileName.substring(fileName.indexOf(".")+1);

                post.setImage("data:image/"+ext+";base64,"+ Base64.getEncoder().encodeToString(file.getBytes()));
            }

            post.setStatus("ACTIVE");

            Post ifExist = postRepository.findPostByTitleAndBody(title, body);

            if(ifExist == null) {
                postRepository.save(post);
                result = true;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }

    /**
     * GET by id operation on Post
     * @params postId
     * @return post object
     * */
    public List<PostMapper> getPost(Person currentUser) {
        List<PostMapper> posts = new ArrayList<>();
        try {
            //get all posts
            List<Post> postData = postRepository.findAllByStatusIsOrderById("ACTIVE");
            for (Post postEach:postData) {
                PostMapper post = new PostMapper();
                post.setId(postEach.getId());
                post.setTitle(postEach.getTitle());
                post.setBody(postEach.getBody());
                post.setImageName(postEach.getImage());
                post.setName(postEach.getPerson().getFullname());
                post.setUsername(postEach.getPerson().getUsername());
                //the total number of likes on this particular post
                List<Likes> numberOfLikes = likeRepository.findAllByPostId(postEach.getId());
                int likeCount = numberOfLikes.size();
                post.setNoLikes(likeCount);
                //the total number of comments on this particular post
                List<Comment> noOfComment = commentRepository.findAllByPostId(postEach.getId());
                int commentCount = noOfComment.size();
                post.setNoComments(commentCount);
                //return true if current user liked this post, else false
                List<Likes> postLiked = likeRepository.findAllByPostIdAndPersonId(postEach.getId(), currentUser.getId());
                if(postLiked.size() > 0){
                    post.setLikedPost(true);
                }
                posts.add(post);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return posts;
    }
    /**
     * GET operation on Post
     * @param postId
     * @return boolean(true for successful creation and false on failure to create)
     * */
    public Post getPostById(Long postId){
        Post post = null;
        String notFound = "User not found";
        try{
            post = postRepository.findPostByStatusAndId("ACTIVE",postId);
        }catch(Exception e){
            System.out.println("All was not well "+e.getMessage());
        }
        return post;
    }
    /**
     * CREATE operation on Comment
     * @param person
     * @param post
     * @return boolean(true for successful update and false on failure on post)
     * */
    public String updatePost(Person person, Post post) {
        String flag = "failed to upload post";
        try {
            Post data = postRepository.findById(post.getId()).get();
            //checking if the person updating the post is same as the owner of the post
            if(data.getPerson().getEmail().equals(person.getEmail())){
                if(post.getTitle() != null)
                    data.setTitle(post.getTitle());
                if(post.getBody() != null)
                    data.setBody(post.getBody());
                postRepository.save(data);
                flag = "successfully updated post";
            }else{
                flag = "user not authorized";
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
        return flag;
    }
    /**
     * DELETE operation on Post
     * @param postId
     * @param person
     * @return boolean
     * */
    public String deletePost(Long postId, Person person){
        String status =  "failed to delete post";
        try {
            Person person1 = personRepository.findPersonByEmail(person.getEmail()).get();
            Post post = postRepository.findPostByIdAndPersonId(postId, person1.getId());
            if(post != null){
                post.setStatus("INACTIVE");
                postRepository.save(post);
                status = "successfully deleted post";
            }else{
                status = "user not authorized";
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return status;
    }

    public List<Post> displayAllPostByFollowee(Long id, Person person) {
        Person person1 = personRepository.findPersonByEmail(person.getEmail()).get();
        List<Post> posts = new ArrayList<>();

        try {
            List<Follow> list = followRepository.findAllByFolloweeId(person1.getId());


            List<Long> listofIDs = list.stream().map(user -> user.getCurrentUserId())
                    .collect(Collectors.toList());

            if(listofIDs.contains(id)){
                posts = postRepository.findPostByPersonId(person1.getId());
            }

        } catch (Exception exception) {
            exception.printStackTrace();
        }

        return posts;
    }


}
