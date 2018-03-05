package pl.pwn.reaktor.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import pl.pwn.reaktor.model.Article;

@Repository
public interface ArticleRepository extends JpaRepository<Article, Long>{
	
	//@Query("SELECT a FROM Article a ORDER BY id decs")
	
	Article findFirstByOrderByIdDesc();

}
