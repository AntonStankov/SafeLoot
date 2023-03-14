package com.example.SafeLoot.entity;

import com.example.SafeLoot.entity.helpClasses.Usage;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import kotlin.jvm.Transient;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.net.URL;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "password_storage")
public class PasswordStorage {
    @Column
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String passwordName;
    @Column(nullable = false)
    private String passwordEncr;

    @Column(nullable = false)
    private URL url;

//    @CollectionTable(name="usages")
    @JsonIgnore
    @OneToMany(mappedBy = "passwordStorage", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Usage> usage;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;
}
