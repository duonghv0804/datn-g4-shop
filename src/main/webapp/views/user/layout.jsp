<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<script src="//cdn.jsdelivr.net/npm/sweetalert2@11"></script>
<html>
<head>
    <title>G4 Shop</title>
    <link
            rel="icon"
            href=
                    "/../views/admin/css/Summer.png"
            type="image/x-icon"
    />
    <link rel="stylesheet" href="/../views/user/css/index.css"/>
    <link rel="stylesheet" href="/../views/user/css/styleguide.css"/>
    <link rel="stylesheet" href="/../views/user/css/productdetail.css"/>
    <link rel="stylesheet" href="/../views/user/css/product.css"/>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-T3c6CoIi6uLrA9TneNEoa7RxnatzjcDSCmG1MXxSR1GAsXEV/Dwwykc2MPK8M2HN" crossorigin="anonymous">
    <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.15.4/css/all.css"
          integrity="sha384-DyZ88mC6Up2uqS4h/KRgHuoeGwBcD4Ng9SiP4dIRy0EXTlnuz47vAwmeGwVChigm" crossorigin="anonymous"/>
</head>
<style>
    <%--        fixed navbar--%>
    body {
        padding-top: 70px; /* Adjust this value based on your navbar height */
    }
.update-button{
    display: block;
    width: 100%;
    padding: 0.25rem 1rem;
    clear: both;
    font-weight: 400;
    color: #212529;
    text-align: inherit;
    text-decoration: none;
    white-space: nowrap;
    background-color: transparent;
    border: 0;
}
    .custom-nav {
        position: fixed;
        top: 0;
        left: 0;
        width: 100%;
        z-index: 1000;
        transition: background-color 0.3s;
    }

    .custom-nav.scrolled {
        background-color: #ffffff;
    }

    .menu-div-2 {
        display: flex;
        align-items: center;
    }

    /* Optional: Add margin to the right of the last item to separate it from the dropdown */
    .menu-div-2 .nav-item.dropdown {
        margin-right: 10px; /* Adjust as needed */
    }


</style>
<body>
<header>
    <nav class="navbar navbar-expand-lg navbar-dark bg-dark custom-nav">
        <div class="container">
            <a class="navbar-brand" href="/"><img src="/../views/admin/css/SPORT65.png"  width="80px" height="40px"  alt=""></a>
            <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNav"
                    aria-controls="navbarNav" aria-expanded="false" aria-label="Toggle navigation">
                <span class="navbar-toggler-icon"></span>
            </button>
            <div class="collapse navbar-collapse justify-content-center" id="navbarNav">
                <ul class="navbar-nav m-auto"> <!-- Use 'mr-auto' here -->
                    <li class="nav-item  ">
                        <a style="color: #ffffff" class="nav-link" href="/">Trang chủ</a>
                    </li>
                    <li class="nav-item  ">
                        <a style="color: #ffffff" class="nav-link" href="/san-pham">Sản Phẩm</a>
                    </li>
                    <li class="nav-item  ">
                        <a style="color: #ffffff" class="nav-link" href="/sale">Sale</a>
                    </li>
                    <li class="nav-item">
                        <a style="color: #ffffff" class="nav-link" href="/gioi-thieu">Giới Thiệu</a>
                    </li>
                </ul>
                <div class="menu-div-2 ">
                    <div class="d-flex">

                        <li class="nav-item dropdown">
                            <a class="nav-link" href="/gio-hang" role="button">
                                <i class="fas fa-shopping-cart" style="color: #ffffff;"></i>
                            </a>
                        </li>
                        <li class="nav-item dropdown">
                            <a class="nav-link mb-4 ms-4 " href="#" role="button" data-bs-toggle="dropdown"
                               aria-expanded="false">
                                <i class="fas fa-user" style="color: #ffffff;"></i>
                            </a>
                            <ul class="dropdown-menu">
                                <c:choose>
                                    <c:when test="${not empty sessionScope.khachHang}">
                                        <li><a class="update-button" href="#"
                                               data-bs-toggle="modal" data-bs-target="#exampleModal"
                                               data-id="${sessionScope.khachHang.id}"
                                               data-hoVaTen="${sessionScope.khachHang.hoVaTen}"
                                               data-soDienThoai="${sessionScope.khachHang.soDienThoai}"
                                               data-email="${sessionScope.khachHang.email}"
                                               data-diaChi="${sessionScope.khachHang.diaChi}"
                                               data-xaPhuong="${sessionScope.khachHang.xaPhuong}"
                                               data-quanHuyen="${sessionScope.khachHang.quanHuyen}"
                                               data-tinhThanhPho="${sessionScope.khachHang.tinhThanhPho}">Thông Tin Tài
                                            Khoản</a></li>
                                        <li><a class="dropdown-item" href="/hoa-don">Đơn hàng của tôi</a></li>
                                        <li><a class="dropdown-item" href="/dang-xuat">Đăng xuất</a></li>
                                    </c:when>
                                    <c:otherwise>
                                        <li><a class="dropdown-item" href="/dang-nhap">Đăng nhập</a></li>
                                    </c:otherwise>
                                </c:choose>
                            </ul>
                        </li>
                    </div>
                </div>
            </div>
        </div>
    </nav>
    <!-- Carousel -->
    <jsp:include page="${ viewBanner }"/>
</header>
<!-- Content -->
<main>
    <!-- content -->
    <jsp:include page="${ viewContent }"/>
</main>
<footer>
    <div class="container mt-5">
        <div class="row pt-4">
            <div class="col-md-3 col-12 ">
                <div class="d-flex mb-2">
                    <i class="fas fa-caret-down fa-rotate-270"></i>
                    <div class="text-sm ">
                        <a href="/chinh-sach-bao-mat" class="text-decoration-none text-dark">Chính sách bảo mật</a>
                    </div>
                </div>
            </div>
            <div class="col-md-5 col-12">
                <div class="d-flex mb-2">
                    <div class="text-sm font-weight-bold mr-1">Địa chỉ: </div>
                    <div class="text-sm flex-fill"> Trường cao đẳng FPT Hà Nội</div>
                </div>
                <div class="d-flex mb-2">
                    <div class="text-sm font-weight-bold mr-1">Hotline:</div>
                    <div class="text-sm"> 0123456789</div>
                </div>
            </div>
        </div>

    </div>
    <hr>
    <div class="row col-4 offset-5 pb-3">
        <p> © 2024 G4 . Đã đăng ký Bản quyền</p>
    </div>
</footer>
<!-- Include Bootstrap JS and jQuery (if needed) -->
<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/js/bootstrap.bundle.min.js"
        integrity="sha384-kenU1KFdBIe4zVF0s0G1M5b4hcpxyD9F7jL+jjXkk+Q2h455rYXK/7HAuoJl+0I4"
        crossorigin="anonymous"></script>
</body>

</html>
<div class="modal fade" id="exampleModal" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true"
     data-bs-backdrop="static">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="exampleModalLabel">Thêm Khách Hàng</h5>
                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
            </div>
            <div class="modal-body">
                <form:form id="edit-form" modelAttribute="kh" method="post" action="/admin/khach-hang/store">
                    <div class="row mb-3">
                        <div class="col">
                            <div class="form-group">
                                <label for="hoVaTen" class="form-label">Họ và tên</label>
                                <form:input type="text" path="hoVaTen" id="hoVaTen" class="form-control"/>
                                    <%--                                                required="true"/>--%>
                            </div>
                        </div>
                    </div>
                    <div class="row mb-3">
                        <div class="col">
                            <div class="form-group">
                                <label for="soDienThoai" class="form-label">Số điện thoại</label>
                                <form:input type="tel" path="soDienThoai" id="soDienThoaiTK" class="form-control"/>
                                    <%--                                                required="true"/>--%>
                            </div>
                        </div>
                        <div class="col">
                            <div class="form-group">
                                <label for="email" class="form-label">Email</label>
                                <form:input type="email" path="email" id="emailTK" class="form-control"/>
                                    <%--                                                required="true"/>--%>
                            </div>
                        </div>
                    </div>
                    <div class="row mb-3">
                        <div class="col">
                            <div class="form-group">
                                <label for="tinhThanhPho" class="form-label">Tỉnh/Thành phố</label>
                                <form:input type="text" path="tinhThanhPho" class="form-control" disabled="true"/>
                                <select id="provinceSelect" class="form-select">
                                    <option value="" disabled selected>Chọn tỉnh/thành phố</option>
                                </select>
                                <form:input type="hidden" path="tinhThanhPho" id="provinceName" class="form-control" />
                            </div>
                        </div>
                        <div class="col">
                            <div class="form-group">
                                <label for="quanHuyen" class="form-label">Quận/Huyện</label>
                                <form:input type="text" path="quanHuyen"  class="form-control" disabled="true"/>
                                <select id="districtSelect" class="form-select">
                                    <option value="" disabled selected>Chọn quận/huyện</option>
                                </select>
                                <form:input type="hidden" path="quanHuyen" id="districtName" class="form-control"/>
                            </div>
                        </div>
                    </div>
                    <div class="row mb-3">
                        <div class="col">
                            <div class="form-group">
                                <label for="xaPhuong" class="form-label">Xã/Phường</label>
                                <form:input type="text" path="xaPhuong" class="form-control" disabled="true"/>
                                <select id="wardSelect" class="form-select">
                                    <option value="" disabled selected>Chọn phường/xã</option>
                                </select>
                                <form:input type="hidden" path="xaPhuong" id="wardName" class="form-control"/>
                            </div>
                        </div>
                        <div class="col">
                            <div class="form-group">
                                <label for="diaChi" class="form-label">Địa chỉ</label>
                                <form:input type="text" path="diaChi" id="diaChiTK" class="form-control"/>
                                    <%--                                                required="true"/>--%>
                            </div>
                        </div>
                    </div>
                    <button type="submit" class="btn btn-success mt-3">Lưu</button>
                </form:form>
            </div>
        </div>
    </div>
</div>
<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
<script>
    $(document).ready(function () {
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
    });
</script>
<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
<script>
    $(".update-button").click(function () {
        let id = $(this).data("id");

        // Thực hiện yêu cầu AJAX để lấy dữ liệu khách hàng dựa trên id
        $.ajax({
            url: "get/" + id,
            type: "GET",
            success: function (data) {
                // Đặt giá trị cho các trường trong modal bằng dữ liệu từ yêu cầu AJAX
                $("#hoVaTen").val(data.hoVaTen);
                $("#soDienThoaiTK").val(data.soDienThoai);
                $("#emailTK").val(data.email);
                $("#diaChiTK").val(data.diaChi);
                $("#xaPhuong").val(data.xaPhuong);
                $("#quanHuyen").val(data.quanHuyen);
                $("#tinhThanhPho").val(data.tinhThanhPho);

                // Đặt action của form trong modal (action cập nhật với ID của khách hàng)
                $("#edit-form").attr("action", "update/" + id);
            },
            error: function (error) {
                console.error("Error:", error);
            },
        });
    });
    document.addEventListener("DOMContentLoaded", function () {
        var updateButtons = document.querySelectorAll(".update-button");
        var clickClose = document.querySelector(".btn-close");

        updateButtons.forEach(function (button) {
            button.addEventListener("click", function () {
                var modalTitle = document.querySelector(".modal-title");
                var matKhauInput = document.querySelector("#matKhau");

                // Đặt tiêu đề modal thành "Cập Nhật Khách Hàng"
                modalTitle.textContent = "Sửa Thông Tin Khách Hàng";

                // Vô hiệu hoá ô input mật khẩu
                matKhauInput.setAttribute("disabled", "disabled");
            });
        });
    });

    document.addEventListener("DOMContentLoaded", function () {
        // Kiểm tra thông báo thành công
        <c:if test="${not empty successMessage}">
        Swal.fire({
            position: 'center',
            icon: 'success',
            title: '${successMessage}',
            showConfirmButton: false,
            timer: 2000
        });
        </c:if>

        // Kiểm tra thông báo lỗi
        <c:if test="${not empty errorMessage}">
        Swal.fire({
            position: 'center',
            icon: 'error',
            title: '${errorMessage}',
            showConfirmButton: false,
            timer: 2000
        });
        </c:if>
    });
</script>