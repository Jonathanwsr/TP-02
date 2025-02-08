package TopReports.Dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class UsuarioDTO {



    private Long id;

    @NotBlank(message = "O nome de usuário não pode ser nulo ou vazio.")
    @Size(max = 20, message = "O nome de usuário deve ter no máximo 20 caracteres.")
    private String userName;

    @NotBlank(message = "A senha não pode ser nula ou vazia.")
    @Size(min = 5, message = " A senha deve ter no mínimo 5 caracteres.")
    private String password;

   // @NotBlank(message = "O role não pode ser nulo ou vazio.")
    private String role;




    public UsuarioDTO( ) {
        this.id = id;
        this.userName= userName;
        this.password= password;
        this.role= role;


    }



    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}


/*

public UsuarioDTO(Long id, String userName, String password, String role ) {
        this.id = id;
        this.userName= userName;
        this.password= password;
        this.role= role;


    }
 */