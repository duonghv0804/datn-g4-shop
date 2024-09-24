package com.example.websitebanquanao.controllers.admins;

import com.example.websitebanquanao.entities.ThuongHieu;
import com.example.websitebanquanao.infrastructures.requests.LoaiRequest;
import com.example.websitebanquanao.infrastructures.requests.ThuongHieuRequest;
import com.example.websitebanquanao.infrastructures.responses.ThuongHieuResponse;
import com.example.websitebanquanao.repositories.ThuongHieuRepository;
import com.example.websitebanquanao.services.ThuongHieuService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/admin/thuong-hieu")
public class ThuongHieuController {
    @Autowired
    private ThuongHieuService thuongHieuService;

    @Autowired
    private ThuongHieuRequest thuongHieuRequest;

    private static final String redirect = "redirect:/admin/thuong-hieu/index";
    @Autowired
    private ThuongHieuRepository thuongHieuRepository;

    @GetMapping("index")
    public String index(@RequestParam(name = "page", defaultValue = "1") int page, Model model, @ModelAttribute("successMessage") String successMessage) {
        Page<ThuongHieuResponse> thuongHieuPage = thuongHieuService.getPage(page, 5);
        model.addAttribute("thuongHieuPage", thuongHieuPage);
        model.addAttribute("th", thuongHieuRequest);
        model.addAttribute("successMessage", successMessage);
        model.addAttribute("view", "/views/admin/thuong-hieu/index.jsp");
        return "admin/layout";
    }

    @GetMapping("delete/{id}")
    public String delete(@PathVariable("id") Integer id, RedirectAttributes redirectAttributes) {
        thuongHieuService.delete(id);
        redirectAttributes.addFlashAttribute("successMessage", "Xoá thương hiệu thành công");
        return redirect;
    }

    @PostMapping("store")
    public String store(@Valid @ModelAttribute("th") ThuongHieuRequest thuongHieuRequest, BindingResult result, Model model, RedirectAttributes redirectAttributes) {

        String ten = thuongHieuRequest.getTen().trim();

        if (ten.isEmpty() || !ten.equals(thuongHieuRequest.getTen())) {
            redirectAttributes.addFlashAttribute("errorMessage", "Tên không hợp lệ (không được có khoảng trắng ở đầu )");
            return redirect; // Replace with your actual redirect path
        }

        if (!thuongHieuService.isTenValid(thuongHieuRequest.getTen())) {
            redirectAttributes.addFlashAttribute("errorMessage", "Tên toàn khoảng trắng không hợp lệ");
            return redirect;
        }

        if (result.hasErrors()) {
            model.addAttribute("view", "/views/admin/thuong-hieu/index.jsp");
            return "admin/layout";
        }

        if (thuongHieuRepository.existsByTen(thuongHieuRequest.getTen())) {
            redirectAttributes.addFlashAttribute("errorMessage", "Thương hiệu đã tồn tại");
            return redirect;
        }


        thuongHieuService.add(thuongHieuRequest);
        // Lưu thông báo thêm thành công vào session
        redirectAttributes.addFlashAttribute("successMessage", "Thêm thành công");
        return redirect;
    }

    @PostMapping("update/{id}")
    public String update(@PathVariable("id") Integer id,
                         @Valid @ModelAttribute("th") ThuongHieuRequest thuongHieuRequest,
                         BindingResult result,
                         Model model,
                         RedirectAttributes redirectAttributes) {
        ThuongHieu existingThuongHieu = thuongHieuService.findById(id);
        if (existingThuongHieu == null) {
            redirectAttributes.addFlashAttribute("errorMessage", "Thương hiệu không tồn tại");
            return redirect;
        }
        String updatedTen = thuongHieuRequest.getTen().trim();
        if (updatedTen.isEmpty() || !updatedTen.equals(thuongHieuRequest.getTen().trim())) {
            redirectAttributes.addFlashAttribute("errorMessage", "Tên không hợp lệ (không được có khoảng trắng ở đầu)");
            return redirect;
        }
        if (!thuongHieuService.isTenValid(updatedTen)) {
            redirectAttributes.addFlashAttribute("errorMessage", "Tên toàn khoảng trắng không hợp lệ");
            return redirect;
        }
        if (thuongHieuRepository.existsByTen(updatedTen) && !updatedTen.equals(existingThuongHieu.getTen())) {
            redirectAttributes.addFlashAttribute("errorMessage", "Tên thương hiệu đã tồn tại");
            return redirect;
        }
        if (updatedTen.equals(existingThuongHieu.getTen())) {
            thuongHieuRequest.setTen(existingThuongHieu.getTen());
        }

        // Thực hiện cập nhật
        thuongHieuService.update(thuongHieuRequest, id);
        redirectAttributes.addFlashAttribute("successMessage", "Cập nhật thành công");
        return redirect;
    }


    @GetMapping("get/{id}")
    @ResponseBody
    public ResponseEntity<ThuongHieuResponse> getThuongHieu(@PathVariable("id") Integer id) {
        return ResponseEntity.ok(thuongHieuService.getById(id));
    }

}
