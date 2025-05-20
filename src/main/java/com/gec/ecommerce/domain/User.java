package com.gec.ecommerce.domain;

import jakarta.persistence.*;
import jakarta.persistence.GenerationType;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;


@Entity
@Table(name = "users", schema = "security")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

//    @NotBlank(message = "Nome completo é obrigatório")
//    @Size(max = 100, message = "Nome não pode exceder 100 caracteres")
    @Column(name = "fullname", nullable = false)
    private String fullname;

//    @NotBlank(message = "Nome de usuário é obrigatório")
//    @Size(max = 50, message = "Nome de usuário não pode exceder 50 caracteres")
    @Column( name = "username", nullable = false, unique = true)
    private String username;

//    @NotBlank(message = "Senha é obrigatória")
//    @Size(min = 12, max = 50, message = "Senha deve ter entre 12 a 50 caracteres")
    @Column(name = "password", nullable = false)
    private String password;

//    @NotBlank(message = "Email é obrigatório")
//    @Email(message = "Email precisa ser válido")
//    @Size(max = 100, message = "Email não pode exceder 100 caracteres")
    @Column(name = "email", nullable = false, unique = true)
    private String email;

//    @NotBlank(message = "Telefone é obrigatório")
//    @Size(min = 11, max = 11, message = "O número de telefone deve ter 11 dígitos")
    @Column( name = "phone", nullable = false, unique = true)
    private String phone;

    @Column( name = "createdAt")
    private LocalDateTime createdAt;

//    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
//    private List<Order> orders = new ArrayList<>();

//    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
//    private Cart cart;

//    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
//    private List<RabbitConnectionDetails.Address> addresses = new ArrayList<>();

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
    }
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
