package com.abw.ecommerce.UserService.model.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.Accessors;
import org.hibernate.annotations.NaturalId;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@With
@Accessors(fluent = true)
@Entity
@Table(name = "roles")
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", unique = true, nullable = false, updatable = false)
    private Long id;

    @Enumerated(EnumType.STRING)
    @NaturalId
    @Column(name = "roleName", length = 60)
    private RoleName name;

}
