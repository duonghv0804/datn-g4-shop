package com.example.websitebanquanao.infrastructures.requests;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DangNhapUserRequest {
    @NotEmpty(message = "Tên không được trống")
    private String email;

    @NotEmpty(message = "Email không được trống")
    @Email(message = "Email không hợp lệ")
    private String matKhau;
}
