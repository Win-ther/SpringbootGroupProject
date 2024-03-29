package se.iths.springbootgroupproject.services;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import se.iths.springbootgroupproject.entities.User;
import se.iths.springbootgroupproject.repositories.UserRepository;

import java.util.List;

@Service
@Transactional
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // Needs queries in UserRepository
    // @Cacheable("firstName")
    public List<User> findByFirstName(String firstName) {
        return userRepository.findByFirstName(firstName);
    }

    // Needs queries in UserRepository
    // @Cacheable("lastName")
    public List<User> findByLastName(String lastName) {
        return userRepository.findByLastName(lastName);
    }

    @Cacheable("username")
    public User findByUserName(String userName) {
        return userRepository.findByUserName(userName).orElse(null);
    }

    @Cacheable("email")
    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @CacheEvict(value = {"email", "username"}, allEntries = true)
    public void updateEmail(String email, Long id) {
        userRepository.updateEmail(email, id);
    }

}