package com.example.websitebanquanao.controllers.users;

import com.example.websitebanquanao.entities.*;
import com.example.websitebanquanao.infrastructures.requests.*;
import com.example.websitebanquanao.infrastructures.responses.*;
import com.example.websitebanquanao.repositories.GioHangChiTietRepository;
import com.example.websitebanquanao.services.*;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Controller
public class TrangChuController {

    @Autowired
    private SanPhamService sanPhamService;

    @Autowired
    private SanPhamChiTietService ctspService;

    @Autowired
    private MauSacService mauSacService;

    @Autowired
    private KichCoService kichCoService;
    @Autowired
    private HinhThucThanhToanService hinhThucThanhToanService;

    @Autowired
    private AnhSanPhamService anhSanPhamService;

    @Autowired
    private KhachHangService khachHangService;

    @Autowired
    private GioHangChiTietService gioHangChiTietService;

    @Autowired
    private GioHangChiTietRepository gioHangChiTietRepository;

    @Autowired
    private KhuyenMaiChiTietService khuyenMaiChiTietService;

    @Autowired
    private GiamGiaService giamGiaService;

    @Autowired
    private HoaDonService hoaDonService;

    @Autowired
    private HoaDonChiTietService hoaDonChiTietService;

    @Autowired
    private HttpSession session;

    @Autowired
    private KhachHangRequest khachHangRequest;

    @Autowired
    private EmailService emailService;

    private static final String redirect = "redirect:/";

    // trang chủ
    @GetMapping("")
    public String trangChu(Model model, @ModelAttribute("successMessage") String successMessage) {
        List<TrangChuResponse> productList = sanPhamService.getListTrangChu("");
        model.addAttribute("successMessage", successMessage);
        List<TrangChuResponse> firstEightProducts = productList.subList(0, Math.min(productList.size(), 8));
        model.addAttribute("listTrangChu", firstEightProducts);
        model.addAttribute("kh", khachHangRequest);
        model.addAttribute("listBanChay", sanPhamService.getListBanChay(""));
        model.addAttribute("viewBanner", "/views/user/banner.jsp");
        model.addAttribute("viewContent", "/views/user/trang-chu.jsp");
        return "user/layout";
    }

//    // trang sản phẩm tất cả
//    @GetMapping("/san-pham")
//    public String sanPham(Model model, @RequestParam(defaultValue = "", name = "sort", required = false) String sort) {
//        model.addAttribute("idLoai", -1);
//        model.addAttribute("kh", khachHangRequest);
//        model.addAttribute("listLoai", sanPhamService.getListLoai());
//        model.addAttribute("listSanPham", sanPhamService.getListTrangChu(sort));
//        model.addAttribute("viewContent", "/views/user/san-pham.jsp");
//        return "user/layout";
//    }
//
//    // trang sản phẩm theo loại
//    @GetMapping("/san-pham/{idLoai}")
//    public String sanPhamById(Model model, @PathVariable("idLoai") Integer idLoai, @RequestParam(defaultValue = "", name = "sort", required = false) String sort) {
//        System.out.println("idLoai: " + idLoai);
//        model.addAttribute("listLoai", sanPhamService.getListLoai());
//        model.addAttribute("listSanPham", sanPhamService.getListSanPhamByIdLoai(idLoai, sort));
//        model.addAttribute("kh", khachHangRequest);
//        model.addAttribute("idLoai", idLoai);
//        model.addAttribute("viewContent", "/views/user/san-pham.jsp");
//        return "user/layout";
//    }
//
//    // lấy số phần trăm khuyến mãi để hiển thị lên sản phẩm
//    @GetMapping("/so-phan-tram-giam/{idSanPham}")
//    @ResponseBody
//    public ResponseEntity<Integer> soPhanTramGiamKhuyenMai(@PathVariable("idSanPham") UUID idSanPham) {
//        return ResponseEntity.ok(khuyenMaiChiTietService.getSoPhanTramGiamByIdSanPham(idSanPham));
//    }
//
//    // trang sản phẩm chi tiết
//    @GetMapping("/san-pham/{idSanPham}/{idMauSac}")
//    public String sanPhamChiTiet(@PathVariable("idSanPham") UUID idSanPham, @PathVariable("idMauSac") Integer idMauSac, Model model) {
//        Integer idKichCo = sanPhamService.getMinIdKichCoByIdMauSacnAndIdSanPham(idSanPham, idMauSac);
//        model.addAttribute("sanPham", sanPhamService.getByIdSanPhamAndIdMauSacAndIdKichCo(idSanPham, idMauSac, idKichCo));
//        model.addAttribute("kh", khachHangRequest);
//        model.addAttribute("listMauSac", mauSacService.getListMauSacByIdSanPham(idSanPham));
//        model.addAttribute("listKichCo", kichCoService.getListKichCoByIdSanPhamAndMauSac(idSanPham, idMauSac));
//        model.addAttribute("listAnh", anhSanPhamService.getListAnhByIdSanPham(idSanPham));
//        model.addAttribute("idMauSac", idMauSac);
//        model.addAttribute("viewContent", "/views/user/san-pham-chi-tiet.jsp");
//        return "user/layout";
//    }
//
//    // trang sản phẩm sale
//    @GetMapping("/sale")
//    public String sanPhamSale(Model model, @RequestParam(defaultValue = "", name = "sort", required = false) String sort) {
//        model.addAttribute("listSanPham", sanPhamService.getListSale(sort));
//        model.addAttribute("kh", khachHangRequest);
//        model.addAttribute("viewContent", "/views/user/khuyen-mai.jsp");
//        return "user/layout";
//    }
//
//    @ModelAttribute("gioHang")
//    public GioHangUserRequest taoGioHangUserRequest() {
//        return new GioHangUserRequest();
//    }
//
//    // trang giỏ hàng
//    @GetMapping("/gio-hang")
//    public String gioHang(Model model, @ModelAttribute("thongBaoGiamGia") String thoangBaoGiamGia) {
//        KhachHangResponse khachHangResponse = (KhachHangResponse) session.getAttribute("khachHang");
//        model.addAttribute("kh", khachHangRequest);
//        model.addAttribute("listGG", giamGiaService.getAll());
//        GiamGiaResponse giamGiaResponse = (GiamGiaResponse) session.getAttribute("giamGia");
//        if (khachHangResponse == null) {
//            return "redirect:/dang-nhap";
//        } else {
//            BigDecimal tongTien = gioHangChiTietService.getTongTienByIdKhachHang(khachHangResponse.getId());
//            List<GioHangUserResponse> listGioHang = gioHangChiTietService.getListByIdKhachHang(khachHangResponse.getId());
//            model.addAttribute("listGioHang", listGioHang);
//            if (listGioHang.isEmpty()) {
//                model.addAttribute("nutThanhToan", 0);
//            } else {
//                model.addAttribute("nutThanhToan", 1);
//            }
//            model.addAttribute("tongTien", tongTien.intValue());
//            if (giamGiaResponse != null) {
//                int soPhanTramGiam = giamGiaResponse.getSoPhanTramGiam();
//                BigDecimal soTienDuocGiam = tongTien.multiply(new BigDecimal(soPhanTramGiam).divide(new BigDecimal(100)));
//                BigDecimal soTienSauKhiGiam = tongTien.subtract(soTienDuocGiam);
//                model.addAttribute("soTienDuocGiam", soTienDuocGiam.intValue());
//                model.addAttribute("soTienSauKhiGiam", soTienSauKhiGiam.intValue());
//                model.addAttribute("thongBaoGiamGia", thoangBaoGiamGia);
//            } else {
//                model.addAttribute("soTienDuocGiam", 0);
//                model.addAttribute("soTienSauKhiGiam", tongTien.intValue());
//                model.addAttribute("thongBaoGiamGia", "");
//            }
//        }
//
//        String thongBaoGiamGia = (String) session.getAttribute("thongBaoGiamGia");
//        if (thongBaoGiamGia != null) {
//            model.addAttribute("thongBaoGiamGia", thongBaoGiamGia);
//            session.removeAttribute("thongBaoGiamGia");
//        }
//
//        model.addAttribute("viewContent", "/views/user/gio-hang.jsp");
//        return "user/layout";
//    }
//
//    // thêm sản phẩm vào giả hàng trang chi tiết
//    @PostMapping("/gio-hang/{id}")
//    public String themGioHang(@ModelAttribute("gioHang") GioHangUserRequest gioHangUserRequest, @PathVariable("id") UUID id, RedirectAttributes redirectAttributes) {
//        UUID idSanPhamChiTiet = sanPhamService.getIdSanPhamChiTietByIdMauSacnAndIdSanPham(id, gioHangUserRequest.getIdMauSac(), gioHangUserRequest.getIdKichCo());
//        SanPhamChiTiet ctsp = ctspService.findById(idSanPhamChiTiet);
//        KhachHangResponse khachHangResponse = (KhachHangResponse) session.getAttribute("khachHang");
//        GioHangChiTiet gioHang = gioHangChiTietRepository.findByIdSanPhamChiTietIdAndIdGioHangId(ctsp.getId(), khachHangResponse.getId());
//        int soLuong = 0;
//        if (gioHang != null) {
//            soLuong = gioHang.getSoLuong();
//        }
//        if ((gioHangUserRequest.getSoLuong() + soLuong) > ctsp.getSoLuong()) {
//            redirectAttributes.addFlashAttribute("errorMessage", "Số lượng sản phẩm trong kho không đủ ");
//        } else {
//            gioHangChiTietService.add(id, khachHangResponse.getId(), gioHangUserRequest);
//        }
//        return "redirect:/gio-hang";
//    }
//
//    // thay dổi số lượng sản phẩm trong giỏ hàng
//    @PostMapping("/gio-hang/update/{id}")
//    public String capNhatGioHang(@PathVariable("id") UUID id, @RequestParam("soLuong") Integer soLuong, RedirectAttributes redirectAttributes) {
//        KhachHangResponse khachHangResponse = (KhachHangResponse) session.getAttribute("khachHang");
//        SanPhamChiTiet ctsp = ctspService.findById(id);
//        if (soLuong > ctsp.getSoLuong()) {
//            redirectAttributes.addFlashAttribute("errorMessage", "Hết hàng");
//            return "redirect:/gio-hang";
//        }
//        gioHangChiTietService.updateByIdSanPhamChiTietAndIdKhachHang(id, khachHangResponse.getId(), soLuong);
//        return "redirect:/gio-hang";
////        }
//    }
//
//    // xóa sản phẩm trong giỏ hàng
//    @GetMapping("/gio-hang/{id}")
//    public String xoaGioHang(@PathVariable("id") UUID id) {
//        KhachHangResponse khachHangResponse = (KhachHangResponse) session.getAttribute("khachHang");
//        gioHangChiTietService.deleteByIdSanPhamChiTietAndIdKhachHang(id, khachHangResponse.getId());
//        return "redirect:/gio-hang";
//    }
//
//    // add voucher
//    @PostMapping("/ap-dung-voucher")
//    public String apDungVoucher(@RequestParam(value = "ma", required = false) GiamGia ma, HttpSession session) {
//        KhachHangResponse khachHangResponse = (KhachHangResponse) session.getAttribute("khachHang");
//        BigDecimal tongTien = gioHangChiTietService.getTongTienByIdKhachHang(khachHangResponse.getId());
//        String thongBaoGiamGia = "";
//        if (ma == null) {
//            thongBaoGiamGia = "Phải chọn mã giảm giá.";
//            session.setAttribute("thongBaoGiamGia", thongBaoGiamGia);
//            return "redirect:/gio-hang";
//        }
//        GiamGiaResponse giamGiaResponse = giamGiaService.findByMa(ma.getMa());
//        if (giamGiaResponse == null) {
//            thongBaoGiamGia = "Mã giảm giá không tồn tại.";
//        } else {
//            if (tongTien.intValue() + 1 <= giamGiaResponse.getSoTienToiThieu()) {
//                thongBaoGiamGia = "Mã giảm giá Không được áp dụng do chưa đử tiền tối thiểu";
//                giamGiaResponse = null;
//            } else {
//                thongBaoGiamGia = "Đã áp dụng mã giảm giá.";
//            }
//        }
//        session.setAttribute("giamGia", giamGiaResponse);
//        session.setAttribute("thongBaoGiamGia", thongBaoGiamGia);
//        return "redirect:/gio-hang";
//    }
//
//    // trang thanh toán
//    @GetMapping("thanh-toan")
//    public String thanhToan(Model model, RedirectAttributes redirectAttributes) {
//        KhachHangResponse khachHangResponse = (KhachHangResponse) session.getAttribute("khachHang");
//        GiamGiaResponse giamGiaResponse = (GiamGiaResponse) session.getAttribute("giamGia");
//        model.addAttribute("kh", khachHangRequest);
//        if (khachHangResponse == null) {
//            return "redirect:/dang-nhap";
//        } else {
//            if (khachHangResponse.getDiaChi() == null) {
//                redirectAttributes.addFlashAttribute("errorMessage", "Địa chỉ khách hàng vs sđt chưa được cập nhật, hãy cập nhật thêm (Thông Tin Tài Khoản) ở phần bên trái!");
//                return "redirect:/gio-hang";
//            } else {
//                BigDecimal tongTien = gioHangChiTietService.getTongTienByIdKhachHang(khachHangResponse.getId());
//                model.addAttribute("listGioHang", gioHangChiTietService.getListByIdKhachHang(khachHangResponse.getId()));
//                model.addAttribute("sumSoLuong", gioHangChiTietService.sumSoLuongByIdKhachHang(khachHangResponse.getId()));
//                session.setAttribute("khachHang", khachHangService.getById(khachHangResponse.getId()));
//                model.addAttribute("khachHang", khachHangService.getById(khachHangResponse.getId()));
//                model.addAttribute("listHTTT", hinhThucThanhToanService.getAll());
//                model.addAttribute("tongTien", tongTien.intValue());
//                if (giamGiaResponse != null) {
//                    int soPhanTramGiam = giamGiaResponse.getSoPhanTramGiam();
//                    BigDecimal soTienDuocGiam = tongTien.multiply(new BigDecimal(soPhanTramGiam).divide(new BigDecimal(100)));
//                    BigDecimal soTienSauKhiGiam = tongTien.subtract(soTienDuocGiam);
//                    model.addAttribute("soTienDuocGiam", soTienDuocGiam.intValue());
//                    model.addAttribute("soTienSauKhiGiam", soTienSauKhiGiam.intValue());
//                } else {
//                    model.addAttribute("soTienDuocGiam", 0);
//                    model.addAttribute("soTienSauKhiGiam", tongTien.intValue());
//                }
//            }
//        }
//        model.addAttribute("viewContent", "/views/user/thanh-toan.jsp");
//        return "user/layout";
//    }
//
//    @ModelAttribute("formThanhToan")
//    public FormThanhToan formThanhToan() {
//        return new FormThanhToan();
//    }
//
//    // form thanh toán
//    @PostMapping("thanh-toan")
//    public String formThanhToan(Model model, @ModelAttribute("formThanhToan") FormThanhToan formThanhToan, @RequestParam(value = "diaChiMacDinh", required = false) Integer diaChiMacDinh, @RequestParam(value = "phiVanChuyen") String phiVanChuyen) {
//        KhachHangResponse khachHangResponse = (KhachHangResponse) session.getAttribute("khachHang");
//        GiamGiaResponse giamGiaResponse = (GiamGiaResponse) session.getAttribute("giamGia");
//        BigDecimal tongTien = gioHangChiTietService.getTongTienByIdKhachHang(khachHangResponse.getId());
//        String phi1 = phiVanChuyen.replace(" vnđ", "");
//        String phi2 = phi1.replace(",", "");
//        BigDecimal phiBigDecimal = BigDecimal.valueOf(Double.valueOf(phi2));
//
//        if (khachHangResponse == null) {
//            return "redirect:/dang-nhap";
//        } else {
//            if (diaChiMacDinh == null) {
//                diaChiMacDinh = 0;
//            }
//            if (giamGiaResponse == null) {
//                BigDecimal soTienDuocGiam = null;
//                UUID id = hoaDonService.addHoaDonUser(formThanhToan, khachHangResponse, null, diaChiMacDinh, tongTien, soTienDuocGiam, phiBigDecimal);
//                return "redirect:/hoa-don/" + id;
//            } else {
//                int soPhanTramGiam = giamGiaResponse.getSoPhanTramGiam();
//                BigDecimal soTienDuocGiam = tongTien.multiply(new BigDecimal(soPhanTramGiam).divide(new BigDecimal(100)));
//                UUID id = hoaDonService.addHoaDonUser(formThanhToan, khachHangResponse, giamGiaResponse, diaChiMacDinh, tongTien, soTienDuocGiam, phiBigDecimal);
//                session.setAttribute("giamGia", null);
//                return "redirect:/hoa-don/" + id;
//            }
//        }
//    }
//
//    // xem danh sách hoá đơn
//    @GetMapping("/hoa-don")
//    public String hoaDon(Model model,@RequestParam(name = "page", defaultValue = "1") int page) {
//        KhachHangResponse khachHangResponse = (KhachHangResponse) session.getAttribute("khachHang");
//        model.addAttribute("kh", khachHangRequest);
//        if (khachHangResponse == null) {
//            return "redirect:/dang-nhap";
//        } else {
//            model.addAttribute("listHoaDon", hoaDonChiTietService.getPage(khachHangResponse.getId(),page,10));
//        }
//        model.addAttribute("viewContent", "/views/user/hoa-don.jsp");
//        return "user/layout";
//    }
//
//    @GetMapping("/so-phan-tram-giam-gia/{id}")
//    @ResponseBody
//    public ResponseEntity<Integer> soPhanTramGiamGiamGia(@PathVariable("id") UUID id) {
//        return ResponseEntity.ok(hoaDonService.getSoPhanTramGiamByIdHoaDon(id));
//    }
//
//    // xem hoá đơn chi tiết
//    @GetMapping("/hoa-don/{id}")
//    public String hoaDonChiTiet(@PathVariable("id") UUID id, Model model) {
//        model.addAttribute("hoaDon", hoaDonService.findHoaDonUserResponseById(id));
//        model.addAttribute("listSanPhamTrongHoaDon", hoaDonChiTietService.getListByIdHoaDon(id));
//        model.addAttribute("kh", khachHangRequest);
//        model.addAttribute("id", id);
//        model.addAttribute("listHTTT", hinhThucThanhToanService.getAll());
//        session.setAttribute("idHoaDon", id);
//
//        BigDecimal tongTien = hoaDonChiTietService.sumTongTien(id);
//        Integer soPhanTramGiam = hoaDonService.getSoPhanTramGiamByIdHoaDon(id);
//        BigDecimal soTienDuocGiam = tongTien.multiply(new BigDecimal(soPhanTramGiam).divide(new BigDecimal(100)));
//        System.out.println(tongTien.intValue() - soTienDuocGiam.intValue());
//        model.addAttribute("soTienTruocGiam", hoaDonChiTietService.sumTongTien(id).intValue());
//        model.addAttribute("soTienDuocGiam", soTienDuocGiam.intValue());
//        model.addAttribute("soTienSauKhiGiam", tongTien.intValue() - soTienDuocGiam.intValue());
//
//        model.addAttribute("viewContent", "/views/user/hoa-don-chi-tiet.jsp");
//        return "user/layout";
//    }
//
//    @PostMapping("/hoa-don/update-trang-thai/{id}")
//    public String updateTrangThaiHoaDon(@PathVariable("id") UUID id, @RequestParam("trangThai") Integer trangThai, @RequestParam("ghiChu") String ghiChu, RedirectAttributes redirectAttributes) {
//        HoaDon hoaDon = hoaDonService.getById(id);
//        hoaDon.setGhiChu(ghiChu);
//        hoaDon.setTrangThai(trangThai);
//        hoaDonService.update(hoaDon, id);
//        redirectAttributes.addFlashAttribute("successMessage", "hóa đơn đã được hủy");
//        return "redirect:/hoa-don/" + id;
//    }

    // trang đăng nhập
    @GetMapping("/dang-nhap")
    public String dangNhap(Model model, @ModelAttribute("loginError") String loginError) {
        model.addAttribute("dangNhap", new DangNhapUserRequest());
        model.addAttribute("dangKy", new DangKyUserRequest());
        model.addAttribute("loginError", loginError);
        model.addAttribute("kh", khachHangRequest);
        model.addAttribute("viewContent", "/views/user/dang-nhap.jsp");
        return "user/layout";
    }

    // lấy form đăng nhập
    @PostMapping("/dang-nhap")
    public String dangNhap(@ModelAttribute("dangNhap") DangNhapUserRequest dangNhapUserRequest, RedirectAttributes redirectAttributes) {
        String email = dangNhapUserRequest.getEmail();
        String matKhau = dangNhapUserRequest.getMatKhau();
        if (email == null || email.isEmpty() || matKhau == null || matKhau.isEmpty()) {
            redirectAttributes.addFlashAttribute("loginError", "Vui lòng điền đầy đủ thông tin.");
            return "redirect:/dang-nhap";
        }
        KhachHangResponse khachHangResponse = khachHangService.getByEmailAndMatKhau(email, matKhau);
        if (khachHangResponse != null) {
            if (khachHangResponse.getTrangThai() == 1) {
                redirectAttributes.addFlashAttribute("loginError", "Tài khoản của bạn đã bị khóa không thể đăng nhập. Liên hệ Shop để biết thêm...");
                return "redirect:/dang-nhap";

            } else {
                session.setAttribute("khachHang", khachHangResponse);
                return "redirect:/";
            }
        } else {
            redirectAttributes.addFlashAttribute("loginError", "Tài khoản hoặc mật khẩu không đúng.");
            return "redirect:/dang-nhap";
        }
    }

    // lấy form đăng ký
    @PostMapping("/dang-ky")
    public String dangKy(@ModelAttribute("dangKy") DangKyUserRequest dangKyUserRequest, RedirectAttributes redirectAttributes) {
        String email = dangKyUserRequest.getEmailDK();
        String matKhau = dangKyUserRequest.getMatKhauDK();
        String hoTen = dangKyUserRequest.getHoTen();

        if (hoTen == null || hoTen.isEmpty() || matKhau == null || matKhau.isEmpty() || hoTen == null || hoTen.isEmpty()) {
            redirectAttributes.addFlashAttribute("loginError", "Vui lòng điền đầy đủ thông tin");
            return "redirect:/dang-nhap#register";
        }
        if (!khachHangService.isEmail(email)) {
            redirectAttributes.addFlashAttribute("loginError", "Email chưa đúng định dạng, ví dụ: abc@yahoo.com");
            return "redirect:/dang-nhap#register";
        }
        if (!khachHangService.isPasswordValid(matKhau)) {
            redirectAttributes.addFlashAttribute("loginError", "Mật khẩu phải có ít nhất 6 ký tự và chứa ít nhất một chữ và một số");
            return "redirect:/dang-nhap#register";
        }
        if (khachHangService.existsByEmail(dangKyUserRequest.getEmailDK())) {
            redirectAttributes.addFlashAttribute("loginError", "Tài khoản đã tồn tại");
            return "redirect:/dang-nhap#register";
        }

        KhachHangRequest khachHangRequest = new KhachHangRequest();
        khachHangRequest.setEmail(email);
        khachHangRequest.setMatKhau(matKhau);
        khachHangRequest.setHoVaTen(hoTen);

        khachHangService.add(khachHangRequest);
        redirectAttributes.addFlashAttribute("successMessage", "Đăng ký thành công");
        return "redirect:/dang-nhap#login";
    }

    // đăng xuất
    @GetMapping("/dang-xuat")
    public String dangXuat() {
        session.setAttribute("khachHang", null);
        return "redirect:/";
    }

//    // trang giới thiệu
//    @GetMapping("/gioi-thieu")
//    public String gioiThieu(Model model) {
//        model.addAttribute("kh", khachHangRequest);
//        model.addAttribute("viewContent", "/views/user/gioi-thieu.jsp");
//        return "user/layout";
//    }
//
//    // trang chính sách bảo mật
//    @GetMapping("/chinh-sach-bao-mat")
//    public String chinhSachBaoMat(Model model) {
//        model.addAttribute("kh", khachHangRequest);
//        model.addAttribute("viewContent", "/views/user/chinh-sach-bao-mat.jsp");
//        return "user/layout";
//    }
//
//    // trang chính sách đổi trả
//    @GetMapping("/chinh-sach-doi-tra")
//    public String chinhSachDoiTra(Model model) {
//        model.addAttribute("kh", khachHangRequest);
//        model.addAttribute("viewContent", "/views/user/chinh-sach-doi-tra.jsp");
//        return "user/layout";
//    }
//
//    @PostMapping("update/{id}")
//    public String update(@PathVariable("id") UUID id, @Valid @ModelAttribute("kh") KhachHangRequest khachHangRequest, BindingResult result, Model model, RedirectAttributes redirectAttributes) {
//        if (khachHangRequest.validUpdate()) {
//            redirectAttributes.addFlashAttribute("errorMessage", "Vui lòng điền đầy đủ thông tin.");
//            return redirect;
//        }
//        if (!khachHangService.isSoDienThoai(khachHangRequest.getSoDienThoai())) {
//            redirectAttributes.addFlashAttribute("errorMessage", "Số điện thoại không đúng định dạng.");
//            return redirect;
//        }
//        khachHangRequest.setTrangThai(0);
//        khachHangService.update(khachHangRequest, id);
//        session.setAttribute("khachHang", khachHangService.getById(id));
//        redirectAttributes.addFlashAttribute("successMessage", "Cập nhật khách hàng thành công");
//
//        return redirect;
//    }
//
//    @PostMapping("hoa-don/update/{id}")
//    public String updateBenTrangHdUser(@PathVariable("id") UUID id, @Valid @ModelAttribute("kh") KhachHangRequest khachHangRequest, BindingResult result, Model model, RedirectAttributes redirectAttributes) {
//        if (khachHangRequest.validUpdate()) {
//            redirectAttributes.addFlashAttribute("errorMessage", "Vui lòng điền đầy đủ thông tin.");
//            return redirect;
//        }
//        if (!khachHangService.isSoDienThoai(khachHangRequest.getSoDienThoai())) {
//            redirectAttributes.addFlashAttribute("errorMessage", "Số điện thoại không đúng định dạng.");
//            return redirect;
//        }
//        khachHangRequest.setTrangThai(0);
//        khachHangService.update(khachHangRequest, id);
//        session.setAttribute("khachHang", khachHangService.getById(id));
//        redirectAttributes.addFlashAttribute("successMessage", "Cập nhật khách hàng thành công");
//
//        return redirect;
//    }
//
//    @GetMapping("get/{id}")
//    @ResponseBody
//    public ResponseEntity<KhachHangResponse> getKhachHang(@PathVariable("id") UUID id) {
//        return ResponseEntity.ok(khachHangService.getById(id));
//    }
//
//    @GetMapping("hoa-don/get/{id}")
//    @ResponseBody
//    public ResponseEntity<KhachHangResponse> getKhachHang1(@PathVariable("id") UUID id) {
//        return ResponseEntity.ok(khachHangService.getById(id));
//    }
}
