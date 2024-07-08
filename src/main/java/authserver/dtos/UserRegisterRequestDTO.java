package authserver.dtos;

import java.util.List;

import authserver.models.Role;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class UserRegisterRequestDTO {
	private String username;
	private String password;
	private List<Role> roles;
}
