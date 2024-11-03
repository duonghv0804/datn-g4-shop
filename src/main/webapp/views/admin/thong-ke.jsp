<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8"/>
    <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
    <title>Document</title>
    <link
            rel="stylesheet"
            href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.4.1/css/all.min.css"
            integrity="sha512-/RUbtHakVMJrg1ILtwvDIceb/cDkk97rWKvfnFSTOmNbytCyEylutDqeEr9adIBye3suD3RfcsXLOLBqYRW4gw=="
            crossorigin="anonymous"
            referrerpolicy="no-referrer"
    />
    <script src="https://cdn.jsdelivr.net/npm/chart.js"></script>

    <style>
        body {
            margin: 0;
            padding: 0;
            box-sizing: border-box;
        }

        .section-1 .box {
            background-color: #fff;
            border-radius: 10px;
            box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
            padding: 20px;
            display: flex;
            align-items: center;
            margin: 0 auto;
            margin-bottom: 20px;
        }

        .section-1 .box .icon {
            width: 50px;
            height: 50px;
            border-radius: 50%;
            background-color: #f08080;
            display: flex;
            align-items: center;
            justify-content: center;
        }

        .section-1 .box .text {
            font-size: 20px;
            font-weight: 600;
            float: left;
            overflow: auto;
            margin-left: 1.25rem;
        }

        /* section 2 */
        .section-2 .box {
            background-color: #fff;
            border-radius: 10px;
            box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
            padding: 20px;
            display: flex;
            align-items: center;
            margin: 0 auto;
            margin-bottom: 20px;
        }

        .section-2 .icon {
            width: 50px;
            height: 50px;
            border-radius: 50%;
            background-color: #f08080;
            display: flex;
            align-items: center;
            justify-content: center;
        }

        .section-2 .text {
            font-size: 20px;
            font-weight: 600;
            float: left;
            overflow: auto;
            margin-left: 0;
        }

        .section-2 .box {
            display: flex;
            justify-content: space-between;
            align-items: center;
        }

        .section-2 .box-1 {
            /*background-color: darkorange;*/
        }

        .section-2 .box-2 {
            /*background-color: #f08080;*/
        }

        /* section 3 */
        .section-3 .box {
            background-color: #fff;
            border-radius: 10px;
            box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
            padding: 20px;
        }

        .section-3 .box .title {
            font-size: 20px;
            font-weight: 600;
            float: left;
            overflow: auto;
            margin-left: 0;
        }

        .section-3 .box {
            gap: 20px;
        }

        .section-3 .row-1 {
            display: flex;
            gap: 20px;
        }

        .section-3 .table th:first-child {
            width: 10%;
        }

        .section-3 .table th:nth-child(2) {
            width: 20%;
        }

        .section-3 .table th:nth-child(3) {
            width: 30%;
        }

        .section-3 .table th:nth-child(4) {
            width: 20%;
        }

        #top_x_div {
            width: 43rem;
            height: 500px;
        }
    </style>
</head>
<body>
<section class="section-1 mt-4">
    <div class="container">
        <div class="row">
            <div class="col-md-3 col-sm-6">
                <div class="box">
                    <div class="icon">
                        <i class="fas fa-money-bill-wave"></i>
                    </div>
                    <div class="text">
                        <span class="fw-bold money">
                            ${tongTien} Vnđ
                        </span>
                        <p>Tổng tiền bán ra</p>
                    </div>
                </div>
            </div>
            <div class="col-md-3 col-sm-6">
                <div class="box">
                    <div class="icon">
                        <i class="fas fa-cart-plus"></i>
                    </div>
                    <div class="text">
                        <span class="fw-bold">${tongSanPhamDaBan}</span>
                        <p>Sản phẩm đã bán</p>
                    </div>
                </div>
            </div>
            <div class="col-md-3 col-sm-6">
                <div class="box">
                    <div class="icon">
                        <i class="fas fa-file-invoice"></i>
                    </div>
                    <div class="text">
                        <span class="fw-bold">${tongDonHangDaBan}</span>
                        <p class="fs-5">Số lượng đơn hàng</p>
                    </div>
                </div>
            </div>
            <div class="col-md-3 col-sm-6">
                <div class="box">
                    <div class="icon">
                        <i class="fas fa-users"></i>
                    </div>
                    <div class="text">
                        <span class="fw-bold">${tongKhachHangDaMua}</span>
                        <p>Số lượng khách hàng</p>
                    </div>
                </div>
            </div>
        </div>
    </div>
</section>
<section class="section-2 mt-3">
    <div class="container">
        <div class="row">
            <div class="col-lg-6">
                <div class="box box-1">
                    <div class="text">
                        <div class="count">
                  <span>
                    Nhân viên có doanh số cao nhất ${tenNhanVien}
                  </span>
                        </div>
                        <div class="desc">với doanh thu
                            <span class="money_2">${tongTien} VNĐ</span> và ${soLuong} đơn
                        </div>
                    </div>
                    <div class="icon">
                        <i class="fas fa-user"></i>
                    </div>
                </div>
            </div>
            <div class="col-lg-6">
                <div class="box box-2">
                    <div class="text">
                        <div class="count">
                  <span class="fw-bold">
                    Sản phẩm bán chạy nhất ${tenSanPham}
                  </span>
                        </div>
                        <div class="desc">với số lượng ${soLuongSanPham} sản phẩm</div>
                    </div>
                    <div class="icon">
                        <i class="fas fa-cart-plus"></i>
                    </div>
                </div>
            </div>
        </div>
    </div>
</section>
<section class="section-3 mt-3">
    <div class="container">
        <div class="row">
            <div class="col-lg-7 col-left">
                <div class="box">
                    <div class="row-1">
                        <div class="title">
                  <span class="text-uppercase text-danger">
                    Biểu đồ thống kê doanh thu theo từng tháng
                  </span>

                        </div>
                        <%--                        <div class="filter">--%>
                        <%--                            <select--%>
                        <%--                                    class="form-select"--%>
                        <%--                                    aria-label="Default select example"--%>
                        <%--                            >--%>
                        <%--                                <option value="">Tháng 1</option>--%>
                        <%--                                <option value="">Tháng 2</option>--%>
                        <%--                                <option value="">Tháng 3</option>--%>
                        <%--                                <option value="">Tháng 4</option>--%>
                        <%--                                <option value="">Tháng 5</option>--%>
                        <%--                                <option value="">Tháng 6</option>--%>
                        <%--                                <option value="">Tháng 7</option>--%>
                        <%--                                <option value="">Tháng 8</option>--%>
                        <%--                                <option value="">Tháng 9</option>--%>
                        <%--                                <option value="">Tháng 10</option>--%>
                        <%--                                <option value="">Tháng 11</option>--%>
                        <%--                                <option value="">Tháng 12</option>--%>
                        <%--                            </select>--%>
                        <%--                        </div>--%>
                    </div>
                    <div class="chart">
                        <div id="top_x_div"></div>
                    </div>
                </div>
            </div>
            <div class="col-lg-5 col-right">
                <div class="box">
                    <div class="title mb-3">
                        <span class="top_sp">Top sản phẩm</span>
                    </div>
                    <select id="orderBy" name="orderBy" class="ms-1 form-select-sm w-25">
                        <option value="desc">Giảm dần</option>
                        <option value="asc">Tăng dần</option>
                    </select>
                    <button onclick="loadTop5SanPham()" class="btn btn-sm btn-outline-success">Lọc</button>
                    <table class="table text-center">
                        <thead>
                        <tr>
                            <th scope="col">STT</th>
                            <th scope="col">Ảnh</th>
                            <th scope="col">Tên</th>
                            <th scope="col">Số lượng đã bán</th>
                        </tr>
                        </thead>
                        <tbody  id="sanPhamTable">
                        <!-- Dữ liệu sẽ được cập nhật ở đây -->
                        </tbody>
                    </table>

                </div>
            </div>
            <script src="https://code.jquery.com/jquery-3.6.4.min.js"></script>
            <script>
                $(document).ready(function () {
                    // Đặt giá trị mặc định cho #orderBy và tải dữ liệu
                    $("#orderBy").val("desc");

                    loadTop5SanPham();
                });

                function loadTop5SanPham() {
                    // top sp

                    var orderBy = $("#orderBy").val();
                    var apiUrl = (orderBy === "desc") ? "/topbanchay" : "/topbancham";
                    var top_sp = $('.top_sp').text();
                    $.ajax({
                        type: "GET",
                        url: apiUrl,
                        data: { orderBy: orderBy },
                        dataType: "json",
                        success: function (data) {
                            updateTable(data);
                        },
                        error: function () {
                            alert("Đã xảy ra lỗi khi tải dữ liệu.");
                        }
                    });
                }

                function updateTable(data) {
                    var tableBody = $("#sanPhamTable");
                    tableBody.empty();

                    if (data && data.length > 0) {
                        $.each(data, function (index, sanPhamInfo) {
                            var row = "<tr>" +
                                "<td>" + (index + 1) + "</td>" +
                                "<td><img src='" + sanPhamInfo[2] + "' alt='' width='50px' height='50px' /></td>" +
                                "<td>" + sanPhamInfo[0] + "</td>" +
                                "<td>" + sanPhamInfo[1] + "</td>" +
                                "</tr>";

                            tableBody.append(row);
                        });
                    } else {
                        // Thêm một thông báo hoặc xử lý khác khi không có dữ liệu
                        console.log("Không có dữ liệu");
                        tableBody.append("<tr><td colspan='4'>Không có dữ liệu</td></tr>");
                    }
                }

            </script>


        </div>
    </div>
</section>

<%--    bieu do--%>
<script type="text/javascript" src="https://www.gstatic.com/charts/loader.js"></script>
<script type="text/javascript">
    google.charts.load('current', {'packages': ['bar']});
    google.charts.setOnLoadCallback(drawStuff);

    function drawStuff() {
        var data = new google.visualization.arrayToDataTable([
            ['', 'Vnđ:'],
            ["${Tru0ThangTruoc}", ${TongDoanhThuThangHienTai}],
            ["${Tru1ThangTruoc}", ${TongDoanhThuThangHienTaiTru1}],
            ["${Tru2ThangTruoc}", ${TongDoanhThuThangHienTaiTru2}],
            ["${Tru3ThangTruoc}", ${TongDoanhThuThangHienTaiTru3}],
            ["${Tru4ThangTruoc}", ${TongDoanhThuThangHienTaiTru4}],
            ["${Tru5ThangTruoc}", ${TongDoanhThuThangHienTaiTru5}],
            ["${Tru6ThangTruoc}", ${TongDoanhThuThangHienTaiTru6}]
        ]);

        var options = {
            legend: {position: 'none'},
            chart: {
                title: 'Biểu đồ :',
                subtitle: ''
            },
            axes: {
                x: {
                    0: {side: 'top'} // Top x-axis.
                }
            },
            bar: {groupWidth: "90%"}
        };

        var chart = new google.charts.Bar(document.getElementById('top_x_div'));
        // Convert the Classic options to Material options.
        chart.draw(data, google.charts.Bar.convertOptions(options));
    };
    $(document).ready(function () {
        var money = $('.money').text();
        var money_2 = $('.money_2').text();
        // format theo tiền việt nam
        var money1 = money.replace(/\B(?=(\d{3})+(?!\d))/g, ',');
        money_2 = money_2.replace(/\B(?=(\d{3})+(?!\d))/g, ',');
        $('.money').text(money1);
        $('.money_2').text(money_2);
        console.log(money1);

    });
</script>
</body>
</html>
