package com.example.SafeLoot.entity.helpClasses;

import com.example.SafeLoot.entity.PasswordStorage;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity(name = "usage")
public class Usage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long id;

    @Column
    private String ips;

    @Column
    private Date usageDate;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    private PasswordStorage passwordStorage;
}
