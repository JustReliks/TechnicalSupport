package org.TechnicalSupport.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.TechnicalSupport.entity.enums.RequestStatus;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "t_role")
public class Status {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "name")
    private RequestStatus status;

}
