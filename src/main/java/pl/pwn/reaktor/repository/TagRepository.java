package pl.pwn.reaktor.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import pl.pwn.reaktor.model.Tag;

@Repository
public interface TagRepository extends JpaRepository<Tag, Long> {

}
