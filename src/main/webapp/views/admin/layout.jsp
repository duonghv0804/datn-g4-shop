<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="f" uri="jakarta.tags.functions" %>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form" %>

<html>
<head>
   <title>Admin</title>
   <link rel="icon"
         href="/../views/admin/css/Summer.png" type="image/x-icon"/>
   <link href="/../views/admin/css/styles.css" rel="stylesheet"/>
   <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/toastr.js/latest/css/toastr.min.css"/>
   <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.4/css/all.min.css" rel="stylesheet">
   <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
   <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"
           integrity="sha384-C6RzsynM9kWDrMNeT87bh95OGNyZPhcTNXj1NW7RuBCsyN/o0jlpcV8Qyq46cDfL"
           crossorigin="anonymous"></script>
   <%--   <link rel="preconnect" href="https://fonts.googleapis.com">--%>
   <%--   <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>--%>
   <%--   <link href="https://fonts.googleapis.com/css2?family=Dosis:wght@200..800&family=Exo+2:ital,wght@0,100..900;1,100..900&family=Inter:wght@100..900&family=Noto+Serif:ital,wght@0,100..900;1,100..900&family=Roboto+Mono:ital,wght@0,100..700;1,100..700&display=swap"--%>
   <%--         rel="stylesheet">--%>
   <script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>
</head>
<style>

    /* Bo tròn hình ảnh */
    .img-fluid {
        border-radius: 50%;
        background-color: white; /* Đổi màu nền thành trắng */
    }

    .font {
        font-family: "Exo 2", sans-serif;
        font-optical-sizing: auto;
        font-style: normal;
    }

    /* Sidebar tuỳ chỉnh */
    .custom-sidebar {
        background-color: white; /* Đổi màu nền thành trắng */
        border-right: 1px solid #dee2e6;
    }

    .custom-sidebar .list-group-item {
        background-color: white; /* Đổi màu nền thành trắng */
        color: #0000008C; /* Đổi màu chữ thành đen */
        border: none;
    }

    .custom-sidebar .list-group-item:hover {
        background-color: #f1f1f1; /* Màu xám nhạt khi hover */
    }

    .custom-sidebar .list-group-item.active {
        background-color: #e7e7e7; /* Màu xám nhạt cho mục active */
        border-right: 3px solid #007bff;
    }

    .custom-sidebar .list-group-item.active:hover {
        background-color: #e7e7e7; /* Giữ màu xám nhạt khi hover */
    }

    .custom-sidebar .list-group-item.active i {
        color: #007bff; /* Màu xanh dương cho icon khi active */
    }

    .custom-sidebar .list-group-item i {
        margin-right: 10px;
    }

    .custom-sidebar .sidebar-heading {
        text-align: center;
        padding: 20px 0;
        border-bottom: 1px solid #dee2e6;
    }

    .custom-sidebar .sidebar-heading img {
        width: 100px;
    }

    .custom-sidebar .sidebar-heading h3 {
        font-size: 1.5rem;
        font-weight: 700;
        margin-bottom: 0;
        color: #000000; /* Đổi màu chữ thành đen */
    }

    .custom-sidebar .sidebar-heading p {
        font-size: 0.875rem;
        font-weight: 400;
        margin-bottom: 0;
        color: #000000; /* Đổi màu chữ thành đen */
    }

    /* Dropdown menu tuỳ chỉnh */
    .dropdown {
        position: relative;
        display: inline-block;
    }

    .dropdown:hover .dropdown-menu {
        display: block;
        margin-left: 50px;
    }

    /* Để giữ menu hiển thị khi hover ra ngoài */
    .dropdown-menu:hover {
        display: block;
    }

    .dropdown-menu a {
        display: block;
        padding: 10px;
        text-decoration: none;
        color: #212529;
    }
</style>

<body>
<div class="d-flex " id="wrapper">
   <!-- Sidebar-->
   <div class="border-end custom-sidebar font" id="sidebar-wrapper">
      <div class="sidebar-heading border-bottom bg-light">
         <a href="/admin">
            <img src="/../views/admin/css/Summer.png" alt="" class="img-fluid" style="width: 150px">
         </a>

      </div>
      <div class="list-group list-group-flush">
         <c:if test="${admin.chucVu == 0}">
            <!-- Hiển thị menu cho quản lý -->
            <div class="dropdown">
               <a class="list-group-item list-group-item-action list-group-item-light p-3 dropdown-toggle" href="#"
                  role="button" data-bs-toggle="dropdown" aria-expanded="false">
                  <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor"
                       class="bi bi-list-ul" viewBox="0 0 16 16">
                     <path fill-rule="evenodd"
                           d="M5 11.5a.5.5 0 0 1 .5-.5h9a.5.5 0 0 1 0 1h-9a.5.5 0 0 1-.5-.5m0-4a.5.5 0 0 1 .5-.5h9a.5.5 0 0 1 0 1h-9a.5.5 0 0 1-.5-.5m0-4a.5.5 0 0 1 .5-.5h9a.5.5 0 0 1 0 1h-9a.5.5 0 0 1-.5-.5m-3 1a1 1 0 1 0 0-2 1 1 0 0 0 0 2m0 4a1 1 0 1 0 0-2 1 1 0 0 0 0 2m0 4a1 1 0 1 0 0-2 1 1 0 0 0 0 2"/>
                  </svg>
                  Quản lí sản phẩm
               </a>

               <ul class="dropdown-menu">
                  <a class="dropdown-item" href="/admin/san-pham/index">Sản phẩm</a>
                  <a class="dropdown-item" href="/admin/san-pham-chi-tiet/index">Sản Phẩm Chi Tiết</a>
                  <a class="dropdown-item" href="/admin/loai/index">Loại</a>
                  <a class="dropdown-item" href="/admin/kich-co/index">Kích cỡ</a>
                  <a class="dropdown-item" href="/admin/mau-sac/index">Màu sắc</a>
                  <a class="dropdown-item" href="/admin/thuong-hieu/index">Thương hiệu</a>
                  <a class="dropdown-item" href="/admin/sex/index">Sex</a>
               </ul>
            </div>

            <a class="list-group-item list-group-item-action list-group-item-light p-3"
               href="/admin/khach-hang/index">
               <svg xmlns="http://www.w3.org/2000/svg" width="20" height="20" fill="currentColor"
                    class="bi bi-person" viewBox="0 0 16 16">
                  <path d="M8 8a3 3 0 1 0 0-6 3 3 0 0 0 0 6m2-3a2 2 0 1 1-4 0 2 2 0 0 1 4 0m4 8c0 1-1 1-1 1H3s-1 0-1-1 1-4 6-4 6 3 6 4m-1-.004c-.001-.246-.154-.986-.832-1.664C11.516 10.68 10.289 10 8 10s-3.516.68-4.168 1.332c-.678.678-.83 1.418-.832 1.664z"/>
               </svg>
               Khách Hàng</a>
            <a class="list-group-item list-group-item-action list-group-item-light p-3"
               href="/admin/nhan-vien/index">
               <svg xmlns="http://www.w3.org/2000/svg" width="18" height="18" fill="currentColor"
                    class="bi bi-people" viewBox="0 0 16 16">
                  <path d="M15 14s1 0 1-1-1-4-5-4-5 3-5 4 1 1 1 1zm-7.978-1L7 12.996c.001-.264.167-1.03.76-1.72C8.312 10.629 9.282 10 11 10c1.717 0 2.687.63 3.24 1.276.593.69.758 1.457.76 1.72l-.008.002-.014.002zM11 7a2 2 0 1 0 0-4 2 2 0 0 0 0 4m3-2a3 3 0 1 1-6 0 3 3 0 0 1 6 0M6.936 9.28a6 6 0 0 0-1.23-.247A7 7 0 0 0 5 9c-4 0-5 3-5 4q0 1 1 1h4.216A2.24 2.24 0 0 1 5 13c0-1.01.377-2.042 1.09-2.904.243-.294.526-.569.846-.816M4.92 10A5.5 5.5 0 0 0 4 13H1c0-.26.164-1.03.76-1.724.545-.636 1.492-1.256 3.16-1.275ZM1.5 5.5a3 3 0 1 1 6 0 3 3 0 0 1-6 0m3-2a2 2 0 1 0 0 4 2 2 0 0 0 0-4"/>
               </svg>
               Nhân viên</a>
            <a class="list-group-item list-group-item-action list-group-item-light p-3"
               href="/admin/giam-gia/index">
               <svg xmlns="http://www.w3.org/2000/svg" width="18" height="18" fill="currentColor"
                    class="bi bi-percent" viewBox="0 0 16 16">
                  <path d="M13.442 2.558a.625.625 0 0 1 0 .884l-10 10a.625.625 0 1 1-.884-.884l10-10a.625.625 0 0 1 .884 0M4.5 6a1.5 1.5 0 1 1 0-3 1.5 1.5 0 0 1 0 3m0 1a2.5 2.5 0 1 0 0-5 2.5 2.5 0 0 0 0 5m7 6a1.5 1.5 0 1 1 0-3 1.5 1.5 0 0 1 0 3m0 1a2.5 2.5 0 1 0 0-5 2.5 2.5 0 0 0 0 5"/>
               </svg>
               Giảm giá</a>
            <a class="list-group-item list-group-item-action list-group-item-light p-3"
               href="/admin/khuyen-mai/index">
               <svg xmlns="http://www.w3.org/2000/svg" width="20" height="20" fill="currentColor"
                    class="bi bi-ticket-perforated" viewBox="0 0 16 16">
                  <path d="M4 4.85v.9h1v-.9zm7 0v.9h1v-.9zm-7 1.8v.9h1v-.9zm7 0v.9h1v-.9zm-7 1.8v.9h1v-.9zm7 0v.9h1v-.9zm-7 1.8v.9h1v-.9zm7 0v.9h1v-.9z"/>
                  <path d="M1.5 3A1.5 1.5 0 0 0 0 4.5V6a.5.5 0 0 0 .5.5 1.5 1.5 0 1 1 0 3 .5.5 0 0 0-.5.5v1.5A1.5 1.5 0 0 0 1.5 13h13a1.5 1.5 0 0 0 1.5-1.5V10a.5.5 0 0 0-.5-.5 1.5 1.5 0 0 1 0-3A.5.5 0 0 0 16 6V4.5A1.5 1.5 0 0 0 14.5 3zM1 4.5a.5.5 0 0 1 .5-.5h13a.5.5 0 0 1 .5.5v1.05a2.5 2.5 0 0 0 0 4.9v1.05a.5.5 0 0 1-.5.5h-13a.5.5 0 0 1-.5-.5v-1.05a2.5 2.5 0 0 0 0-4.9z"/>
               </svg>
               Khuyến mãi</a>
            <a class="list-group-item list-group-item-action list-group-item-light p-3"
               href="/admin/thong-ke/index">
               <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor"
                    class="bi bi-graph-up-arrow" viewBox="0 0 16 16">
                  <path fill-rule="evenodd"
                        d="M0 0h1v15h15v1H0zm10 3.5a.5.5 0 0 1 .5-.5h4a.5.5 0 0 1 .5.5v4a.5.5 0 0 1-1 0V4.9l-3.613 4.417a.5.5 0 0 1-.74.037L7.06 6.767l-3.656 5.027a.5.5 0 0 1-.808-.588l4-5.5a.5.5 0 0 1 .758-.06l2.609 2.61L13.445 4H10.5a.5.5 0 0 1-.5-.5"/>
               </svg>
               Thống kê</a>
            <a class="list-group-item list-group-item-action list-group-item-light p-3"
               href="/admin/hinh-thuc-thanh-toan/index">
               <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor"
                    class="bi bi-cash-coin" viewBox="0 0 16 16">
                  <path fill-rule="evenodd"
                        d="M11 15a4 4 0 1 0 0-8 4 4 0 0 0 0 8m5-4a5 5 0 1 1-10 0 5 5 0 0 1 10 0"/>
                  <path d="M9.438 11.944c.047.596.518 1.06 1.363 1.116v.44h.375v-.443c.875-.061 1.386-.529 1.386-1.207 0-.618-.39-.936-1.09-1.1l-.296-.07v-1.2c.376.043.614.248.671.532h.658c-.047-.575-.54-1.024-1.329-1.073V8.5h-.375v.45c-.747.073-1.255.522-1.255 1.158 0 .562.378.92 1.007 1.066l.248.061v1.272c-.384-.058-.639-.27-.696-.563h-.668zm1.36-1.354c-.369-.085-.569-.26-.569-.522 0-.294.216-.514.572-.578v1.1zm.432.746c.449.104.655.272.655.569 0 .339-.257.571-.709.614v-1.195z"/>
                  <path d="M1 0a1 1 0 0 0-1 1v8a1 1 0 0 0 1 1h4.083q.088-.517.258-1H3a2 2 0 0 0-2-2V3a2 2 0 0 0 2-2h10a2 2 0 0 0 2 2v3.528c.38.34.717.728 1 1.154V1a1 1 0 0 0-1-1z"/>
                  <path d="M9.998 5.083 10 5a2 2 0 1 0-3.132 1.65 6 6 0 0 1 3.13-1.567"/>
               </svg>
               Hình Thức Thanh Toán</a>

         </c:if>
         <c:if test="${admin.chucVu == 1}">
            <!-- Hiển thị menu cho nhân viên -->
            <div class="dropdown">
               <a class="list-group-item list-group-item-action list-group-item-light p-3 dropdown-toggle" href="#"
                  role="button" data-bs-toggle="dropdown" aria-expanded="false">
                  <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor"
                       class="bi bi-list-ul" viewBox="0 0 16 16">
                     <path fill-rule="evenodd"
                           d="M5 11.5a.5.5 0 0 1 .5-.5h9a.5.5 0 0 1 0 1h-9a.5.5 0 0 1-.5-.5m0-4a.5.5 0 0 1 .5-.5h9a.5.5 0 0 1 0 1h-9a.5.5 0 0 1-.5-.5m0-4a.5.5 0 0 1 .5-.5h9a.5.5 0 0 1 0 1h-9a.5.5 0 0 1-.5-.5m-3 1a1 1 0 1 0 0-2 1 1 0 0 0 0 2m0 4a1 1 0 1 0 0-2 1 1 0 0 0 0 2m0 4a1 1 0 1 0 0-2 1 1 0 0 0 0 2"/>
                  </svg>
                  Quản lí sản phẩm
               </a>

               <ul class="dropdown-menu">
                  <a class="dropdown-item" href="/admin/san-pham/index">Sản phẩm</a>
                  <a class="dropdown-item" href="/admin/san-pham-chi-tiet/index">Sản Phẩm Chi Tiết</a>
                  <a class="dropdown-item" href="/admin/loai/index">Loại</a>
                  <a class="dropdown-item" href="/admin/kich-co/index">Kích cỡ</a>
                  <a class="dropdown-item" href="/admin/mau-sac/index">Màu sắc</a>
                  <a class="dropdown-item" href="/admin/thuong-hieu/index">Thương hiệu</a>
                  <a class="dropdown-item" href="/admin/sex/index">Sex</a>
               </ul>
            </div>
            <a class="list-group-item list-group-item-action list-group-item-light p-3"
               href="/admin/khach-hang/index">
               <svg xmlns="http://www.w3.org/2000/svg" width="20" height="20" fill="currentColor"
                    class="bi bi-person" viewBox="0 0 16 16">
                  <path d="M8 8a3 3 0 1 0 0-6 3 3 0 0 0 0 6m2-3a2 2 0 1 1-4 0 2 2 0 0 1 4 0m4 8c0 1-1 1-1 1H3s-1 0-1-1 1-4 6-4 6 3 6 4m-1-.004c-.001-.246-.154-.986-.832-1.664C11.516 10.68 10.289 10 8 10s-3.516.68-4.168 1.332c-.678.678-.83 1.418-.832 1.664z"/>
               </svg>
               Khách Hàng</a>
            <a class="list-group-item list-group-item-action list-group-item-light p-3"
               href="/admin/giam-gia/index">
               <svg xmlns="http://www.w3.org/2000/svg" width="18" height="18" fill="currentColor"
                    class="bi bi-percent" viewBox="0 0 16 16">
                  <path d="M13.442 2.558a.625.625 0 0 1 0 .884l-10 10a.625.625 0 1 1-.884-.884l10-10a.625.625 0 0 1 .884 0M4.5 6a1.5 1.5 0 1 1 0-3 1.5 1.5 0 0 1 0 3m0 1a2.5 2.5 0 1 0 0-5 2.5 2.5 0 0 0 0 5m7 6a1.5 1.5 0 1 1 0-3 1.5 1.5 0 0 1 0 3m0 1a2.5 2.5 0 1 0 0-5 2.5 2.5 0 0 0 0 5"/>
               </svg>
               Giảm giá</a>
            <a class="list-group-item list-group-item-action list-group-item-light p-3"
               href="/admin/khuyen-mai/index">
               <svg xmlns="http://www.w3.org/2000/svg" width="18" height="18" fill="currentColor"
                    class="bi bi-percent" viewBox="0 0 16 16">
                  <path d="M13.442 2.558a.625.625 0 0 1 0 .884l-10 10a.625.625 0 1 1-.884-.884l10-10a.625.625 0 0 1 .884 0M4.5 6a1.5 1.5 0 1 1 0-3 1.5 1.5 0 0 1 0 3m0 1a2.5 2.5 0 1 0 0-5 2.5 2.5 0 0 0 0 5m7 6a1.5 1.5 0 1 1 0-3 1.5 1.5 0 0 1 0 3m0 1a2.5 2.5 0 1 0 0-5 2.5 2.5 0 0 0 0 5"/>
               </svg>
               Khuyến mãi</a>
         </c:if>
      </div>
   </div>

   <!-- Page content wrapper-->
   <div id="page-content-wrapper">
      <!-- Top navigation-->
      <nav class="navbar navbar-expand-lg navbar-light bg-light border-bottom">
         <div class="container-fluid">
            <%-- toggle sidebar --%>
            <a id="sidebarToggle" style="border: none; color: #212529">
               <svg xmlns="http://www.w3.org/2000/svg" width="30" height="30" fill="currentColor"
                    class="bi bi-list" viewBox="0 0 16 16">
                  <path fill-rule="evenodd"
                        d="M2.5 12a.5.5 0 0 1 .5-.5h10a.5.5 0 0 1 0 1H3a.5.5 0 0 1-.5-.5m0-4a.5.5 0 0 1 .5-.5h10a.5.5 0 0 1 0 1H3a.5.5 0 0 1-.5-.5m0-4a.5.5 0 0 1 .5-.5h10a.5.5 0 0 1 0 1H3a.5.5 0 0 1-.5-.5"/>
               </svg>
            </a>

            <button class="navbar-toggler" type="button" data-bs-toggle="collapse"
                    data-bs-target="#navbarSupportedContent" aria-controls="navbarSupportedContent"
                    aria-expanded="false" aria-label="Toggle navigation"><span class="navbar-toggler-icon"></span>
            </button>

            <%-- header navbar --%>
            <div class="collapse navbar-collapse font" id="navbarSupportedContent">
               <ul class="navbar-nav ms-auto mt-2 mt-lg-0">
                  <li class="nav-item active">
                     <a class="nav-link" href="/admin/ban-hang">
                        <i class="fas fa-cart-plus"></i>
                        Bán hàng tại quầy
                     </a>
                  </li>
                  <li class="nav-item active">
                     <a class="nav-link" href="/admin/hoa-don">
                        <i class="fas fa-file-invoice"></i>
                        Hoá đơn
                     </a>
                  </li>
                  <li class="nav-item active">
                     <div class="dropdown">
                        <a class="btn btn-secondary dropdown-toggle" href="#" role="button" data-bs-toggle="dropdown" aria-expanded="false">
                           <span>Xin chào, </span><span class="fw-semibold">${sessionScope.admin.hoVaTen}</span>
                        </a>

                        <ul class="dropdown-menu ms-0">
                           <li>
                              <div class="px-2">
                                 <span class="text-secondary">Chức vụ: </span>
                                 <c:if test="${admin.chucVu == 0}">
                                    <span>Quản lý</span>
                                 </c:if>
                                 <c:if test="${admin.chucVu == 1}">
                                    <span>Nhân viên</span>
                                 </c:if>
                              </div>
                           </li>
                           <li><hr class="dropdown-divider"></li>
                           <li>
                              <a class="nav-link text-danger" href="/logout">
                                 <i class="fas fa-sign-out-alt"></i>
                                 Đăng xuất
                              </a>
                           </li>
                        </ul>
                     </div>

                  </li>
               </ul>
            </div>
         </div>
      </nav>
      <!-- Page content-->
      <div class="container-fluid font">
         <h1></h1>
         <jsp:include page="${ view }"/>
      </div>
   </div>
</div>
<!-- Bootstrap core JS-->
<script src="/../js/bootstrap.min.js"></script>
<!-- Core theme JS-->
<script src="https://cdnjs.cloudflare.com/ajax/libs/toastr.js/latest/js/toastr.min.js"></script>
<!-- Bao gồm các tập lệnh Spring form và các tập lệnh khác -->
<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.min.js"></script>
<script>

    window.addEventListener('load', event => {
        const sidebarToggle = document.body.querySelector('#sidebarToggle');

        // Function to toggle sidebar and save the state to localStorage
        const toggleSidebar = () => {
            document.body.classList.toggle('sb-sidenav-toggled');
            localStorage.setItem('sb|sidebar-toggle', document.body.classList.contains('sb-sidenav-toggled'));
        };

        if (sidebarToggle) {
            sidebarToggle.addEventListener('click', event => {
                event.preventDefault();
                toggleSidebar();
            });
        }

        // Check localStorage for the sidebar state and apply it
        const savedSidebarState = localStorage.getItem('sb|sidebar-toggle');
        if (savedSidebarState && savedSidebarState === 'true') {
            document.body.classList.add('sb-sidenav-toggled');
        }
    });
</script>
</body>
</html>
