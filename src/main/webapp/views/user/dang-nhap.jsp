<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<style>
    form {
        background-color: #f8f9fa;
        padding: 20px;
        border: 1px solid #dee2e6;
        border-radius: 5px;
        margin-top: 10px;
        margin-bottom: 10px;
    }
</style>

<div class="row mt-5 col-6 offset-3">
    <div>
        <ul class="nav nav-tabs" id="myTab" role="tablist">
            <li class="nav-item" role="presentation">
                <a class="nav-link active text-dark" id="login-tab" data-bs-toggle="tab"
                   href="#login" role="tab"
                   aria-controls="login" aria-selected="true">Đăng Nhập</a>
            </li>
            <li class="nav-item" role="presentation">
                <a class="nav-link text-dark" id="register-tab" data-bs-toggle="tab"
                   href="#register" role="tab"
                   aria-controls="register" aria-selected="false">Đăng Ký</a>
            </li>
        </ul>
        <div class="tab-content" id="myTabContent">
            <div class="tab-pane fade show active" id="login" role="tabpanel"
                 aria-labelledby="login-tab">

                <form:form modelAttribute="dangNhap" action="/dang-nhap" method="post">
                    <div class="row">
                        <div class="col-6">
                            <label for="loginEmail" class="form-label">Email (*)</label>
                            <form:input path="email" type="email" class="form-control" id="loginEmail"  aria-describedby="emailHelp" />
                        </div>
                        <div class="col-6">
                            <label for="loginPassword" class="form-label">Mật Khẩu (*)</label>
                            <form:input path="matKhau" type="password" class="form-control" id="loginPassword"/>
                        </div>
                    </div>
                    <br/>
                    <c:if test="${not empty loginError}">
                        <div class="alert alert-danger">${loginError}</div>
                    </c:if>
                    <div class="row mt-3">
                        <div class="col-9">
                        </div>
<%--                        <div class="col-3">--%>
<%--                            <a class="link-dark" style="text-decoration: none">Quên mật khẩu</a>--%>
<%--                        </div>--%>
                    </div>
                    <button type="submit" class="btn btn-dark mt-5">Đăng Nhập</button>
                </form:form>
            </div>
            <div class="tab-pane fade" id="register" role="tabpanel"
                 aria-labelledby="register-tab">
                <form:form id="registrationForm" modelAttribute="dangKy" action="/dang-ky" method="post">
                    <div class="row">
                        <div class="col-6">
                            <label for="registerName" class="form-label">Họ Và Tên (*)</label>
                            <form:input path="hoTen" type="text" class="form-control" id="registerName"/>
                        </div>
                        <div class="col-6">
                            <label for="registerEmail" class="form-label">Email (*)</label>
                            <form:input path="emailDK" type="email" class="form-control" id="registerEmail"/>
                        </div>
                    </div>
                    <div class="row mt-3">
                        <div class="col-6">
                            <label for="registerPassword" class="form-label">Mật Khẩu (*)</label>
                            <form:input path="matKhauDK" type="password" class="form-control" id="registerPassword" />
                        </div>
                        <div class="col-6">
                            <label for="registerPassword" class="form-label">Xác nhận Mật Khẩu (*)</label>
                            <input type="password" class="form-control" id="confirmPassword">
                        </div>
                    </div>
                    <div class="flex items-center mt-3">
                        <div class="flex items-center">
                            <label class="label-nho text-base">
                                <input type="checkbox" id="accept" name="accept" value="ok" class="hidden-checkbox">
                                <span class="checkmark rounded"></span>
                                Tôi đồng ý với Chính sách bảo mật và Chính sách đổi trả của G4
                            </label>
                        </div>
                    </div>
                    <c:if test="${not empty loginError}">
                        <div class="alert alert-danger">${loginError}</div>
                    </c:if>

                    <c:if test="${not empty successMessage}">
                        <div class="alert alert-success">${successMessage}</div>
                    </c:if>
                    <button type="submit" class="btn btn-dark mt-5">Đăng Ký</button>
                </form:form>
            </div>
        </div>
    </div>
</div>

<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
<script type="text/javascript">
    $(document).ready(function() {
        // Gọi hàm hideErrorMessage khi trang đã tải hoàn toàn
        hideErrorMessage();
        hideErrorMessage2();

        // Xác định fragment identifier trong URL và chuyển đến tab tương ứng
        var hash = window.location.hash;
        if (hash) {
            $('.nav-link').removeClass('active');
            $('.tab-pane').removeClass('active show');
            $('a[href="' + hash + '"]').addClass('active');
            $(hash).addClass('active show');
        }
    });

    function hideErrorMessage() {
        // Sử dụng jQuery để ẩn thông báo sau 5 giây
        setTimeout(function() {
            $('.alert-danger').fadeOut('slow');
        }, 5000);
    }

    function hideErrorMessage2() {
        // Sử dụng jQuery để ẩn thông báo sau 5 giây
        setTimeout(function() {
            $('.alert-success').fadeOut('slow');
        }, 5000);
    }


    document.addEventListener("DOMContentLoaded", function () {
        // Lắng nghe sự kiện khi form được gửi
        document.getElementById("registrationForm").addEventListener("submit", function (event) {
            // Kiểm tra xem checkbox đã được chọn
            var checkbox = document.getElementById("accept");
            if (!checkbox.checked) {
                alert("Vui lòng đồng ý với các chính sách trước khi đăng ký.");
                event.preventDefault(); // Ngăn chặn việc gửi form
                return;
            }

            // Kiểm tra xem mật khẩu và xác nhận mật khẩu trùng nhau
            var password = document.getElementById("registerPassword").value;
            var confirmPassword = document.getElementById("confirmPassword").value;
            if (password !== confirmPassword) {
                alert("Mật khẩu và xác nhận mật khẩu phải trùng nhau.");
                event.preventDefault(); // Ngăn chặn việc gửi form
            }
        });
    });
</script>