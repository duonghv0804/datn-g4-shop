package com.example.websitebanquanao.services;

import com.example.websitebanquanao.configs.QRCodeGenerator;
import com.example.websitebanquanao.entities.*;
import com.example.websitebanquanao.infrastructures.requests.SanPhamChiTietRequest;
import com.example.websitebanquanao.infrastructures.responses.BanHangTaiQuayResponse;
import com.example.websitebanquanao.infrastructures.responses.NhanVienResponse;
import com.example.websitebanquanao.infrastructures.responses.SanPhamChiTietResponse;
import com.example.websitebanquanao.infrastructures.responses.SanPhamResponse;
import com.example.websitebanquanao.repositories.SanPhamChiTietRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class SanPhamChiTietService {
    @Autowired
    private SanPhamChiTietRepository sanPhamChiTietRepository;
    @Autowired
    private QRCodeGenerator qrCodeGenerator;
    private final int pageSize = 10;

    // admin
    public Page<SanPhamChiTietResponse> findByConditions(
            String timKiem,
            String tenMauSac,
            String tenKichCo,
            Integer trangThai,
            Integer page
    ) {
        Pageable pageable = PageRequest.of(page - 1, pageSize);
        return sanPhamChiTietRepository.findByConditions(timKiem, tenMauSac, tenKichCo, trangThai,pageable);
    }

    public List<SanPhamChiTietResponse> getAll() {
        return sanPhamChiTietRepository.getAll();
    }

//    public Page<SanPhamChiTietResponse> getPage(int page, int pageSize) {
//        Pageable pageable = PageRequest.of(page - 1, pageSize);
//        return sanPhamChiTietRepository.getPage(pageable);
//    }

    public List<SanPhamChiTietResponse> getlisttam() {
        return sanPhamChiTietRepository.Getlisttam();
    }

    public List<BanHangTaiQuayResponse> findAllCtsp() {
        return sanPhamChiTietRepository.findAllCtsp();
    }


    // mã sp tự tăng :
    public String maSPCount() {
        String code = "";
        List<SanPhamChiTiet> list = sanPhamChiTietRepository.findAll();
        if (list.isEmpty()) {
            code = "SP0001";
        } else {
            int max = 0;
            for (SanPhamChiTiet sp : list) {
                String ma = sp.getMaSanPham();
                if (ma.length() >= 4) {
                    int so = Integer.parseInt(ma.substring(2));
                    if (so > max) {
                        max = so;
                    }
                }
            }
            max++;
            if (max < 10) {
                code = "SP000" + max;
            } else if (max < 100) {
                code = "SP00" + max;
            } else if (max < 1000) {
                code = "SP0" + max;
            } else {
                code = "SP" + max;
            }
        }
        return code;
    }

    public boolean isDuplicate(UUID idSanPham, Integer idMauSac, Integer idKichCo) {
        return sanPhamChiTietRepository.checkTrung(idSanPham, idMauSac, idKichCo);
    }

    public Boolean add(SanPhamChiTietRequest sanPhamChiTietRequest) {
        List<Integer> listIdMauSac = sanPhamChiTietRequest.getIdMauSac();
        List<Integer> listIdKichCo = sanPhamChiTietRequest.getIdKichCo();

        SanPham sanPham = new SanPham();
        sanPham.setId(sanPhamChiTietRequest.getIdSanPham());

        for (Integer idMauSac : listIdMauSac) {
            for (Integer idKichCo : listIdKichCo) {
                if (isDuplicate(sanPham.getId(), idMauSac, idKichCo)) {
                    System.out.println("SanPhamChiTietService.add: Duplicate product with same ID, size, and color.");
                    return false;
                }

                MauSac mauSac = new MauSac();
                mauSac.setId(idMauSac);

                KichCo kichCo = new KichCo();
                kichCo.setId(idKichCo);

                SanPhamChiTiet sanPhamChiTiet = new SanPhamChiTiet();
                sanPhamChiTiet.setMaSanPham(maSPCount());
                sanPhamChiTiet.setGia(sanPhamChiTietRequest.getGia());
                sanPhamChiTiet.setSoLuong(sanPhamChiTietRequest.getSoLuong());
                sanPhamChiTiet.setMoTa(sanPhamChiTietRequest.getMoTa());
                sanPhamChiTiet.setTrangThai(sanPhamChiTietRequest.getTrangThai());
                sanPhamChiTiet.setIdSanPham(sanPham);
                sanPhamChiTiet.setIdMauSac(mauSac);
                sanPhamChiTiet.setIdKichCo(kichCo);

                if (sanPhamChiTietRequest.getNgay_tao() == null) {
                    sanPhamChiTiet.setNgay_tao(new Date());
                } else {
                    sanPhamChiTiet.setNgay_tao(sanPhamChiTietRequest.getNgay_tao());
                }
                sanPhamChiTiet.setNgay_sua(new Date());

                SanPhamChiTiet sanPhamChiTietSaved = sanPhamChiTietRepository.save(sanPhamChiTiet);

                String qrCodeData = String.valueOf(sanPhamChiTietSaved.getId());
                String filePath = "src/main/java/com/example/websitebanquanao/images/" + sanPhamChiTietSaved.getMaSanPham() + ".png";
                qrCodeGenerator.generateQRCode(qrCodeData, filePath);
                System.out.println("SanPhamChiTietService.add: " + sanPhamChiTietSaved.getId());
                System.out.println("File path: " + filePath);
            }
        }
        return true;
    }

    public void update(SanPhamChiTietRequest sanPhamChiTietRequest, UUID id) {
        SanPhamChiTiet sanPhamChiTiet = sanPhamChiTietRepository.findById(id).orElse(null);
        // khong cap nhat san phan, mau, co
        if (sanPhamChiTiet != null) {
            sanPhamChiTiet.setGia(sanPhamChiTietRequest.getGia());
            sanPhamChiTiet.setSoLuong(sanPhamChiTietRequest.getSoLuong());
            sanPhamChiTiet.setMoTa(sanPhamChiTietRequest.getMoTa());
            sanPhamChiTiet.setTrangThai(sanPhamChiTietRequest.getTrangThai());

            // Thiết lập ngày tạo là ngày hiện tại nếu chưa có
            sanPhamChiTiet.setNgay_sua(new Date());

            sanPhamChiTiet.setNgay_sua(new Date());
            sanPhamChiTietRepository.save(sanPhamChiTiet);
            System.out.println("SanPhamChiTietService.update: " + sanPhamChiTiet.getId());
        }
    }

    public void updatetam(SanPhamChiTietRequest sanPhamChiTietRequest, UUID id) {
        // Tìm đối tượng SanPhamChiTiet theo id
        SanPhamChiTiet sanPhamChiTiet = sanPhamChiTietRepository.findById(id).orElse(null);

        // Nếu tìm thấy đối tượng, cập nhật thông tin
        if (sanPhamChiTiet != null) {
            // Cập nhật các thuộc tính từ request
            sanPhamChiTiet.setGia(sanPhamChiTietRequest.getGia());
            sanPhamChiTiet.setSoLuong(sanPhamChiTietRequest.getSoLuong());
            sanPhamChiTiet.setMoTa(sanPhamChiTietRequest.getMoTa());

            // Thiết lập trạng thái là 2
            sanPhamChiTiet.setTrangThai(2);

            // Cập nhật ngày sửa là ngày hiện tại
            sanPhamChiTiet.setNgay_sua(new Date());

            // Lưu đối tượng vào cơ sở dữ liệu
            sanPhamChiTietRepository.save(sanPhamChiTiet);

            // In ra log để kiểm tra
            System.out.println("SanPhamChiTietService.update: " + sanPhamChiTiet.getId());
        }
    }


    @Transactional
    public void updateList() {
        List<SanPhamChiTietResponse> list = sanPhamChiTietRepository.Getlisttam();
        for (SanPhamChiTietResponse entity : list) {
            sanPhamChiTietRepository.updateTrangThai(entity.getId(), 1);
        }
    }


    public void delete(UUID id) {
        SanPhamChiTiet sanPhamChiTiet = sanPhamChiTietRepository.findById(id).orElse(null);
        if (sanPhamChiTiet != null) {
            sanPhamChiTietRepository.deleteById(id);
            System.out.println("SanPhamChiTietService.delete: " + id);
        }
    }

    public SanPhamChiTiet findById(UUID idSanPhamChiTiet) {
        if (sanPhamChiTietRepository.findById(idSanPhamChiTiet).isPresent()) {
            return sanPhamChiTietRepository.findById(idSanPhamChiTiet).get();
        } else {
            return null;
        }
    }

    public void updateSoLuong(SanPhamChiTiet sanPhamChiTiet) {
        sanPhamChiTietRepository.save(sanPhamChiTiet);
    }

    @Transactional
    public void updateSoLuongAfterDelete(UUID idSanPhamChiTiet, int currentSoLuong) {
        // Gọi phương thức tương ứng từ repository để thực hiện việc cập nhật số lượng
        sanPhamChiTietRepository.updateSoLuongAfterDelete(idSanPhamChiTiet, currentSoLuong);
    }

    public List<SanPhamChiTiet> findByIdSanPham(UUID idSanPham) {
        return sanPhamChiTietRepository.findSanPhamChiTietByIdSanPham(idSanPham);
    }

    // user
    public SanPhamChiTiet getByIdSanPhamAndIdMauSacAndIdKichCo(UUID idSanPham, Integer idMauSac, Integer idKichCo) {
        System.out.println("SanPhamChiTietService.getByIdSanPhamAndIdMauSacAndIdKichCo: " + idSanPham + " " + idMauSac + " " + idKichCo);
        return sanPhamChiTietRepository.getByIdSanPhamAndIdMauSacAndIdKichCo(idSanPham, idMauSac, idKichCo);
    }

    @Transactional
    public void updateSoLuongByIdSanPhamChiTiet(UUID idSanPhamChiTiet, int currentSoLuong) {
        SanPhamChiTiet sanPhamChiTiet = sanPhamChiTietRepository.findById(idSanPhamChiTiet).orElse(null);

        int soLuong = sanPhamChiTiet.getSoLuong() - currentSoLuong;

        sanPhamChiTietRepository.updateSoLuongAfterDelete(idSanPhamChiTiet, soLuong);
        System.out.println("SanPhamChiTietService.updateSoLuongByIdSanPhamChiTiet: " + idSanPhamChiTiet + " " + soLuong);
    }

    public List<BanHangTaiQuayResponse> filterProducts(String size, String color, String searchTerm) {
        // Xử lý yêu cầu lọc và trả về danh sách sản phẩm phù hợp
        List<BanHangTaiQuayResponse> filteredProducts = sanPhamChiTietRepository.filterProducts(size, color, searchTerm);
        return filteredProducts;
    }

    @Transactional
    public void xuLyTraHangVaoKho(UUID idSanPhamChiTiet, int soLuongTraHang) {
        // Lấy số lượng hiện tại trong kho
        Integer soLuongHienTai = sanPhamChiTietRepository.getSoLuongSanPhamChiTietByIdSanPham(idSanPhamChiTiet);

        // Cập nhật số lượng trong kho (cộng thêm số lượng trả hàng)
        int soLuongDaCapNhat = soLuongHienTai + soLuongTraHang;
        sanPhamChiTietRepository.updateSoLuongAfterDelete(idSanPhamChiTiet, soLuongDaCapNhat);
    }

    public Integer getSoLuongSanPhamChiTietByIdSanPham(UUID idSanPham) {
        return sanPhamChiTietRepository.getSoLuongSanPhamChiTietByIdSanPham(idSanPham);
    }

    public SanPhamChiTiet findByMaSanPham(String maSanPham) {
        return sanPhamChiTietRepository.findByMaSanPham(maSanPham);
    }

    public Integer getSoLuongSanPham(UUID idSanPham, Integer idMauSac, Integer idKichCo) {
        return sanPhamChiTietRepository.getSoLuongSanPham(idSanPham, idMauSac, idKichCo);
    }

    public Page<SanPhamChiTietResponse> searchByTenSanPham(String tenSanPham,int page, int pageSize) {
        Pageable pageable = PageRequest.of(page - 1, pageSize);
        return sanPhamChiTietRepository.getByTenSanPham(tenSanPham,pageable);
    }
}
