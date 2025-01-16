<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="f" uri="jakarta.tags.functions" %>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<link rel="preconnect" href="https://fonts.googleapis.com">
<link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>

<html lang="en">
<style>


    #searchResults {
        list-style-type: none;
        padding: 0;
        margin: 0;
    }

    .custom-border {
        border: 2px solid black; /* Độ dày và màu sắc của viền */
        border-radius: 10px; /* Bo góc cho viền */
        box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1); /* Đổ bóng cho viền */
        padding: 20px; /* Khoảng cách bên trong */
    }

    .result-item {
        padding: 8px;
        background-color: white;
        border: 1px solid #ddd;
        cursor: pointer;
    }

    .result-item:hover {
        background-color: #f5f5f5;
    }

    .not-found {
        padding: 8px;
        background-color: white;
        color: red;
        font-style: italic;
    }

    .oval-switch {
        display: inline-block;
        width: 50px;
        height: 26px;
        background-color: #ccc;
        border-radius: 13px;
        position: relative;
    }

    .oval-switch input {
        display: none;
    }

    .oval-switch .slider {
        position: absolute;
        cursor: pointer;
        background-color: #00FF00; /* Màu xanh khi bật */
        width: 24px;
        height: 24px;
        border-radius: 50%;
        left: 1px;
        transition: 0.4s;
    }

    .oval-switch input:checked + .slider {
        background-color: #00FF00; /* Màu xanh khi bật */
        left: 25px;
    }

    .oval-switch input:not(:checked) + .slider {
        background-color: #fff; /* Màu trắng khi tắt */
        left: 1px;
    }

    .card {
        border: 1px solid #e0e0e0;
        border-radius: 10px;
        margin: 20px;
    }

    .card-header {
        background-color: #007bff;
        color: #fff;
        border-radius: 10px 10px 0 0;
    }

    .card-body {
        padding: 20px;
    }

    .btn-primary {
        background-color: #007bff;
        border: none;
    }

    .btn-primary:hover {
        background-color: #0056b3;
    }

    .row {
        margin-bottom: 20px;
    }

    .row.border {
        border: 1px solid #e0e0e0;
        border-radius: 10px;
        padding: 20px;
    }

    table {
        width: 100%;
    }

    table th {
        background-color: #007bff;
        color: #fff;
    }

    table tbody tr:nth-child(even) {
        background-color: #f2f2f2;
    }

    table tbody tr:nth-child(odd) {
        background-color: #fff;
    }

    table td {
        text-align: center;
    }

    input[type="text"],
    input[type="number"],
    .form-control,
    select.form-select,
    textarea {
        width: 100%;
        padding: 10px;
        border: 1px solid #ccc;
        border-radius: 5px;
    }

    .float-start {
        float: left;
    }

    .float-end {
        float: right;
    }

    .fw-bold {
        font-weight: bold;
    }

    .image-input {
        display: none;
    }

    .image-preview-container {
        position: relative;
        width: 300px;
        height: 300px;
        margin: 10px;
        border: 1px dashed #ccc;
        text-align: center;
        cursor: pointer; /* Sử dụng con trỏ kiểu tay khi di chuột vào */
    }

    .custom-table thead {
        background-color: #007bff;
        color: white;
    }

    .custom-table tbody tr:nth-child(odd) {
        background-color: #f2f2f2;
    }

    .custom-table tbody tr:hover {
        background-color: #e9ecef;
    }

    .custom-table th,
    .custom-table td {
        vertical-align: middle;
    }

    .image-preview {
        max-width: 100%;
        max-height: 100%;
        display: none;
    }

    .image-placeholder {
        position: absolute;
        top: 50%;
        left: 50%;
        transform: translate(-50%, -50%);
        font-size: 36px;
        color: #333;
    }

    /* Ẩn label khi đã chọn ảnh */
    .image-input-label.selected {
        display: none;
    }
</style>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Quản lý hóa đơn</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-T3c6CoIi6uLrA9TneNEoa7RxnatzjcDSCmG1MXxSR1GAsXEV/Dwwykc2MPK8M2HN" crossorigin="anonymous">
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.4/css/all.min.css" rel="stylesheet">
    <script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>

</head>


<body>

<c:if test="${not empty sessionScope.successMessage}">
    <script>
        Swal.fire({
            title: "Thành Công!",
            text: "${sessionScope.successMessage}",
            icon: "success"
        });
    </script>
    <% session.removeAttribute("successMessage"); %>
</c:if>
<c:if test="${not empty sessionScope.errorMessage}">
    <script>
        Swal.fire({
            title: "Thất Bại!",
            text: "${sessionScope.errorMessage}",
            icon: "error"
        });
        <% session.removeAttribute("errorMessage"); %>
    </script>
</c:if>

<div class="card border rounded">
    <div class="card-header">
        <div class="row mb-1"> <!-- Thêm class mb-1 để giảm khoảng cách -->
            <div class="col">
                <p class="fw-bold fs-4 text-dark">Bán Hàng Tại Quầy</p>
            </div>
            <div class="col d-flex justify-content-end align-items-center">
                <form method="post" action="/admin/ban-hang/add-hoa-don">
                    <input type="hidden" name="id" value="${admin.id}">
                    <button type="submit" class="btn btn-primary">Tạo mới</button>
                </form>
            </div>
        </div>
        <div class="row mt-1"> <!-- Thêm class mt-1 để giảm khoảng cách -->
            <div class="col text-center">
                <p class="fw-bold">Đơn Hàng: ${hoaDon.ma}</p>
            </div>
        </div>
        <div class=" row justify-content-center">
            <c:forEach items="${listHoaDon}" var="hoaDon" varStatus="index">
                <div class="d-grid gap-2 col-1">
                    <a href="/admin/ban-hang/view-hoa-don/${hoaDon.id}" class="btn btn-primary">
                            ${hoaDon.ma}
                    </a>
                </div>
            </c:forEach>
        </div>
    </div>

    <div class="card-body ">
        <!-- Giỏ hàng -->
        <div class="row border mt-2 gap-3">
            <div class="col-9 border custom-border ">
                <div class="d-flex justify-content-between align-items-center mb-3">
                    <p class="fw-bold mb-0">Giỏ Hàng</p>
                    <div>
                        <button class="btn btn-primary" data-bs-toggle="modal" data-bs-target="#SPModal"
                                id="SPModal_btn">
                            Thêm sản phẩm
                        </button>
                        <button type="button" class="btn btn-primary" data-bs-toggle="modal" data-bs-target="#QRModal"
                                id="QRModal_btn">
                            Quét QR
                        </button>
                        <button type="button" class="btn btn-primary" data-bs-toggle="modal" data-bs-target="#MASPModal"
                                id="MASPModal_btn">
                            Thêm với mã SP
                        </button>
                    </div>
                </div>
                <table class="table text-center custom-table">
                    <thead>
                    <tr>
                        <th scope="col">#</th>
                        <th scope="col" style="width: 150px;"><i class="fas fa-image"></i></th>
                        <th scope="col">Sản phẩm</th>
                        <th scope="col">Giá</th>
                        <th scope="col">Số lượng</th>
                        <th scope="col">Tổng tiền</th>
                        <th scope="col">Thao tác</th>
                    </tr>
                    </thead>
                    <tbody>
                    <form id="paymentForm" action="/create-payment-link" method="post">
                        <button type="submit" style="display: none">Tạo Link thanh toán</button>
                        <input type="hidden" name="tongTien" value="" id="total">
                        <input type="hidden" name="ma" value="${hoaDon.ma}">
                        <input type="hidden" name="idHoaDon" value="${idHoaDon}">
                    </form>
                    <c:forEach items="${listSanPhamTrongGioHang}" var="sp" varStatus="status">
                        <tr>
                            <td>${status.index + 1}</td>
                            <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
                            <script>
                                $(document).ready(function () {
                                    var idSanPham = '${sp.idSanPham}';
                                    $.ajax({
                                        url: '/get-anh-san-pham/' + idSanPham,
                                        type: 'GET',
                                        dataType: 'json',
                                        success: function (data) {
                                            var listAnhSanPham = data;
                                            var carouselInner = $('#carouselExampleSlidesOnly_${sp.idSanPham} .carousel-inner');
                                            carouselInner.empty();
                                            $.each(listAnhSanPham, function (index, anhSanPham) {
                                                var isActive = index === 0 ? 'active' : '';
                                                var carouselItem = '<div class="carousel-item ' + isActive + '">'
                                                    + '<img src="' + anhSanPham.duongDan + '" class="d-block" id="custom-anh" style="width: 150px; height: 150px">'
                                                    + '</div>';
                                                carouselInner.append(carouselItem);
                                            });
                                        },
                                        error: function () {
                                            console.log('Lỗi khi lấy danh sách ảnh sản phẩm');
                                        }
                                    });
                                });
                            </script>
                            <td>
                                <div id="carouselExampleSlidesOnly_${sp.idSanPham}"
                                     class="carousel slide" data-bs-ride="carousel" data-bs-interval="3000">
                                    <div class="carousel-inner" style="width: 150px; height: 150px">
                                        <c:forEach items="${listAnhSanPham}" var="anhSanPham" varStatus="status">
                                            <div class="carousel-item ${status.index == 0 ? 'active' : ''}">
                                                <img src="${anhSanPham.duongDan}" class="d-block"
                                                     style="width: 150px; height: 150px">
                                            </div>
                                        </c:forEach>
                                    </div>
                                </div>
                            </td>
                            <td>
                                <p>${sp.tenSanPham}</p>
                                <p>${sp.tenMau}/${sp.tenSize}</p>
                            </td>
                            <td id="formattedGia_${sp.idSanPham}">${sp.gia}</td>
                            <script>
                                var giaSanPhamElement = document.getElementById("formattedGia_${sp.idSanPham}");
                                var giaSanPhamText = giaSanPhamElement.innerText;
                                var formattedGia = parseInt(giaSanPhamText.replace(/[^\d]/g, '')).toLocaleString('en-US');
                                giaSanPhamElement.innerText = formattedGia + " vnđ";
                            </script>
                            <td>${sp.soLuong}</td>
                            <td id="formattedTotal_${sp.idSanPham}">${sp.soLuong * sp.gia}</td>
                            <script>
                                var tongGiaSanPhamElement = document.getElementById("formattedTotal_${sp.idSanPham}");
                                var tongGiaSanPhamText = tongGiaSanPhamElement.innerText;
                                var formattedTongGia = parseInt(tongGiaSanPhamText.replace(/[^\d]/g, '')).toLocaleString('en-US');
                                tongGiaSanPhamElement.innerText = formattedTongGia + " vnđ";
                            </script>
                            <input type="hidden" id="quantity" value=""/>
                            <c:set var="tongTien" value="${tongTien + (sp.soLuong * sp.gia)}"/>
                            <td>
                                <c:if test="${hoaDon.trangThai == 0}">
                                    <form method="post" action="/admin/ban-hang/delete-gio-hang/${sp.idHoaDonChiTiet}">
                                        <div class="input-group">
                                            <input type="number" name="soLuong" min="1" max="${sp.soLuong}" value="1"
                                                   class="w-50"
                                                   oninput="this.value = this.value.replace(/[^0-9]/g, '');">
                                            <div class="input-group-append mt-2">
                                                <button type="submit" style="border: none" class="ms-2">
                                                    <i class="fas fa-trash" style="color: #fb0404;"></i>
                                                </button>
                                            </div>
                                        </div>
                                    </form>
                                </c:if>
                                <c:if test="${hoaDon.trangThai == 1}">
                                    <span class="text-danger">Không thể thao tác</span>
                                </c:if>
                            </td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
            </div>
            <div class="col border custom-border">
                <div class="row">
                    <div class="col">
                        <div class="mb-3 ">
                            <label for="searchInputKh"
                                   class="form-label fw-bold text-center d-flex justify-content-center">Tìm kiếm khách
                                hàng</label>
                            <input type="text" class="form-control" id="searchInputKh"
                                   placeholder="Nhập tên khách hàng">
                        </div>
                        <!-- Dropdown sẽ hiển thị kết quả tìm kiếm -->
                        <ul id="searchResults" class="list-group" style="display: none;"></ul>
                    </div>
                </div>
                <div class="row">
                    <div class="col">
                        <div class="mb-3">
                            <p class="fw-bold text-center">Thông tin khách hàng</p>
                            <p class="text-center" id="tenKhachHang">Tên khách hàng: Khách Lẻ</p>
                            <p class="text-center" id="emailKhachHang"></p>
                            <p class="text-center" id="soDienThoaiKhachHang">Số điện thoại: </p>
                            <p class="text-center" id="diaChiKhachHang">Địa chỉ: </p>
                        </div>
                    </div>
                </div>

                <input type="hidden" id="idKhachHangInput" value="">
            </div>
        </div>
        <!-- Thanh toán -->
        <form method="post" action="/admin/ban-hang/thanh-toan/${idHoaDon}" id="thong-tin-thanh-toanForm"
              enctype="multipart/form-data">
            <input type="hidden" name="idKhachHang" id="idKhachHang">
            <div class="row border mt-3">
                <div class="row mb-3">
                    <div class="col">
                        <p class="fw-bold fs-4 text-dark">Thông tin thanh toán</p>
                    </div>

                    <div class="col">
                        <div class="mb-3 mt-2 d-flex justify-content-end align-items-center">
                            <label class="form-label me-2 mb-0 fw-bold">Giao Hàng</label>
                            <label class="oval-switch mb-0">
                                <input type="checkbox" id="toggleSwitch">
                                <span class="slider"></span>
                            </label>
                        </div>

                    </div>
                </div>
                <hr>
                <div class="row">
                    <div class="col-6" id="form-khach-hang">
                        <div class="row mb-3">
                            <div class="col">
                                <div class="form-group">
                                    <label for="hoVaTen" class="form-label">Họ và tên</label>
                                    <input type="text" id="hoVaTen" class="form-control" name="nguoiNhan"
                                    />
                                </div>
                            </div>
                            <div class="col">
                                <div class="form-group">
                                    <label class="form-label">SĐT</label>
                                    <input type="text" class="form-control" name="sdt"
                                           id="sdt"/>
                                </div>
                            </div>
                        </div>
                        <div class="row mb-3">
                            <div class="col" style="display: none" id="tinh-div">
                                <select id="provinceSelect" class="form-select">
                                    <option value="" disabled selected>Chọn tỉnh/thành phố</option>
                                </select>
                                <input type="hidden" id="provinceName" name="tinhThanh">
                            </div>
                            <div class="col" style="display: none" id="huyen-div">
                                <select id="districtSelect" class="form-select">
                                    <option value="" disabled selected>Chọn quận/huyện</option>
                                </select>
                                <input type="hidden" id="districtName" name="quanHuyen">
                            </div>
                            <div class="col" style="display: none" id="xa-div">
                                <select id="wardSelect" class="form-select">
                                    <option value="" disabled selected>Chọn phường/xã</option>
                                </select>
                                <input type="hidden" id="wardName" name="xaPhuong">
                            </div>

                        </div>
                        <div class="row mb-3">
                            <div class="col" style="display: none" id="ma-van-chuyen-div">
                                <input type="text" class="form-control" id="ma-van-chuyen" name="maVanChuyen"
                                       placeholder="Mã vận chuyển" readonly>
                            </div>
                            <div class="col" style="display: none" id="ten-don-vi-div">
                                <input type="text" id="ten-don-vi" class="form-control" name="tenDonViVanChuyen"
                                       placeholder="Tên đơn vị vận chuyển" readonly>
                            </div>
                            <div class="col" style="display: none" id="dia-chi-chi-tiet-div">
                                <input type="text" class="form-control" id="dia-chi" name="diaChi" rows="3"
                                       placeholder="Địa chỉ chi tiết">
                            </div>

                        </div>
                        <div class="row mb-2 justify-content-center">
                            <div class="col-auto">
                                <label for="imageInput" class="image-preview-container">
                                    <label class="form-label">Ảnh chuyển khoản</label>
                                    <img id="imageDisplay" class="image-preview" src="" alt="Image">
                                    <span class="image-placeholder" id="placeholder1">+</span>
                                </label>
                                <input name="anh" type="file" id="imageInput" class="image-input" accept="image/*"
                                       onchange="displayImage();">
                            </div>
                        </div>

                        <script>
                            function displayImage() {
                                var input = document.getElementById('imageInput');
                                var imageDisplay = document.getElementById('imageDisplay');
                                var placeholder = document.getElementById('placeholder1'); // Đã thêm id vào placeholder

                                if (input.files && input.files[0]) {
                                    var reader = new FileReader();

                                    reader.onload = function (e) {
                                        imageDisplay.src = e.target.result;
                                        imageDisplay.style.display = 'block';
                                        placeholder.style.display = 'none';
                                        // Chuyển đổi ảnh thành base64 và hiển thị nó
                                        convertToBase64(input.files[0], function (base64Image) {
                                            // Lưu base64Image vào một biến hoặc gửi nó điều kiện cần thiết
                                        });
                                    };
                                    console.log(input.files[0]);

                                    reader.readAsDataURL(input.files[0]);
                                } else {
                                    imageDisplay.src = ''; // Xóa ảnh nếu không có tệp được chọn
                                    imageDisplay.style.display = 'none';
                                    placeholder.style.display = 'block';
                                }
                            }

                            function convertToBase64(file, callback) {
                                var reader = new FileReader();
                                reader.readAsDataURL(file);
                                reader.onload = function () {
                                    var base64Image = reader.result.split(',')[1];
                                    callback(base64Image);
                                };
                                reader.onerror = function (error) {
                                    console.error('Error reading file: ', error);
                                };
                            }
                        </script>
                    </div>

                    <div class="col-6" id="thong-tin-thanh-toan">
                        <div class="row mb-3">
                            <div class="col" id="giam-gia-div">
                                <div class="mb-3">
                                    <label class="form-label">Giảm Giá</label>
                                    <select class="form-select" id="giam-gia" name="giamGia"
                                            aria-label="Default select example">
                                        <!-- Các option được tạo từ danh sách listGG -->
                                        <option value="">Không sử dụng</option>
                                        <c:forEach items="${listGG}" var="lshgg">
                                            <fmt:parseNumber var="tongTienSo" value="${tongTien}"/>
                                            <fmt:parseNumber var="soTienToiThieu" value="${lshgg.soTienToiThieu}"/>
                                            <c:if test="${lshgg.soLuong >=0}">
                                                <c:if test="${tongTienSo >= soTienToiThieu}">
                                                    <option value="${lshgg.id}"
                                                            data-soPhanTramGiam="${lshgg.soPhanTramGiam}">${lshgg.soPhanTramGiam}%
                                                    </option>
                                                </c:if>
                                            </c:if>
                                        </c:forEach>
                                    </select>
                                </div>
                            </div>
                            <div class="col" id="hinh-thuc-thanh-toan-div">
                                <div class="mb-3">
                                    <label class="form-label">Hình thức thanh toán</label>
                                    <select class="form-select" id="hinh-thuc-thanh-toan" name="httt"
                                            aria-label="Default select example">
                                        <!-- Vòng lặp để tạo các tùy chọn từ danh sách listHTTT -->
                                        <c:forEach items="${listHTTT}" var="lshttt">
                                            <c:if test="${lshttt.trangThai == 1}">
                                                <option value=${lshttt.id} selected>${lshttt.ten}</option>
                                            </c:if>
                                        </c:forEach>
                                    </select>
                                </div>
                            </div>
                        </div>

                        <div class="row mb-3">
                            <div class="col">
                                <label class="form-label">Tổng Tiền</label>
                                <input type="text" class="form-control" id="tong-tien" name="tong-tien"
                                       value="${tongTien}" readonly>
                            </div>
                            <div class="col">
                                <label class="form-label">Tiền Cần Thanh Toán</label>
                                <input type="text" class="form-control" id="tien-thanh-toan" value="0"
                                       name="tien-thanh-toan" readonly>
                            </div>
                        </div>
                        <div class="row mb-3">
                            <div class="col">
                                <div class="mb-3" id="tien-khach-dua-div">
                                    <label class="form-label">Tiền khách đưa</label>
                                    <input type="number" class="form-control" id="tien-khach-dua" value="0"
                                           name="tienKhachDua" step="0.01">
                                </div>
                            </div>
                            <div class="col" id="phi-van-chuyen-div" style="display: none">
                                <label class="form-label">Phí Vận Chuyển</label>
                                <input class="form-control" type="number" id="feeInput" name="phiVanChuyen" value="0"
                                       readonly>
                            </div>
                        </div>
                        <script>
                            const calculateShippingFee = (huyen, xa) => {
                                $.ajax({
                                    url: 'https://dev-online-gateway.ghn.vn/shiip/public-api/v2/shipping-order/fee',
                                    type: 'GET',
                                    data: {
                                        service_type_id: '2',
                                        to_district_id: huyen,
                                        to_ward_code: xa,
                                        height: '9',
                                        length: '29',
                                        weight: '5000',
                                        width: '18',
                                    },
                                    headers: {
                                        token: '442f5c30-4906-11ef-8e53-0a00184fe694',
                                        shop_id: '193116',
                                        ContentType: 'application/json',
                                    },
                                    success: (response) => {
                                        if (response && response.data && response.data.total !== undefined) {
                                            const feeResponse = response.data.total;
                                            var feeText = feeResponse.toLocaleString('vi-VN');
                                            console.log("tính phí ship: " + feeResponse);
                                            document.getElementById('feeInput').value = feeText;
                                            document.getElementById('ma-van-chuyen').value = "${hoaDon.ma}";
                                            document.getElementById('ten-don-vi').value = "Giao Hàng Nhanh";
                                            // Hiển thị phần phí vận chuyển
                                        } else {
                                            console.error('Lỗi khi gọi API tính phí ship: ', response);
                                            // Xử lý lỗi và cập nhật giá trị mặc định hoặc 0
                                            document.getElementById('feeInput').value = '0';
                                            document.getElementById('phi-van-chuyen-div').style.display = 'none';
                                        }
                                    },
                                    error: (xhr, status, error) => {
                                        console.error('Lỗi khi gọi API tính phí ship:', error);
                                        // Xử lý lỗi và cập nhật giá trị mặc định hoặc 0
                                        document.getElementById('feeInput').value = '0';
                                        document.getElementById('phi-van-chuyen-div').style = 'none';
                                    }
                                });
                            };
                        </script>
                        <div class="row mb-3">
                            <div class="col">
                                <label class="form-label">Tiền Giảm</label>
                                <input type="text" class="form-control" id="tien-giam" name="tien-giam" value="0"
                                       readonly>
                            </div>
                            <div class="col">
                                <div class="mb-3" id="tien-thua-div">
                                    <label class="form-label">Tiền Thừa</label>
                                    <input type="text" class="form-control" id="tien-thua" value="0" readonly>
                                </div>
                            </div>
                        </div>
                        <div class="row mb-3">
                            <div class="col">
                                <div class="mb-3">
                                    <label class="form-label">Ghi chú</label>
                                    <textarea class="form-control" id="ghi-chu" name="ghiChu" rows="3"
                                              placeholder="Ghi chú"></textarea>
                                </div>
                            </div>
                        </div>
                        <div class="row mb-3 justify-content-center">
                            <div class="col-auto">
                                <c:if test="${tongTien>0}">
                                    <button type="submit" class="btn btn-primary" id="thanh_toan"
                                            onclick="exportPDFBill()">Thanh toán
                                    </button>
                                </c:if>
                                <c:if test="${tongTien==null}">
                                    <button type="submit" class="btn btn-primary" id="thanh_toan"
                                            onclick="exportPDFBill()" disabled>Thanh toán
                                    </button>
                                </c:if>
                            </div>
                        </div>

                        <script>
                            // Nếu URL không phải view-hoa-don thì không cho thanh toán và tạo đơn hàng
                            var url = window.location.href;
                            if (url.indexOf("view-hoa-don") == -1) {
                                document.getElementById("thanh_toan").disabled = true;
                                // Disable modal thêm sản phẩm, thêm với mã sản phẩm, quét mã QR
                                document.getElementById("SPModal_btn").disabled = true;
                                document.getElementById("MASPModal_btn").disabled = true;
                                document.getElementById("QRModal_btn").disabled = true;
                            }
                        </script>
                    </div>

                </div>
            </div>
        </form>
    </div>
</div>
<!-- Modal QR -->
<div class="modal fade" id="QRModal" data-bs-backdrop="static" data-bs-keyboard="false" tabindex="-1"
     aria-labelledby="staticBackdropLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
            <div class="modal-body">
                <div class="form-group">
                    <label>Quét mã QR code</label>
                    <div class="video-container">
                        <video id="qr-video" width="300px" height="300px"></video>
                        <canvas id="qr-canvas" style="display: none;"></canvas>
                        <div id="qr-result">Chưa có mã QR code được quét.</div>
                    </div>
                </div>
            </div>

        </div>
    </div>
</div>
<!-- End Modal QR -->
<!-- Modal mã Sản Phẩm -->
<div class="modal fade" id="MASPModal" data-bs-backdrop="static" data-bs-keyboard="false" tabindex="-1"
     aria-labelledby="staticBackdropLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="staticBackdropLabel">Thêm sản phẩm vào giỏ hàng</h5>
                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
            </div>
            <div class="modal-body">
                <!-- Thêm các trường để nhập mã sản phẩm và số lượng -->
                <div class="mb-3">
                    <label for="maSanPhamChiTiet" class="form-label">Mã Sản Phẩm Chi Tiết:</label>
                    <input type="text" class="form-control" id="maSanPhamChiTiet">
                </div>
                <div class="mb-3">
                    <label for="soLuongMoi" class="form-label">Số Lượng:</label>
                    <input type="number" class="form-control" id="soLuongMoi" value="1" min="1">
                </div>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Đóng</button>
                <button type="button" class="btn btn-primary" onclick="themSanPhamVaoGioHang()">Thêm vào giỏ hàng
                </button>
            </div>
        </div>
    </div>
</div>

<!-- End Modal mã Sản Phẩm -->
<!-- Modal Sản Phẩm -->
<div class="modal fade" id="SPModal" data-bs-backdrop="static" data-bs-keyboard="false" tabindex="-1"
     aria-labelledby="staticBackdropLabel" aria-hidden="true">
    <div class="modal-dialog modal-xl modal-dialog-scrollable">
        <div class="modal-content">
            <div class="modal-header">
                <h1 class="modal-title fs-5" id="staticBackdropLabel">Danh sách sản phẩm</h1>
                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
            </div>
            <div class="modal-body">
                <!-- thêm các filter với combox box -->
                <div class="row mt-2">
                    <!-- Tìm kiếm theo tên sản phẩm -->
                    <div class="col-6">
                        <div class="input-group">
                            <input id="searchInput" type="text" class="form-control" placeholder="Tìm kiếm sản phẩm">
                            <button id="searchButton" class="btn btn-primary">Tìm kiếm</button>
                        </div>
                    </div>
                    <div class="col-6 ">
                        <button class="btn btn-primary w100 " id="allProduct">Tất cả sản phẩm</button>
                    </div>
                </div>
                <div class="row">
                    <table class="table text-center">
                        <thead>
                        <tr>
                            <th scope="col">#</th>
                            <th scope="col" style="width: 150px;"><i class="fas fa-image"></i></th>
                            <th scope="col">Sản phẩm</th>
                            <th scope="col">Giá</th>
                            <th scope="col">Số lượng</th>
                            <th scope="col">Thao tác</th>
                        </tr>
                        </thead>
                        <tbody id="productTableBody">
                        <!-- Các dòng sản phẩm sẽ được thêm vào đây -->
                        </tbody>
                    </table>

                    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
                    <script>
                        var allProducts; // Variable to store all products for filtering
                        $(document).ready(function () {
                            // Tải sản phẩm từ API
                            $.ajax({
                                url: '/view/san-pham-chi-tiet',
                                type: 'GET',
                                dataType: 'json',
                                success: function (data) {
                                    // Hiển thị sản phẩm trong bảng
                                    allProducts = data;
                                    renderProducts(allProducts);
                                },
                                error: function () {
                                    console.log('Lỗi khi tải sản phẩm từ API');
                                }
                            });
                            $('#searchButton').on('click', function () {
                                var searchTerm = $('#searchInput').val().toLowerCase();
                                searchProducts(searchTerm);
                            });
                            // Xử lý sự kiện khi nhấn nút "Tất cả sản phẩm"
                            $('#allProduct').on('click', function () {
                                renderProducts(allProducts);
                            });
                        });

                        function renderProducts(products) {
                            // Xóa nội dung bảng hiện tại
                            $('#productTableBody').empty();

                            // Duyệt qua các sản phẩm và thêm dòng vào bảng
                            $.each(products, function (index, product) {
                                var productRow = '<tr>' +
                                    '<td>' + (index + 1) + '</td>' +
                                    '<td>' +
                                    '<div id="carouselExampleSlidesOnly_' + product.idSanPham + '" class="carousel slide" data-bs-ride="carousel" data-bs-interval="1000">' +
                                    '<div class="carousel-inner" style="width: 150px; height: 150px"></div>' +
                                    '</div>' +
                                    '</td>' +
                                    '<td>' +
                                    '<p>' + product.tenSanPham + '</p>' +
                                    '<p>' + product.tenMauSac + '</p>' +
                                    '<p>' + product.tenKichCo + '</p>' +
                                    '</td>' +
                                    '<td>' +
                                    '<p class="fw-bold gia-san-pham" id="gia-san-pham_' + product.id + '">' + product.gia + '</p>' +
                                    '<p class="fw-bold gia-moi" id="gia-moi_' + product.id + '"></p>' +
                                    '</td>' +
                                    '<td>' +
                                    '<p>' + product.soLuong + '</p>' +
                                    '</td>' +
                                    '<td>';

                                if (product.soLuong > 0) {
                                    productRow += '<form method="post" action="/admin/ban-hang/add-gio-hang/${idHoaDon}">' +
                                        '<input type="hidden" name="idSanPhamChiTiet" value="' + product.id + '"/>' +
                                        '<input type="hidden" name="gia" value="' + product.gia + ' " id="gia_' + product.id + '"/>' +
                                        '<input type="hidden" name="soLuong" value="' + product.soLuong + '"/>' +
                                        '<input type="number" name="soLuongMoi" id="soLuongMoi_' + product.id + '" class="form-control" min="1" max="' + product.soLuong + '" value="1" style="width: 100px; display: inline-block;" required="true" oninput="this.value = this.value.replace(/[^0-9]/g, \'\');" onchange="updatePromotion(' + product.id + ')">' +
                                        '<button type="submit" class="btn btn-primary mt-1 w-25">Thêm</button>';
                                } else {
                                    productRow += '<span class="text-danger">Hết hàng</span>';
                                }

                                productRow += '</td></tr>';

                                $('#productTableBody').append(productRow);

                                // Load ảnh sản phẩm
                                loadProductImages(product.idSanPham);

                                // Load discount percentage
                                getDiscountPercentage(product.id);
                            });
                        }

                        function loadProductImages(productId) {
                            var idSanPham = productId;
                            $.ajax({
                                url: '/get-anh-san-pham/' + idSanPham,
                                type: 'GET',
                                dataType: 'json',
                                success: function (data) {
                                    // Xử lý phản hồi từ máy chủ và cập nhật danh sách ảnh
                                    var listAnhSanPham = data;
                                    var carouselInner = $('#carouselExampleSlidesOnly_' + productId + ' .carousel-inner');
                                    carouselInner.empty();

                                    $.each(listAnhSanPham, function (index, anhSanPham) {
                                        var isActive = index === 0 ? 'active' : '';
                                        var carouselItem = '<div class="carousel-item ' + isActive + '">' +
                                            '<img src="' + anhSanPham.duongDan + '" class="d-block" id="custom-anh" style="width: 150px; height: 150px">' +
                                            '</div>';
                                        carouselInner.append(carouselItem);
                                    });
                                },
                                error: function () {
                                    console.log('Lỗi khi lấy danh sách ảnh sản phẩm');
                                }
                            });
                        }

                        function getDiscountPercentage(productId) {
                            $.ajax({
                                url: '/phan-tram-giam/' + productId,
                                type: 'GET',
                                dataType: 'json',
                                success: function (data) {
                                    // Update the UI to display the discount percentage
                                    updateDiscountPercentage(productId, data);
                                },
                                error: function () {
                                    console.log('Lỗi khi tải phần trăm giảm từ API');
                                }
                            });
                        }

                        function updateDiscountPercentage(productId, discountPercentage) {
                            var giaSanPhamId = '#gia-san-pham_' + productId;
                            var giaMoiId = '#gia-moi_' + productId;
                            const giaInput = document.getElementById('gia_' + productId);
                            var giaSanPhamElement = $(giaSanPhamId);
                            var giaMoiElement = $(giaMoiId);

                            var giaSanPham = parseFloat(giaSanPhamElement.text());

                            if (discountPercentage > 0) {
                                var giaMoi = giaSanPham - (giaSanPham * discountPercentage) / 100;
                                giaInput.value = giaMoi;
                                // Update the UI with the discount percentage and the new price
                                giaMoiElement.text('Giảm ' + discountPercentage + '%: ' + giaMoi);
                                giaSanPhamElement.addClass('text-decoration-line-through');
                            } else {
                                // If there is no discount, hide the giaMoiElement
                                giaMoiElement.hide();
                            }
                        }

                        function updatePromotion(productId) {
                            // Your existing code for updating promotion

                            // After updating promotion, fetch and display the discount percentage
                            getDiscountPercentage(productId);
                        }

                        function searchProducts(searchTerm) {
                            // Lọc sản phẩm dựa trên từ khóa tìm kiếm
                            var filteredProducts = [];

                            // Duyệt qua tất cả sản phẩm và kiểm tra tên sản phẩm
                            $.each(allProducts, function (index, product) {
                                if (product.tenSanPham.toLowerCase().includes(searchTerm)) {
                                    filteredProducts.push(product);
                                }
                            });

                            // Hiển thị sản phẩm được lọc
                            renderProducts(filteredProducts);
                        }

                        // filter
                    </script>
                </div>
            </div>
        </div>
    </div>
</div>
</div>
<!-- Modal hiển thị mã QR -->
<div class="modal fade" id="qrCodeModal" tabindex="-1" role="dialog" aria-labelledby="qrCodeModalLabel"
     aria-hidden="true">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="qrCodeModalLabel">Mã QR Code</h5>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
            <div class="modal-body">
                <!-- Nội dung mã QR sẽ được hiển thị ở đây -->
            </div>
        </div>
    </div>
</div>

<script src="https://rawgit.com/schmich/instascan-builds/master/instascan.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"
        integrity="sha384-C6RzsynM9kWDrMNeT87bh95OGNyZPhcTNXj1NW7RuBCsyN/o0jlpcV8Qyq46cDfL"
        crossorigin="anonymous"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/moment.js/2.29.4/moment.min.js"></script>

<script>
    // or via CommonJS
    // bật/tắt giao hàng
    var toggleSwitch = document.getElementById('toggleSwitch');
    var formKhachHang = document.getElementById('form-khach-hang');
    var thongTinThanhToan = document.getElementById('thong-tin-thanh-toan');
    var thanhToan = document.getElementById('thanh_toan');
    var thongTinThanhToanForm = document.getElementById('thong-tin-thanh-toanForm');
    var tienKhachDua = document.getElementById('tien-khach-dua-div');
    var tienThua = document.getElementById('tien-thua-div');
    var phiVanChuyen = document.getElementById('phi-van-chuyen-div');
    var hinhThucThanhToan = document.getElementById('hinh-thuc-thanh-toan-div');
    var giamGia = document.getElementById('giam-gia-div');
    var hoVaTen = document.getElementById('hoVaTen');
    var tinhDiv = document.getElementById('tinh-div');
    var huyenDiv = document.getElementById('huyen-div');
    var xaDiv = document.getElementById('xa-div');
    var tenDV = document.getElementById('ten-don-vi-div');
    var maCH = document.getElementById('ma-van-chuyen-div');
    var diaChiCuThe = document.getElementById('dia-chi-chi-tiet-div');
    toggleSwitch.addEventListener('change', function () {
        if (this.checked) {
            // Switch is ON (bật)
            thanhToan.innerHTML = 'Tạo đơn hàng';
            phiVanChuyen.style.display = 'block';
            // đổi action
            // tienKhachDua.style.display = 'none';
            // hinhThucThanhToan.style.display = 'none';
            // giamGia.style.display = 'none';
            // tienThua.style.display = 'none';
            tinhDiv.style.display = 'block';
            huyenDiv.style.display = 'block';
            xaDiv.style.display = 'block';
            tenDV.style.display = 'block';
            maCH.style.display = 'block';
            diaChiCuThe.style.display = 'block';
            thongTinThanhToanForm.action = '/admin/ban-hang/tao-don-hang/${idHoaDon}';

        } else {
            // Switch is OFF (tắt)
            // disable form input hovaten
            thanhToan.innerHTML = 'Thanh toán';
            tienKhachDua.style.display = 'block';
            tienThua.style.display = 'block';
            phiVanChuyen.style.display = 'none';
            hinhThucThanhToan.style.display = 'block';
            giamGia.style.display = 'block';
            tinhDiv.style.display = 'none';
            huyenDiv.style.display = 'none';
            xaDiv.style.display = 'none';
            tenDV.style.display = 'none';
            maCH.style.display = 'none';
            diaChiCuThe.style.display = 'none';
            // đổi action
            thongTinThanhToanForm.action = '/admin/ban-hang/thanh-toan/${idHoaDon}';

        }
    });

    const scanner = new Instascan.Scanner({video: document.getElementById('qr-video')});
    // Thêm sự kiện cho khi quét QR code thành công
    scanner.addListener('scan', function (content) {
        if (content !== "") {
            // Hiển thị kết quả quét được lên giao diện
            document.getElementById('qr-result').textContent = 'Quét thành công';
            $.ajax({
                url: `/admin/ban-hang/add-gio-hang-qr-code/${idHoaDon}`,
                method: "POST",
                data: {
                    idSanPhamChiTiet: content,
                    gia: $("#gia_" + content).val(),
                    soLuongMoi: 1,
                },
                success: function (response) {
                    // Xử lý khi thêm thành công
                    // alert("Đã thêm sản phẩm vào giỏ hàng!");
                    Swal.fire({
                        title: "Thông Báo!",
                        text: "Đã thêm sản phẩm vào giỏ hàng!",
                        icon: "success"
                    });
                    location.reload();
                },
                error: function (xhr, status, error) {
                    // Xử lý khi có lỗi từ server
                    Swal.fire({
                        title: "Thông Báo!",
                        text: "Lỗi: " + xhr.responseText,
                        icon: "error"
                    });
                    // alert("Lỗi: " + xhr.responseText);
                    // load lại trang
                    location.reload();
                }
            });
        }
    });


    // Khi trang web được tải, bắt đầu quét QR code
    Instascan.Camera.getCameras().then(function (cameras) {
        if (cameras.length > 0) {
            scanner.start(cameras[0]);
        } else {
            Swal.fire({
                title: "Thông Báo!",
                text: "Không tìm thấy máy ảnh",
                icon: "error"
            });
            // alert('Không tìm thấy máy ảnh.');
        }
    }).catch(function (e) {
        console.error(e);
    });

    // Hàm được gọi khi nhấn nút "Thêm vào giỏ hàng" trong modal
    function themSanPhamVaoGioHang() {
        // Lấy giá trị từ các trường nhập liệu trong modal
        var maSanPhamChiTiet = $("#maSanPhamChiTiet").val();
        var soLuongMoi = $("#soLuongMoi").val();

        // Gọi AJAX để thêm sản phẩm vào giỏ hàng
        $.ajax({
            url: `/admin/ban-hang/add-gio-hang-ma-san-pham/${idHoaDon}`,
            method: "POST",
            data: {
                maSanPhamChiTiet: maSanPhamChiTiet,
                soLuongMoi: soLuongMoi,
            },
            success: function (response) {
                // Xử lý khi thêm thành công
                // alert("Đã thêm sản phẩm vào giỏ hàng!");
                Swal.fire({
                    title: "Thông Báo!",
                    text: "Đã thêm sản phẩm vào giỏ hàng!",
                    icon: "success"
                });
                $("#SPModal").modal("hide");  // Đóng modal sau khi thêm sản phẩm thành công
                location.reload();
            },
            error: function (xhr, status, error) {
                // Xử lý khi có lỗi từ server
                Swal.fire({
                    title: "Thông Báo!",
                    text: "Lỗi: " + xhr.responseText,
                    icon: "error"
                });
                // alert("Lỗi: " + xhr.responseText);
                console.log("Lỗi: " + xhr.responseText);
                // load lại trang
                location.reload();
            }
        });
    }


    // Hàm để mở modal khi cần nhập thông tin sản phẩm
    function moModalThemSanPham() {
        // Xử lý mở modal ở đây nếu cần
        $("#SPModal").modal("show");
    }

    //
    function fillCustomerData(customer) {
        $('#tenKhachHang').text('Tên khách hàng: ' + customer.hoVaTen);
        $('#emailKhachHang').text(customer.email);
        $('#soDienThoaiKhachHang').text('Số điện thoại: ' + (customer.soDienThoai ? customer.soDienThoai : 'N/A'));
        $('#diaChiKhachHang').text('Địa chỉ: ' + (customer.diaChi ? customer.diaChi : 'N/A'));
    }

    $(document).ready(function () {
        // Lấy dữ liệu khách hàng từ API
        $.get('http://localhost:8080/view/thong-tin-khach-hang', function (data) {
            // Lưu trữ dữ liệu khách hàng để sử dụng sau này
            var customerData = data;

            // Hàm cập nhật thông tin khách hàng dựa trên ID khách hàng được chọn
            function updateCustomerInfo(selectedCustomerId) {
                var selectedCustomer = customerData.find(function (customer) {
                    return customer.id === selectedCustomerId;
                });
                // Cập nhật thông tin khách hàng trên giao diện
                console.log(selectedCustomer.id)
                $('#hoVaTen').val(selectedCustomer.hoVaTen);
                $('#sdt').val((selectedCustomer.soDienThoai ? selectedCustomer.soDienThoai : 'Chưa Có'));
                $('#dia-chi').val((selectedCustomer.diaChi ? selectedCustomer.diaChi : 'Chưa Có'));
                $('#idKhachHang').val(selectedCustomer.id);
                $('#tenKhachHang').text('Tên khách hàng: ' + selectedCustomer.hoVaTen);
                $('#emailKhachHang').text((selectedCustomer.email ? selectedCustomer.email : ''));
                $('#soDienThoaiKhachHang').text('Số điện thoại: ' + (selectedCustomer.soDienThoai ? selectedCustomer.soDienThoai : ''));
                $('#diaChiKhachHang').text('Địa chỉ: ' + (selectedCustomer.diaChi ? selectedCustomer.diaChi : ''));

                // Cập nhật giá trị ID khách hàng được chọn trong input ẩn
                $('#idKhachHangInput').val(selectedCustomerId);

                fillCustomerData(selectedCustomer);
            }

            // Hàm lọc và cập nhật danh sách khách hàng dựa trên từ khóa tìm kiếm
            function filterCustomers(searchTerm) {
                var filteredCustomers = customerData.filter(function (customer) {
                    // Tìm kiếm không phân biệt chữ hoa, chữ thường trong tên khách hàng và số điện thoại
                    return (
                        customer.hoVaTen.toLowerCase().includes(searchTerm.toLowerCase()) ||
                        (customer.soDienThoai && customer.soDienThoai.includes(searchTerm))
                    );
                });

                // Xóa các lựa chọn hiện tại trong danh sách kết quả
                $('#searchResults').empty();

                if (filteredCustomers.length === 0) {
                    // Hiển thị thông báo khi không tìm thấy khách hàng
                    $('#searchResults').append('<li class="result-item not-found">Không tìm thấy khách hàng</li>');
                } else {
                    // Thêm khách hàng đã lọc vào danh sách kết quả
                    filteredCustomers.forEach(function (customer) {
                        var displayText = customer.hoVaTen + (customer.soDienThoai ? ' - ' + customer.soDienThoai : '');
                        $('#searchResults').append('<li class="result-item" data-id="' + customer.id + '">' + displayText + '</li>');
                    });
                }
            }

            // Xử lý sự kiện input cho ô tìm kiếm khách hàng (tìm kiếm thời gian thực)
            $('#searchInputKh').on('input', function () {
                var searchTerm = $(this).val();
                filterCustomers(searchTerm);

                // Hiện dropdown khi có kết quả tìm kiếm
                if (searchTerm) {
                    $('#searchResults').show();
                } else {
                    $('#searchResults').hide();
                }
            });

            // Xử lý sự kiện click cho việc cập nhật thông tin khách hàng
            $(document).on('click', '.result-item', function () {
                var selectedCustomerId = $(this).data('id');
                updateCustomerInfo(selectedCustomerId);

                // Ẩn dropdown khi chọn một kết quả
                $('#searchResults').hide();
            });
        });

    });


    // api ghn
    $(document).ready(function () {
        // Biến kiểm tra trạng thái đã chọn
        var isDistrictSelected = false;
        var isWardSelected = false;
        var isServiceSelected = false;
        // Gọi API để lấy dữ liệu tỉnh/thành phố
        $.ajax({
            url: 'https://dev-online-gateway.ghn.vn/shiip/public-api/master-data/province',
            method: 'GET',
            headers: {
                'Content-Type': 'application/json',
                'Token': 'a76df0d2-77a1-11ee-b1d4-92b443b7a897'
            },
            success: function (data) {
                if (data.code === 200) {
                    const select = document.getElementById('provinceSelect');
                    const input = document.getElementById('provinceName'); // Get the input element

                    data.data.forEach(province => {
                        const option = document.createElement('option');
                        option.value = province.ProvinceID;
                        option.text = province.ProvinceName;
                        select.appendChild(option);
                    });

                    // Set province name in the input field when a province is selected
                    $('#provinceSelect').change(function () {
                        const selectedProvinceName = $('#provinceSelect option:selected').text();
                        input.value = selectedProvinceName; // Set the value of the input field
                    });
                }
            },
            error: function (error) {
                console.error(error);
            }
        });

        // Gọi API để lấy dữ liệu quận/huyện khi thay đổi tỉnh/thành phố
        $('#provinceSelect').change(function () {
            isDistrictSelected = false; // Đặt lại trạng thái khi thay đổi tỉnh/thành phố
            isWardSelected = false; // Đặt lại trạng thái khi thay đổi tỉnh/thành phố

            const provinceID = $(this).val();
            $.ajax({
                url: 'https://dev-online-gateway.ghn.vn/shiip/public-api/master-data/district',
                method: 'GET',
                headers: {
                    'Content-Type': 'application/json',
                    'Token': 'a76df0d2-77a1-11ee-b1d4-92b443b7a897'
                },
                data: {
                    province_id: provinceID
                },
                success: function (data) {
                    if (data.code === 200) {
                        const select = document.getElementById('districtSelect');
                        const input = document.getElementById('districtName');
                        select.innerHTML = '';
                        data.data.forEach(district => {
                            const option = document.createElement('option');
                            option.value = district.DistrictID;
                            option.text = district.DistrictName;
                            select.appendChild(option);
                        });
                        $('#districtSelect').change(function () {
                            const selectedDistrictName = $('#districtSelect option:selected').text();
                            input.value = selectedDistrictName; // Set the value of the input field
                        });
                    }
                },
                error: function (error) {
                    console.error(error);
                }
            });
        });

        // Gọi API để lấy dữ liệu phường/xã khi thay đổi quận/huyện
        $('#districtSelect').change(function () {
            isDistrictSelected = true; // Đánh dấu trạng thái khi đã chọn quận/huyện
            isWardSelected = false; // Đặt lại trạng thái khi thay đổi quận/huyện

            const districtID = $(this).val();
            $.ajax({
                url: 'https://dev-online-gateway.ghn.vn/shiip/public-api/master-data/ward?district_id=' + districtID,
                method: 'GET',
                headers: {
                    'Content-Type': 'application/json',
                    'Token': 'a76df0d2-77a1-11ee-b1d4-92b443b7a897'
                },
                data: {
                    district_id: districtID
                },
                success: function (data) {
                    if (data.code === 200) {
                        const select = document.getElementById('wardSelect');
                        const input = document.getElementById('wardName');
                        select.innerHTML = '';
                        data.data.forEach(ward => {
                            const option = document.createElement('option');
                            option.value = ward.WardCode;
                            option.text = ward.WardName;
                            select.appendChild(option);
                        })
                        $('#wardSelect').change(function () {
                            const selectedWardName = $('#wardSelect option:selected').text();
                            input.value = selectedWardName; // Set the value of the input field
                        });
                    }
                },
                error: function (error) {
                    console.error(error);
                }
            });
        });


        // tính tiền
        $(document).ready(function () {
            var tongTienInput = $("#tong-tien"); // Lấy ô input của tổng tiền
            var tongTien = parseFloat(tongTienInput.val().replace(/[^\d]/g, '')) || 0;
            tongTienInput.val(tongTien.toLocaleString('vi-VN'));
            var tienThanhToan = $("#tien-thanh-toan");
            tienThanhToan.val(tongTien.toLocaleString('vi-VN'));
            var vanchuyen = $("#toggleSwitch")
            var phuongxa = $("#wardSelect")
            var quanHuyen = $("#districtSelect")
            var tinhTP = $("#provinceSelect")
            var selectElement = $("#hinh-thuc-thanh-toan");
            var selectElementGiamGia = $("#giam-gia");
            var tienKhachDuaInput = $("#tien-khach-dua");
            var tienGiam = $("#tien-giam");
            var tienThuaLabel = $("#tien-thua");
            phuongxa.on("change", function () {
                console.log(quanHuyen.val(), phuongxa.val())
                calculateShippingFee(quanHuyen.val(), phuongxa.val())
            })
            vanchuyen.on("change", function () {
                updateTienGiam();
            })
            // Sự kiện change cho hình thức thanh toán
            selectElement.on("change", function () {
                updateTienKhachDua()
                var tongTienValue = parseFloat(tienThanhToan.val().replace(/[^\d]/g, '')) || 0;
                // Gán giá trị vào input có id="total"
                var totalInput = document.getElementById('total');
                totalInput.value = tongTienValue;

            });
            // Sự kiện change cho giảm giá
            selectElementGiamGia.on("change", function () {
                updateTienGiam(); // Cập nhật tiền khách đưa khi thay đổi hình thức thanh toán
            });

            // Sự kiện blur cho tiền khách đưa
            tienKhachDuaInput.on("blur", function () {
                updateTienThua(); // Cập nhật tiền thừa khi blur khỏi trường tiền khách đưa
            });

            // Sự kiện input để ngăn chặn việc nhập chữ trong trường tiền khách đưa
            tienKhachDuaInput.on("input", function () {
                formatCurrency(this); // Gọi hàm formatCurrency để giữ lại chỉ số và dấu phẩy
            });

            function formatCurrency(input) {
                // Giữ lại chỉ các ký tự số và dấu phẩy
                let inputValue = input.value;

                // Định dạng lại giá trị
                inputValue = inputValue.replace(/,/g, '');

                // Gán giá trị đã định dạng lại vào trường input
                input.value = inputValue.toLocaleString('en-US');
            }

            function updateTienKhachDua() {
                var hinhThucThanhToan = selectElement.val().replace(/[^\d]/g, '') || 0;
                var tongTien = parseFloat(tienThanhToan.val().replace(/[^\d]/g, '')) || 0;

                if (hinhThucThanhToan === "2" || hinhThucThanhToan === "3") {
                    tienKhachDuaInput.val(tongTien).toLocaleString('vi-VN');
                } else {
                    tienKhachDuaInput.val("");
                }
            }

            function updateTienGiam() {
                var selectElementGiamGia = $('#giam-gia');
                var selectedOption = selectElementGiamGia.find(':selected');
                var soPhanTramGiam = selectedOption.data('sophantramgiam');

                var tongTien = parseFloat(tongTienInput.val().replace(/[^\d]/g, '')) || 0;
                if (soPhanTramGiam !== undefined) {
                    var tienGiamne = tongTien * (soPhanTramGiam / 100)
                    tienGiam.val(tienGiamne.toLocaleString('vi-VN'));
                    tongTienInput.val(tongTien.toLocaleString('vi-VN'));
                    tienThanhToan.val((tongTien - tienGiamne).toLocaleString('vi-VN'));

                } else {
                    tongTienInput.val(tongTien.toLocaleString('vi-VN'));
                    tienThanhToan.val(tongTien.toLocaleString('vi-VN'));
                    tienGiam.val(0);
                }
            }

            function updateTienThua() {
                var tienKhachDua = parseFloat(tienKhachDuaInput.val().replace(/[^\d]/g, '')) || 0;
                var tongTien = parseFloat(tienThanhToan.val().replace(/[^\d]/g, '')) || 0;
                if (tienKhachDua >= tongTien) {
                    var tienThua = tienKhachDua - tongTien;
                    tienThuaLabel.val(tienThua.toLocaleString('vi-VN'));
                } else {
                    tienThuaLabel.val("0");
                    Swal.fire({
                        title: "Thông Báo!",
                        text: "Tiền khách đưa phải lớn hơn hoặc bằng tổng tiền!",
                        icon: "error"
                    });
                    // alert("Tiền khách đưa phải lớn hơn hoặc bằng tổng tiền!");
                }
            }
        });
        $(document).ready(function () {
            // Sự kiện change trên phần tử select
            $('#hinh-thuc-thanh-toan').on('change', function () {
                var selectedValue = $(this).val();
                var selectedThanhToan = $("#tien-thanh-toan");
                if (isNaN(parseFloat(selectedThanhToan.val()))) {
                    Swal.fire({
                        title: "Thông Báo!",
                        text: "Bạn chưa có giá tiền để thanh toán qr",
                        icon: "error"
                    });
                } else {
                    // // Kiểm tra giá trị được chọn và thực hiện hành động tương ứng
                    if (selectedValue === '1') {
                        // Tiền mặt - ẩn form
                        $('#paymentForm').hide();
                    } else if (selectedValue === '2') {
                        if (parseFloat(selectedThanhToan.val()) == 0) {
                            Swal.fire({
                                title: "Thông Báo!",
                                text: "Bạn chưa có sản phẩm để thanh toán",
                                icon: "error"
                            });
                        }else{
                            // Chuyển khoản - hiển thị form và tự động submit form
                            $('#paymentForm').show().submit();
                        }

                    }
                }
            });
        });

    });

    // send email
    function sendEmail() {
        // gửi đến email khách hàng
        const emailKhachHang = $('#emailKhachHang').text();
        var to = emailKhachHang;
        var maHoaDon = '${hoaDon.ma}';
        $.ajax({
            type: "POST",
            url: "/send",
            data: {to: to, maHD: maHoaDon},
            success: function (response) {
                console.log(response);
            },
            error: function (error) {
            }
        });
    }

    // click thanh toán sẽ gửi email
    // $("#thanh_toan").click(function () {
    //     sendEmail();
    // });
    const emailKhachHang = $('#emailKhachHang').text();
</script>
</body>

</html>
