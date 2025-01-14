<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-T3c6CoIi6uLrA9TneNEoa7RxnatzjcDSCmG1MXxSR1GAsXEV/Dwwykc2MPK8M2HN"
          crossorigin="anonymous">
    <!-- Custom CSS to make input borders square and button styles -->
    <style>
        input.form-control,
        button.btn-primary {
            border-radius: 0; /* Set border-radius to 0 for sharp corners */
        }

        button.btn-primary {
            border: 1px solid #fff; /* Set button border to white */
            background-color: #000; /* Set background color to black */
            color: #fff; /* Set text color to white */
        }

        .additional-content {
            display: none; /* Hide additional content by default */
        }

        .change-password-text {
            font-size: 18px; /* Set font size to 18px for larger text */
            color: #000; /* Set text color to black */
        }
    </style>
</head>

<body>
<div class="container mt-5 col-3">
    <h2>Địa Chỉ Giao Hàng</h2>
    <div class="form-group">
        <label for="hoTen" class="mt-3 mb-2 fw-bold">Họ Và Tên</label>
        <input type="text" class="form-control" id="hoTen">
    </div>
    <div class="form-group">
        <label for="email" class="mt-3 mb-2 fw-bold">Email</label>
        <input type="email" class="form-control" id="email">
    </div>

    <div class="form-group">
        <label for="soDienThoai" class="mt-3 mb-2 fw-bold">Số Điện Thoại</label>
        <input type="tel" class="form-control" id="soDienThoai">
    </div>

    <div class="form-group">
        <label for="soDienThoai" class="mt-3 mb-2 fw-bold">Tỉnh/Thành Phố</label>
        <input type="tel" class="form-control" id="tinhThanhPho">
    </div>

    <div class="form-group">
        <label for="soDienThoai" class="mt-3 mb-2 fw-bold">Quận/Huyện</label>
        <input type="tel" class="form-control" id="quanHuyen">
    </div>

    <div class="form-group">
        <label for="soDienThoai" class="mt-3 mb-2 fw-bold">Xã/Phường</label>
        <input type="tel" class="form-control" id="xaPhuong">
    </div>

    <div class="form-group">
        <label for="soDienThoai" class="mt-3 mb-2 fw-bold">Địa Chỉ</label>
        <input type="tel" class="form-control" id="diaChi" placeholder=". . .">
    </div>



    <button type="button" class="btn btn-primary mt-3" onclick="submitForm()">Submit</button>
</div>

<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
<script>
    function toggleAdditionalContent() {
        $('.additional-content').toggle();
    }

    function submitForm() {
    }
</script>
</body>

</html>
