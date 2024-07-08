package authserver.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import authserver.models.User;


public interface UserRepository extends JpaRepository<User, Long>{
	
	Optional<User> findByUsername(String username);
}
