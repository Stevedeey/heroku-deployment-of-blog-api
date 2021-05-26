package com.example.blogapi.service.serviceImplementation;

import com.example.blogapi.dto.PersonMapper;
import com.example.blogapi.model.Person;
import com.example.blogapi.repository.PersonRepository;
import com.example.blogapi.service.PersonService;
import com.example.blogapi.utility.Encryption;
import com.example.blogapi.utility.PersonValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class PersonImpl implements PersonService {
    @Autowired
    PersonRepository personRepository;

    @Autowired
   private static PersonValidator validator;


    public String createUser(Person person) {

        String flag = "failed";
        //validating users input
        PersonValidator userValidator = new PersonValidator();
        String message = userValidator.validateRegistration(person.getEmail(),
                person.getUsername(),person.getPassword(), person.getFullname());
        if(!message.equals("Successful validation")) return message;

        try {
            Optional check = personRepository.findPersonByEmail(person.getEmail());
            if(check.isEmpty()) {
                person.setPassword(Encryption.encryptPassword(person.getPassword()));
                personRepository.save(person);
                flag = "successfully created";
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return flag;

    }

    /**
     * GET operation on Person
     * @param email
     * @param password
     * @return Person
     * */
    public String loginUser(String email, String password){
        String message = "";
        try {
            Optional<Person> person = personRepository.findPersonByEmail(email);
            if(person.isPresent()){
                if(person.get().getIsDeleted()==1) return "user not found";
                if(!password.equals(Encryption.decryptPassword(person.get().getPassword())))
                    message = "password incorrect";
                else
                    message = "successful";
            }else{
                message = "email not found";
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return message;
    }

    /**
     * GET operation on Person
     * @return Person(all)
     * */
    public List<PersonMapper> getUsers(){
        List<PersonMapper> persons = new ArrayList<>();
        try {
            List<Person> personList = personRepository.findAll();
            personList.forEach(person->{
                PersonMapper personMapper = new PersonMapper();
                personMapper.setFullname(person.getFullname());
                personMapper.setUsername(person.getUsername());
                personMapper.setId(person.getId());
                personMapper.setEmail(person.getEmail());
                if(person.getProfilePicture()== null) personMapper.setProfilePicture("No image");
                else personMapper.setProfilePicture(person.getProfilePicture());
                persons.add(personMapper);
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
        return persons;
    }

    /**
     * GET operation on Person
     * @return Person
     * */
    public PersonMapper getUserById(Long id){
        PersonMapper personMapper = new PersonMapper();
        try {
            Person person = personRepository.findById(id).get();
            personMapper.setId(person.getId());
            personMapper.setEmail(person.getEmail());
            personMapper.setUsername(person.getUsername());
            personMapper.setFullname(person.getFullname());
            if(person.getProfilePicture() == null) personMapper.setProfilePicture("No image");
            else personMapper.setProfilePicture(person.getProfilePicture());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return personMapper;
    }

    public String updateImage(MultipartFile file, Long personId, Person user){
        String flag = "failed";
        try {
            String fileName = StringUtils.cleanPath(file.getOriginalFilename());
            //Extract the image extension
            String ext = fileName.substring(fileName.indexOf(".")+1);
            if(fileName.isEmpty()){
                return "Please select an image";
            }
            //find person by id
            Person person = personRepository.findPersonByEmail(user.getEmail()).get();

            if(person.getId() == personId){
                //set person image
                person.setProfilePicture("data:image/"+ext+";base64,"+Base64.getEncoder().encodeToString(file.getBytes()));
                personRepository.save(person);
                flag = "successfully uploaded image";
            }else{
                flag = "user not authorized";
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return flag;
    }

    public boolean deleteUser(Long personId, Person person){
        boolean flag = false;
        Date date = new Date();
        SimpleDateFormat DateFor = new SimpleDateFormat("dd-M-yyyy hh:mm:ss");
        try {
            Person user = personRepository.findPersonByEmail(person.getEmail()).get();
            if(user.getId() == personId){
                Calendar calendar = Calendar.getInstance();
                calendar.add(Calendar.MINUTE, 5);
                String presentDate = DateFor.format(calendar.getTime());
                user.setPersonDeactivated(1);
                user.setRemoveDate(presentDate);
                personRepository.save(user);
                flag = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return flag;
    }
  public String reverseDelete(Person pers, Long personId){
      String flag = "";
      try {
          Person person = personRepository.findPersonByEmail(pers.getEmail()).get();
          if(person.getId() != personId) flag = "user not authorized"; // if person that wants to delete is not in session
          else{
              if(person.getPersonDeactivated() == 1){ //checking if the user has initiated the delete action
                  person.setPersonDeactivated(0);

                  personRepository.save(person);
                  flag = "Delete action successfully reversed";
              }
          }
      } catch (Exception e) {
          e.printStackTrace();
      }
      return flag;
  }
    public void deactivatedPersonScheduler() {
        List<Person> persons = personRepository.findAllByPersonDeactivated(1);
        Date date = new Date();
        SimpleDateFormat DateFor = new SimpleDateFormat("dd-M-yyyy hh:mm:ss");
        System.out.println("scheduler working");
        persons.forEach(person -> {
            String presentDate = DateFor.format(date);
            String deleteDate = person.getRemoveDate();
            int actionDelete = presentDate.compareTo(deleteDate);
            if (actionDelete > 0 || actionDelete == 0) {
                System.out.println("user finally deleted");
                person.setIsDeleted(1);
                personRepository.save(person);
            }
        });
        System.out.println("The list of scheduled for deletion"+persons);
    }

}
