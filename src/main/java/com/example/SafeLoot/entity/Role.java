package com.example.SafeLoot.entity;


import com.example.SafeLoot.entity.User;
import com.example.SafeLoot.entity.helpClasses.Rights;
import com.example.SafeLoot.entity.helpClasses.Roles;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "roles")
public class Role {

    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Roles roleName;

    @Column(name = "rights", nullable = true)
    @Enumerated(EnumType.STRING)
    private List<Rights> rights;

    @ManyToMany(mappedBy = "roles", fetch = FetchType.LAZY)
    private List<User> users = new ArrayList<>();

    public Role(Roles roleName) {
        this.roleName = roleName;
    }
}
