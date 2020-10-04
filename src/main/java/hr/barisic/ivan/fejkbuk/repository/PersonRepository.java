package hr.barisic.ivan.fejkbuk.repository;

import hr.barisic.ivan.fejkbuk.entity.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonRepository extends JpaRepository<Person, Long> {
}
