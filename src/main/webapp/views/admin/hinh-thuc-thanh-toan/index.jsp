<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>
<div class="container mt-3">
    <h1 class="text-center">Quản Lý Hình Thức Thanh Toán</h1>
    <div class="row mt-3">
        <div class="col-6">
            <button type="button" class="btn btn-success" data-bs-toggle="modal" data-bs-target="#exampleModal">
                Thêm Hình Thức Thanh Toán
            </button>
        </div>
    </div>

    <table class="table table-bordered mt-3 text-center">
        <thead>
        <tr>
            <th>STT</th>
            <th>Mã</th>
            <th>Tên</th>
            <th>Trạng thái</th>
            <th>Thao Tác</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach varStatus="index" items="${htttPage.content}" var="gg">
            <tr>
                <td>${index.index + htttPage.number * htttPage.size + 1}</td>
                <td>${gg.ma}</td>
                <td>${gg.ten}</td>
                <td>
                    <c:if test="${gg.trangThai == 1}">
                        <span class="badge bg-success">Hoạt Động</span>
                    </c:if>
                    <c:if test="${gg.trangThai == 0}">
                        <span class="badge bg-danger">Không Hoạt Động</span>
                    </c:if>
                </td>
                <td>
                        <%--                    <a href="/admin/giam-gia/delete/${gg.id}" class="btn btn-danger"--%>
                        <%--                       onclick="return confirm('Bạn có chắc chắn muốn xoá không?')">Xoá</a>--%>
                    <a href="#" class="update-button"
                       data-bs-toggle="modal" data-bs-target="#exampleModal"
                       data-id="${gg.id}" data-hoVaTen="${gg.ma}" data-ten="${gg.ten} "
                       data-trangThai="${gg.trangThai}">
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
                    <h5 class="modal-title" id="exampleModalLabel">Hình Thức Thanh Toán</h5>
                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                </div>
                <div class="modal-body">
                    <form:form id="edit-form" modelAttribute="gg" method="post"
                               action="/admin/hinh-thuc-thanh-toan/store">
                        <div class="row mb-3">
                            <div class="col">
                                <div class="form-group">
                                    <label for="ten">Tên</label>
                                    <form:input type="text" path="ten" id="ten" class="form-control" required="true"
                                                maxlength="50"/>
                                </div>
                            </div>
                            <div class="col">
                                <div class="form-group">
                                    <label for="trangThai" class="form-label">Trạng thái</label>
                                    <form:select path="trangThai" id="trangThai" class="form-control" required="true">
                                        <option value="1">Hoạt Động</option>
                                        <option value="0">Không Hoạt Động</option>
                                    </form:select>
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
        <c:if test="${htttPage.totalPages > 1}">
            <ul class="pagination justify-content-center">
                <li class="page-item <c:if test="${htttPage.number == 0}">disabled</c:if>">
                    <a class="page-link" href="?page=1">First</a>
                </li>
                <c:forEach var="i" begin="1" end="${htttPage.totalPages}">
                    <li class="page-item${htttPage.number + 1 == i ? ' active' : ''}">
                        <a class="page-link" href="?page=${i}">${i}</a>
                    </li>
                </c:forEach>
                <li class="page-item <c:if test="${htttPage.number == htttPage.totalPages-1}">disabled</c:if>">
                    <a class="page-link" href="?page=${htttPage.totalPages}">Last</a>
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
            url: "/admin/hinh-thuc-thanh-toan/get/" + id,
            type: "GET",
            success: function (data) {
                // Đặt giá trị cho các trường trong modal bằng dữ liệu từ yêu cầu AJAX
                $("#ma").val(data.ma);
                $("#ten").val(data.ten);
                $("#trangThai").val(data.trangThai);

                // Đặt action của form trong modal (action cập nhật với ID của giảm giá)
                $("#edit-form").attr("action", "/admin/hinh-thuc-thanh-toan/update/" + id);
            },
            error: function (error) {
                console.error("Error:", error);
            },
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

