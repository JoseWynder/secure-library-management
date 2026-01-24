package io.github.josewynder.libraryapi.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.UUID;

@Table
@Entity
@Data
public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "client_id")
    private String clientId;

    @Column(name = "client_secret")
    private String clientSecret;

    @Column(name = "redirect_uri")
    private String redirectUri;

    @Column
    private String scope;
}
