<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<section class="content">
    <style>
        .product-card {
            width: 15rem; /* Consistent smaller card size */
            margin: auto; /* Center the card */
            transition: transform 0.2s; /* Smooth hover effect */
            border-radius: 10px; /* Rounded corners */
            box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1); /* Soft shadow */
            height: 100%; /* Đảm bảo các thẻ product-card có cùng chiều cao */
        }

        .product-card:hover {
            transform: scale(1.05); /* Slightly enlarge on hover */
        }

        .product-image {
            width: 15rem; /* Match card size */
            height: 15rem; /* Maintain aspect ratio */
            object-fit: cover; /* Ensure the image covers the card */
            border-radius: 10px 10px 0 0; /* Rounded top corners */
        }

        .product-name {
            font-size: 0.9rem; /* Smaller text size */
            margin: 0.5rem 0; /* Margin for better spacing */
        }

        .product-price {
            font-size: 1rem;
            color: #ff5733; /* Distinct color for better visibility */
            color: #28a745; /* Distinct color for better visibility */
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
            text-decoration: line-through;
        }

        .new-price {
            color: #28a745;
            font-size: 1.1rem;
        }

        .card-body {
            padding: 1rem; /* Added padding for better spacing */
        }

    </style>

    <div class="container">
        <h3 class="fw-bold text-lg-start mt-4" style="margin-bottom: 50px">Sản phẩm mới nhất</h3>
        <div class="row row-cols-2 row-cols-md-4 g-4">
            <c:forEach items="${listTrangChu}" var="sanPham">
                <div class="col">
                    <a href="/san-pham/${sanPham.id}/${sanPham.idMauSac}"
                       class="text-decoration-none text-dark">
                        <div class="card product-card border-0">
                            <img src="${sanPham.anh}" class="card-img-top product-image" alt="${sanPham.ten}">
                            <span class="discount-percentage" id="so-phan-tram-giam_${sanPham.id}"></span>
                            <div class="card-body text-center">
                                <p class="product-name">${sanPham.ten}</p>
                                <p class="fw-bold product-price" id="gia-san-pham_${sanPham.id}">${sanPham.gia} vnđ</p>
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
                                    if (data > 0) {
                                        span.show();
                                        span.html("-" + data + "%");
                                    } else {
                                        span.hide();
                                    }
                                    var giaSanPham = ${sanPham.gia};
                                    var soPhanTramGiam = data;
                                    var giaSauGiam = giaSanPham - (giaSanPham * soPhanTramGiam / 100);
                                    giaSauGiam = Math.floor(giaSauGiam);
                                    giaSpan.hide();
                                    if (data > 0) {
                                        giaSpan.after('<p class="fw-bold new-price">' + giaSauGiam.toLocaleString('en-US') + ' vnđ</p>');
                                        giaSpan.after('<p class="fw-bold old-price" style="text-decoration: line-through;">' + giaCu + '</p>');
                                    } else {
                                        giaSpan.after('<p class="fw-bold new-price">' + giaSanPham.toLocaleString('en-US') + ' vnđ</p>');
                                    }
                                }
                            },
                            error: function () {
                                // Handle error if any
                            }
                        });
                    });
                </script>
            </c:forEach>
        </div>

        <h3 class="fw-bold text-lg-start mt-4" style="margin-bottom: 50px">Sản phẩm bán chạy nhất</h3>
        <div class="row row-cols-2 row-cols-md-4 g-4">
            <c:forEach items="${listBanChay}" var="sanPham">
                <div class="col">
                    <a href="/san-pham/${sanPham.id}/${sanPham.idMauSac}"
                       class="text-decoration-none text-dark">
                        <div class="card product-card border-0">
                            <img src="${sanPham.anh}" class="card-img-top product-image" alt="${sanPham.ten}">
                            <span class="discount-percentage" id="so-phan-tram-giam_2_${sanPham.id}"></span>
                            <div class="card-body text-center">
                                <p class="product-name">${sanPham.ten}</p>
                                <p class="fw-bold product-price" id="gia-san-pham_2_${sanPham.id}">${sanPham.gia}
                                    vnđ</p>
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
                                var span = $("#so-phan-tram-giam_2_" + idSanPham);
                                var giaSpan = $("#gia-san-pham_2_" + idSanPham);
                                var giaCu = giaSpan.html();

                                if (data != null) {
                                    if (data > 0) {
                                        span.show();
                                        span.html(data + "% off");
                                    } else {
                                        span.hide();
                                    }
                                    var giaSanPham = ${sanPham.gia};
                                    var soPhanTramGiam = data;
                                    var giaSauGiam = giaSanPham - (giaSanPham * soPhanTramGiam / 100);
                                    giaSauGiam = Math.floor(giaSauGiam);
                                    giaSpan.hide();
                                    if (data > 0) {
                                        giaSpan.after('<p class="fw-bold new-price">' + giaSauGiam.toLocaleString('en-US') + ' vnđ</p>');
                                        giaSpan.after('<p class="fw-bold old-price" style="text-decoration: line-through;">' + giaCu + '</p>');
                                    } else {
                                        giaSpan.after('<p class="fw-bold new-price">' + giaSanPham.toLocaleString('en-US') + ' vnđ</p>');
                                    }
                                }
                            },
                            error: function () {
                                // Handle error if any
                            }
                        });
                    });
                </script>
            </c:forEach>
        </div>
    </div>
    </div>
</section>
<section>

    <div class="row justify-content-center col-2 offset-5 px-1 " style="margin-top: 50px">
        <a href="/san-pham"
           class="text-decoration-none text-dark fw-bold text-center py-3 px-5 border border-none rounded-pill">Xem
            Thêm</a>
    </div>
</section>