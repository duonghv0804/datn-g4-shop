package com.example.websitebanquanao.controllers.admins;

import com.example.websitebanquanao.infrastructures.requests.LoginAdminRequest;
import com.example.websitebanquanao.infrastructures.requests.NhanVienRequest;
import com.example.websitebanquanao.services.GiamGiaService;
import com.example.websitebanquanao.services.KhuyenMaiService;
import com.example.websitebanquanao.services.NhanVienService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class LoginController {
    @Autowired
    private NhanVienService nhanVienService;
    @Autowired
    private HttpSession session;
    @Autowired
    private KhuyenMaiService khuyenMaiService;
    @Autowired
    private GiamGiaService giamGiaService;

    // view login
    @GetMapping("/login-admin")
    public String login(Model model) {
        model.addAttribute("nv", new LoginAdminRequest());
        // truoc khi vao login update lai khuyen mai, co the dung cronjob de lam
        khuyenMaiService.checkNgayKetThuc();
        giamGiaService.checkNgayKetThuc();
        return "admin/login/login";
    }

    // check login
    @PostMapping("/check-login-admin")
    public String checkLogin(@ModelAttribute("nv") LoginAdminRequest loginAdminRequest, Model model) {
        String email = loginAdminRequest.getEmail();
        String matKhau = loginAdminRequest.getMatKhau();

        session.removeAttribute("error"); // xoa loi truoc do trong session

        if (email.trim().isEmpty() || matKhau.trim().isEmpty()) {
            session.setAttribute("error", "Vui lòng nhập đầy đủ thông tin");
            return "admin/login/login";
        }

        NhanVienRequest nhanVienRequest = nhanVienService.checkLogin(email, matKhau);

        if (nhanVienRequest != null) {
            if (nhanVienRequest.getTrangThai() == 1) {
                session.setAttribute("error", "Tài khoản của bạn đã bị khóa. Liên hệ Admin để biết thêm");
                return "admin/login/login";

            } else {
                session.setAttribute("admin", nhanVienRequest);
                return "redirect:/admin";
            }
        } else {
            session.setAttribute("error", "Email hoặc mật khẩu không đúng");
            return "admin/login/login";
        }
    }

    // logout
    @GetMapping("/logout")
    public String logout() {
        // xoa tat ca session
        session.invalidate();
        return "redirect:/login-admin";
    }
}
