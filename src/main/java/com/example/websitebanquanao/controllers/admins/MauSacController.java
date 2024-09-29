package com.example.websitebanquanao.controllers.admins;

import com.example.websitebanquanao.entities.MauSac;
import com.example.websitebanquanao.infrastructures.requests.MauSacRequest;
import com.example.websitebanquanao.infrastructures.responses.MauSacResponse;
import com.example.websitebanquanao.repositories.MauSacRepository;
import com.example.websitebanquanao.services.MauSacService;
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

@Controller
@RequestMapping("/admin/mau-sac")
public class MauSacController {
    @Autowired
    private MauSacService mauSacService;

    @Autowired
    private MauSacRequest mauSacRequest;

    private static final String redirect = "redirect:/admin/mau-sac/index";
    @Autowired
    private MauSacRepository mauSacRepository;

    @GetMapping("index")
    public String index(@RequestParam(name = "page", defaultValue = "1") int page, Model model, @ModelAttribute("successMessage") String successMessage) {
        Page<MauSacResponse> mauSacPage = mauSacService.getPage(page, 5);
        model.addAttribute("mauSacPage", mauSacPage);
        model.addAttribute("ms", mauSacRequest);
        model.addAttribute("successMessage", successMessage);
        model.addAttribute("view", "/views/admin/mau-sac/index.jsp");
        return "admin/layout";
    }

    @GetMapping("delete/{id}")
    public String delete(@PathVariable("id") Integer id, RedirectAttributes redirectAttributes) {
        mauSacService.delete(id);
        redirectAttributes.addFlashAttribute("successMessage", "Xoá màu sắc thành công");
        return redirect;
    }

    @PostMapping("store")
    public String store(@Valid @ModelAttribute("ms") MauSacRequest mauSacRequest, BindingResult result, Model model, RedirectAttributes redirectAttributes) {
        String ten = mauSacRequest.getTen().trim();

        if (ten.isEmpty() || !ten.equals(mauSacRequest.getTen())) {
            redirectAttributes.addFlashAttribute("errorMessage", "Tên không hợp lệ (không được có khoảng trắng ở đầu )");
            return redirect; // Replace with your actual redirect path
        }

        if (!mauSacService.isTenValid(mauSacRequest.getTen())) {
            redirectAttributes.addFlashAttribute("errorMessage", "Tên toàn khoảng trắng không hợp lệ");
            return redirect;
        }

        if (result.hasErrors()) {
            model.addAttribute("view", "/views/admin/mau-sac/index.jsp");
            return "admin/layout";
        }

        if (mauSacRepository.existsByTen(mauSacRequest.getTen())) {
            redirectAttributes.addFlashAttribute("errorMessage", "Thêm mới không thành công - Màu sắc đã tồn tại");
            return redirect;
        }
        mauSacService.add(mauSacRequest);
        // Lưu thông báo thêm thành công vào session
        redirectAttributes.addFlashAttribute("successMessage", "Thêm màu sắc thành công");
        return redirect;
    }

    @PostMapping("update/{id}")
    public String update(@PathVariable("id") Integer id, @Valid @ModelAttribute("ms") MauSacRequest mauSacRequest, BindingResult result, Model model, RedirectAttributes redirectAttributes) {
        MauSac existingMauSac = mauSacService.findById(id);
        if (existingMauSac == null) {
            redirectAttributes.addFlashAttribute("errorMessage", "Màu Sắc không tồn tại");
            return redirect;
        }
        String updatedTen = mauSacRequest.getTen().trim();
        if (updatedTen.isEmpty() || !updatedTen.equals(mauSacRequest.getTen().trim())) {
            redirectAttributes.addFlashAttribute("errorMessage", "Tên không hợp lệ (không được có khoảng trắng ở đầu)");
            return redirect;
        }
        if (!mauSacService.isTenValid(updatedTen)) {
            redirectAttributes.addFlashAttribute("errorMessage", "Tên toàn khoảng trắng không hợp lệ");
            return redirect;
        }
        if (result.hasErrors()) {
            model.addAttribute("view", "/views/admin/mau-sac/index.jsp");
            return "admin/layout";
        }
        if (mauSacRepository.existsByTen(updatedTen) && !updatedTen.equals(existingMauSac.getTen())) {
            redirectAttributes.addFlashAttribute("errorMessage", "Cập nhật không thành công - Tên màu sắc đã tồn tại");
            return redirect;
        }
        if (updatedTen.equals(existingMauSac.getTen())) {
            mauSacRequest.setTen(existingMauSac.getTen());
        }


        mauSacService.update(mauSacRequest, id);
        redirectAttributes.addFlashAttribute("successMessage", "Cập nhật màu sắc thành công");
        return redirect;
    }

    @GetMapping("get/{id}")
    @ResponseBody
    public ResponseEntity<MauSacResponse> getMauSac(@PathVariable("id") Integer id) {
        return ResponseEntity.ok(mauSacService.getById(id));
    }

    @PostMapping("/them-nhanh")
    public String themNhanh(@Valid @ModelAttribute("ms") MauSacRequest mauSacRequest, BindingResult result, Model model, RedirectAttributes redirectAttributes) {
        if (!mauSacService.isTenValid(mauSacRequest.getTen())) {
            redirectAttributes.addFlashAttribute("errorMessage", "Tên toàn khoảng trắng không hợp lệ");
            return "redirect:/admin/san-pham-chi-tiet/create";
        }
        if (mauSacRepository.existsByTen(mauSacRequest.getTen())) {
            redirectAttributes.addFlashAttribute("errorMessage", "Thêm mới không thành công - Màu sắc đã tồn tại");
            return "redirect:/admin/san-pham-chi-tiet/create";
        }
        mauSacService.add(mauSacRequest);
        return "redirect:/admin/san-pham-chi-tiet/create";
    }
}
