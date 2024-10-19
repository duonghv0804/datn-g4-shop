package com.example.websitebanquanao.controllers.admins;

import com.example.websitebanquanao.entities.KhachHang;
import com.example.websitebanquanao.infrastructures.requests.KhachHangRequest;
import com.example.websitebanquanao.infrastructures.responses.KhachHangResponse;
import com.example.websitebanquanao.repositories.KhachHangRepository;
import com.example.websitebanquanao.services.KhachHangService;
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
@RequestMapping("/admin/khach-hang")
public class KhachHangController {
    @Autowired
    private KhachHangService khachHangService;

    @Autowired
    private KhachHangRequest khachHangRequest;

    private static final String redirect = "redirect:/admin/khach-hang/index";
    @Autowired
    private KhachHangRepository khachHangRepository;

    @GetMapping("index")
    public String index(@RequestParam(name = "page", defaultValue = "1") int page, Model model, @ModelAttribute("successMessage") String successMessage) {
        Page<KhachHangResponse> khachHangPage = khachHangService.getPage(page, 5);
        model.addAttribute("khachHangPage", khachHangPage);
        model.addAttribute("kh", khachHangRequest);
        model.addAttribute("successMessage", successMessage);
        model.addAttribute("view", "/views/admin/khach-hang/index.jsp");
        return "admin/layout";
    }

    @GetMapping("delete/{id}")
    public String delete(@PathVariable("id") UUID id, RedirectAttributes redirectAttributes) {
        khachHangService.delete(id);
        redirectAttributes.addFlashAttribute("successMessage", "Xoá khách hàng thành công");
        return redirect;
    }

    @PostMapping("store")
    public String store(@Valid @ModelAttribute("kh") KhachHangRequest khachHangRequest, BindingResult result, Model model, RedirectAttributes redirectAttributes) {
        if (khachHangRequest.validNull()) {
            redirectAttributes.addFlashAttribute("errorMessage", "Vui lòng điền đầy đủ thông tin.");
            return redirect;
        }
        if (khachHangService.existsByEmail(khachHangRequest.getEmail())) {
            redirectAttributes.addFlashAttribute("errorMessage", "Email này đã được sử dụng");
            return redirect;
        }
        if (!khachHangService.isEmail(khachHangRequest.getEmail())) {
            redirectAttributes.addFlashAttribute("errorMessage", "Email chưa đúng định dạng, ví dụ: abc@yahoo.com");
            return redirect;
        }
        if (!khachHangService.isSoDienThoai(khachHangRequest.getSoDienThoai())) {
            redirectAttributes.addFlashAttribute("errorMessage", "Số điện thoại không đúng định dạng.");
            return redirect;
        }

        if (!khachHangService.isPasswordValid(khachHangRequest.getMatKhau())) {
            redirectAttributes.addFlashAttribute("errorMessage", "Mật khẩu phải có ít nhất 6 ký tự và chứa ít nhất một chữ và một số");
            return redirect;
        }

        khachHangService.add(khachHangRequest);
        redirectAttributes.addFlashAttribute("successMessage", "Thêm khách hàng thành công");
        return redirect;
    }

    @PostMapping("update/{id}")
    public String update(@PathVariable("id") UUID id, @Valid @ModelAttribute("kh") KhachHangRequest khachHangRequest, BindingResult result, Model model, RedirectAttributes redirectAttributes) {
        if (khachHangRequest.validUpdate()) {
            redirectAttributes.addFlashAttribute("errorMessage", "Vui lòng điền đầy đủ thông tin.");
            return redirect;
        }
        String currentEmail = khachHangRepository.findById(id)
                .map(khachHang -> khachHang.getEmail())
                .orElse(null);
        if (khachHangService.existsByEmail(khachHangRequest.getEmail()) && currentEmail != null && !currentEmail.equals(khachHangRequest.getEmail())) {
            redirectAttributes.addFlashAttribute("errorMessage", "Email này đã được sử dụng");
            return redirect;
        }
        if (!khachHangService.isEmail(khachHangRequest.getEmail())) {
            redirectAttributes.addFlashAttribute("errorMessage", "Email chưa đúng định dạng, ví dụ: abc@yahoo.com");
            return redirect;
        }
        if (!khachHangService.isSoDienThoai(khachHangRequest.getSoDienThoai())) {
            redirectAttributes.addFlashAttribute("errorMessage", "Số điện thoại không đúng định dạng.");
            return redirect;
        }

        khachHangService.update(khachHangRequest, id);
        redirectAttributes.addFlashAttribute("successMessage", "Cập nhật khách hàng thành công");
        return "redirect:/admin/khach-hang/index";
    }

    @GetMapping("get/{id}")
    @ResponseBody
    public ResponseEntity<KhachHangResponse> getKhachHang(@PathVariable("id") UUID id) {
        return ResponseEntity.ok(khachHangService.getById(id));
    }
}
