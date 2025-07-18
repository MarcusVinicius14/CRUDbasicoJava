package teste1.api.modulos.auth.user.DTO;

import teste1.api.modulos.auth.user.UserRole;

public record RegisterDTO (String name, String email, String password, UserRole role ){
}
