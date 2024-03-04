package org.TechnicalSupport.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@Builder
@AllArgsConstructor
@Entity
@Table(name = "t_user")
@NamedEntityGraph(name = "User.roles",
        attributeNodes = @NamedAttributeNode("roles")
)
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;

    private String password;


    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "t_user_role",
            joinColumns = {@JoinColumn(name = "user_id")},
            inverseJoinColumns = {@JoinColumn(name = "role_id")})
    @Builder.Default
    private Set<Role> roles = new HashSet<>();

}
