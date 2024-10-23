<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>



<div class="container mt-3">
    <h1 class="text-center">Quản Lý Khuyến Mãi Chi Tiết</h1>

    <div class="row mt-3">
        <a href="/admin/khuyen-mai/index">
            <div class="col-6">
                <button type="button" class="btn btn-success">
                    <svg xmlns="http://www.w3.org/2000/svg" width="18" height="18" fill="currentColor" class="bi bi-arrow-90deg-left" viewBox="0 0 16 16">
                        <path fill-rule="evenodd" d="M1.146 4.854a.5.5 0 0 1 0-.708l4-4a.5.5 0 1 1 .708.708L2.707 4H12.5A2.5 2.5 0 0 1 15 6.5v8a.5.5 0 0 1-1 0v-8A1.5 1.5 0 0 0 12.5 5H2.707l3.147 3.146a.5.5 0 1 1-.708.708z"/>
                    </svg>
                    Quay lại
                </button>
            </div>
        </a>
    </div>

    <table class="table table-bordered mt-3 text-center">
        <thead>
        <tr>
            <th>STT</th>
            <th>Tên Sản Phẩm</th>
            <th>Loại</th>
            <th>Thao Tác</th>
        </tr>
        </thead>
        <tbody>
        <!-- Bắt đầu tải thư viện jQuery -->
        <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>

        <c:forEach varStatus="index" items="${khuyenMaiChiTietPage}" var="kmct">
            <tr>
                <td>${index.index + 1}</td>
                <td>${kmct.ten}</td>
                <td>${kmct.idLoai.ten}</td>
                <td>
                    <div id="trangThai_${kmct.id}">
                    </div>
                    <script>
                        $(document).ready(function () {
                            var idKhuyenMai = '${idKhuyenMai}';
                            var kmctId = '${kmct.id}';
                            $.ajax({
                                url: "/admin/khuyen-mai/chi-tiet/getTrangThai/" + idKhuyenMai + "/" + kmctId,
                                method: "GET",
                                success: function (data) {
                                    var trangThaiDiv = $("#trangThai_" + kmctId);
                                    if (data === 0) {
                                        trangThaiDiv.html('<a href="/admin/khuyen-mai/add-chi-tiet/' + idKhuyenMai + '/' + kmctId + '" class="btn btn-success">Thêm</a>');
                                    } else if (data === 1) {
                                        trangThaiDiv.html('<a href="/admin/khuyen-mai/delete-chi-tiet/' + idKhuyenMai + '/' + kmctId + '" class="btn btn-danger">Xoá</a>');
                                    }
                                },
                                error: function () {
                                    // Xử lý lỗi nếu có
                                }
                            });
                        });
                    </script>
                </td>
            </tr>
        </c:forEach>

        </tbody>
    </table>
<script>
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
</div>
