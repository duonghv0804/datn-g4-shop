<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div class="row m-0 p-3 justify-content-center">
   <div class="col-md-6 col-sm-12">
      <ul class="nav nav-tabs border-bottom-0" id="myTab" role="tablist">
         <li class="nav-item" role="presentation">
            <a class="nav-link active" id="login-tab" data-bs-toggle="tab"
               href="#login" role="tab"
               aria-controls="login" aria-selected="true">Đăng Nhập</a>
         </li>
         <li class="nav-item" role="presentation">
            <a class="nav-link" id="register-tab" data-bs-toggle="tab"
               href="#register" role="tab"
               aria-controls="register" aria-selected="false">Đăng Ký</a>
         </li>
      </ul>
      <div class="tab-content p-3 border rounded" id="myTabContent">
         <%-- login --%>
         <div class="tab-pane fade show active" id="login" role="tabpanel"
              aria-labelledby="login-tab">
            <div class="mb-3">
               <h1 class="fs-5 m-0">Chào mừng trở lại</h1>
               <p class="text-secondary">Vui lòng điền đầy đủ thông tin bên dưới!</p>
            </div>

            <%-- message login error --%>
            <c:if test="${not empty loginError}">
               <div class="alert alert-danger p-2">${loginError}</div>
            </c:if>
            <form:form modelAttribute="dangNhap" action="/dang-nhap" method="post">
               <div class="row">
                  <div class="col-12 mb-3">
                     <label for="loginEmail" class="form-label">Email <span class="text-danger">*</span></label>
                     <form:input path="email" type="email" class="form-control" id="loginEmail"
                                 aria-describedby="emailHelp"/>
                  </div>
                  <div class="col-12 mb-3">
                     <label for="loginPassword" class="form-label">Mật Khẩu <span class="text-danger">*</span></label>
                     <form:input path="matKhau" type="password" class="form-control" id="loginPassword"/>
                  </div>
               </div>

<%--               <div class="row mt-3">--%>
<%--                  <div class="col-9">--%>
<%--                  </div>--%>
<%--                     &lt;%&ndash;                        <div class="col-3">&ndash;%&gt;--%>
<%--                     &lt;%&ndash;                            <a class="link-dark" style="text-decoration: none">Quên mật khẩu</a>&ndash;%&gt;--%>
<%--                     &lt;%&ndash;                        </div>&ndash;%&gt;--%>
<%--               </div>--%>
               <div class="row m-0">
                  <button type="submit" class="btn btn-primary">Đăng Nhập</button>
               </div>
            </form:form>
         </div>
         <%-- register --%>
         <div class="tab-pane fade" id="register" role="tabpanel"
              aria-labelledby="register-tab">
            <div class="mb-3">
               <h1 class="fs-5 m-0">Tạo tài khoản</h1>
               <p class="text-secondary">G4Shop xin chào, mua sắm để nhận nhiều ưu đãi bạn nhé!</p>
            </div>
            <%-- message login error --%>
            <c:if test="${not empty loginError}">
               <div class="alert alert-danger p-2">${loginError}</div>
            </c:if>
            <%-- success mesage register --%>
            <c:if test="${not empty successMessage}">
               <div class="alert alert-success p-2">${successMessage}</div>
            </c:if>
            <form:form id="registrationForm" modelAttribute="dangKy" action="/dang-ky" method="post">
               <div class="row">
                  <div class="col-6">
                     <label for="registerName" class="form-label">Họ và tên <span class="text-danger">*</span></label>
                     <form:input path="hoTen" type="text" class="form-control" id="registerName"/>
                  </div>
                  <div class="col-6">
                     <label for="registerEmail" class="form-label">Email <span class="text-danger">*</span></label>
                     <form:input path="emailDK" type="email" class="form-control" id="registerEmail"/>
                  </div>
               </div>
               <div class="row mt-3">
                  <div class="col-6">
                     <label for="registerPassword" class="form-label">Mật khẩu <span class="text-danger">*</span></label>
                     <form:input path="matKhauDK" type="password" class="form-control" id="registerPassword"/>
                  </div>
                  <div class="col-6">
                     <label for="registerPassword" class="form-label">Xác nhận mật khẩu <span class="text-danger">*</span></label>
                     <input type="password" class="form-control" id="confirmPassword">
                  </div>
               </div>
               <div class="flex items-center mt-3">
                  <div class="flex items-center">
                     <label class="label-nho text-base">
                        <input type="checkbox" id="accept" name="accept" value="ok" class="form-check-input hidden-checkbox">
                        <span class="checkmark rounded"></span>
                        Tôi đồng ý với <a class="text-decoration-none" href="/chinh-sach-bao-mat">Chính sách bảo mật</a> và <a class="text-decoration-none" href="/chinh-sach-doi-tra">Chính sách đổi trả</a> của G4Shop
                     </label>
                  </div>
               </div>

               <div class="row m-0 mt-3">
                  <button type="submit" class="btn btn-primary">Đăng Ký</button>
               </div>
            </form:form>
         </div>
      </div>
   </div>
</div>

<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
<script type="text/javascript">
    $(document).ready(function () {
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
        setTimeout(function () {
            $('.alert-danger').fadeOut('slow');
        }, 5000);
    }

    function hideErrorMessage2() {
        // Sử dụng jQuery để ẩn thông báo sau 5 giây
        setTimeout(function () {
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