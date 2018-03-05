package pl.pwn.reaktor.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import pl.pwn.reaktor.model.User;

@Repository
public interface UserRepsitory extends JpaRepository<User, Long >{

}
