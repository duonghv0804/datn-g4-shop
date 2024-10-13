<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>
<style>
    .image-input {
        display: none;
    }

    .image-preview-container {
        position: relative;
        width: 100px;
        height: 100px;
        margin: 10px;
        border: 1px dashed #ccc;
        text-align: center;
        cursor: pointer; /* Sử dụng con trỏ kiểu tay khi di chuột vào */
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


<div class="container mt-3">
    <h1 class="text-center">Quản Lý Sản Phẩm</h1>

    <button type="button" class="btn btn-success" data-bs-toggle="modal" data-bs-target="#exampleModal">
        Thêm sản phẩm
    </button>

    <div class="row">
        <div>
            <table class="table table-bordered mt-3 text-center">
                <thead>
                <tr>
                    <th>STT</th>
                    <th>Tên Sản Phẩm</th>
                    <th>Ảnh Hiển Thị</th>
                    <th>Loại</th>
                    <th>Thương Hiệu</th>
                    <th>Sex</th>
                    <th>Trạng thái</th>
                    <th>Thao Tác</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach varStatus="index" items="${sanPhamPage.content}" var="sp">
                    <tr>
                        <td>${index.index + sanPhamPage.number * sanPhamPage.size + 1}</td>
                        <td>${sp.ten}</td>
                        <td>
                            <img src="${sp.anh}" alt="" width="100px" height="100px">
                        </td>
                        <td>${sp.tenLoai}</td>
                        <td>${sp.tenThuongHieu}</td>
                        <td>${sp.tenSex}</td>
                        <td>
                            <c:if test="${sp.trang_thai == 1}">
                                <span class="badge bg-success">Còn hàng</span>
                            </c:if>
                            <c:if test="${sp.trang_thai == 0}">
                                <span class="badge bg-danger">Hết hàng</span>
                            </c:if>
                        </td>
                        <td>
                            <a href="#" class=" update-button"
                               data-bs-toggle="modal" data-bs-target="#exampleModal"
                               data-id="${sp.id}"
                               data-ten="${sp.ten}"
                               data-anh="${sp.anh}"
                               data-idLoai="${sp.idLoai}"
                               data-idThuongHieu="${sp.idThuongHieu}"
                               data-idSex="${sp.idSex}"

                            >
                                <svg xmlns="http://www.w3.org/2000/svg" width="18" height="18" fill="currentColor" class="bi bi-pencil-fill" viewBox="0 0 16 16">
                                    <path d="M12.854.146a.5.5 0 0 0-.707 0L10.5 1.793 14.207 5.5l1.647-1.646a.5.5 0 0 0 0-.708zm.646 6.061L9.793 2.5 3.293 9H3.5a.5.5 0 0 1 .5.5v.5h.5a.5.5 0 0 1 .5.5v.5h.5a.5.5 0 0 1 .5.5v.5h.5a.5.5 0 0 1 .5.5v.207zm-7.468 7.468A.5.5 0 0 1 6 13.5V13h-.5a.5.5 0 0 1-.5-.5V12h-.5a.5.5 0 0 1-.5-.5V11h-.5a.5.5 0 0 1-.5-.5V10h-.5a.5.5 0 0 1-.175-.032l-.179.178a.5.5 0 0 0-.11.168l-2 5a.5.5 0 0 0 .65.65l5-2a.5.5 0 0 0 .168-.11z"/>
                                </svg>
                            </a>
<%--                            <a href="/admin/san-pham/delete/${sp.id}" class="btn btn-danger"--%>
<%--                               onclick="return confirm('Bạn có chắc chắn muốn xoá không?')">Xoá</a>--%>
                        </td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>
    </div>


    <div class="modal fade" id="exampleModal" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true"
         data-bs-backdrop="static">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title" id="exampleModalLabel">Thêm Sản Phẩm</h5>
                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                </div>
                <div class="modal-body">
                    <form:form id="edit-form" modelAttribute="sp" method="post" action="/admin/san-pham/store" enctype="multipart/form-data">
                        <div class="row">
                            <!-- Tên Sản Phẩm -->
                            <div class="col-md-6 mb-3">
                                <div class="form-group">
                                    <label for="ten" class="form-label">Tên Sản Phẩm</label>
                                    <form:input type="text" path="ten" id="ten" class="form-control"/>
                                    <form:errors path="ten" cssClass="text-danger mt-1"/>
                                </div>
                            </div>

                            <!-- Loại -->
                            <div class="col-md-6 mb-3">
                                <div class="form-group">
                                    <label class="form-label" for="idLoai">Loại</label>
                                    <form:select class="form-select form-select-sm" path="idLoai" id="idLoai">
                                        <c:forEach items="${listLoai}" var="loai">
                                            <option value="${loai.id}">${loai.ten}</option>
                                        </c:forEach>
                                    </form:select>
                                </div>
                            </div>

                            <!-- Câu lạc bộ -->
                            <div class="col-md-6 mb-3">
                                <div class="form-group">
                                    <label class="form-label" for="idSex">Sex</label>
                                    <form:select class="form-select form-select-sm" id="idSex" path="idSex">
                                        <c:forEach items="${listSex}" var="sex">
                                            <option value="${sex.id}">${sex.ten}</option>
                                        </c:forEach>
                                    </form:select>
                                </div>
                            </div>

                            <!-- Thương Hiệu -->
                            <div class="col-md-6 mb-3">
                                <div class="form-group">
                                    <label class="form-label" for="idThuongHieu">Thương Hiệu</label>
                                    <form:select class="form-select form-select-sm" path="idThuongHieu" id="idThuongHieu">
                                        <c:forEach items="${listThuongHieu}" var="th">
                                            <option value="${th.id}">${th.ten}</option>
                                        </c:forEach>
                                    </form:select>
                                </div>
                            </div>

                            <!-- Trạng thái -->
                            <div class="col-md-6 mb-3">
                                <div class="form-group">
                                    <label class="form-label" for="trang_thai">Trạng thái</label>
                                    <form:select path="trang_thai" id="trang_thai" class="form-select form-select-sm">
                                        <option value="1">Còn hàng</option>
                                        <option value="0">Hết hàng</option>
                                    </form:select>
                                </div>
                            </div>
                        </div>

                        <!-- Ảnh sản phẩm -->
                        <div class="mt-3 text-center">
                            <label class="form-label">Ảnh bìa sản phẩm</label>
                            <div class="text-center">
                                <label for="imageInput" class="image-preview-container">
                                    <img id="imageDisplay" class="image-preview" src="" alt="Image">
                                    <span class="image-placeholder" id="placeholder1">+</span>
                                </label>
                                <form:input path="anh" type="file" id="imageInput" class="image-input" accept="image/*"
                                            onchange="displayImage()"/>
                            </div>
                        </div>
<%--                        <c:if test="${sp.id == null} ">--%>
                            <div class="mt-3">
                                <label class="form-label">Ảnh sản phẩm</label>
                                <div>
                                    <c:forEach var="i" begin="0" end="2">
                                        <label for="imageInput${i}" class="image-preview-container">
                                            <img id="imageDisplay${i}" class="image-preview" src="" alt="Image ${i + 1}">
                                            <span class="image-placeholder" id="placeholder${i}">+</span>
                                        </label>
                                        <input type="file" id="imageInput${i}" class="image-input" accept="image/*"
                                               onchange="displayImage2(${i}, 'imageDisplay${i}', 'placeholder${i}'); convertImageToBase642(${i});"/>
                                        <label class="image-input-label selected" for="imageInput${i}">Chọn ảnh</label>

                                        <!-- Thêm hidden input để lưu trữ giá trị base64Images -->
                                        <form:input path="duongDan[${i}]" type="hidden" id="base64Images${i}"/>
                                    </c:forEach>
                                </div>
                            </div>
<%--                        </c:if>--%>
                        <!-- Nút submit -->
                        <div class="mt-4 text-center">
                            <button type="submit" class="btn btn-primary">Lưu</button>
                        </div>
                    </form:form>
                </div>
                    <script>
                        document.addEventListener('DOMContentLoaded', function() {
                            const form = document.getElementById('edit-form');
                            const tenInput = document.getElementById('ten');
                            const idLoaiSelect = document.getElementById('idLoai');
                            const idThuongHieuSelect = document.getElementById('idThuongHieu');
                            const idSexSelect = document.getElementById('idSex');
                            const imageInput = document.getElementById('imageInput');

                            form.addEventListener('submit', function(event) {
                                // Check if the "Tên Sản Phẩm" input is empty
                                if (tenInput.value.trim() === '') {
                                    event.preventDefault();
                                    alert('Vui lòng nhập Tên Sản Phẩm.');
                                    return;
                                }

                                // Check if the "Loại" select is not selected
                                if (idLoaiSelect.value === '') {
                                    event.preventDefault();
                                    alert('Vui lòng chọn Loại.');
                                    return;
                                }

                                // Check if the "Thương Hiệu" select is not selected
                                if (idThuongHieuSelect.value === '') {
                                    event.preventDefault();
                                    alert('Vui lòng chọn Thương Hiệu.');
                                    return;
                                }

                                // Check if the "Câu Lạc Bộ" select is not selected
                                if (idSexSelect.value === '') {
                                    event.preventDefault();
                                    alert('Vui lòng chọn Câu Lạc Bộ.');
                                    return;
                                }

                                // Only check for image if no image is already present or a new image is being uploaded
                                if (imageInput.files.length === 0 && !$("#imageDisplay").attr("src")) {
                                    event.preventDefault();
                                    alert('Vui lòng chọn Ảnh sản phẩm.');
                                    return;
                                }
                            });
                        });
                    </script>

                </div>
            </div>
        </div>
    </div>

    <div class="mt-3">
        <div class="text-center">
            <c:if test="${sanPhamPage.totalPages > 1}">
                <ul class="pagination">
                    <li class="page-item <c:if test="${sanPhamPage.number == 0}">disabled</c:if>">
                        <a class="page-link" href="?page=1">First</a>
                    </li>
                    <c:forEach var="i" begin="1" end="${sanPhamPage.totalPages}">
                        <li class="page-item <c:if test="${sanPhamPage.number + 1 == i}">active</c:if>">
                            <a class="page-link" href="?page=${i}">${i}</a>
                        </li>
                    </c:forEach>
                    <li class="page-item <c:if test="${sanPhamPage.number == sanPhamPage.totalPages-1}">disabled</c:if>">
                        <a class="page-link" href="?page=${sanPhamPage.totalPages}">Last</a>
                    </li>
                </ul>
            </c:if>
        </div>
    </div>
</div>


<!-- Bao gồm các tập lệnh Spring form và các tập lệnh khác -->
<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
<script>
    $(".update-button").click(function () {
        let id = $(this).data("id");

        // Thực hiện yêu cầu AJAX để lấy dữ liệu sản phẩm dựa trên id
        $.ajax({
            url: "/admin/san-pham/get/" + id,
            type: "GET",
            success: function (data) {
                $("#ten").val(data.ten);
                $("#trang_thai").val(data.trang_thai);
                $("#idLoai").val(data.idLoai);
                $("#idThuongHieu").val(data.idThuongHieu);
                $("#idSex").val(data.idSex);

                if (data.anh) {
                    $("#imageDisplay").attr("src", data.anh);
                    $("#placeholder1").hide();
                    $("#imageDisplay").show();
                } else {
                    $("#imageDisplay").attr("src", "");
                    $("#imageDisplay").hide();
                    $("#placeholder1").show();
                }

                $("#edit-form").attr("action", "/admin/san-pham/update/" + id);
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
                modalTitle.textContent = "Cập Nhật Sản Phẩm";
            });
        });

        clickClose.addEventListener("click", function () {
            var modalTitle = document.querySelector(".modal-title");
            modalTitle.textContent = "Thêm Sản Phẩm";
            $("#edit-form").trigger("reset");
            $("#imageDisplay").attr("src", "");
            $("#imageDisplay").hide();
            $("#placeholder1").show();
            $("#edit-form").attr("action", "/admin/san-pham/store");
        });
    });


    function displayImage() {
        var input = document.getElementById('imageInput');
        var imageDisplay = document.getElementById('imageDisplay');
        var placeholder = document.getElementById('placeholder1');

        if (input.files && input.files[0]) {
            var reader = new FileReader();

            reader.onload = function (e) {
                imageDisplay.src = e.target.result;
                imageDisplay.style.display = 'block';
                placeholder.style.display = 'none';
            };

            reader.readAsDataURL(input.files[0]);
        } else {
            imageDisplay.src = ''; // Xóa ảnh nếu không có tệp được chọn
            imageDisplay.style.display = 'none';
            placeholder.style.display = 'block';
        }
    }


    function displayImage2(index, imageId, placeholderId) {
        var input = document.getElementById("imageInput" + index);
        var imageDisplay = document.getElementById(imageId);
        var placeholder = document.getElementById(placeholderId);
        var base64ImagesInput = document.getElementById("base64Images" + index);

        var file = input.files[0];
        var reader = new FileReader();

        reader.onload = function (e) {
            var base64Image = e.target.result;
            imageDisplay.style.display = "block";
            imageDisplay.src = base64Image;
            placeholder.style.display = "none";

            // Update the hidden input field with base64 data
            base64ImagesInput.value = base64Image;
            console.log(base64Image);
        };

        reader.readAsDataURL(file);
    }

    function convertImageToBase642(index) {
        var input = document.getElementById("imageInput" + index);
        var base64ImagesInput = document.getElementById("base64Images" + index);

        var file = input.files[0];
        var reader = new FileReader();

        reader.onload = function (e) {
            var base64Image = e.target.result;
            // Update the hidden input field with base64 data
            base64ImagesInput.value = base64Image;
        };

        reader.readAsDataURL(file);
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
</body>
</html>
