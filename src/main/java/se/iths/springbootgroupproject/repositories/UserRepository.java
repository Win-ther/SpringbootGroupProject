package se.iths.springbootgroupproject.repositories;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.ListCrudRepository;
import se.iths.springbootgroupproject.entities.User;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends ListCrudRepository<User, Long> {
    List<User> findByFirstName(String firstName);

    List<User> findByLastName(String lastName);

    Optional<User> findByUserName(String userName);

    Optional<User> findByGithubId(Integer githubId);

    Optional<User> findByEmail(String email);

    @Query("""
            update User u set u.email = ?1 where u.id = ?2
            """)
    @Modifying
    @Transactional
    void updateEmail(String email, Long id);

}
