package com.example.blogapi.service.serviceImplementation;

import com.example.blogapi.dto.FavouriteMapper;
import com.example.blogapi.model.Favourite;
import com.example.blogapi.model.Person;
import com.example.blogapi.model.Post;
import com.example.blogapi.repository.FavouriteRepository;
import com.example.blogapi.repository.PersonRepository;
import com.example.blogapi.repository.PostRepository;
import com.example.blogapi.service.FavouriteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class FavouriteServiceImpl implements  FavouriteService {

    @Autowired
    FavouriteRepository favouriteRepository;

    @Autowired
    PersonRepository personRepository;

    @Autowired
    PostRepository postRepository;

    public List<FavouriteMapper> getFavouritesByUser(Person person){
        List<FavouriteMapper> favourites = new ArrayList<>();

        try {
            Person user = personRepository.findPersonByEmail(person.getEmail()).get();
            List<Favourite> favouritesData = favouriteRepository.findFavouriteByCurrentUserId(user.getId());

            favouritesData.forEach(favourite -> {

                FavouriteMapper favouriteMapper = new FavouriteMapper();

                Post post = postRepository.findById(favourite.getPostId()).get();

                favouriteMapper.setPostId(favourite.getPostId());
                favouriteMapper.setPostBody(post.getBody());
                favouriteMapper.setPostTitle(post.getTitle());

                favourites.add(favouriteMapper);
            });
        } catch (Exception e) {
            e.printStackTrace();
        }

        return  favourites;
    }

    public boolean saveFavourite(Person person, Long postId){

        boolean flag = false;

        Person person1 = personRepository.findPersonByEmail(person.getEmail()).get();

        Favourite favourite = new Favourite();
        favourite.setCurrentUserId(person1.getId());
        favourite.setPostId(postId);

        try {

            favouriteRepository.save(favourite);
            flag = true;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return flag;
    }

}
