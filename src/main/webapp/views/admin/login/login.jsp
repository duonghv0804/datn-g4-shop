<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="f" uri="jakarta.tags.functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<html>
<head>
   <title>Admin</title>
   <link rel="icon" href="/../views/admin/css/Summer.png" type="image/x-icon"/>
   <link href="/../views/admin/css/styles.css" rel="stylesheet"/>
   <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/toastr.js/latest/css/toastr.min.css"/>
   <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css">
   <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.2/css/all.min.css"
         integrity="sha512-z3gLpd7yknf1YoNbCzqRKc4qyor8gaKU1qmn+CShxbuBusANI9QpRohGBreCFkKxLhei6S9CQXFEbbKuqLg0DA=="
         crossorigin="anonymous" referrerpolicy="no-referrer"/>
</head>
<body>
<style>
    .divider:after,
    .divider:before {
        content: "";
        flex: 1;
        height: 1px;
        background: #eee;
    }

    .h-custom {
        height: calc(100% - 73px);
    }

    @media (max-width: 450px) {
        .h-custom {
            height: 100%;
        }
    }
</style>

<section class="vh-100">
   <div class="container-fluid h-custom">
      <div class="row d-flex justify-content-center align-items-center h-100">
         <%-- image background --%>
         <div class="col-md-9 col-lg-6 col-xl-5">
            <img src="https://mdbcdn.b-cdn.net/img/Photos/new-templates/bootstrap-login-form/draw2.webp"
                 class="img-fluid" alt="Sample image">
         </div>
         <div class="col-md-8 col-lg-6 col-xl-4 offset-xl-1">
            <%-- third patty login --%>
            <div class="d-flex flex-row align-items-center justify-content-center justify-content-lg-start">
               <p class="lead fw-normal mb-0 me-3">Đăng nhập với</p>
               <button type="button" class="btn btn-primary btn-floating mx-1">
                  <i class="fab fa-facebook-f"></i>
               </button>

               <button type="button" class="btn btn-primary btn-floating mx-1">
                  <i class="fab fa-twitter"></i>
               </button>

               <button type="button" class="btn btn-primary btn-floating mx-1">
                  <i class="fab fa-linkedin-in"></i>
               </button>
            </div>

            <div class="divider d-flex align-items-center my-4">
               <p class="text-center fw-bold mx-3 mb-0">Hoặc</p>
            </div>
            <%-- error login --%>
            <c:if test="${sessionScope.error != null}">
               <div class="alert alert-danger p-2 my-3"> <!-- Display error message here -->
                     ${sessionScope.error}
               </div>
            </c:if>

            <form:form modelAttribute="nv" action="/check-login-admin" method="post">
               <!-- Email input -->
               <div class="form-outline mb-3">
                  <label class="form-label" for="form3Example3">Email <span class="text-danger">*</span></label>
                  <form:input path="email" type="email" id="form3Example3" class="form-control"
                              placeholder="Vui lòng nhập email"/>
               </div>

               <!-- Password input -->
               <div class="form-outline mb-3">
                  <label class="form-label" for="form3Example4">Mật khẩu <span class="text-danger">*</span></label>
                  <form:input path="matKhau" type="password" id="form3Example4"
                              class="form-control" placeholder="Vui lòng nhập mật khẩu"/>
               </div>

               <div class="text-center mb-3">
                  <button type="submit" class="btn btn-primary w-100">Đăng nhập</button>
               </div>
            </form:form>
         </div>
      </div>
   </div>
   <div
           class="d-flex flex-column flex-md-row text-center text-md-start justify-content-between py-4 px-4 px-xl-5 bg-primary">
      <!-- Copyright -->
      <div class="text-white mb-3 mb-md-0">
         DATN FA24 - SD84
      </div>
      <!-- Copyright -->

      <!-- Right -->
      <div>
         <a href="#!" class="text-decoration-none text-white me-4">
            <i class="fab fa-facebook-f"></i>
         </a>
         <a href="#!" class="text-decoration-none text-white me-4">
            <i class="fab fa-twitter"></i>
         </a>
         <a href="#!" class="text-decoration-none text-white me-4">
            <i class="fab fa-google"></i>
         </a>
         <a href="#!" class="text-decoration-none text-white">
            <i class="fab fa-linkedin-in"></i>
         </a>
      </div>
      <!-- Right -->
   </div>
</section>
<!-- Bootstrap core JS-->
<script src="/../js/bootstrap.min.js"></script>
<!-- Core theme JS-->
<script src="https://cdnjs.cloudflare.com/ajax/libs/toastr.js/latest/js/toastr.min.js"></script>
<!-- Bao gồm các tập lệnh Spring form và các tập lệnh khác -->
<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.min.js"></script>


</body>

</html>
