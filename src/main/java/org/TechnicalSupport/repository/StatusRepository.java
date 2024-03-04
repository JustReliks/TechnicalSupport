package org.TechnicalSupport.repository;

import org.TechnicalSupport.entity.Status;
import org.TechnicalSupport.entity.enums.RequestStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StatusRepository extends JpaRepository<Status, Long> {
    Optional<Status> findByStatusEnum(RequestStatus requestStatus);
}
