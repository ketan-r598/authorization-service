package authserver.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import authserver.models.Role;


public interface RoleRepository extends JpaRepository<Role, Long> {

	Optional<Role> findById(Long id);
}
