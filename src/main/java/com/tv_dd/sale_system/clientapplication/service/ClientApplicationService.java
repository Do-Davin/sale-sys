package com.tv_dd.sale_system.clientapplication.service;

import java.util.List;
import com.tv_dd.sale_system.clientapplication.dto.ClientApplicationRequest;
import com.tv_dd.sale_system.clientapplication.model.ClientApplication;
import com.tv_dd.sale_system.clientapplication.repository.ClientApplicationRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class ClientApplicationService {
    private final ClientApplicationRepository clientApplicationRepository;

    public ClientApplicationService(ClientApplicationRepository clientApplicationRepository) {
        this.clientApplicationRepository = clientApplicationRepository;
    }

    public List<ClientApplication> getAll() {
        return clientApplicationRepository.findAll();
    }

    public ClientApplication getById(Integer id) {
        return clientApplicationRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Client application not found"));
    }

    public ClientApplication create(ClientApplicationRequest request) {
        String name = request.name().trim();
        if (clientApplicationRepository.findByName(name).isPresent()) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Client application name already exists");
        }

        ClientApplication clientApplication = new ClientApplication();
        clientApplication.setName(name);
        clientApplication.setEnable(request.enable());

        ClientApplication saved = clientApplicationRepository.save(clientApplication);

        log.info("Client application created clientApplicationId={} name={}", saved.getId(), saved.getName());

        return saved;
    }

    public ClientApplication update(Integer id, ClientApplicationRequest request) {
        ClientApplication clientApplication = getById(id);

        String name = request.name().trim();
        clientApplicationRepository.findByName(name).ifPresent(existing -> {
            if (!existing.getId().equals(id)) {
                throw new ResponseStatusException(HttpStatus.CONFLICT, "Client application name already exists");
            }
        });

        clientApplication.setName(name);
        clientApplication.setEnable(request.enable());

        ClientApplication saved = clientApplicationRepository.save(clientApplication);

        log.info("Client application updated clientApplicationId={} name={}", saved.getId(), saved.getName());

        return saved;
    }
}
