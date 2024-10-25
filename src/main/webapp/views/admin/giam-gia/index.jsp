<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>
<div class="container mt-3">
    <h1 class="text-center">Quản Lý Giảm Giá</h1>


    <div class="row mt-3">
        <div class="col-6">
            <button type="button" class="btn btn-success" data-bs-toggle="modal" data-bs-target="#exampleModal">
                Thêm Giảm Giá
            </button>
        </div>
    </div>

    <table class="table table-bordered mt-3 text-center">
        <thead>
        <tr>
            <th>STT</th>
            <th>Mã</th>
            <th>Số phần trăm giảm</th>
            <th>Giá Trị Tối Thiểu</th>
            <th>Số lượng</th>
            <th>Ngày bắt đầu</th>
            <th>Ngày kết thúc</th>
            <th>Trạng thái</th>
            <th>Thao Tác</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach varStatus="index" items="${giamGiaPage.content}" var="gg">
            <tr>
                <td>${index.index + giamGiaPage.number * giamGiaPage.size + 1}</td>
                <td>${gg.ma}</td>
                <td>${gg.soPhanTramGiam} %</td>
                <td>
                    <fmt:formatNumber value="${gg.soTienToiThieu}" pattern="#,##0 vnđ"/>
                </td>
                <td>${gg.soLuong}</td>
                <td>${gg.ngayBatDau}</td>
                <td>${gg.ngayKetThuc}</td>
                <td>
                    <c:if test="${gg.trang_thai == 1}">
                        <span class="badge bg-success">Còn hạn</span>
                    </c:if>
                    <c:if test="${gg.trang_thai == 0}">
                        <span class="badge bg-danger">Hết hạn</span>
                    </c:if>
                </td>
                <td>
                        <%--                    <a href="/admin/giam-gia/delete/${gg.id}" class="btn btn-danger"--%>
                        <%--                       onclick="return confirm('Bạn có chắc chắn muốn xoá không?')">Xoá</a>--%>
                    <a href="#" class="update-button"
                       data-bs-toggle="modal" data-bs-target="#exampleModal"
                       data-id="${gg.id}" data-hoVaTen="${gg.ma}" data-soPhanTramGiam="${gg.soPhanTramGiam}"
                       data-soTienToiThieu="${gg.soTienToiThieu}"
                       data-soLuong="${gg.soLuong}" data-ngayBatDau="${gg.ngayBatDau}"
                       data-ngayKetThuc="${gg.ngayKetThuc}">
                        <svg xmlns="http://www.w3.org/2000/svg" width="18" height="18" fill="currentColor"
                             class="bi bi-pencil-fill" viewBox="0 0 16 16">
                            <path d="M12.854.146a.5.5 0 0 0-.707 0L10.5 1.793 14.207 5.5l1.647-1.646a.5.5 0 0 0 0-.708zm.646 6.061L9.793 2.5 3.293 9H3.5a.5.5 0 0 1 .5.5v.5h.5a.5.5 0 0 1 .5.5v.5h.5a.5.5 0 0 1 .5.5v.5h.5a.5.5 0 0 1 .5.5v.207zm-7.468 7.468A.5.5 0 0 1 6 13.5V13h-.5a.5.5 0 0 1-.5-.5V12h-.5a.5.5 0 0 1-.5-.5V11h-.5a.5.5 0 0 1-.5-.5V10h-.5a.5.5 0 0 1-.175-.032l-.179.178a.5.5 0 0 0-.11.168l-2 5a.5.5 0 0 0 .65.65l5-2a.5.5 0 0 0 .168-.11z"/>
                        </svg>
                    </a>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>

    <!-- Modal Thêm và Cập Nhật -->
    <div class="modal fade" id="exampleModal" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true"
         data-bs-backdrop="static">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title" id="exampleModalLabel">Thêm Giảm Giá</h5>
                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                </div>
                <div class="modal-body">
                    <form:form id="edit-form" modelAttribute="gg" method="post" action="/admin/giam-gia/store">
                        <div class="row mb-3">
                            <div class="col">
                                <div a class="form-group">
                                    <label for="soPhanTramGiam">Giá Trị Tối Thiểu</label>
                                    <form:input type="number" path="soTienToiThieu" id="soTienToiThieu"
                                                class="form-control"
                                                min="1" required="true"/>
                                </div>
                            </div>
                        </div>
                        <div class="row mb-3">

                            <div class="col">
                                <div a class="form-group">
                                    <label for="soPhanTramGiam">Số phần trăm</label>
                                    <form:input type="number" path="soPhanTramGiam" id="soPhanTramGiam"
                                                class="form-control"
                                                min="1" max="100" required="true"/>
                                </div>
                            </div>

                            <div class="col">
                                <div class="form-group">
                                    <label for="soLuong">Số lượng</label>
                                    <form:input type="number" path="soLuong" id="soLuong" class="form-control"
                                                min="0" max="10000" required="true"/>
                                </div>
                            </div>
                        </div>
                        <div class="row mb-3">
                            <div class="col">
                                <div class="form-group">
                                    <label for="ngayBatDau">Ngày Bắt Đầu</label>
                                    <form:input type="date" path="ngayBatDau" id="ngayBatDau" class="form-control"
                                                required="true"/>
                                </div>
                            </div>
                            <div class="col">
                                <div class="form-group">
                                    <label for="ngayKetThuc">Ngày Kết Thúc</label>
                                    <form:input type="date" path="ngayKetThuc" id="ngayKetThuc" class="form-control"
                                                required="true"/>
                                </div>
                            </div>

                        </div>


                        <button type="submit" class="btn btn-success mt-3">Lưu</button>
                    </form:form>
                </div>
            </div>
        </div>
    </div>

</div>
</div>
<div class="mt-6">
    <div class="text-center">
        <c:if test="${giamGiaPage.totalPages > 1}">
            <ul class="pagination justify-content-center">
                <li class="page-item <c:if test="${giamGiaPage.number == 0}">disabled</c:if>">
                    <a class="page-link" href="?page=1">First</a>
                </li>
                <c:forEach var="i" begin="1" end="${giamGiaPage.totalPages}">
                    <li class="page-item${giamGiaPage.number + 1 == i ? ' active' : ''}">
                        <a class="page-link" href="?page=${i}">${i}</a>
                    </li>
                </c:forEach>
                <li class="page-item <c:if test="${giamGiaPage.number == giamGiaPage.totalPages-1}">disabled</c:if>">
                    <a class="page-link" href="?page=${giamGiaPage.totalPages}">Last</a>
                </li>
            </ul>
        </c:if>
    </div>
</div>

<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
<script>
    $(".update-button").click(function () {
        let id = $(this).data("id");

        // Thực hiện yêu cầu AJAX để lấy dữ liệu giảm giá dựa trên id
        $.ajax({
            url: "/admin/giam-gia/get/" + id,
            type: "GET",
            success: function (data) {
                // Đặt giá trị cho các trường trong modal bằng dữ liệu từ yêu cầu AJAX
                $("#ma").val(data.ma);
                $("#soPhanTramGiam").val(data.soPhanTramGiam);
                $("#soTienToiThieu").val(data.soTienToiThieu);
                $("#soLuong").val(data.soLuong);
                $("#ngayBatDau").val(data.ngayBatDau);
                $("#ngayKetThuc").val(data.ngayKetThuc);

                // Đặt action của form trong modal (action cập nhật với ID của giảm giá)
                $("#edit-form").attr("action", "/admin/giam-gia/update/" + id);
            },
            error: function (error) {
                console.error("Error:", error);
            },
        });
    });

    $(document).ready(function () {
        var form = $("#edit-form");
        var ngayBatDau = $("#ngayBatDau");
        var ngayKetThuc = $("#ngayKetThuc");
        hideErrorMessage();
        hideErrorMessage2();

        form.submit(function (event) {
            var startDate = new Date(ngayBatDau.val());
            var endDate = new Date(ngayKetThuc.val());

            if (endDate <= startDate) {
                event.preventDefault(); // Ngăn chặn việc gửi form nếu ngày kết thúc không hợp lệ
                alert('Ngày kết thúc phải sau ngày bắt đầu.');
            }
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

