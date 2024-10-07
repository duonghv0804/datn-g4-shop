package com.example.websitebanquanao.controllers.admins;

import com.example.websitebanquanao.entities.Sex;
import com.example.websitebanquanao.infrastructures.requests.SexRequest;
import com.example.websitebanquanao.infrastructures.responses.SexResponse;
import com.example.websitebanquanao.repositories.SexRepository;
import com.example.websitebanquanao.services.SexService;
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
@RequestMapping("/admin/sex")
public class SexController {
    @Autowired
    private SexService sexService;

    @Autowired
    private SexRequest sexRequest;

    private static final String redirect = "redirect:/admin/sex/index";
    @Autowired
    private SexRepository sexRepository;

    @GetMapping("index")
    public String index(@RequestParam(name = "page", defaultValue = "1") int page, Model model, @ModelAttribute("successMessage") String successMessage) {
        Page<SexResponse> SexPage = sexService.getPage(page, 5);
        model.addAttribute("sexPage", SexPage);
        model.addAttribute("sex", sexRequest);
        model.addAttribute("successMessage", successMessage);
        model.addAttribute("view", "/views/admin/sex/index.jsp");
        return "admin/layout";
    }

    @GetMapping("delete/{id}")
    public String delete(@PathVariable("id") Integer id, RedirectAttributes redirectAttributes) {
        sexService.delete(id);
        redirectAttributes.addFlashAttribute("successMessage", "Xoá giới tính thành công");
        return redirect;
    }

    @PostMapping("store")
    public String store(@Valid @ModelAttribute("sex") SexRequest sexRequest, BindingResult result, Model model, RedirectAttributes redirectAttributes) {

        String ten = sexRequest.getTen().trim();

        if (ten.isEmpty() || !ten.equals(sexRequest.getTen())) {
            redirectAttributes.addFlashAttribute("errorMessage", "Tên không hợp lệ (không được có khoảng trắng ở đầu )");
            return redirect; // Replace with your actual redirect path
        }

        if (!sexService.isTenValid(sexRequest.getTen())) {
            redirectAttributes.addFlashAttribute("errorMessage", "Tên toàn khoảng trắng không hợp lệ");
            return redirect;
        }

        if (result.hasErrors()) {
            model.addAttribute("view", "/views/admin/sex/index.jsp");
            return "admin/layout";
        }

        if (sexRepository.existsByTen(sexRequest.getTen())) {
            redirectAttributes.addFlashAttribute("errorMessage", "Thêm mới không thành công - Sex đã tồn tại");
            return redirect;
        }


        sexService.add(sexRequest);
        // Lưu thông báo thêm thành công vào session
        redirectAttributes.addFlashAttribute("successMessage", "Thêm sex thành công");
        return redirect;
    }

    @PostMapping("update/{id}")
    public String update(@PathVariable("id") Integer id,
                         @Valid @ModelAttribute("sex") SexRequest sexRequest,
                         BindingResult result,
                         Model model,
                         RedirectAttributes redirectAttributes) {
        Sex existingSex = sexService.findById(id);
        if (existingSex == null) {
            redirectAttributes.addFlashAttribute("errorMessage", "Sex không tồn tại");
            return redirect;
        }
        String updatedTen = sexRequest.getTen().trim();
        if (updatedTen.isEmpty() || !updatedTen.equals(sexRequest.getTen().trim())) {
            redirectAttributes.addFlashAttribute("errorMessage", "Tên không hợp lệ (không được có khoảng trắng ở đầu)");
            return redirect;
        }
        if (!sexService.isTenValid(updatedTen)) {
            redirectAttributes.addFlashAttribute("errorMessage", "Tên toàn khoảng trắng không hợp lệ");
            return redirect;
        }
        if (sexRepository.existsByTen(updatedTen) && !updatedTen.equals(existingSex.getTen())) {
            redirectAttributes.addFlashAttribute("errorMessage", "Cập nhật không thành công - Tên Sex đã tồn tại");
            return redirect;
        }
        if (updatedTen.equals(existingSex.getTen())) {
            sexRequest.setTen(existingSex.getTen());
        }

        // Thực hiện cập nhật
        sexService.update(sexRequest, id);
        redirectAttributes.addFlashAttribute("successMessage", "Cập nhật Sex thành công");
        return redirect;
    }


    @GetMapping("get/{id}")
    @ResponseBody
    public ResponseEntity<SexResponse> getSex(@PathVariable("id") Integer id) {
        return ResponseEntity.ok(sexService.getById(id));
    }

}
