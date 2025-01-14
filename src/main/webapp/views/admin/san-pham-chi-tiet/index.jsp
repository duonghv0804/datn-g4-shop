<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="f" uri="jakarta.tags.functions" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>

<script>
    function applyFilters() {
        // Lấy giá trị của các bộ lọc
        var selectedStatus = document.getElementById("statusSelect").value;
        var selectedColor = document.getElementById("colorSelect").value;
        var selectedSize = document.getElementById("sizeSelect").value;

        // Xây dựng URL với các tham số lọc
        var filterUrl = "/admin/san-pham-chi-tiet/index?";
        var params = [];

        if (selectedStatus !== "") {
            params.push("trangThai=" + encodeURIComponent(selectedStatus));
        }
        if (selectedColor !== "") {
            params.push("tenMauSac=" + encodeURIComponent(selectedColor));
        }
        if (selectedSize !== "") {
            params.push("tenKichCo=" + encodeURIComponent(selectedSize));
        }

        // Kết hợp các tham số lại thành một URL
        filterUrl += params.join("&");

        // Chuyển hướng đến URL đã xây dựng
        window.location.href = filterUrl;
    }

    // Giữ lại giá trị tìm kiếm trong ô input khi tải lại trang
    document.addEventListener("DOMContentLoaded", function () {
        var currentUrl = new URL(window.location.href);
        var searchQuery = currentUrl.searchParams.get("timKiem");
        if (searchQuery) {
            document.getElementById("searchInput").value = decodeURIComponent(searchQuery);
        }
    });

    function searchByName(event) {
        if (event.key === "Enter") {
            var searchQuery = document.getElementById("searchInput").value;

            // Lấy URL hiện tại
            var currentUrl = new URL(window.location.href);

            // Xóa tham số `page` để không giữ lại phân trang
            currentUrl.searchParams.delete("page");

            // Cập nhật hoặc thêm tham số `timKiem`
            if (searchQuery !== "") {
                currentUrl.searchParams.set("timKiem", searchQuery);
            } else {
                currentUrl.searchParams.delete("timKiem");
            }

            // Chuyển hướng đến URL mới
            window.location.href = currentUrl.toString();
        }
    }
</script>

<div>
    <h3 class="text-center mt-3">Quản Lý Sản Phẩm Chi Tiết</h3>
    <div class="row col-2 ms-1 mt-3">
        <a href="/admin/san-pham-chi-tiet/create" class="btn btn-success">Thêm mới</a>
    </div>

    <div class="row ">
        <!-- Tìm kiếm theo tên -->
        <div class="col-4 ms-1 mt-3">
            <input type="text" id="searchInput" class="form-control" placeholder="Tìm kiếm theo tên, mã"
                   onkeypress="searchByName(event)">
        </div>
        <!-- lọc đang bán-ngừng bán -->
        <div class="col-2 ms-1 mt-3">
            <select id="statusSelect" class="form-select" aria-label="Default select example"
                    onchange="applyFilters()">
                <option value="" ${param.trangThai == null ? 'selected' : ''}>Tất cả</option>
                <option value="1" ${param.trangThai == '1' ? 'selected' : ''}>Đang bán</option>
                <option value="0" ${param.trangThai == '0' ? 'selected' : ''}>Ngừng bán</option>

            </select>
        </div>
        <!-- lọc theo màu sắc -->
        <div class="col-2 ms-1 mt-3">
            <select id="colorSelect" class="form-select" aria-label="Default select example"
                    onchange="applyFilters()">
                <option value="" ${param.tenMauSac == null ? 'selected' : ''}>Tất cả màu sắc</option>
                <c:forEach items="${listMauSac}" var="color">
                    <option value="${color.ten}" ${param.tenMauSac == color.ten ? 'selected' : ''}>
                            ${color.ten}
                    </option>
                </c:forEach>
            </select>
        </div>
        <!-- Lọc theo kích cỡ -->
        <div class="col-2 ms-1 mt-3">
            <select id="sizeSelect" class="form-select" aria-label="Default select example"
                    onchange="applyFilters()">
                <option value="" ${param.tenKichCo == null ? 'selected' : ''}>Tất cả kích cỡ</option>
                <c:forEach items="${listKichCo}" var="size">
                    <option value="${size.ten}" ${param.tenKichCo == size.ten ? 'selected' : ''}>
                            ${size.ten}
                    </option>
                </c:forEach>
            </select>
        </div>

        <div class="ms-1">
            <table class="table table-bordered text-center mt-3">
                <thead>
                <tr>
                    <th>STT</th>
                    <th>Mã sản phẩm</th>
                    <th>Tên sản phẩm</th>
                    <th>Màu Sắc</th>
                    <th>Kích cỡ</th>
                    <th>Giá</th>
                    <th>Số lượng</th>
                    <th>Trạng thái</th>
                    <th colspan="2">Thao tác</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${list.content}" var="sanPhamChiTiet" varStatus="status">
                    <tr>
                        <td>${status.index + 1}</td>
                        <td>${sanPhamChiTiet.maSanPham}</td>
                        <td>${sanPhamChiTiet.tenSanPham}</td>
                        <td>${sanPhamChiTiet.tenMauSac}</td>
                        <td>${sanPhamChiTiet.tenKichCo}</td>
                        <td>
                            <fmt:formatNumber value="${sanPhamChiTiet.gia}" pattern="#,##0 vnđ"/>
                        </td>

                        <td>${sanPhamChiTiet.soLuong}</td>
                        <td>
                            <c:if test="${sanPhamChiTiet.trangThai == 1}">
                                <span class="badge bg-success">Đang bán</span>
                            </c:if>
                            <c:if test="${sanPhamChiTiet.trangThai == 0}">
                                <span class="badge bg-danger">Ngừng bán</span>
                            </c:if>
                            <c:if test="${sanPhamChiTiet.trangThai == 2}">
                                <span class="badge bg-danger">Tạm thời</span>
                            </c:if>
                        </td>
                        <td>
                            <a class="btn btn-sm btn-warning" href="/admin/san-pham-chi-tiet/edit/${sanPhamChiTiet.id}">
                                Cập nhật
                            </a>
                        </td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>
    </div>
</div>
<div class="mt-3">
<%--    <div class="text-center">--%>
<%--        <c:if test="${list.totalPages > 0}">--%>
<%--            <ul class="pagination">--%>
<%--                <!-- Lấy URL hiện tại và loại bỏ tham số 'page' -->--%>
<%--                <c:set var="currentParams" value="${pageContext.request.queryString}" />--%>
<%--                <c:if test="${not empty currentParams}">--%>
<%--                    <!-- Loại bỏ mọi tham số liên quan đến 'page' -->--%>
<%--                    <c:set var="currentParams" value="${f:replace(currentParams, 'page=' + list.number + 1, '')}" />--%>
<%--                    <c:set var="currentParams" value="${f:replace(currentParams, '&page=', '')}" />--%>
<%--                    <c:set var="currentParams" value="${f:replace(currentParams, 'page=', '')}" />--%>
<%--                    <!-- Loại bỏ ký tự '&' thừa ở đầu chuỗi nếu có -->--%>
<%--                    <c:if test="${f:startsWith(currentParams, '&')}">--%>
<%--                        <c:set var="currentParams" value="${f:substring(currentParams, 1,0)}" />--%>
<%--                    </c:if>--%>
<%--                </c:if>--%>

<%--                <!-- Xử lý link cho trang đầu -->--%>
<%--                <li class="page-item <c:if test="${list.number == 0}">disabled</c:if>">--%>
<%--                    <a class="page-link" href="?page=1<c:if test='${not empty currentParams}'>&${currentParams}</c:if>">First</a>--%>
<%--                </li>--%>

<%--                <!-- Xử lý các trang ở giữa -->--%>
<%--                <c:forEach var="i" begin="1" end="${list.totalPages}">--%>
<%--                    <li class="page-item <c:if test="${list.number + 1 == i}">active</c:if>">--%>
<%--                        <a class="page-link" href="?page=${i}<c:if test='${not empty currentParams}'>&${currentParams}</c:if>">${i}</a>--%>
<%--                    </li>--%>
<%--                </c:forEach>--%>

<%--                <!-- Xử lý link cho trang cuối -->--%>
<%--                <li class="page-item <c:if test="${list.number == list.totalPages - 1}">disabled</c:if>">--%>
<%--                    <a class="page-link" href="?page=${list.totalPages}<c:if test='${not empty currentParams}'>&${currentParams}</c:if>">Last</a>--%>
<%--                </li>--%>
<%--            </ul>--%>
<%--        </c:if>--%>
<%--    </div>--%>

    <div class="text-center">
        <c:if test="${list.totalPages > 0}">
            <ul class="pagination">
                <li class="page-item <c:if test="${list.number == 0}">disabled</c:if>">
                    <a class="page-link" href="?page=1">First</a>
                </li>
                <c:forEach var="i" begin="1" end="${list.totalPages}">
                    <li class="page-item <c:if test="${list.number + 1 == i}">active</c:if>">
                        <a class="page-link" href="?page=${i}">${i}</a>
                    </li>
                </c:forEach>
                <li class="page-item <c:if test="${list.number == list.totalPages-1}">disabled</c:if>">
                    <a class="page-link" href="?page=${list.totalPages}">Last</a>
                </li>
            </ul>
        </c:if>
    </div>

</div>
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
