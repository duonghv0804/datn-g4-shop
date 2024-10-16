package com.example.websitebanquanao.controllers.admins;

import com.example.websitebanquanao.entities.KhachHang;
import com.example.websitebanquanao.infrastructures.requests.NhanVienRequest;
import com.example.websitebanquanao.infrastructures.responses.NhanVienResponse;
import com.example.websitebanquanao.repositories.KhachHangRepository;
import com.example.websitebanquanao.repositories.NhanVienRepository;
import com.example.websitebanquanao.services.NhanVienService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.UUID;

@Controller
@RequestMapping("/admin/nhan-vien")
public class NhanVienController {

    @Autowired
    private NhanVienService nhanVienService;

    @Autowired
    private NhanVienRequest nhanVienRequest;

    @Autowired
    private NhanVienRepository nhanVienRepository;

    private static final String redirect = "redirect:/admin/nhan-vien/index";


    @GetMapping("index")
    public String index(@RequestParam(name = "page", defaultValue = "1") int page, Model model, @ModelAttribute("successMessage") String successMessage) {
        Page<NhanVienResponse> nhanVienPage = nhanVienService.getPage(page, 5);
        model.addAttribute("nhanVienPage", nhanVienPage);
        model.addAttribute("nv", nhanVienRequest);
        model.addAttribute("successMessage", successMessage);
        model.addAttribute("view", "/views/admin/nhan-vien/index.jsp");
        return "admin/layout";
    }

    @GetMapping("delete/{id}")
    public String delete(@PathVariable("id") UUID id, RedirectAttributes redirectAttributes) {
        nhanVienService.delete(id);
        // Lưu thông báo xoá thành công vào session
        redirectAttributes.addFlashAttribute("successMessage", "Xoá nhân viên thành công");
        return redirect; // Sử dụng redirect để chuyển hướng đến trang danh sách
    }

    @PostMapping("store")
    public String store(@Valid @ModelAttribute("nv") NhanVienRequest nhanVienRequest, BindingResult result, Model model, RedirectAttributes redirectAttributes) {
        if (nhanVienRequest.validNull()) {
            redirectAttributes.addFlashAttribute("errorMessage", "Vui lòng điền đầy đủ thông tin.");
            return redirect;
        }
        if (nhanVienService.existsByEmail(nhanVienRequest.getEmail())) {
            redirectAttributes.addFlashAttribute("errorMessage", "Email này đã được sử dụng");
            return redirect;
        }

        if (!nhanVienService.isEmail(nhanVienRequest.getEmail())) {
            redirectAttributes.addFlashAttribute("errorMessage", "Email chưa đúng định dạng, ví dụ: abc@yahoo.com");
            return redirect;
        }
        if (!nhanVienService.isSoDienThoai(nhanVienRequest.getSoDienThoai())) {
            redirectAttributes.addFlashAttribute("errorMessage", "Số điện thoại không đúng định dạng.");
            return redirect;
        }

        if (!nhanVienService.isPasswordValid(nhanVienRequest.getMatKhau())) {
            redirectAttributes.addFlashAttribute("errorMessage", "Mật khẩu phải có ít nhất 6 ký tự và chứa ít nhất một chữ và một số");
            return redirect;
        }

        nhanVienService.add(nhanVienRequest);
        redirectAttributes.addFlashAttribute("successMessage", "Thêm nhân viên thành công");
        return redirect;
    }

    @PostMapping("update/{id}")
    public String update(@PathVariable("id") UUID id, @Valid @ModelAttribute("nv") NhanVienRequest nhanVienRequest, RedirectAttributes redirectAttributes) {
        if (nhanVienRequest.validUpdate()) {
            redirectAttributes.addFlashAttribute("errorMessage", "Vui lòng điền đầy đủ thông tin.");
            return redirect;
        }
        String currentEmail = nhanVienRepository.findById(id)
                .map(nhanVien -> nhanVien.getEmail())
                .orElse(null);
        if (nhanVienService.existsByEmail(nhanVienRequest.getEmail()) && currentEmail != null && !currentEmail.equals(nhanVienRequest.getEmail())) {
            redirectAttributes.addFlashAttribute("errorMessage", "Email này đã được sử dụng");
            return redirect;
        }

        if (!nhanVienService.isEmail(nhanVienRequest.getEmail())) {
            redirectAttributes.addFlashAttribute("errorMessage", "Email chưa đúng định dạng, ví dụ: abc@yahoo.com");
            return redirect;
        }
        if (!nhanVienService.isSoDienThoai(nhanVienRequest.getSoDienThoai())) {
            redirectAttributes.addFlashAttribute("errorMessage", "Số điện thoại không đúng định dạng.");
            return redirect;
        }
        nhanVienService.update(nhanVienRequest, id);
        redirectAttributes.addFlashAttribute("successMessage", "Cập nhật nhân viên thành công");
        return "redirect:/admin/nhan-vien/index";
    }

    @GetMapping("get/{id}")
    @ResponseBody
    public ResponseEntity<NhanVienResponse> getGiamGia(@PathVariable("id") UUID id) {
        return ResponseEntity.ok(nhanVienService.getById(id));
    }
}
