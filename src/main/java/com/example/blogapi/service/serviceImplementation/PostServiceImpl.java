package com.example.blogapi.service.serviceImplementation;

import com.example.blogapi.dto.PostMapper;
import com.example.blogapi.model.Comment;
import com.example.blogapi.model.Likes;
import com.example.blogapi.model.Person;
import com.example.blogapi.model.Post;
import com.example.blogapi.repository.CommentRepository;
import com.example.blogapi.repository.LikeRepository;
import com.example.blogapi.repository.PersonRepository;
import com.example.blogapi.repository.PostRepository;
import com.example.blogapi.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

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


    /**
     * CREATE operation on Post
     * @param file
     * @param body
     * @params title
     * @return boolean(true for successful creation and false on failure to create)
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
    public List<Post> getPostById(Long postId){
        List<Post> postList = new ArrayList<>();

        try{
            postList = postRepository.findPostById(postId);
        }catch(Exception e){
            System.out.println("Something went wrong1 "+e.getMessage());
        }

        return postList;
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
            if(data.getPerson().getEmail() == person.getEmail()){
                data.setTitle(post.getTitle());
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
     * @param personId
     * @return boolean
     * */
    public String deletePost(Long postId, Long personId){
        String status =  "failed to delete post";

        try {

            Post post = postRepository.findPostByIdAndPersonId(postId, personId);

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
}
