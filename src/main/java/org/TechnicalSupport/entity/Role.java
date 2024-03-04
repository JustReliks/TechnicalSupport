package org.TechnicalSupport.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.TechnicalSupport.entity.enums.UserRole;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "t_role")
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "name")
    private UserRole role;

}
