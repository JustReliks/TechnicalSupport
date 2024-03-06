package org.TechnicalSupport.repository;

import org.TechnicalSupport.entity.Request;
import org.TechnicalSupport.entity.Status;
import org.TechnicalSupport.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RequestRepository extends JpaRepository<Request, Long> {

    Page<Request> findAllByAuthor(User user, Pageable pageable);

    Page<Request> findAllByAuthorAndStatus(User user, Status status, Pageable pageable);


    Page<Request> findAllByNameContainingAndStatus(String name, Status status, Pageable pageable);

    Page<Request> findAllByNameContaining(String name, Pageable pageable);

    Page<Request> findAllByAuthorAndNameContainingAndStatus(User user, String name, Status status, Pageable pageable);

    Page<Request> findAllByStatus(Status status, Pageable pageable);

}
