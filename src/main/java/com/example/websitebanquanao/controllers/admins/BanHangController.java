package com.example.websitebanquanao.controllers.admins;

import com.example.websitebanquanao.entities.*;
import com.example.websitebanquanao.infrastructures.requests.HoaDonRequest;
import com.example.websitebanquanao.infrastructures.responses.BanHangTaiQuayResponse;
import com.example.websitebanquanao.infrastructures.responses.GioHangResponse;
import com.example.websitebanquanao.infrastructures.responses.GioHangUserResponse;
import com.example.websitebanquanao.infrastructures.responses.KhachHangResponse;
import com.example.websitebanquanao.services.*;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;


@Controller
@RequestMapping("/admin/ban-hang")
public class BanHangController {
    @Autowired
    private HinhThucThanhToanService hinhThucThanhToanService;
    @Autowired
    private GiamGiaService giamGiaService;
    @Autowired
    private SanPhamChiTietService sanPhamChiTietService;
    @Autowired
    private HoaDonService hoaDonService;
    @Autowired
    private HoaDonChiTietService hoaDonChiTietService;
    @Autowired
    private SanPhamChiTietService ctspService;
    @Autowired
    private KichCoService kichCoService;
    @Autowired
    private MauSacService mauSacService;
    @Autowired
    private KhachHangService khachHangService;
    @Autowired
    private CreatePDF createPDF;

    @Autowired
    HttpSession session;

    @GetMapping("")
    public String index(@RequestParam(name = "page", defaultValue = "1") int page, @RequestParam(name = "pageSize", defaultValue = "6") int pageSize, Model model) {
        List<BanHangTaiQuayResponse> listProduct = sanPhamChiTietService.findAllCtsp();
        List<KhachHangResponse> listKhachHang = khachHangService.getAll();
        model.addAttribute("listSize", kichCoService.getAll());
        model.addAttribute("listMauSac", mauSacService.getAll());
        model.addAttribute("listHTTT", hinhThucThanhToanService.getAll());
        model.addAttribute("listGG", giamGiaService.getAll());
        model.addAttribute("listProduct", listProduct);
        model.addAttribute("listHoaDon", hoaDonService.getAllHoaDonChuaThanhToan());
        model.addAttribute("listKhachHang", listKhachHang);
        model.addAttribute("hdct", new HoaDonChiTiet());
        model.addAttribute("view", "/views/admin/ban-hang.jsp");
        return "admin/layout";
    }

    @PostMapping("/add-hoa-don")
    public String addHoaDon(@ModelAttribute("hd") HoaDonRequest hoaDonRequest, @RequestParam("id") UUID id, Model model) {
        NhanVien nhanVien = new NhanVien();
        nhanVien.setId(id);
        System.out.println(nhanVien.getId());
        hoaDonRequest.setIdNhanVien(nhanVien);
        Instant currentInstant = Instant.now();
        hoaDonRequest.setNgayTao(currentInstant);
        hoaDonRequest.setTrangThai(0);
        Boolean check = hoaDonService.checkSoLuongHoaDonChuaThanhToan();
        if (check == false) {
            session.setAttribute("errorMessage", "Số lượng hoá đơn chờ vượt quá 5 hoá đơn");
            return "redirect:/admin/ban-hang";
        } else {
            hoaDonService.add(hoaDonRequest);
            session.setAttribute("successMessage", "Thêm hoá đơn thành công");
        }
        return "redirect:/admin/ban-hang";
    }

    @PostMapping("/add-gio-hang/{idHoaDon}")
    public String addGioHang(@ModelAttribute("hdct") HoaDonChiTiet hoaDonChiTiet, @PathVariable("idHoaDon") UUID idHoaDon, @RequestParam("idSanPhamChiTiet") UUID idSanPhamChiTiet, @RequestParam("gia") int gia, @RequestParam("soLuongMoi") int soLuongMoi) {
        // Lấy chi tiết sản phẩm từ cơ sở dữ liệu dựa trên idSanPhamChiTiet
        SanPhamChiTiet ctsp = ctspService.findById(idSanPhamChiTiet);
        //validate full trường
        if (idSanPhamChiTiet == null || gia == 0 || soLuongMoi == 0) {
            session.setAttribute("errorMessage", "Vui lòng nhập số lượng");
            return "redirect:/admin/ban-hang/view-hoa-don/" + idHoaDon;

        }
        // validate số lượng nhập lớn hơn trong kho
        if (soLuongMoi > ctsp.getSoLuong()) {
            session.setAttribute("errorMessage", "Số lượng sản phẩm trong kho không đủ");
            return "redirect:/admin/ban-hang/view-hoa-don/" + idHoaDon;

        }
        // validate số lượng nhập lớn hơn 0
        if (soLuongMoi < 0) {
            session.setAttribute("errorMessage", "Vui lòng nhập số lượng lớn hơn 0");
            return "redirect:/admin/ban-hang/view-hoa-don/" + idHoaDon;

        }
        // validate số lượng phải là số nguyên
        if (soLuongMoi % 1 != 0) {
            session.setAttribute("errorMessage", "Vui lòng nhập số lượng là số nguyên");
            return "redirect:/admin/ban-hang/view-hoa-don/" + idHoaDon;

        }

        if (ctsp != null) {
            // Kiểm tra xem sản phẩm đã tồn tại trong hoá đơn chưa
            HoaDonChiTiet existingChiTiet = hoaDonChiTietService.getChiTietByHoaDonAndSanPham(idHoaDon, idSanPhamChiTiet);

            if (existingChiTiet != null) {
                // Sản phẩm đã tồn tại trong hoá đơn, tăng số lượng
                int soLuongHienTai = existingChiTiet.getSoLuong();
                existingChiTiet.setSoLuong(soLuongHienTai + soLuongMoi);
                hoaDonChiTietService.update(existingChiTiet);

                // Thông báo thành công
                session.setAttribute("successMessage", "Sản phẩm đã được thêm vào giỏ hàng");
            } else {
                // Sản phẩm chưa tồn tại trong hoá đơn, tạo hoá đơn chi tiết mới
                hoaDonChiTiet.setSoLuong(soLuongMoi);
                hoaDonChiTiet.setIdHoaDon(hoaDonService.getById(idHoaDon));
                hoaDonChiTiet.setIdSanPhamChiTiet(ctsp);
                hoaDonChiTietService.add(hoaDonChiTiet);

                // Thông báo thành công
                session.setAttribute("successMessage", "Sản phẩm đã được thêm vào giỏ hàng");
            }

            // Trừ đi số lượng mới từ số lượng hiện tại
            int soLuongConLai = ctsp.getSoLuong() - soLuongMoi;

            if (soLuongConLai < 0) {
                // Xử lý tình huống số lượng âm (tuỳ theo quy tắc của bạn)
                soLuongConLai = 0;
            }

            // Cập nhật số lượng mới của sản phẩm
            ctsp.setSoLuong(soLuongConLai);
            ctspService.updateSoLuong(ctsp);

            // Lưu trạng thái thành công vào session
            session.setAttribute("successMessage", "Sản phẩm đã được thêm vào giỏ hàng");
        } else {
            // Sản phẩm không tồn tại, lưu trạng thái thất bại vào session
            session.setAttribute("errorMessage", "Sản phẩm không tồn tại hoặc có lỗi xảy ra");

        }

        return "redirect:/admin/ban-hang/view-hoa-don/" + idHoaDon;
    }

    @GetMapping("/view-hoa-don/{id}")
    public String viewHoaDon(@PathVariable("id") UUID id, @RequestParam(name = "page", defaultValue = "1") int page, @RequestParam(name = "pageSize", defaultValue = "6") int pageSize, Model model) {
        // Lấy thông tin hoá đơn chi tiết dựa trên id hoá đơn
        HoaDon hoaDon = hoaDonService.getById(id);
        List<GioHangResponse> listSanPhamTrongGioHang = hoaDonChiTietService.getHoaDonChiTietByHoaDonId(id);

        // Truyền idHoaDon để sử dụng trong form
        List<BanHangTaiQuayResponse> listProduct = sanPhamChiTietService.findAllCtsp();
        model.addAttribute("listProduct", listProduct);
        model.addAttribute("listSize", kichCoService.getAll());
        model.addAttribute("listMauSac", mauSacService.getAll());
        model.addAttribute("listHoaDon", hoaDonService.getAllHoaDonChuaThanhToan());
        model.addAttribute("listHTTT", hinhThucThanhToanService.getAll());
        model.addAttribute("listGG", giamGiaService.getAll());

        model.addAttribute("idHoaDon", id);
        model.addAttribute("hoaDon", hoaDon); // Truyền giá trị hoaDon vào model

        model.addAttribute("listSanPhamTrongGioHang", listSanPhamTrongGioHang);

        model.addAttribute("view", "/views/admin/ban-hang.jsp");
        return "admin/layout";
    }

    @PostMapping("/delete-gio-hang/{idHoaDonChiTiet}")
    public String deleteGioHang(
            @PathVariable("idHoaDonChiTiet") UUID idHoaDonChiTiet,
            @RequestParam("soLuong") int soLuong
    ) {
        // Lấy thông tin hoá đơn chi tiết dựa trên id
        HoaDonChiTiet hoaDonChiTiet = hoaDonChiTietService.getById(idHoaDonChiTiet);
        // get so luong san pham chi tiet
        SanPhamChiTiet sanPhamChiTiet = ctspService.findById(hoaDonChiTiet.getIdSanPhamChiTiet().getId());
        Integer soLuongSanPhamChiTiet = sanPhamChiTiet.getSoLuong();

        if (hoaDonChiTiet != null) {
            // Kiểm tra và xử lý số lượng nhập
            if (soLuong > 0) {
                // Số lượng hiện tại của hoá đơn chi tiết
                int currentSoLuong = hoaDonChiTiet.getSoLuong();

                // Cập nhật số lượng sản phẩm chi tiết trước khi xoá
                ctspService.updateSoLuongAfterDelete(hoaDonChiTiet.getIdSanPhamChiTiet().getId(), soLuongSanPhamChiTiet + soLuong);

                if (soLuong >= currentSoLuong) {
                    // Nếu số lượng nhập lớn hơn hoặc bằng số lượng hiện tại, thì xoá hoá đơn chi tiết
                    hoaDonChiTietService.delete(idHoaDonChiTiet);
                } else {
                    // Nếu số lượng nhập nhỏ hơn số lượng hiện tại, thì cập nhật số lượng hoá đơn chi tiết
                    hoaDonChiTiet.setSoLuong(currentSoLuong - soLuong);
                    hoaDonChiTietService.update(hoaDonChiTiet);
                }
            }

            // Lấy id hoá đơn
            UUID idHoaDon = hoaDonChiTiet.getIdHoaDon().getId();

            // Chuyển hướng hoặc trả về trang hiển thị hoá đơn
            return "redirect:/admin/ban-hang/view-hoa-don/" + idHoaDon;
        }

        return "redirect:/admin/ban-hang";
    }

    @PostMapping("/thanh-toan/{idHoaDon}")
    public String thanhToan(@PathVariable("idHoaDon") UUID idHoaDon, @RequestParam("httt") HinhThucThanhToan hinhThucThanhToan, @RequestParam(value = "giamGia", required = false) GiamGia giamGia, @RequestParam("ghiChu") String ghiChu, @RequestParam("tong-tien") String tongTien, @RequestParam("tienKhachDua") String tienKhachDua, @RequestParam("tien-giam") String tienGiamGia, @RequestParam("tien-thanh-toan") String tienThanhToan, @RequestParam("nguoiNhan") String hoVaTen, @RequestParam("sdt") String soDienThoai, @RequestParam(value = "idKhachHang", required = false) UUID idKhachHang) {
        String tongTien2 = tongTien.replace(".", "");
        String tienGiam2 = tienGiamGia.replace(".", "");
        String tienThanhToan2 = tienThanhToan.replace(".", "");

        if (hinhThucThanhToan == null) {
            session.setAttribute("errorMessage", "Vui lòng chọn hình thức thanh toán");
            return "redirect:/admin/ban-hang/view-hoa-don/" + idHoaDon;
        }

        if (hoVaTen.isEmpty() || hoVaTen.isBlank()) {
            session.setAttribute("errorMessage", "Vui lòng nhập họ tên khách hàng thật nghiêm túc");
            return "redirect:/admin/ban-hang/view-hoa-don/" + idHoaDon;
        }
        if (Float.valueOf(tienKhachDua) < Float.valueOf(tienThanhToan2)) {
            session.setAttribute("errorMessage", "Số tiền khách đưa còn thiếu");
            return "redirect:/admin/ban-hang/view-hoa-don/" + idHoaDon;
        }

        if (soDienThoai.isEmpty() || soDienThoai.isBlank()) {
            session.setAttribute("errorMessage", "Vui lòng nhập sđt khách hàng thật nghiêm túc");
            return "redirect:/admin/ban-hang/view-hoa-don/" + idHoaDon;
        }

        HoaDon hoaDon = hoaDonService.getById(idHoaDon);
        session.setAttribute("hoaDon", hoaDon);
        session.setAttribute("listHoaDonChiTiet", hoaDonChiTietService.getListByIdHoaDon(hoaDon.getId()));
        List<GioHangUserResponse> listCheck = hoaDonChiTietService.getListByIdHoaDon(hoaDon.getId());
        if (listCheck.isEmpty()) {
            session.setAttribute("errorMessage", "Bạn chưa chọn sản phẩm");
            return "redirect:/admin/ban-hang/view-hoa-don/" + idHoaDon;
        } else {
            Instant currentInstant = Instant.now();
            if (idKhachHang == null) {
                if (hoaDon != null) {
                    if (giamGia != null) {
                        hoaDon.setTrangThai(1);
                        hoaDon.setNgayThanhToan(currentInstant);
                        hoaDon.setHinhThucThanhToan(hinhThucThanhToan);
                        hoaDon.setIdGiamGia(giamGia);
                        giamGia.setSoLuong(giamGia.getSoLuong() - 1);
                        hoaDon.setNguoiNhan(hoVaTen);
                        hoaDon.setSoDienThoai(soDienThoai);
                        hoaDon.setTongTien(BigDecimal.valueOf(Float.valueOf(tongTien2)));
                        hoaDon.setTienGiam(BigDecimal.valueOf(Float.valueOf(tienGiam2)));
                        hoaDon.setThanhToan(BigDecimal.valueOf(Float.valueOf(tienThanhToan2)));
                        hoaDon.setGhiChu(ghiChu);
                        hoaDon.setLoaiHoaDon(0);
                        hoaDonService.update(hoaDon, idHoaDon);
                        createPDF.exportPDFBill(hoaDon, hoaDonChiTietService.getListByIdHoaDon(hoaDon.getId()), hoaDonService.sumTongTienByIdHoaDon(hoaDon.getId()).toString());
                        session.setAttribute("successMessage", "Thanh toán thành công");
                    } else {
                        hoaDon.setTrangThai(1);
                        hoaDon.setNgayThanhToan(currentInstant);
                        hoaDon.setHinhThucThanhToan(hinhThucThanhToan);
                        hoaDon.setNguoiNhan(hoVaTen);
                        hoaDon.setSoDienThoai(soDienThoai);
                        hoaDon.setTongTien(BigDecimal.valueOf(Float.valueOf(tongTien2)));
                        hoaDon.setTienGiam(BigDecimal.valueOf(Float.valueOf(tienGiam2)));
                        hoaDon.setThanhToan(BigDecimal.valueOf(Float.valueOf(tienThanhToan2)));
                        hoaDon.setGhiChu(ghiChu);
                        hoaDon.setLoaiHoaDon(0);
                        hoaDonService.update(hoaDon, idHoaDon);
                        createPDF.exportPDFBill(hoaDon, hoaDonChiTietService.getListByIdHoaDon(hoaDon.getId()), hoaDonService.sumTongTienByIdHoaDon(hoaDon.getId()).toString());
                        session.setAttribute("successMessage", "Thanh toán thành công");
                    }
                }
            } else {
                if (hoaDon != null) {
                    if (giamGia != null) {
                        hoaDon.setTrangThai(1);
                        hoaDon.setNgayThanhToan(currentInstant);
                        hoaDon.setHinhThucThanhToan(hinhThucThanhToan);
                        hoaDon.setGhiChu(ghiChu);
                        hoaDon.setIdGiamGia(giamGia);
                        giamGia.setSoLuong(giamGia.getSoLuong() - 1);
                        hoaDon.setTongTien(BigDecimal.valueOf(Float.valueOf(tongTien2)));
                        hoaDon.setTienGiam(BigDecimal.valueOf(Float.valueOf(tienGiam2)));
                        hoaDon.setThanhToan(BigDecimal.valueOf(Float.valueOf(tienThanhToan2)));
                        KhachHang khachHang = new KhachHang();
                        khachHang.setId(idKhachHang);
                        hoaDon.setNguoiNhan(hoVaTen);
                        hoaDon.setSoDienThoai(soDienThoai);
                        hoaDon.setIdKhachHang(khachHang);
                        hoaDon.setLoaiHoaDon(0);
                        hoaDonService.update(hoaDon, idHoaDon);
                        createPDF.exportPDFBill(hoaDon, hoaDonChiTietService.getListByIdHoaDon(hoaDon.getId()), hoaDonService.sumTongTienByIdHoaDon(hoaDon.getId()).toString());
                        session.setAttribute("successMessage", "Thanh toán thành công");
                    } else {
                        hoaDon.setTrangThai(1);
                        hoaDon.setNgayThanhToan(currentInstant);
                        hoaDon.setHinhThucThanhToan(hinhThucThanhToan);
                        hoaDon.setGhiChu(ghiChu);
                        hoaDon.setTongTien(BigDecimal.valueOf(Float.valueOf(tongTien2)));
                        hoaDon.setTienGiam(BigDecimal.valueOf(Float.valueOf(tienGiam2)));
                        hoaDon.setThanhToan(BigDecimal.valueOf(Float.valueOf(tienThanhToan2)));
                        KhachHang khachHang = new KhachHang();
                        khachHang.setId(idKhachHang);
                        hoaDon.setNguoiNhan(hoVaTen);
                        hoaDon.setSoDienThoai(soDienThoai);
                        hoaDon.setIdKhachHang(khachHang);
                        hoaDon.setLoaiHoaDon(0);
                        hoaDonService.update(hoaDon, idHoaDon);
                        createPDF.exportPDFBill(hoaDon, hoaDonChiTietService.getListByIdHoaDon(hoaDon.getId()), hoaDonService.sumTongTienByIdHoaDon(hoaDon.getId()).toString());
                        session.setAttribute("successMessage", "Thanh toán thành công");
                    }
                }
            }

        }
        return "redirect:/admin/ban-hang";
    }

    @PostMapping("/tao-don-hang/{idHoaDon}")
    public String taoDonHang(@PathVariable("idHoaDon") UUID idHoaDon,
                             @RequestParam("nguoiNhan") String nguoiNhan, @RequestParam("sdt") String sdt,
                             @RequestParam("diaChi") String diaChi, @RequestParam("ghiChu") String ghiChu,
                             @RequestParam("xaPhuong") String xaPhuong, @RequestParam("quanHuyen") String quanHuyen,
                             @RequestParam("tinhThanh") String tinhThanh, @RequestParam("phiVanChuyen") String phiVanChuyen,
                             @RequestParam("maVanChuyen") String maVanChuyen, @RequestParam("tenDonViVanChuyen") String tenDonViVanChuyen,
                             @RequestParam("anh") MultipartFile anh, @RequestParam("tong-tien") String tongTien, @RequestParam("tien-giam") String tienGiamGia, @RequestParam("tien-thanh-toan") String tienThanhToan, @RequestParam("tienKhachDua") String tienKhachDua
    ) {

        String tienKhachDua2 = tienKhachDua.replace(".", "");
        String tongTien2 = tongTien.replace(".", "");
        String phi2 = phiVanChuyen.replace(".", "");
        String tienGiam2 = tienGiamGia.replace(".", "");
        String tienThanhToan2 = tienThanhToan.replace(".", "");
        if (Float.valueOf(tienKhachDua) < Float.valueOf(tienThanhToan2)) {
            session.setAttribute("errorMessage", "Số tiền khách đưa còn thiếu");
            return "redirect:/admin/ban-hang/view-hoa-don/" + idHoaDon;
        }
        // validate full trường và session tồn tại 3s
        if (nguoiNhan.isEmpty() || sdt.isEmpty() || ghiChu.isEmpty() || xaPhuong.isEmpty() || quanHuyen.isEmpty() || tinhThanh.isEmpty() || phiVanChuyen == null || maVanChuyen.isEmpty() || tenDonViVanChuyen.isEmpty()) {
            session.setAttribute("errorMessage", "Vui lòng nhập đầy đủ thông tin");
            return "redirect:/admin/ban-hang/view-hoa-don/" + idHoaDon;
        }
        HoaDon hoaDon = hoaDonService.getById(idHoaDon);

        if (hoaDon != null) {
            hoaDon.setThanhToan(BigDecimal.valueOf(Float.valueOf(tienKhachDua2)));
            hoaDon.setTrangThai(4);
            hoaDon.setNguoiNhan(nguoiNhan);
            hoaDon.setSoDienThoai(sdt);
            hoaDon.setLoaiHoaDon(2);
            hoaDon.setDiaChi(diaChi);
            hoaDon.setGhiChu(ghiChu);
            hoaDon.setTongTien(BigDecimal.valueOf(Float.valueOf(tongTien2)));
            hoaDon.setTienGiam(BigDecimal.valueOf(Float.valueOf(tienGiam2)));
            hoaDon.setThanhToan(BigDecimal.valueOf(Float.valueOf(tienThanhToan2)));
            hoaDon.setTinhThanhPho(tinhThanh);
            hoaDon.setQuanHuyen(quanHuyen);
            hoaDon.setXaPhuong(xaPhuong);
            hoaDon.setPhiVanChuyen(BigDecimal.valueOf(Float.valueOf(phi2)));
            hoaDon.setMaVanChuyen(maVanChuyen);
            hoaDon.setTenDonViVanChuyen(tenDonViVanChuyen);
            createPDF.exportPDFBill(hoaDon, hoaDonChiTietService.getListByIdHoaDon(hoaDon.getId()), hoaDonService.sumTongTienByIdHoaDon(hoaDon.getId()).toString());
            hoaDonService.updateHoaDonAnh(hoaDon, idHoaDon, anh);
            session.setAttribute("successMessage", "Tạo đơn hàng thành công");
        }
        return "redirect:/admin/ban-hang";
    }


    @PostMapping("/add-gio-hang-qr-code/{idHoaDon}")
    @ResponseBody
    public ResponseEntity<String> addGioHangQRCode(@ModelAttribute("hdct") HoaDonChiTiet hoaDonChiTiet, @PathVariable("idHoaDon") UUID idHoaDon, @RequestParam("idSanPhamChiTiet") UUID idSanPhamChiTiet, @RequestParam("gia") int gia, @RequestParam("soLuongMoi") int soLuongMoi) {

        // validate full trường
        if (idSanPhamChiTiet == null || gia == 0 || soLuongMoi == 0) {
            session.setAttribute("errorMessage", "Vui lòng nhập số lượng");
            return new ResponseEntity<String>("Vui lòng nhập đầy đủ thông tin", HttpStatus.BAD_REQUEST);
        }
        // validate số lượng nhập lớn hơn trong kho
        SanPhamChiTiet sanPhamChiTiet = ctspService.findById(idSanPhamChiTiet);
        if (soLuongMoi > sanPhamChiTiet.getSoLuong()) {
            session.setAttribute("errorMessage", "Số lượng sản phẩm trong kho không đủ");
            return new ResponseEntity<String>("Số lượng sản phẩm trong kho không đủ", HttpStatus.BAD_REQUEST);
        }
        SanPhamChiTiet ctsp = ctspService.findById(idSanPhamChiTiet);

        if (ctsp != null) {
            HoaDonChiTiet existingChiTiet = hoaDonChiTietService.getChiTietByHoaDonAndSanPham(idHoaDon, idSanPhamChiTiet);

            if (existingChiTiet != null) {
                int soLuongHienTai = existingChiTiet.getSoLuong();
                existingChiTiet.setSoLuong(soLuongHienTai + soLuongMoi);
                hoaDonChiTietService.update(existingChiTiet);

            } else {
                // Sản phẩm chưa tồn tại trong hoá đơn, tạo hoá đơn chi tiết mới
                hoaDonChiTiet.setSoLuong(soLuongMoi);
                hoaDonChiTiet.setIdHoaDon(hoaDonService.getById(idHoaDon));
                hoaDonChiTiet.setIdSanPhamChiTiet(ctsp);
                hoaDonChiTietService.add(hoaDonChiTiet);

            }

            // Trừ đi số lượng mới từ số lượng hiện tại
            int soLuongConLai = ctsp.getSoLuong() - soLuongMoi;

            if (soLuongConLai < 0) {
                soLuongConLai = 0;
            }

            // Cập nhật số lượng mới của sản phẩm
            ctsp.setSoLuong(soLuongConLai);
            ctspService.updateSoLuong(ctsp);

        }
        session.setAttribute("successMessage", "Sản phẩm đã được thêm vào giỏ hàng");
        return new ResponseEntity<String>("Thêm thành công", HttpStatus.OK);
    }


    @PostMapping("/add-gio-hang-ma-san-pham/{idHoaDon}")
    @ResponseBody
    public ResponseEntity<String> addGioHangMaSanPham(@PathVariable("idHoaDon") UUID idHoaDon,
                                                      @RequestParam("maSanPhamChiTiet") String maSanPhamChiTiet,
                                                      @RequestParam("soLuongMoi") int soLuongMoi) {

        // validate full trường
        if (maSanPhamChiTiet == null || soLuongMoi == 0) {
            session.setAttribute("errorMessage", "Vui lòng nhập đầy đủ thông tin");
            return new ResponseEntity<String>("Vui lòng nhập đầy đủ thông tin", HttpStatus.BAD_REQUEST);
        }

        // Tìm sản phẩm theo mã sản phẩm chi tiết
        SanPhamChiTiet ctsp = ctspService.findByMaSanPham(maSanPhamChiTiet);

        if (ctsp == null) {
            session.setAttribute("errorMessage", "Không tìm thấy sản phẩm với mã sản phẩm chi tiết: " + maSanPhamChiTiet);
            return new ResponseEntity<String>("Không tìm thấy sản phẩm", HttpStatus.BAD_REQUEST);
        }

        // validate số lượng nhập lớn hơn trong kho
        if (soLuongMoi > ctsp.getSoLuong()) {
            session.setAttribute("errorMessage", "Số lượng sản phẩm trong kho không đủ");
            return new ResponseEntity<String>("Số lượng sản phẩm trong kho không đủ", HttpStatus.BAD_REQUEST);
        }

        // Lấy giá từ sản phẩm chi tiết
        BigDecimal gia = ctsp.getGia();


        // Lấy idSanPhamChiTiet từ đối tượng ctsp
        UUID idSanPhamChiTiet = ctsp.getId();

        HoaDonChiTiet existingChiTiet = hoaDonChiTietService.getChiTietByHoaDonAndSanPham(idHoaDon, idSanPhamChiTiet);

        if (existingChiTiet != null) {
            int soLuongHienTai = existingChiTiet.getSoLuong();
            existingChiTiet.setSoLuong(soLuongHienTai + soLuongMoi);
            hoaDonChiTietService.update(existingChiTiet);

        } else {
            // Sản phẩm chưa tồn tại trong hoá đơn, tạo hoá đơn chi tiết mới
            HoaDonChiTiet hoaDonChiTiet = new HoaDonChiTiet();
            hoaDonChiTiet.setSoLuong(soLuongMoi);
            hoaDonChiTiet.setGia(gia);
            hoaDonChiTiet.setIdHoaDon(hoaDonService.getById(idHoaDon));
            hoaDonChiTiet.setIdSanPhamChiTiet(ctsp);
            hoaDonChiTietService.add(hoaDonChiTiet);
        }

        // Trừ đi số lượng mới từ số lượng hiện tại
        int soLuongConLai = ctsp.getSoLuong() - soLuongMoi;

        if (soLuongConLai < 0) {
            soLuongConLai = 0;
        }

        // Cập nhật số lượng mới của sản phẩm
        ctsp.setSoLuong(soLuongConLai);
        ctspService.updateSoLuong(ctsp);

        session.setAttribute("successMessage", "Sản phẩm đã được thêm vào giỏ hàng");
        return new ResponseEntity<String>("Thêm thành công", HttpStatus.OK);
    }
}
