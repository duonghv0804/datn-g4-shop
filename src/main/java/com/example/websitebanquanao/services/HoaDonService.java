package com.example.websitebanquanao.services;

import com.example.websitebanquanao.entities.GiamGia;
import com.example.websitebanquanao.entities.HoaDon;
import com.example.websitebanquanao.entities.KhachHang;
import com.example.websitebanquanao.infrastructures.requests.FormThanhToan;
import com.example.websitebanquanao.infrastructures.requests.HoaDonRequest;
import com.example.websitebanquanao.infrastructures.responses.*;
import com.example.websitebanquanao.repositories.HoaDonRepository;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Date;
import java.time.Instant;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Transactional
public class HoaDonService {
    @Autowired
    private HoaDonRepository hoaDonRepository;

    @Autowired
    private HoaDonChiTietService hoaDonChiTietService;

    @Autowired
    private GiamGiaService giamGiaService;

    @Autowired
    private EmailService emailService;


    // admin
    public List<HoaDon> getAll() {
        return hoaDonRepository.findAllHd();
    }

    public Page<HoaDon> getPage(int page, int pageSize) {
        Pageable pageable = PageRequest.of(page - 1, pageSize);
        return hoaDonRepository.getPage(pageable);
    }

    public List<HoaDon> getAllHoaDonChuaThanhToan() {
        return hoaDonRepository.findAllHoaDon();
    }

    public List<HoaDon> getHoaDonByTrangThai(int trangThai, Boolean checkTH) {
        List<HoaDon> allHoaDon = hoaDonRepository.findAll();
        if (trangThai != 2) {
            return allHoaDon.stream()
                    .filter(hoaDon -> hoaDon.getTrangThai() == trangThai)
                    .collect(Collectors.toList());
        } else {
            if (checkTH) {
                System.out.println(checkTH);
                return allHoaDon.stream()
                        .filter(hoaDon -> hoaDon.getTrangThai() == trangThai && hoaDon.getNgayThanhToan() != null)
                        .collect(Collectors.toList());
            } else {
                System.out.println(checkTH);
                return allHoaDon.stream()
                        .filter(hoaDon -> hoaDon.getTrangThai() == trangThai && hoaDon.getNgayThanhToan() == null)
                        .collect(Collectors.toList());
            }
        }
    }

    public String maHDCount() {
        String code = "";
        List<HoaDon> list = hoaDonRepository.findAll();
        if (list.isEmpty()) {
            code = "HD0001";
        } else {
            int max = 0;
            for (HoaDon hd : list) {
                String ma = hd.getMa();
                if (ma.length() >= 4) {
                    int so = Integer.parseInt(ma.substring(2));
                    if (so > max) {
                        max = so;
                    }
                }
            }
            max++;
            if (max < 10) {
                code = "HD000" + max;
            } else if (max < 100) {
                code = "HD00" + max;
            } else if (max < 1000) {
                code = "HD0" + max;
            } else {
                code = "HD" + max;
            }
        }
        return code;
    }

    private String encodeImageToBase64(MultipartFile file) throws IOException {
        byte[] fileContent = file.getBytes();
        return Base64.encodeBase64String(fileContent);
    }

    public void add(HoaDonRequest hoaDonRequest) {
        HoaDon hoaDon = new HoaDon();
        hoaDon.setMa(maHDCount());
        hoaDon.setNgayTao(hoaDonRequest.getNgayTao());
        hoaDon.setNgayThanhToan(hoaDonRequest.getNgayThanhToan());
        hoaDon.setNgayVanChuyen(hoaDonRequest.getNgayVanChuyen());
        hoaDon.setNgayNhan(hoaDonRequest.getNgayNhan());
        hoaDon.setIdKhachHang(hoaDonRequest.getIdKhachHang());
        hoaDon.setIdNhanVien(hoaDonRequest.getIdNhanVien());
        hoaDon.setIdGiamGia(hoaDonRequest.getIdGiamGia());
        hoaDon.setNguoiNhan(hoaDonRequest.getNguoiNhan());
        hoaDon.setEmail(hoaDonRequest.getEmail());
        hoaDon.setSoDienThoai(hoaDonRequest.getSoDienThoai());
        hoaDon.setHinhThucThanhToan(hoaDonRequest.getHinhThucThanhToan());
        hoaDon.setDiaChi(hoaDonRequest.getDiaChi());
        hoaDon.setXaPhuong(hoaDonRequest.getXaPhuong());
        hoaDon.setQuanHuyen(hoaDonRequest.getQuanHuyen());
        hoaDon.setTinhThanhPho(hoaDonRequest.getTinhThanhPho());
        hoaDon.setTrangThai(hoaDonRequest.getTrangThai());
        hoaDon.setLoaiHoaDon(hoaDonRequest.getLoaiHoaDon());
        java.util.Date currentDate = new java.util.Date();
        hoaDon.setNgaySua(new Date(currentDate.getTime()));
        hoaDonRepository.save(hoaDon);
        System.out.println("HoaDonService.add: " + hoaDon.getMa());
    }

    public HoaDon getById(UUID idHoaDon) {
        if (hoaDonRepository.findById(idHoaDon).isPresent()) {
            return hoaDonRepository.findById(idHoaDon).get();
        } else {
            return null;
        }
    }

    public void update(HoaDon hoaDon, UUID idHoaDon) {
        if (hoaDon != null && idHoaDon != null) {
            java.util.Date currentDate = new java.util.Date();
            hoaDon.setNgaySua(new Date(currentDate.getTime()));
            hoaDonRepository.save(hoaDon);
        }
    }

    public void updateHoaDonAnh(HoaDon hoaDon, UUID idHoaDon, MultipartFile anh) {
        if (hoaDon != null && idHoaDon != null) {
            try {
                hoaDon.setAnhHoaDonChuyenKhoan("data:image/png;base64," + encodeImageToBase64(anh));
            } catch (IOException e) {
                e.printStackTrace();
            }
            hoaDonRepository.save(hoaDon);
        }
    }

    // user
    public UUID addHoaDonUser(FormThanhToan formThanhToan, KhachHangResponse khachHangResponse, GiamGiaResponse giamGiaResponse, int diaChiMacDinh, BigDecimal tongTien, BigDecimal soTienGiam, BigDecimal phiVanChuyen) {
        HoaDon hoaDon = new HoaDon();

        hoaDon.setMa(maHDCount());
        Instant currentInstant = Instant.now();
        hoaDon.setNgayTao(currentInstant);
        if (diaChiMacDinh == 1) {
            hoaDon.setNguoiNhan(khachHangResponse.getHoVaTen());
            hoaDon.setTinhThanhPho(khachHangResponse.getTinhThanhPho());
            hoaDon.setQuanHuyen(khachHangResponse.getQuanHuyen());
            hoaDon.setXaPhuong(khachHangResponse.getXaPhuong());
            hoaDon.setDiaChi(khachHangResponse.getDiaChi());
            hoaDon.setSoDienThoai(khachHangResponse.getSoDienThoai());
            hoaDon.setEmail(khachHangResponse.getEmail());
        } else {
            hoaDon.setNguoiNhan(formThanhToan.getHoTen());
            hoaDon.setTinhThanhPho(formThanhToan.getTinhThanhPho());
            hoaDon.setQuanHuyen(formThanhToan.getQuanHuyen());
            hoaDon.setXaPhuong(formThanhToan.getXaPhuong());
            hoaDon.setDiaChi(formThanhToan.getDiaChi());
            hoaDon.setSoDienThoai(formThanhToan.getSoDienThoai());
            hoaDon.setEmail(formThanhToan.getEmail());
        }
        hoaDon.setTongTien(tongTien);
        hoaDon.setTienGiam(soTienGiam);
        hoaDon.setHinhThucThanhToan(formThanhToan.getHinhThucThanhToan());
        hoaDon.setGhiChu(formThanhToan.getGhiChu());
        hoaDon.setLoaiHoaDon(1);
        hoaDon.setPhiVanChuyen(phiVanChuyen);
        hoaDon.setTrangThai(2);
        java.util.Date currentDate = new java.util.Date();
        hoaDon.setNgaySua(new Date(currentDate.getTime()));
        KhachHang khachHang = new KhachHang();
        khachHang.setId(khachHangResponse.getId());

        if (giamGiaResponse != null) {
            GiamGia giamGia = new GiamGia();
            giamGia.setId(giamGiaResponse.getId());
            hoaDon.setIdGiamGia(giamGia);

            giamGiaService.updateSoLuongByMa(giamGiaResponse.getMa(), giamGiaResponse.getSoLuong() - 1);
        }
        hoaDon.setIdKhachHang(khachHang);
        hoaDonRepository.save(hoaDon);
        hoaDonChiTietService.addHoaDonChiTietUser(hoaDon, khachHangResponse.getId());
        if(diaChiMacDinh == 1){
            emailService.sendEmail(khachHangResponse.getEmail(), "Cảm ơn bạn đã đặt hàng", "Đây là hóa đơn của bạn", hoaDon.getMa());
        }else{
            emailService.sendEmail(formThanhToan.getEmail(), "Cảm ơn bạn đã đặt hàng", "Đây là hóa đơn của bạn", hoaDon.getMa());

        }
        System.out.println("HoaDonService.addHoaDonUser: " + hoaDon.getMa());
        return hoaDon.getId();
    }

    public HoaDonChiTietUserResponse findHoaDonUserResponseById(UUID id) {
        System.out.println("HoaDonService.findHoaDonUserResponseById: " + id);
        return hoaDonRepository.findHoaDonUserResponseById(id);
    }

    public Integer getSoPhanTramGiamByIdHoaDon(UUID id) {
        Integer soPhanTramGiam = hoaDonRepository.getSoPhanTramGiamByIdHoaDon(id);
        if (soPhanTramGiam == null) {
            return 0;
        } else {
            return soPhanTramGiam;
        }
    }

    public BigDecimal sumTongTienByIdHoaDon(UUID idHoaDon) {
        BigDecimal tongTien = hoaDonChiTietService.sumTongTien(idHoaDon);
        Integer soPhanTramGiam = getSoPhanTramGiamByIdHoaDon(idHoaDon);
        BigDecimal soTienDuocGiam = tongTien.multiply(new BigDecimal(soPhanTramGiam).divide(new BigDecimal(100)));
        BigDecimal soTienSauKhiGiam = tongTien.subtract(soTienDuocGiam);
        return soTienSauKhiGiam;
    }

    @Transactional
    public void updateNgayThanhToanByIdHoaDon(String ma, Instant ngayThanhToan) {
        String hd = hoaDonRepository.findTenDonViByMa(ma);
        BigDecimal thanhToan = hoaDonRepository.findTongTienByMa(ma);
        if (hd != null) {
            hoaDonRepository.updateNgayThanhToanByIdHoaDon(ma, ngayThanhToan, 4, thanhToan);
        } else {
            hoaDonRepository.updateNgayThanhToanByIdHoaDon(ma, ngayThanhToan, 2, thanhToan);
        }
    }

    // nếu số lượng hoá đơn có trạng thái là 0 > 5 hoá đơn thì không cho tạo đơn hàng
    public Boolean checkSoLuongHoaDonChuaThanhToan() {
        List<HoaDon> hoaDonList = hoaDonRepository.findAll();
        int count = 0;
        for (HoaDon hoaDon : hoaDonList) {
            if (hoaDon.getTrangThai() == 0) {
                count++;
            }
        }
        if (count > 4) {
            return false;
        } else {
            return true;
        }
    }
}
