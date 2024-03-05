package org.TechnicalSupport.repository;

import org.TechnicalSupport.entity.Request;
import org.TechnicalSupport.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RequestRepository extends JpaRepository<Request, Long> {

    Optional<Request> findByName(String name);

    Page<Request> findAllByAuthor(User user, Pageable pageable);

}
