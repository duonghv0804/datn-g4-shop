package com.example.websitebanquanao.infrastructures.requests;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DangKyUserRequest {

    @NotEmpty(message = "Tên không được để trống")
    private String hoTen;

    @NotEmpty(message = "Email không được để trống")
    @Email(message = "Email không đúng định dạng")
    private String emailDK;

    @NotEmpty(message = "Mật Khẩu không được để trống")
    @Pattern(regexp = "^(?=.*[a-zA-Z])(?=.*[0-9]).{6,}$", message = "Mật khẩu phải có ít nhất 6 ký tự và chứa ít nhất một chữ và một số")
    private String matKhauDK;
}
