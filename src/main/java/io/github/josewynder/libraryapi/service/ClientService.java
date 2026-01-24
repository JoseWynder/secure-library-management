package io.github.josewynder.libraryapi.service;

import io.github.josewynder.libraryapi.model.Client;
import io.github.josewynder.libraryapi.repository.ClientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ClientService {

    private final ClientRepository clientRepository;
    private final PasswordEncoder passwordEncoder;

    public void save(Client client) {
        String clientSecretEncrypted = passwordEncoder.encode(client.getClientSecret());
        client.setClientSecret(clientSecretEncrypted);
        clientRepository.save(client);
    }

    public Client findByClientId(String clientId) {
        return clientRepository.findByClientId(clientId);
    }
}
