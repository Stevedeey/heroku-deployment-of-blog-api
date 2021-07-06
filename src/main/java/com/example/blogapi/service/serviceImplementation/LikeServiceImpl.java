package com.example.blogapi.service.serviceImplementation;

import com.example.blogapi.model.Likes;
import com.example.blogapi.model.Person;
import com.example.blogapi.model.Post;
import com.example.blogapi.repository.LikeRepository;
import com.example.blogapi.repository.PersonRepository;
import com.example.blogapi.repository.PostRepository;
import com.example.blogapi.service.LikeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LikeServiceImpl implements LikeService {
    @Autowired
    PostRepository postRepository;

    @Autowired
    LikeRepository likeRepository;

    @Autowired
    PersonRepository personRepository;

    /**
     * CREATE operation on Comment
     * @param person
     * @param postId
     * @param action
     * @return boolean
     * */
    public boolean likePost(Person person, Long postId, String action){
        boolean result = false;

        Post post = postRepository.findById(postId).get();

        try{

            Person person1 = personRepository.findPersonByEmail(person.getEmail()).get();

            Likes like = new Likes();
            like.setPerson(person1);
            like.setPost(post);

            List<Likes> likes = likeRepository.findAllByPostIdAndPersonId(postId, person1.getId());

            if(action.equals("1") && likes.size() == 0){
                likeRepository.save(like);
                System.out.println("save");
            }else{
                likeRepository.deleteLikesByPostAndPerson(post,person1);
                System.out.println("delete");
            }

            result = true;
        }catch (Exception e){
            e.printStackTrace();
        }

        return result;
    }
}
