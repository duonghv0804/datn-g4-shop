package com.example.websitebanquanao.controllers.admins;

import com.example.websitebanquanao.entities.Loai;
import com.example.websitebanquanao.entities.ThuongHieu;
import com.example.websitebanquanao.infrastructures.requests.LoaiRequest;
import com.example.websitebanquanao.infrastructures.requests.ThuongHieuRequest;
import com.example.websitebanquanao.infrastructures.responses.LoaiResponse;
import com.example.websitebanquanao.infrastructures.responses.ThuongHieuResponse;
import com.example.websitebanquanao.repositories.LoaiRepository;
import com.example.websitebanquanao.repositories.ThuongHieuRepository;
import com.example.websitebanquanao.services.LoaiService;
import com.example.websitebanquanao.services.ThuongHieuService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Date;

@Controller
@RequestMapping("/admin/loai")
public class LoaiController {
    @Autowired
    private LoaiService loaiService;

    @Autowired
    private LoaiRequest loaiRequest;

    private static final String redirect = "redirect:/admin/loai/index";
    @Autowired
    private LoaiRepository loaiRepository;

    @GetMapping("index")
    public String index(@RequestParam(name = "page", defaultValue = "1") int page, Model model, @ModelAttribute("successMessage") String successMessage) {
        Page<LoaiResponse> loaiPage = loaiService.getPage(page, 5);
        model.addAttribute("loaiPage", loaiPage);
        model.addAttribute("th", loaiRequest);
        model.addAttribute("successMessage", successMessage);
        model.addAttribute("view", "/views/admin/loai/index.jsp");
        return "admin/layout";
    }

    @GetMapping("delete/{id}")
    public String delete(@PathVariable("id") Integer id, RedirectAttributes redirectAttributes) {
        loaiService.delete(id);
        redirectAttributes.addFlashAttribute("successMessage", "Xoá loại thành công");
        return redirect;
    }

    @PostMapping("store")
    public String store(@Valid @ModelAttribute("th") LoaiRequest loaiRequest, BindingResult result, Model model, RedirectAttributes redirectAttributes) {

        String ten = loaiRequest.getTen().trim();

        if (ten.isEmpty() || !ten.equals(loaiRequest.getTen())) {
            redirectAttributes.addFlashAttribute("errorMessage", "Tên không hợp lệ (không được có khoảng trắng ở đầu )");
            return redirect; // Replace with your actual redirect path
        }

        if (!loaiService.isTenValid(loaiRequest.getTen())) {
            redirectAttributes.addFlashAttribute("errorMessage", "Tên toàn khoảng trắng không hợp lệ");
            return redirect;
        }

        if (result.hasErrors()) {
            model.addAttribute("view", "/views/admin/loai/index.jsp");
            return "admin/layout";
        }

        if (loaiRepository.existsByTen(loaiRequest.getTen())) {
            redirectAttributes.addFlashAttribute("errorMessage", "Thêm mới không thành công - Loại đã tồn tại");
            return redirect;
        }

        loaiService.add(loaiRequest);
        // Lưu thông báo thêm thành công vào session
        redirectAttributes.addFlashAttribute("successMessage", "Thêm loại thành công");
        return redirect;
    }

    @PostMapping("update/{id}")
    public String update(@PathVariable("id") Integer id,
                         @Valid @ModelAttribute("th") LoaiRequest loaiRequest,
                         BindingResult result,
                         Model model,
                         RedirectAttributes redirectAttributes) {
        Loai existingThuongHieu = loaiService.findById(id);
        if (existingThuongHieu == null) {
            redirectAttributes.addFlashAttribute("errorMessage", "Loại không tồn tại");
            return redirect;
        }

        String updatedTen = loaiRequest.getTen().trim();
        if (updatedTen.isEmpty() || !updatedTen.equals(loaiRequest.getTen().trim())) {
            redirectAttributes.addFlashAttribute("errorMessage", "Tên không hợp lệ (không được có khoảng trắng ở đầu)");
            return redirect;
        }

        if (!loaiService.isTenValid(updatedTen)) {
            redirectAttributes.addFlashAttribute("errorMessage", "Tên toàn khoảng trắng không hợp lệ");
            return redirect;
        }
        if (result.hasErrors()) {
            model.addAttribute("view", "/views/admin/loai/index.jsp");
            return "admin/layout";
        }

        if (loaiRepository.existsByTen(updatedTen) && !updatedTen.equals(existingThuongHieu.getTen())) {
            redirectAttributes.addFlashAttribute("errorMessage", "Cập nhật không thành công - Tên Loại đã tồn tại");
            return redirect;
        }

        if (updatedTen.equals(existingThuongHieu.getTen())) {
            loaiRequest.setTen(existingThuongHieu.getTen());
        }

        // Thực hiện cập nhật
        loaiService.update(loaiRequest, id);
        redirectAttributes.addFlashAttribute("successMessage", "Cập nhật loại thành công");
        return redirect;
    }


    @GetMapping("get/{id}")
    @ResponseBody
    public ResponseEntity<LoaiResponse> getLoai(@PathVariable("id") Integer id) {
        return ResponseEntity.ok(loaiService.getById(id));
    }
}
