package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import domain.TabuWord;

@Repository
public interface TabuWordRepository extends JpaRepository<TabuWord, Integer>{

}
