package com.example.blogapi;

import com.example.blogapi.exceptions.PersonRegistrationException;
import com.example.blogapi.model.Person;
import com.example.blogapi.model.Post;
import com.example.blogapi.repository.PersonRepository;
import com.example.blogapi.service.PersonService;
import com.example.blogapi.service.serviceImplementation.PersonImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class PersonServiceTest {
    @Mock
    PersonRepository personRepository;

   @InjectMocks
   PersonImpl personService;

   @Test
   public void shouldSavePerson() {
       //public Person(Long id, String fullname, String email, String password, String username, int personDeactivated, int isDeleted, String removeDate, String profilePicture, List< Post > post) {
       final Person user = new Person(null, "Olaleye Oluwatosin", "stvolutosin@gmail.com", "1234567890", "stevedeey", 0, 0, null, "none", null);

       given(personRepository.findPersonByEmail(user.getEmail())).willReturn(Optional.empty());

       given(personRepository.save(user)).willAnswer(invocation -> invocation.getArgument(0));

       String savedUser = personService.createUser(user);

       assertThat(savedUser).isNotNull();

       verify(personRepository).save(any(Person.class));

   }

//       @Test
//       void shouldThrowErrorWhenSaveUserWithExistingEmail() {
//           final Person user = new Person(1L, "Olaleye Oluwatosin", "stvolutosin69@gmail.com", "1234567890", "stevedeey", 0, 0, "null", "none", null);
//           given(personRepository.findPersonByEmail(user.getEmail())).willReturn(Optional.of(user));
//            var chech = personRepository.findPersonByEmail(user.getEmail()).get();
//           System.out.println(chech);
//
//           assertThrows(PersonRegistrationException.class,() -> {
//               personService.createUser(chech);
//           });
//
//           verify(personRepository, never()).save(any(Person.class));
//       }

//    @Test
//    void updateUser() {
//        final Person user = new Person(1L, "Olaleye Oluwatosin", "stvolutosin69@gmail.com", "1234567890", "stevedeey", 0, 0, "null", "none", null);
//
//        given(personRepository.save(user)).willReturn(user);
//
//        final Person expected = personRepository.(user);
//
//        assertThat(expected).isNotNull();
//
//        verify(userRepository).save(any(User.class));
//    }
   }

