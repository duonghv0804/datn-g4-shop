package com.example.websitebanquanao.controllers.admins;

import com.example.websitebanquanao.entities.HoaDonChiTiet;
import com.example.websitebanquanao.entities.SanPhamChiTiet;
import com.example.websitebanquanao.infrastructures.requests.*;
import com.example.websitebanquanao.infrastructures.responses.SanPhamChiTietResponse;
import com.example.websitebanquanao.repositories.SanPhamChiTietRepository;
import com.example.websitebanquanao.services.*;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("/admin/san-pham-chi-tiet")
public class SanPhamChiTietController {

    @Autowired
    private SanPhamChiTietService sanPhamChiTietService;

    @Autowired
    private SanPhamService sanPhamService;

    @Autowired
    private MauSacService mauSacService;

    @Autowired
    private KichCoService kichCoService;

    @Autowired
    private HoaDonChiTietService hoaDonChiTietService;

    @Autowired
    private AnhSanPhamService anhSanPhamService;
    @Autowired
    private MauSacRequest mauSacRequest;
    @Autowired
    private KichCoRequest kichCoRequest;
    @Autowired
    private LoaiService loaiService;
    @Autowired
    private SexService sexService;
    @Autowired
    private ThuongHieuService thuongHieuService;
    @Autowired
    private SanPhamRequest sanPhamRequest;
    @Autowired
    HttpSession session;

    // filter, search
    @GetMapping("/index")
    public String index(
            @RequestParam(name = "timKiem", required = false) String timKiem,
            @RequestParam(name = "tenMauSac", required = false) String tenMauSac,
            @RequestParam(name = "tenKichCo", required = false) String tenKichCo,
            @RequestParam(name = "trangThai", required = false) Integer trangThai,
            @RequestParam(name = "page", defaultValue = "1") int page,
            Model model
    ) {
        model.addAttribute("list", sanPhamChiTietService.findByConditions(timKiem, tenMauSac, tenKichCo, trangThai,page));
        model.addAttribute("listMauSac", mauSacService.getAll());
        model.addAttribute("listKichCo", kichCoService.getAll());
//        model.addAttribute("listSex", sexService.getAll());
        model.addAttribute("listThuongHieu", thuongHieuService.getAll());
        model.addAttribute("view", "/views/admin/san-pham-chi-tiet/index.jsp");
        return "admin/layout";
    }

    @GetMapping("/create")
    public String create(Model model) {
        List<SanPhamChiTietResponse> listtam = sanPhamChiTietService.getlisttam();
        model.addAttribute("listtam", listtam);
        model.addAttribute("listSanPham", sanPhamService.getAll());
        model.addAttribute("listMauSac", mauSacService.getAll());
        model.addAttribute("listKichCo", kichCoService.getAll());
        model.addAttribute("listSex", sexService.getAll());
        model.addAttribute("listThuongHieu", thuongHieuService.getAll());
        model.addAttribute("list", sanPhamChiTietService.getAll());
        model.addAttribute("ms", mauSacRequest);
        model.addAttribute("kc", new KichCoRequest());
        model.addAttribute("listLoai", loaiService.getAll());
        model.addAttribute("sp", sanPhamRequest);
        model.addAttribute("sanPhamChiTiet", new SanPhamChiTietRequest());
        model.addAttribute("action", "/admin/san-pham-chi-tiet/add");
        model.addAttribute("view", "/views/admin/san-pham-chi-tiet/create.jsp");
        return "admin/layout";
    }

    @GetMapping("/addlist")
    public String addlist() {
        sanPhamChiTietService.updateList();
        return "redirect:/admin/san-pham-chi-tiet/index";
    }

    @PostMapping("/add")
    public String add(@ModelAttribute("sanPhamChiTiet") SanPhamChiTietRequest sanPhamChiTietRequest, RedirectAttributes redirectAttributes) {
        sanPhamChiTietRequest.setTrangThai(2);
        if (sanPhamChiTietService.add(sanPhamChiTietRequest)) {
            redirectAttributes.addFlashAttribute("successMessage", "Thêm sản phẩm chỉ tiết tạm thành công");
        } else {
            redirectAttributes.addFlashAttribute("errorMessage", "Sản phẩm chi tiết đã tồn tại");
        }
        return "redirect:/admin/san-pham-chi-tiet/create";
    }


    @GetMapping("/delete/{id}")
    public String delete(@PathVariable("id") UUID id) {
        sanPhamChiTietService.delete(id);
        return "redirect:/admin/san-pham-chi-tiet/create";
    }

    @PostMapping("/update/{id}")
    public String update(
            @PathVariable("id") UUID id,
            @Valid @ModelAttribute("sanPhamChiTiet") SanPhamChiTietRequest sanPhamChiTietRequest,
            BindingResult result, Model model,
            RedirectAttributes redirectAttributes
    ) {
        if (result.hasErrors()) {
            model.addAttribute("list", sanPhamChiTietService.getAll());
            model.addAttribute("view", "/views/admin/san-pham-chi-tiet/index.jsp");
            return "admin/layout";
        }
        System.out.println(sanPhamChiTietRequest.toString());
        sanPhamChiTietService.update(sanPhamChiTietRequest, id);
        redirectAttributes.addFlashAttribute("successMessage", "Cập nhật chi tiết sản phẩm thành công");
        return "redirect:/admin/san-pham-chi-tiet/index";
    }

    @PostMapping("/updatetam/{id}")
    public String updatetam(
            @PathVariable("id") UUID id,
            @Valid @ModelAttribute("sanPhamChiTiet") SanPhamChiTietRequest sanPhamChiTietRequest,
            BindingResult result, Model model,
            RedirectAttributes redirectAttributes
    ) {
        if (result.hasErrors()) {
            model.addAttribute("list", sanPhamChiTietService.getAll());
            model.addAttribute("view", "/views/admin/san-pham-chi-tiet/create.jsp");
            System.out.println("Validation errors: " + result.getAllErrors());
            return "admin/layout";
        }

        try {
            System.out.println("Updating product details with id: " + id);
            sanPhamChiTietService.updatetam(sanPhamChiTietRequest, id);
            sanPhamChiTietRequest.setTrangThai(2);
            redirectAttributes.addFlashAttribute("successMessage", "Cập nhật chi tiết sản phẩm thành công");
        } catch (Exception e) {
            System.err.println("Error updating product details: " + e.getMessage());
            model.addAttribute("list", sanPhamChiTietService.getAll());
            model.addAttribute("view", "/views/admin/san-pham-chi-tiet/create.jsp");
            model.addAttribute("errorMessage", "Có lỗi xảy ra khi cập nhật chi tiết sản phẩm.");
            return "admin/layout";
        }

        return "redirect:/admin/san-pham-chi-tiet/create";
    }


    @GetMapping("/updatetam/{id}")
    public String updatetam(@PathVariable("id") SanPhamChiTiet sanPhamChiTiet, Model model) {
        model.addAttribute("listSanPham", sanPhamService.getAll());
        model.addAttribute("listMauSac", mauSacService.getAll());
        model.addAttribute("listKichCo", kichCoService.getAll());

        model.addAttribute("list", sanPhamChiTietService.getAll());

        model.addAttribute("sanPhamChiTiet", sanPhamChiTiet);
        model.addAttribute("action", "/admin/san-pham-chi-tiet/updatetam/" + sanPhamChiTiet.getId());
        model.addAttribute("view", "/views/admin/san-pham-chi-tiet/updatetam.jsp");
        return "admin/layout";
    }


    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") SanPhamChiTiet sanPhamChiTiet, Model model) {
        model.addAttribute("listSanPham", sanPhamService.getAll());
        model.addAttribute("listMauSac", mauSacService.getAll());
        model.addAttribute("listKichCo", kichCoService.getAll());

        model.addAttribute("list", sanPhamChiTietService.getAll());

        model.addAttribute("sanPhamChiTiet", sanPhamChiTiet);
        model.addAttribute("action", "/admin/san-pham-chi-tiet/update/" + sanPhamChiTiet.getId());
        model.addAttribute("view", "/views/admin/san-pham-chi-tiet/update.jsp");
        return "admin/layout";
    }

    @GetMapping("/get-anh/{id}")
    public String getAnh(@PathVariable("id") UUID id, Model model) {
        model.addAttribute("list", sanPhamChiTietService.getAll());
        model.addAttribute("sanPhamChiTiet", new SanPhamChiTietRequest());
        model.addAttribute("listAnh", anhSanPhamService.getAll(id));
        model.addAttribute("view", "/views/admin/san-pham-chi-tiet/create.jsp");
        return "admin/layout";
    }

    @PostMapping("/tra-hang-vao-kho")
    public String traHangVaoKho(@RequestParam("idHoaDon") UUID idHoaDon,
                                @RequestParam("idSanPhamChiTiet") UUID idSanPhamChiTiet,
                                @RequestParam("soLuongTraHang") int soLuongTraHang,
                                RedirectAttributes redirectAttributes) {
        if (hoaDonChiTietService.getHoaDonChiTietByHoaDonIdAndIdSanPhamChiTiet(idHoaDon, idSanPhamChiTiet).getTrangThai() == 2) {
            redirectAttributes.addFlashAttribute("errorMessage", "Sản phẩm này đã được hoàn trước đó.");
            return "redirect:/admin/hoa-don/" + idHoaDon;
        } else if (hoaDonChiTietService.getHoaDonChiTietByHoaDonIdAndIdSanPhamChiTiet(idHoaDon, idSanPhamChiTiet).getTrangThai() == 1) {
            redirectAttributes.addFlashAttribute("errorMessage", "Sản phẩm này chưa được xác nhận lấy trước đó.");
            return "redirect:/admin/hoa-don/" + idHoaDon;
        }
        try {
            // Kiểm tra trạng thái đã hoàn từ session trước khi thực hiện
            String sessionKey = "daHoan_" + idSanPhamChiTiet + "_" + idHoaDon;
            if (session.getAttribute(sessionKey) != null) {
                throw new Exception("Sản phẩm này đã được hoàn trước đó.");
            }
            // Thực hiện trả hàng vào kho
            sanPhamChiTietService.xuLyTraHangVaoKho(idSanPhamChiTiet, soLuongTraHang);
            HoaDonChiTiet hoaDonChiTiet = hoaDonChiTietService.getHoaDonChiTietByHoaDonIdAndIdSanPhamChiTiet(idHoaDon, idSanPhamChiTiet);
            hoaDonChiTiet.setTrangThai(2);
            hoaDonChiTietService.update(hoaDonChiTiet);
            // Lưu trạng thái đã hoàn vào session
            session.setAttribute(sessionKey, true);

            // Trả về view với thông điệp hoặc dữ liệu cần thiết
            redirectAttributes.addFlashAttribute("successMessage", "Trả hàng vào kho thành công.");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "redirect:/admin/hoa-don/" + idHoaDon;
    }
}
