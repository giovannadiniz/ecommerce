package com.gec.ecommerce.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class UserRequest {
    @NotBlank(message = "Nome completo é obrigatório")
    @Size(max = 100, message = "Nome não pode exceder 100 caracteres")
    private String fullname;

    @NotBlank(message = "Nome de usuário é obrigatório")
    @Size(max = 50, message = "Nome de usuário não pode exceder 50 caracteres")
    private String username;

    @NotBlank(message = "Senha é obrigatória")
    @Size(min = 12, max = 50, message = "Senha deve ter entre 12 a 50 caracteres")
    private String password;

    @NotBlank(message = "Email é obrigatório")
    @Email(message = "Email precisa ser válido")
    @Size(max = 100, message = "Email não pode exceder 100 caracteres")
    private String email;

    @NotBlank(message = "Telefone é obrigatório")
    @Size(min = 11, max = 11, message = "O número de telefone deve ter 11 dígitos")
    private String phone;
}

