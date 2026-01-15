package io.github.josewynder.libraryapi.model;

import io.hypersistence.utils.hibernate.type.array.ListArrayType;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.Type;

import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "users")
@Data
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column
    private String login;

    @Column
    private String password;

    @Column
    private String email;


    @Type(ListArrayType.class) // <- deprecated
    // Use @JdbcTypeCode(SqlTypes.ARRAY)
    @Column(name = "roles", columnDefinition = "varchar[]")
    private List<String> roles;
}
