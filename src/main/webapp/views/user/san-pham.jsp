<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<section class="container pt-5">
    <div class="row text-center my-4">
        <!-- Static "Tất cả" link with additional styling -->
        <div class="col-md-auto col-sm-12 mb-3 mb-md-0">
            <a class="link-dark text-decoration-none fw-bold py-2 px-3 rounded d-block" href="/san-pham">
                Tất cả
            </a>
        </div>
        <!-- Dynamic category links with enhanced styling and layout -->
        <c:forEach items="${listLoai}" var="loai">
            <div class="col-md-auto col-sm-12 mb-3 mb-md-0">
                <a class="link-dark text-decoration-none py-2 px-3 rounded d-block" href="/san-pham/${loai.id}">
                        ${loai.ten}
                </a>
            </div>
        </c:forEach>
    </div>

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

        @media (max-width: 767.98px) {
            /* For small screens */
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
            height: 100%; /* Đặt chiều cao cố định cho card */
        }


        .card-body {
            display: flex;
            flex-direction: column;
            justify-content: space-between;
            height: 100%; /* Đặt chiều cao cố định cho phần body của card */
        }

        .product-name, .product-price, .new-price, .old-price {
            margin: 0; /* Xóa margin mặc định */
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
            color: #28a745;
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

        /* Product card styling */
        .product-card {
            position: relative; /* To position the discount percentage absolutely */
            margin-bottom: 20px;
            transition: transform 0.2s;
            border-radius: 10px;
            overflow: hidden; /* Ensure the image doesn't overflow the card */
            box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
            height: 100%; /* Đặt chiều cao cố định cho card */
        }


        .card-body {
            display: flex;
            flex-direction: column;
            justify-content: space-between;
            height: 100%; /* Đặt chiều cao cố định cho phần body của card */
        }

        .product-name, .product-price, .new-price, .old-price {
            margin: 0; /* Xóa margin mặc định */
        }

        .product-card:hover {
            transform: scale(1.05);
        }

        .product-image {
            width: 244.6px; /* Make the image cover the entire width of the card */
            height: 240px; /* Make the image cover the entire height of the card */
            object-fit: cover; /* Ensure the image covers the card without distortion */
            border-radius: inherit; /* Maintain card's border radius */
        }

        .product-name {
            font-size: 0.9rem;
            margin: 0.5rem 0;
        }

        .product-price {
            font-size: 1rem;
            color: #28a745;
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

    <div class="float-end mt-3">
        <select id="sort" class="form-select" aria-label="Default select example">
            <option selected>Sắp xếp theo</option>
            <option value="1">Giá: thấp đến cao</option>
            <option value="2">Giá: cao đến thấp</option>
        </select>
        <script>
            document.getElementById("sort").onchange = function () {
                var selectedValue = this.value;
                var idLoai = '${idLoai}';
                if (selectedValue === "1") {
                    if (idLoai == -1)
                        window.location.href = "/san-pham?sort=asc";
                    else {
                        window.location.href = "/san-pham/${idLoai}?sort=asc";
                    }
                } else if (selectedValue === "2") {
                    if (idLoai == -1)
                        window.location.href = "/san-pham?sort=desc";
                    else {
                        window.location.href = "/san-pham/${idLoai}?sort=desc";
                    }
                }
            };
        </script>
    </div>
    <div class="product px-5 mt-5">
        <div class="row row-cols-1 row-cols-md-2 row-cols-lg-4 g-4">
            <c:forEach items="${listSanPham}" var="sanPham">
                <div class="col">
                    <a href="/san-pham/${sanPham.id}/${sanPham.idMauSac}" class="text-decoration-none text-dark">
                        <div class="card product-card border-0">
                            <img src="${sanPham.anh}" class="product-image" alt="${sanPham.ten}">
                            <span class="discount-percentage" id="so-phan-tram-giam_${sanPham.id}"></span>
                            <div class="card-body">
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
                                // Xử lý lỗi nếu có
                            }
                        });
                    });
                </script>
            </c:forEach>
        </div>
    </div>

    <h5 class="text-center" style="margin-top: 50px">Bạn đã xem hết!</h5>
</section>
