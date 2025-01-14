<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<section class="container pt-5">
    <style>
        .discount-percentage {
            position: absolute; /* Vị trí tuyệt đối */
            top: 0; /* Đặt ở đỉnh */
            left: 0; /* Đặt ở bên trái */
            background-color: red; /* Màu nền đỏ */
            color: white; /* Màu chữ trắng */
            padding: 5px; /* Khoảng cách nội dung từ viền */
            font-weight: bold; /* In đậm */
        }
        .product-image {
            width: 15rem; /* Match card size */
            height: 15rem; /* Maintain aspect ratio */
            object-fit: cover; /* Ensure the image covers the card */
            border-radius: 10px 10px 0 0; /* Rounded top corners */
        }
    </style>

    <div class="float-end mt-3">
        <select id="sort" class=" form-select" aria-label="Default select example">
            <option selected>Sắp xếp theo</option>
            <option value="1">Giá: thấp đến cao</option>
            <option value="2">Giá: cao đến thấp</option>
        </select>
        <script>
            document.getElementById("sort").onchange = function () {
                var selectedValue = this.value; // Lấy giá trị được chọn trong thẻ select
                // Chuyển hướng đến liên kết tương ứng với giá trị đã chọn
                if (selectedValue === "1") {
                    window.location.href = "/sale?sort=asc";
                } else if (selectedValue === "2") {
                    window.location.href = "/sale?sort=desc";
                }
            };
        </script>
    </div>
    <div class="product px-5 mt-5">
        <!-- repeat -->
        <div class="row row-cols-1 row-cols-md-2 row-cols-lg-4 g-4">
            <!-- Sử dụng col-lg-3 để hiển thị 4 sản phẩm trong mỗi hàng và làm cho sản phẩm lớn hơn -->
            <c:forEach items="${listSanPham}" var="sanPham">
                <div class="col">
                    <a href="/san-pham/${sanPham.id}/${sanPham.idMauSac}" class="text-decoration-none text-dark">
                        <div class="card product-card border-0">
                            <img src="${sanPham.anh}" class="card-img-top product-image" alt="${sanPham.ten}">
                            <span class="discount-percentage" id="so-phan-tram-giam_${sanPham.id}"></span>
                            <div class="card-body text-center">
                                <p class="product-name">${sanPham.ten}</p>
                                <p class="fw-bold product-price" id="gia-san-pham_${sanPham.id}">${sanPham.gia}</p>
                                <p class="fw-bold new-price" id="gia-moi_${sanPham.id}"></p>
                            </div>
                        </div>
                    </a>
                </div>
                <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
                <script>
                    $(document).ready(function () {
                        var idSanPham = '${sanPham.id}';
                        $.ajax({
                            url: "/so-phan-tram-giam/" + idSanPham,
                            method: "GET",
                            success: function (data) {
                                var span = $("#so-phan-tram-giam_" + idSanPham);
                                var giaSpan = $("#gia-san-pham_" + idSanPham);
                                var giaCu = giaSpan.html();

                                if (data != null) {
                                    // nếu tồn tại khuyến mãi thì mới hiển thị thẻ span
                                    if (data > 0) {
                                        span.show();
                                        span.html( "-" +data + "%");
                                    } else {
                                        span.hide();
                                    }
                                    // Tính giá sản phẩm sau khi giảm và hiển thị nó
                                    var giaSanPham = ${sanPham.gia};
                                    var soPhanTramGiam = data;
                                    var giaSauGiam = giaSanPham - (giaSanPham * soPhanTramGiam / 100);
                                    giaSauGiam = Math.floor(giaSauGiam);
                                    giaSpan.hide();
                                    if (data > 0) {
                                        giaSpan.after('<p class="fw-bold new-price">' + giaSauGiam.toLocaleString('en-US') + ' vnđ</p>');
                                        giaSpan.after('<p class="fw-bold old-price " style="text-decoration: line-through;">' + giaCu + '</p>');
                                    } else {
                                        giaSpan.show();
                                    }
                                }
                            },
                            error: function () {
                                // Xử lý lỗi nếu có
                            }
                        });
                    });
                </script>

            </c:forEach>
        </div>
    </div>
    <style>
        .product-card {
            margin-bottom: 20px; /* Khoảng cách giữa các sản phẩm */
            transition: transform 0.2s;
            border-radius: 10px;
            box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
        }
        .row > .col {
            margin-bottom: 20px; /* Khoảng cách giữa các hàng sản phẩm */
        }

        @media (min-width: 992px) { /* Đối với màn hình lớn hơn (desktop) */
            .product-card {
                margin-bottom: 30px; /* Khoảng cách lớn hơn giữa các sản phẩm */
            }
        }
        .product-card:hover {
            transform: scale(1.05); /* Slightly enlarge on hover */
        }

        .product-name {
            font-size: 0.9rem; /* Smaller text size */
            margin: 0.5rem 0;
        }
        .product-price {
            font-size: 1rem;
            color: #ff5733; /* Different color for better visibility */
        }
        .discount-percentage {
            position: absolute;
            top: 10px;
            left: 10px;
            background: #ff0000;
            color: #fff;
            padding: 0.3rem 0.6rem;
            border-radius: 50%;
            font-size: 0.8rem;
        }
        .old-price {
            color: #888;
            font-size: 0.9rem;
        }
        .new-price {
            color: #28a745;
            font-size: 1.1rem;
        }
        .card-body {
            padding: 1rem; /* Added padding for better spacing */
        }
    </style>
    <h5 class="text-center">Bạn đã xem hết!</h5>
    <style>
        .discount-percentage {
            position: absolute;
            top: 0;
            left: 0;
            background-color: red;
            color: white;
            padding: 5px;
            font-weight: bold;
        }
        .link-dark {
            transition: all 0.3s ease;
        }
        .link-dark:hover {
            background-color: #f8f9fa;
            color: #007bff;
            text-decoration: underline;
        }
        .rounded {
            border-radius: 0.25rem;
        }
        @media (max-width: 767.98px) { /* For small screens */
            .col-md-auto {
                text-align: center;
            }
        }

        /* Product card styling */
        .product-card {
            position: relative; /* To position the discount percentage absolutely */
            margin-bottom: 20px;
            transition: transform 0.2s;
            border-radius: 10px;
            overflow: hidden; /* Ensure the image doesn't overflow the card */
            box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
        }
        .product-card:hover {
            transform: scale(1.05);
        }

        .product-image {
            width: 100%; /* Make the image cover the entire width of the card */
            height: 100%; /* Make the image cover the entire height of the card */
            object-fit: cover; /* Ensure the image covers the card without distortion */
            border-radius: inherit; /* Maintain card's border radius */
        }
        .product-name {
            font-size: 0.9rem;
            margin: 0.5rem 0;
        }
        .product-price {
            font-size: 1rem;
            color: #ff5733;
        }
        .discount-percentage {
            position: absolute;
            top: 10px;
            left: 10px;
            background: #ff0000;
            color: #fff;
            padding: 0.3rem 0.6rem;
            border-radius: 50%;
            font-size: 0.8rem;
        }
        .old-price {
            color: #888;
            font-size: 0.9rem;
        }
        .new-price {
            color: #28a745;
            font-size: 1.1rem;
        }
        .card-body {
            text-align: center;
            padding: 1rem;
        }
    </style>
</section>