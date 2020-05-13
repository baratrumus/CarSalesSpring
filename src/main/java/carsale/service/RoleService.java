package carsale.service;

import carsale.dao.RoleRepository;
import carsale.models.Roles;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleService {
    private final RoleRepository roleRepository;

    public RoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    public Roles save(Roles role) {
        return roleRepository.save(role);
    }

    public Roles getRoleById(int id) {
        return roleRepository.getById(id);
    }

    public List<Roles> getAllRoles() {
        return roleRepository.getAll();
    }
}
