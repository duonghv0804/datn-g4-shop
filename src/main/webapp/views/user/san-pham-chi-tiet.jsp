<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/sweetalert2@10/dist/sweetalert2.min.css">
<script src="https://cdn.jsdelivr.net/npm/sweetalert2@10"></script>
<style>
    .thumbnail-container {
        display: flex;
        flex-direction: column; /* Xếp các hình ảnh theo chiều dọc */
        align-items: flex-start; /* Căn chỉnh các hình ảnh nhỏ về phía bên trái */
        margin-right: 20px; /* Khoảng cách giữa ảnh nhỏ và ảnh lớn */
    }

    .thumbnail-container img {
        cursor: pointer;
        border: 1px solid #dee2e6;
        border-radius: 0.375rem;
        margin-bottom: 8px;
        width: 50px;
        height: 50px;
    }

    .main-image-container {
        flex: 1; /* Đảm bảo rằng hình ảnh chính chiếm hết không gian còn lại */
    }

    .main-image {
        max-width: 500px;
        height: 600px;
        border-radius: 0.375rem;
    }
</style>
<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.min.js"></script>
<div class="container">
    <div class="p-4" style="margin-top: 15px">
        <div class="row row-cols-1 row-cols-lg-2 g-4">
            <%--            ----------------ANH SP --------------------%>
                <div class="col-md-6 d-flex">
                    <div class="thumbnail-container">
                        <c:forEach items="${listAnh}" var="hinhAnh" varStatus="loop">
                            <c:if test="${loop.index < 3}">
                                <img src="${hinhAnh.duongDan}" class="img-thumbnail" />
                            </c:if>
                        </c:forEach>
                    </div>
                    <div class="main-image-container">
                        <c:forEach items="${listAnh}" var="hinhAnh" varStatus="loop">
                            <c:if test="${loop.index == 2}">
                                <img src="${hinhAnh.duongDan}" alt="Main Product Image" class="main-image" id="mainImage" />
                            </c:if>
                        </c:forEach>
                    </div>
                </div>
                <script>
                    $(document).ready(function() {
                        $('.thumbnail-container img').on('click', function() {
                            var newSrc = $(this).attr('src').replace('100x100', '400x600');
                            $('#mainImage').attr('src', newSrc);
                        });
                    });
                </script>
            <%-------------------------------------------------------------%>

                <div class="aside">
            <div class="col " >
                <h1 class="h2">${sanPham.ten}</h1>
                <p class="text-muted">Mã sản phẩm: ${sanPham.maSanPham} | Tình trạng: <span
                        class="text-danger">${sanPham.trangThai == 0 ? "Hết Hàng" :"Còn Hàng"  }</span> | Thương hiệu:
                    ${sanPham.tenThuongHieu}</p>

                <div class="mt-4 p-4 bg-light rounded-lg shadow-sm">
                    <div class="d-flex justify-content-between align-items-center flex-wrap">
                        <span class="h4">Giá:</span>
                        <div class="text-end d-flex align-items-center">
                            <span class="text-danger fw-bold fs-4" id="gia-moi"></span>
                            <span id="gia-san-pham">${sanPham.gia}</span>
                            <div class="bg-danger text-white fw-bold py-05 px-2 rounded-pill ms-3"
                                 id="so-phan-tram-giam_${sanPham.id}" style="font-size: 14px; display: none;"></div>
                        </div>
                    </div>
                </div>

                <script>
                    document.addEventListener("DOMContentLoaded", function () {
                        var giaSanPhamElement = document.getElementById("gia-san-pham");
                        var giaSanPhamText = giaSanPhamElement.innerText;
                        var formattedGia = parseInt(giaSanPhamText.replace(/[^\d]/g, '')).toLocaleString('en-US');
                        giaSanPhamElement.innerHTML = '<span style="color: #212529bf; margin-left: 10px">' + formattedGia + 'đ</span>';

                        var idSanPham = '${sanPham.id}';
                        fetch(`/so-phan-tram-giam/${idSanPham}`)
                            .then(response => response.json())
                            .then(data => {
                                if (data != null && data > 0) {
                                    var giaSanPham = ${sanPham.gia};
                                    var giaSauGiam = Math.floor(giaSanPham - (giaSanPham * data / 100));

                                    var giaMoiElement = document.getElementById("gia-moi");
                                    giaMoiElement.innerText = giaSauGiam.toLocaleString('en-US') + 'đ';
                                    giaMoiElement.style.color = "red";  // Set new price color

                                    giaSanPhamElement.classList.add("text-decoration-line-through", "text-muted");

                                    var discountElement = document.getElementById("so-phan-tram-giam_${sanPham.id}");
                                    discountElement.innerText = data + "%";
                                    discountElement.style.display = "inline-block";
                                } else {
                                    var giaMoiElement = document.getElementById("gia-moi");
                                    giaMoiElement.innerText = formattedGia + ' vnđ';
                                    giaMoiElement.style.color = "#ff2c26";  // Set new price color
                                    giaSanPhamElement.style.display = "none";
                                }
                            })
                            .catch(error => console.error('Error:', error));
                    });
                </script>


                <form:form modelAttribute="gioHang" action="/gio-hang/${sanPham.id}" method="post">
                    <div class="mt-4">
                        <span class="fw-bold">Màu sắc:</span>
                        <div class="d-flex flex-wrap gap-2 mt-2">
                            <div id="mauSacSelect">
                                <c:forEach items="${listMauSac}" var="mauSac">
                                    <input class="btn-check" type="radio" id="mauSac${mauSac.id}" name="idMauSac"
                                           value="${mauSac.id}" ${mauSac.id == idMauSac ? "checked" : ""}>
                                    <label class="btn btn-outline-secondary d-inline-block me-2 mb-2"
                                           for="mauSac${mauSac.id}">
                                            ${mauSac.ten}
                                    </label>
                                </c:forEach>
                            </div>
                            <script>
                                const checkboxes = document.querySelectorAll('input[name="idMauSac"]');
                                checkboxes.forEach((checkbox) => {
                                    checkbox.addEventListener('change', function () {
                                        const selectedValues = document.querySelector('input[name="idMauSac"]:checked').value;
                                        const newURL = "http://localhost:8080/san-pham/${sanPham.id}/" + selectedValues;
                                        window.location.href = newURL;
                                    });
                                });
                            </script>
                        </div>


                        <div class="mt-4">
                            <span class="fw-bold">Kích cỡ:</span>

                            <div class="d-flex flex-wrap gap-2 mt-2">
                                <div id="kichCoSelect">
                                    <c:forEach items="${listKichCo}" var="kichCo" >
                                        <input class="btn-check" type="radio" name="idKichCo" id="kichCo${kichCo.id}"
                                               value="${kichCo.id}">
                                        <label class="btn btn-outline-secondary d-inline-block me-2 mb-2"
                                               for="kichCo${kichCo.id}">
                                                ${kichCo.ten}
                                        </label>
                                    </c:forEach>
                                </div>
                            </div>
                            <div>
                                <p class="text-danger ms-3 mt-2" id="textKichCo" style="display: none">Bạn cần chọn kích
                                    cỡ</p>

                            </div>

                        </div>


                        <script>
                            $(document).ready(function () {
                                // Kiểm tra số lượng âm
                                $("#quantity").on('input', function () {
                                    var value = parseInt($(this).val());
                                    if (value < 1 || isNaN(value)) {
                                        $(this).val(1); // Đặt lại giá trị về 1 nếu là số âm hoặc không hợp lệ
                                    }
                                });

                                // Xử lý sự kiện click cho nút "Thêm vào giỏ hàng"
                                $("#btnThemVaoGioHang").click(function (event) {
                                    // Kiểm tra xem người dùng đã chọn kích cỡ và màu sắc chưa
                                    var kichCoSelected = $("input[name='idKichCo']:checked").length > 0;
                                    var mauSacSelected = $("input[name='idMauSac']:checked").length > 0;

                                    if (!kichCoSelected || !mauSacSelected) {
                                        // Ngăn form không được submit
                                        event.preventDefault();

                                        // Hiển thị thông báo lỗi phù hợp
                                        if (!kichCoSelected) {
                                            $("#textKichCo").show();
                                        } else {
                                            $("#textKichCo").hide();
                                        }

                                        if (!mauSacSelected) {
                                            $("#textMauSac").show();
                                        } else {
                                            $("#textMauSac").hide();
                                        }
                                    }
                                });

                                // Ẩn thông báo lỗi khi người dùng chọn kích cỡ hoặc màu sắc
                                $("input[name='idKichCo']").change(function () {
                                    $("#textKichCo").hide();
                                });

                                $("input[name='idMauSac']").change(function () {
                                    $("#textMauSac").hide();
                                });

                                // Vô hiệu hóa tùy chọn "Chọn" cho màu sắc
                                $("#mauSacSelect input[value='']").attr("disabled", "disabled");
                            });

                            function decrement(event) {
                                event.preventDefault();
                                var quantityInput = document.getElementById("quantity");
                                var currentValue = parseInt(quantityInput.value);
                                if (currentValue > 1) {
                                    quantityInput.value = currentValue - 1;
                                }
                            }

                            function increment(event) {
                                event.preventDefault();
                                var kichCoSelected = $("input[name='idKichCo']:checked").length > 0;
                                if (!kichCoSelected) {
                                    $("#textKichCo").show();
                                    return;
                                }
                                $("#textKichCo").hide();
                                var quantityInput = document.getElementById("quantity");
                                var currentValue = parseInt(quantityInput.value);
                                var max = parseInt(quantityInput.getAttribute('max'));
                                if (currentValue < max) {
                                    quantityInput.value = currentValue + 1;
                                }
                            }
                        </script>

                        <div class="mt-4">
                            <div class="quantity">
                                <span class="fw-bold">Số lượng:</span>
                                <div class="d-flex align-items-center mt-2">
                                    <button class="btn btn-outline-secondary d-inline-block me-2 mb-2" onclick="decrement(event)">-</button>

                                    <form:input path="soLuong" id="quantity" type="text" value="1" min="1" max="100"
                                                class="form-control text-center border-top-0 border-bottom-0"
                                                style="width: 80px;" />

                                    <button class="btn btn-outline-secondary d-inline-block me-2 mb-2" onclick="increment(event)">+</button>
                                </div>
                            </div>
                            <div id="soLuongSanPham" class="mt-3"></div>
                            <script>
                                const mauSacSelect = document.getElementById('mauSacSelect');
                                const kichCoSelect = document.getElementById('kichCoSelect');
                                const soLuongSanPhamDiv = document.getElementById('soLuongSanPham');

                                mauSacSelect.addEventListener('change', fetchData);
                                kichCoSelect.addEventListener('change', fetchData);

                                function fetchData() {
                                    const idMauSac = document.querySelector('input[name="idMauSac"]:checked');
                                    const idKichCo = document.querySelector('input[name="idKichCo"]:checked');

                                    fetch(`/so-luong-san-pham/${sanPham.id}/` + idMauSac.value + `/` + idKichCo.value)
                                        .then(response => {
                                            if (!response.ok) {
                                                throw new Error('Network response was not ok');
                                            }
                                            return response.json();
                                        })
                                        .then(data => {
                                            soLuongSanPhamDiv.innerText = `Số lượng sản phẩm: ` + data;
                                            const quantityInput = document.getElementById('quantity');
                                            quantityInput.setAttribute('max', data);

                                            quantityInput.value = 1;
                                        })
                                        .catch(error => {
                                            console.error('There was a problem with the fetch operation:', error);
                                        });
                                }
                            </script>
                        </div>

<%--                    </div>--%>

                    </div>
                    <div class="mt-4 d-flex gap-3">
                        <c:if test="${khachHang != null}">
                            <button class="btn btn-secondary flex-grow-1" type="submit" id="btnThemVaoGioHang">THÊM VÀO
                                GIỎ
                            </button>
                            <p class="text-danger ms-3 mt-2" id="textKichCo" style="display: none">Bạn cần chọn kích
                                cỡ</p>
                            <p class="text-danger ms-3 mt-2" id="textMauSac" style="display: none">Bạn cần chọn màu
                                sắc</p>
                            <p class="text-danger ms-3 mt-2" id="soLuongError" style="display: none">Số lượng phải lớn hơn hoặc bằng 1
                            </p>

                        </c:if>
                    </div>

                    <div class="mt-4">
                        <c:if test="${khachHang == null}">
                            <button class="btn btn-danger flex-grow-1 w-100">BẠN CẦN ĐĂNG NHẬP ĐỂ MUA HÀNG</button>
                        </c:if>
                    </div>
                </form:form>
<%--                <div class="mt-4 d-flex align-items-center gap-2 text-muted">--%>
<%--                    <a href="#" class="text-primary"><img src="https://openui.fly.dev/openui/24x24.svg?text=S"--%>
<%--                                                          alt="Facebook"/></a>--%>
<%--                    <a href="#" class="text-primary"><img src="https://openui.fly.dev/openui/24x24.svg?text=P"--%>
<%--                                                          alt="Messenger"/></a>--%>
<%--                    <a href="#" class="text-primary"><img src="https://openui.fly.dev/openui/24x24.svg?text=O"--%>
<%--                                                          alt="Twitter"/></a>--%>
<%--                    <a href="#" class="text-danger"><img src="https://openui.fly.dev/openui/24x24.svg?text=R"--%>
<%--                                                         alt="Pinterest"/></a>--%>
<%--                    <a href="#" class="text-primary"><img src="https://openui.fly.dev/openui/24x24.svg?text=T"--%>
<%--                                                          alt="Email"/></a>--%>
<%--                    <a href="#" class="text-primary"><img src="https://openui.fly.dev/openui/24x24.svg?text=6"--%>
<%--                                                          alt="Email"/></a>--%>
<%--                    <a href="#" class="text-primary"><img src="https://openui.fly.dev/openui/24x24.svg?text=5"/></a>--%>
<%--                </div>--%>

                <div class="mt-4 d-flex gap-2 text-muted">
                    <div class="d-flex align-items-center gap-2">
                        <img src="https://openui.fly.dev/openui/24x24.svg?text=🔒" alt="Secure Payment"/>
                        <span>Hàng phân phối chính hãng 100%</span>
                    </div>
                    <div class="d-flex align-items-center gap-2">
                        <img src="https://openui.fly.dev/openui/24x24.svg?text=📞" alt="Customer Support"/>
                        <span>TỔNG ĐÀI 24/7 : 0123456789</span>
                    </div>
                </div>
            </div>

                </div>
        </div>
        <script>
            $(document).ready(function () {
                // Kiểm tra số lượng âm và chữ
                $("#quantity").on('input', function () {
                    // Chỉ cho phép nhập số
                    var value = $(this).val();
                    // Loại bỏ tất cả các ký tự không phải số
                    value = value.replace(/[^0-9]/g, '');
                    // Chuyển giá trị thành số
                    value = parseInt(value);
                    // Kiểm tra giá trị âm hoặc không hợp lệ
                    if (value < 1 || isNaN(value)) {
                        value = 1; // Đặt lại giá trị về 1 nếu là số âm hoặc không hợp lệ
                    }
                    $(this).val(value);
                });
                // Handle the 'Add to Cart' button click

                $("#btnThemVaoGioHang").click(function (event) {
                    // Kiểm tra xem người dùng đã chọn kích cỡ và màu sắc chưa
                    var kichCoSelected = $("input[name='idKichCo']:checked").length > 0;
                    var mauSacSelected = $("input[name='idMauSac']:checked").length > 0;

                    // Nếu không chọn kích cỡ hoặc màu sắc, hiển thị lỗi
                    if (!kichCoSelected || !mauSacSelected) {
                        event.preventDefault(); // Ngăn form không được submit

                        if (!kichCoSelected) {
                            $("#textKichCo").show();
                        } else {
                            $("#textKichCo").hide();
                        }

                        if (!mauSacSelected) {
                            $("#textMauSac").show();
                        } else {
                            $("#textMauSac").hide();
                        }
                        return; // dung lai khi chua chon mau sac
                    }

                    // khi da chon mau sac va kich co thì hien thi c
                    var confirmAddToCart = confirm("Bạn có chắc chắn muốn thêm sản phẩm này vào giỏ hàng không?");

                    if (!confirmAddToCart) {
                        // Nếu người dùng không đồng ý, ngăn chặn việc submit form
                        event.preventDefault();
                        return;
                    }
                });

                // Ẩn thông báo lỗi khi chọn kích cỡ hoặc màu sắc
                $("input[name='idKichCo']").change(function () {
                    $("#textKichCo").hide();
                });

                $("input[name='idMauSac']").change(function () {
                    $("#textMauSac").hide();
                });

                // Disable the "Chọn" option for color
                $("#mauSacSelect input[value='']").attr("disabled", "disabled");
            });
        </script>
    </div>

</div>
