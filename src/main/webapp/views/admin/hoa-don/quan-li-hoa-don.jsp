<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="f" uri="jakarta.tags.functions" %>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form" %>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="/css/bootstrap.min.css">

</head>
<style>
    .selected {
        text-decoration: underline;
    }
</style>
<script>
    function filterByStatus(trangThai, checkTH) {
        var url;
        if (trangThai === '') {
            url = "/admin/hoa-don"; // Trả về trang chủ nếu không có trạng thái
        } else {
            url = "/admin/hoa-don/filter?trangThai=" + trangThai + "&checkTH=" + checkTH;
        }
        window.location.href = url;
    }
</script>
<body>
<div class="px-4">
    <div class="row mt-2">
        <div class="col-lg-3">
            <h3>Hoá đơn</h3>
        </div>
    </div>
    <div class="row mt-2">
        <div class="col-12">
            <div class="btn-group">
                <button class="btn btn-toolbar ms-2 ${param.trangThai == null ? 'selected' : ''}"
                        onclick="filterByStatus('',false)">Tất cả
                </button>
                <button class="btn btn-toolbar ms-2 ${(param.trangThai == '2' && param.checkTH == false) ? 'selected' : ''}"
                        onclick="filterByStatus('2',false)">Chờ xác nhận
                </button>
                <button class="btn btn-toolbar ms-2 ${param.checkTH == true ? 'selected' : ''}"
                        onclick="filterByStatus('2',true)">Đã thanh toán chuyển khoản
                </button>
                <button class="btn btn-toolbar ms-2 ${param.trangThai == '4' ? 'selected' : ''}"
                        onclick="filterByStatus('4',false)">Đã xác nhận / Đang giao
                </button>
                <button class="btn btn-toolbar ms-2 ${param.trangThai == '1' ? 'selected' : ''}"
                        onclick="filterByStatus('1',false)">Đã hoàn thành
                </button>
                <button class="btn btn-toolbar ms-2 ${param.trangThai == '10' ? 'selected' : ''}"
                        onclick="filterByStatus('10',false)">Đã huỷ/Chờ hoàn tiền
                </button>
                <button class="btn btn-toolbar ms-2 ${param.trangThai == '5' ? 'selected' : ''}"
                        onclick="filterByStatus('5',false)">Đã huỷ
                </button>
            </div>
        </div>
    </div>
    <hr>
    <div class="row mt-3">
        <section class="col-lg-12">
            <div class="table-responsive">
                <table class="table">
                    <thead>
                    <tr>
                        <th>STT</th>
                        <th>Mã Hoá Đơn</th>
                        <th>Nhân Viên</th>
                        <th>Khách Hàng</th>
                        <th>Ngày tạo</th>
                        <th>Loại hoá đơn</th>
                        <th>Tổng tiền</th>
                        <th>Trạng thái</th>
                        <th>Thao tác</th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:if test="${param.trangThai == null}">
                        <c:forEach items="${listHoaDon.content}" var="hoaDon" varStatus="index">
                            <tr class="show-tabs" data-tab="tabs-${index.count}">
                                <td>${index.count}</td>
                                <td>${hoaDon.ma}</td>
                                <td>${hoaDon.idNhanVien.hoVaTen}</td>
                                <td>
                                    <c:if test="${hoaDon.idKhachHang != null}">
                                        ${hoaDon.idKhachHang.hoVaTen}
                                    </c:if>
                                    <c:if test="${hoaDon.idKhachHang == null}">
                                        ${hoaDon.nguoiNhan  != null ? hoaDon.nguoiNhan : "Khách lẻ"}
                                    </c:if>
                                </td>
                                <td>${hoaDon.ngayTao}</td>
                                <td>
                                    <c:if test="${hoaDon.loaiHoaDon == 0}">
                                        <span class="text-black">Bán tại quầy</span>
                                    </c:if>
                                    <c:if test="${hoaDon.loaiHoaDon == 1}">
                                        <span class="text-black">Bán Online</span>
                                    </c:if>
                                    <c:if test="${hoaDon.loaiHoaDon == 2}">
                                        <span class="text-black">Giao Hàng</span>
                                    </c:if>

                                    <c:if test="${hoaDon.loaiHoaDon == null}">
                                        <span class="text-black">Hóa đơn chờ</span>
                                    </c:if>
                                </td>
                                <td>
                                    <p class="text-truncate text-danger" id="tongTien_${hoaDon.ma}"></p>
                                    <script>
                                        document.addEventListener('DOMContentLoaded', function () {
                                            var giaSanPhamElement = document.getElementById("tongTien_${hoaDon.ma}");

                                            fetch(`/so-phan-tram-giam-gia/${hoaDon.id}`)
                                                .then(response => response.json())
                                                .then(data => {

                                                    var soPhanTramGiam = data;
                                                    var soTienBanDau = ${hoaDon.tongTien};
                                                    var soTienSauGiam = soTienBanDau - (soTienBanDau * (soPhanTramGiam / 100));
                                                    var formattedTienSauGiam = soTienSauGiam.toLocaleString('en-US');
                                                    giaSanPhamElement.innerText = formattedTienSauGiam + " vnđ";
                                                })
                                                .catch(error => console.error('Lỗi:', error));
                                        });
                                    </script>
                                </td>
                                <td>
                                    <c:if test="${hoaDon.trangThai == 0}">
                                        <span class="text-secondary">Chờ thanh toán</span>
                                    </c:if>
                                    <c:if test="${hoaDon.trangThai == 1}">
                                        <span class="text-success">Đã hoàn thành</span>
                                    </c:if>
                                    <c:if test="${hoaDon.trangThai == 2 && hoaDon.ngayThanhToan ==null}">
                                        <span class="text-secondary">Chờ xác nhận</span>
                                    </c:if>
                                    <c:if test="${hoaDon.trangThai == 2 && hoaDon.ngayThanhToan !=null}">
                                        <span class="text-success">Đã thanh toán chuyển khoản</span>
                                    </c:if>
                                    <c:if test="${hoaDon.trangThai == 4}">
                                        <span class="text-success">Đã xác nhận</span>
                                    </c:if>
                                    <c:if test="${hoaDon.trangThai == 5}">
                                        <span class="text-danger">Đã huỷ</span>
                                    </c:if>
                                    <c:if test="${hoaDon.trangThai == 10}">
                                        <span class="text-secondary">Đã huỷ/Chờ hoàn tiền</span>
                                    </c:if>
                                </td>
                                <td>
                                    <a href="/admin/hoa-don/${hoaDon.id}" class="btn btn-primary detail-link"
                                       data-tab="tabs-${index.count}">Chi tiết</a>
                                </td>
                            </tr>
                        </c:forEach>
                    </c:if>
                    <c:if test="${param.trangThai != null}">
                        <c:forEach items="${listHoaDon}" var="hoaDon" varStatus="index">
                            <tr class="show-tabs" data-tab="tabs-${index.count}">
                                <td>${index.count}</td>
                                <td>${hoaDon.ma}</td>
                                <td>${hoaDon.idNhanVien.hoVaTen}</td>
                                <td>
                                    <c:if test="${hoaDon.idKhachHang != null}">
                                        ${hoaDon.idKhachHang.hoVaTen}
                                    </c:if>
                                    <c:if test="${hoaDon.idKhachHang == null}">
                                        ${hoaDon.nguoiNhan  != null ? hoaDon.nguoiNhan : "Khách lẻ"}
                                    </c:if>
                                </td>
                                <td>${hoaDon.ngayTao}</td>
                                <td>
                                    <c:if test="${hoaDon.loaiHoaDon == 0}">
                                        <span class="text-black">Bán tại quầy</span>
                                    </c:if>
                                    <c:if test="${hoaDon.loaiHoaDon == 1}">
                                        <span class="text-black">Bán Online</span>
                                    </c:if>
                                    <c:if test="${hoaDon.loaiHoaDon == 2}">
                                        <span class="text-black">Giao Hàng</span>
                                    </c:if>

                                    <c:if test="${hoaDon.loaiHoaDon == null}">
                                        <span class="text-black">Hóa đơn chờ</span>
                                    </c:if>
                                </td>
                                <td>
                                    <p class="text-truncate text-danger" id="tongTien_${hoaDon.ma}"></p>
                                    <script>
                                        document.addEventListener('DOMContentLoaded', function () {
                                            var giaSanPhamElement = document.getElementById("tongTien_${hoaDon.ma}");

                                            fetch(`/so-phan-tram-giam-gia/${hoaDon.id}`)
                                                .then(response => response.json())
                                                .then(data => {

                                                    var soPhanTramGiam = data;
                                                    var soTienBanDau = ${hoaDon.tongTien};
                                                    var soTienSauGiam = soTienBanDau - (soTienBanDau * (soPhanTramGiam / 100));
                                                    var formattedTienSauGiam = soTienSauGiam.toLocaleString('en-US');
                                                    giaSanPhamElement.innerText = formattedTienSauGiam + " vnđ";
                                                })
                                                .catch(error => console.error('Lỗi:', error));
                                        });
                                    </script>
                                </td>
                                <td>
                                    <c:if test="${hoaDon.trangThai == 0}">
                                        <span class="text-secondary">Chờ thanh toán</span>
                                    </c:if>
                                    <c:if test="${hoaDon.trangThai == 1}">
                                        <span class="text-success">Đã hoàn thành</span>
                                    </c:if>
                                    <c:if test="${hoaDon.trangThai == 2 && hoaDon.ngayThanhToan ==null}">
                                        <span class="text-secondary">Chờ xác nhận</span>
                                    </c:if>
                                    <c:if test="${hoaDon.trangThai == 2 && hoaDon.ngayThanhToan !=null}">
                                        <span class="text-success">Đã thanh toán chuyển khoản</span>
                                    </c:if>
                                    <c:if test="${hoaDon.trangThai == 4}">
                                        <span class="text-success">Đã xác nhận</span>
                                    </c:if>
                                    <c:if test="${hoaDon.trangThai == 5}">
                                        <span class="text-danger">Đã huỷ</span>
                                    </c:if>
                                    <c:if test="${hoaDon.trangThai == 10}">
                                        <span class="text-secondary">Đã huỷ/Chờ hoàn tiền</span>
                                    </c:if>
                                </td>
                                <td>
                                    <a href="/admin/hoa-don/${hoaDon.id}" class="btn btn-primary detail-link"
                                       data-tab="tabs-${index.count}">Chi tiết</a>
                                </td>
                            </tr>
                        </c:forEach>
                    </c:if>
                    </tbody>
                </table>
            </div>
        </section>
    </div>
    <div class="mt-3">
        <div class="text-center">
            <c:if test="${param.trangThai == null}">
                <c:if test="${listHoaDon.totalPages > 1}">
                    <ul class="pagination">
                        <li class="page-item <c:if test="${listHoaDon.number == 0}">disabled</c:if>">
                            <a class="page-link" href="?page=1">First</a>
                        </li>
                        <c:forEach var="i" begin="1" end="${listHoaDon.totalPages}">
                            <li class="page-item <c:if test="${listHoaDon.number + 1 == i}">active</c:if>">
                                <a class="page-link" href="?page=${i}">${i}</a>
                            </li>
                        </c:forEach>
                        <li class="page-item <c:if test="${listHoaDon.number == listHoaDon.totalPages-1 }">disabled</c:if>">
                            <a class="page-link" href="?page=${listHoaDon.totalPages}">Last</a>
                        </li>
                    </ul>
                </c:if>
            </c:if>
        </div>
    </div>
</div>
</div>

</body>
</html>

