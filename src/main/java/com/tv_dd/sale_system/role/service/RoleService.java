package com.tv_dd.sale_system.role.service;

import java.util.List;
import com.tv_dd.sale_system.clientapplication.model.ClientApplication;
import com.tv_dd.sale_system.clientapplication.repository.ClientApplicationRepository;
import com.tv_dd.sale_system.role.dto.RoleRequest;
import com.tv_dd.sale_system.role.dto.RoleResponse;
import com.tv_dd.sale_system.role.model.Role;
import com.tv_dd.sale_system.role.repository.RoleRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class RoleService {

    private final RoleRepository roleRepository;
    private final ClientApplicationRepository clientApplicationRepository;

    public List<RoleResponse> getAll() {
        return roleRepository.findAll().stream()
                .map(RoleResponse::from)
                .toList();
    }

    public RoleResponse getById(Integer id) {
        return RoleResponse.from(getRoleById(id));
    }

    @Transactional
    public RoleResponse create(RoleRequest request) {
        String name = request.name().trim();
        if (roleRepository.findByName(name).isPresent()) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Role name already exists");
        }

        Role role = new Role();
        role.setName(name);
        role.setEnable(request.enable());
        role.setClientApplication(resolveClientApplication(request.clientApplicationId()));

        Role saved = roleRepository.save(role);

        log.info("Role created roleId={} name={}", saved.getId(), saved.getName());

        return RoleResponse.from(saved);
    }

    @Transactional
    public RoleResponse update(Integer id, RoleRequest request) {
        Role role = getRoleById(id);

        String name = request.name().trim();
        roleRepository.findByName(name).ifPresent(existing -> {
            if (!existing.getId().equals(id)) {
                throw new ResponseStatusException(HttpStatus.CONFLICT, "Role name already exists");
            }
        });

        role.setName(name);
        role.setEnable(request.enable());
        role.setClientApplication(resolveClientApplication(request.clientApplicationId()));

        Role saved = roleRepository.save(role);

        log.info("Role updated roleId={} name={}", saved.getId(), saved.getName());

        return RoleResponse.from(saved);
    }

    private Role getRoleById(Integer id) {
        return roleRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Role not found"));
    }

    private ClientApplication resolveClientApplication(Integer clientApplicationId) {
        if (clientApplicationId == null) {
            return null;
        }

        return clientApplicationRepository.findById(clientApplicationId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Client application not found"));
    }
}
