<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>


<%--@elvariable id="formThanhToan" type="java"--%>
<form:form modelAttribute="formThanhToan" id="myForm" action="/thanh-toan" method="post">
    <div class="row col-10 container offset-1 mt-1">
        <div class="col-6">
            <div class="text-center py-3 fw-bold  text-xl-center text-uppercase">
                Thông tin người nhận
            </div>
            <hr>
            <input type="checkbox" id="hideInfoCheckbox" name="diaChiMacDinh" class="ms-5"
                   onchange="updateCheckboxValue(this) ">
            <label class="ms-3" for="hideInfoCheckbox">Sử dụng địa chỉ đã đăng ký</label>
            <script>
                function updateCheckboxValue(checkbox) {
                    if (checkbox.checked) {
                        checkbox.value = 1;
                    } else {
                        checkbox.value = 0;
                    }
                }
            </script>
            <br>
            <label class="ms-5 mt-3">Tên người nhận: ${khachHang.hoVaTen}</label>
            <br>
            <label class="ms-5">Số điện thoại: ${khachHang.soDienThoai}</label>
            <br>
            <label class="ms-5">Địa
                chỉ: ${khachHang.diaChi}/${khachHang.xaPhuong}/${khachHang.quanHuyen}/${khachHang.tinhThanhPho}</label>

            <hr>

            <div id="infoContainer">
                <div class="px-md-5 px-3 py-2 form-group">
                    <div class="form-label ">Họ và Tên (*)</div>
                    <form:input path="hoTen" class="w-100 form-control" type="text" placeholder="Họ và Tên"/>
                </div>
                <div class="px-md-5 px-3 py-2 form-group">
                    <div class="form-label ">Tỉnh/Thành Phố (*)</div>
                    <select id="provinceSelect" class="w-100 form-control">
                        <option value="" disabled selected>Chọn tỉnh/thành phố</option>
                    </select>
                    <form:input path="tinhThanhPho" type="hidden" id="provinceName" name="tinhThanh"/>
                </div>
                <div class="px-md-5 px-3 py-2 form-group">
                    <div class="form-label ">Quận/Huyện (*)</div>
                    <select id="districtSelect" class="w-100 form-control">
                        <option value="" disabled selected>Chọn quận/huyện</option>
                    </select>
                    <form:input path="quanHuyen" type="hidden" id="districtName" name="quanHuyen"/>
                </div>
                <div class="px-md-5 px-3 py-2 form-group">
                    <div class="form-label ">Xã/Phường (*)</div>
                    <select id="wardSelect" class="w-100 form-control">
                        <option value="" disabled selected>Chọn xã/phường</option>
                    </select>
                    <form:input path="xaPhuong" type="hidden" id="wardName" name="xaPhuong"/>
                </div>
                <div class="px-md-5 px-3 py-2 form-group">
                    <div class="form-label ">Địa chỉ (*)</div>
                    <form:textarea path="diaChi" class="w-100 form-control" placeholder="..." name="" cols="30"
                                   rows="3"></form:textarea>
                </div>
                <div class="px-md-5 px-3 py-2 form-group">
                    <div class="form-label">Số điện thoại (*)</div>
                    <form:input path="soDienThoai" class="w-100 form-control" type="tel" placeholder="..."
                                oninput="validatePhoneNumber(this)"/>
                </div>

                <div class="px-md-5 px-3 py-2 form-group">
                    <div class="form-label ">Email (*)</div>
                    <form:input path="email" class="w-100 form-control" type="email" placeholder="..."/>
                </div>
            </div>

            <div class="px-md-5 px-3 py-2 form-check  border-bottom">
                <div class="form-label ">Phương thức thanh toán (*)</div>
                <label class="form-check-label text-sm-left fw-bold ">
                    <c:forEach items="${listHTTT}" var="lshttt" varStatus="status">
                        <c:if test="${lshttt.trangThai == 1}">
                            <form:radiobutton path="hinhThucThanhToan" value="${lshttt.id}" name="payment_method"
                                              checked="true"/>
                            ${lshttt.ten}
                        </c:if>
                    </c:forEach>
                </label>
            </div>

            <script>
                document.getElementById('hideInfoCheckbox').addEventListener('change', function () {
                    var infoContainer = document.getElementById('infoContainer');
                    var phiVanChuyenContrainer  = document.getElementById('phiVanChuyenCheck');
                    const thanh = '${khachHang.tinhThanhPho}';
                    findProvinceIdByName(thanh);
                    if (this.checked) {
                        phiVanChuyenContrainer.style.display = 'block';
                        infoContainer.style.display = 'none'; // Ẩn thông tin khi checkbox được tích vào
                    } else {
                        phiVanChuyenContrainer.style.display = 'none';
                        infoContainer.style.display = 'block'; // Hiển thị thông tin khi checkbox được bỏ tích
                    }
                });
            </script>

            <div class="px-md-5 px-3 py-2 form-check">
                <label class="form-check-label text-sm-left fw-bold ">
                </label>
                <label class="mt-2">Lưu ý: Với hình thức thanh toán bằng (chuyển khoản ) VnPay quý khách sẽ thanh toán
                    đơn hàng và phí vận chuyển sẽ trả
                    khi nhận hàng.</label> <br>
                <label class="mt-2"> Với hình thức thanh toán bằng (Tiền mặt ) COD - quý khách nhận hàng và thanh
                    toán.</label>
            </div>

            <div class="px-md-5 px-3 py-2 form-group mb-5">
                <div class="form-label ">Ghi chú (*)</div>
                <form:textarea path="ghiChu" class="w-100 form-control" placeholder="..." name="" cols="30"
                               rows="5"></form:textarea>
            </div>

        </div>
        <div class="col-6 bg-light">
            <div class="ms-3">
                <div class="text-center py-5 fw-bold fs-4 text-xl-center text-uppercase mb-2">
                    GIỎ HÀNG CỦA BẠN
                </div>
                <div class="me-3">
                    <div class="bg-white  border-bottom">
                        <div class="row ms-1 me-1 py-3">
                            <label class="col text-uppercase fw-bold fs-5">Sản phẩm</label>
                            <label class="col text-uppercase fw-bold fs-5 text-end">Tạm tính</label>
                        </div>
                    </div>

                    <div class="bg-white py-3  border-bottom">
                        <c:forEach items="${listGioHang}" var="gioHang">
                            <div class="row ms-1 me-1">
                                <label class="col-8 text-uppercase">${gioHang.tenSanPham} - ${gioHang.tenMauSac}
                                    - ${gioHang.tenKichCo}
                                    x${gioHang.soLuong}</label>
                                <label class="col-4 fw-bold text-end"
                                       id="giaSP_${gioHang.maSanPham}">${gioHang.soLuong * gioHang.gia}</label>
                                <script>
                                    var giaSanPhamElement = document.getElementById("giaSP_${gioHang.maSanPham}");
                                    var giaSanPhamText = giaSanPhamElement.innerText;
                                    var formattedGia = parseInt(giaSanPhamText.replace(/[^\d]/g, '')).toLocaleString('en-US');
                                    giaSanPhamElement.innerText = formattedGia + " vnđ";
                                </script>
                            </div>
                        </c:forEach>
                    </div>

                    <div class="bg-white py-3 border-bottom">
                        <div class="row ms-1 me-1">
                            <label class="col fw-bold fs-6 ">Tạm tính</label>
                            <label class="col fw-bold fs-6 text-end" id="tongTien">${tongTien}</label>
                            <script>
                                var giaSanPhamElement = document.getElementById("tongTien");
                                var giaSanPhamText = giaSanPhamElement.innerText;
                                var formattedGia = parseInt(giaSanPhamText.replace(/[^\d]/g, '')).toLocaleString('en-US');
                                giaSanPhamElement.innerText = formattedGia + " vnđ";
                            </script>
                        </div>
                    </div>

                    <div class="bg-white py-3 border-bottom">
                        <div class="row ms-1 me-1">
                            <label class="col fw-bold fs-6">Mã khuyến mãi</label>
                            <label class="col fs-6 fw-bold text-end" id="soTienDuocGiam">${soTienDuocGiam}</label>
                            <script>
                                var giaSanPhamElement = document.getElementById("soTienDuocGiam");
                                var giaSanPhamText = giaSanPhamElement.innerText;
                                var formattedGia = parseInt(giaSanPhamText.replace(/[^\d]/g, '')).toLocaleString('en-US');
                                giaSanPhamElement.innerText = formattedGia + " vnđ";
                            </script>
                        </div>
                    </div>
                    <div class="bg-white py-3 border-bottom" id="phiVanChuyenCheck" style="display: none;">
                        <div class="row ms-1 me-1 align-items-center">
                            <label class="col fw-bold fs-5 mb-0">Phí vận chuyển :</label>
                            <div class="col-auto">
                                <input class="form-control text-end  fw-bold" type="text" id="feeInput" name="phiVanChuyen" readonly style=" text-align: right;">
                            </div>
                        </div>
                    </div>
                    <div class="bg-white py-3 border-bottom">
                        <div class="row ms-1 me-1">
                            <label class="col fw-bold fs-5">Tổng</label>
                            <label class="col fw-bold fs-5 text-end" id="soTienSauKhiGiam">${soTienSauKhiGiam}</label>
                            <script>
                                var giaSanPhamElement = document.getElementById("soTienSauKhiGiam");
                                var giaSanPhamText = giaSanPhamElement.innerText;
                                var formattedGia = parseInt(giaSanPhamText.replace(/[^\d]/g, '')).toLocaleString('en-US');
                                giaSanPhamElement.innerText = formattedGia + " vnđ";
                            </script>
                        </div>
                    </div>

                </div>

            </div>
            <div class="mt-5">
                <label class="fw-bold">Chính sách</label>
            </div>
            <div class="mt-3 ms-3 bg-light">
                <label>Dữ liệu cá nhân của bạn sẽ được sử dụng để
                    xử lý đơn đặt hàng, hỗ trợ trải nghiệm của
                    bạn trên toàn bộ trang web này và cho các
                    mục đích khác được mô tả trong <a href="/chinh-sach-bao-mat">Chính sách bảo mật</a>
                        <%--                    và <a--%>
                        <%--                            href="/chinh-sach-doi-tra">Chính sách đổi--%>
                        <%--                        trả</a>.--%>
                </label>
            </div>
            <div class="mt-3 bg-light">
                <label class="form-check-label">
                    <input type="checkbox" name="termsAndConditions" id="termsCheckbox" checked>
                    Tôi đã đọc và đồng ý với các điều khoản và điều kiện của trang web *
                </label>
            </div>
            <div class="mt-3">
                <button type="submit" id="submitButton" class="mb-3 w-100 bg-dark text-bg-dark fw-bold btn btn-dark">XÁC
                    NHẬN ĐẶT HÀNG
                </button>
            </div>
            <script>
                function validatePhoneNumber(input) {
                    const phoneNumber = input.value.replace(/\D/g, '');

                    if (phoneNumber.length === 10) {
                        input.setCustomValidity('');
                    } else {
                        input.setCustomValidity('Số điện thoại phải có đúng 10 số.');
                    }
                }

                document.addEventListener('DOMContentLoaded', function () {
                    const form = document.getElementById('myForm');
                    const checkbox = document.getElementById('termsCheckbox');
                    const textarea = document.getElementById('ghiChu');
                    const submitButton = document.getElementById('submitButton');
                    const hoTenInput = document.getElementById('hoTen');
                    const diaChiTextarea = document.getElementById('diaChi');
                    const soDienThoaiInput = document.getElementById('soDienThoai');
                    const emailInput = document.getElementById('email');
                    const provinceSelect = document.getElementById('provinceSelect')
                    const districtSelect = document.getElementById('districtSelect');
                    const wardSelect = document.getElementById('wardSelect');
                    const hideInfoCheckbox = document.getElementById('hideInfoCheckbox');

                    form.addEventListener('submit', function (event) {
                        if (!checkbox.checked) {
                            event.preventDefault();
                            alert('Vui lòng đồng ý với các điều khoản và điều kiện.');
                        }

                        if (!hideInfoCheckbox.checked) {
                            // Check other conditions only if hideInfoCheckbox is not checked
                            if (
                                hoTenInput.value.trim() === '' ||
                                diaChiTextarea.value.trim() === '' ||
                                soDienThoaiInput.value.trim() === '' ||
                                emailInput.value.trim() === '' ||
                                provinceSelect.value === '' ||
                                districtSelect.value === '' ||
                                wardSelect.value === ''
                            ) {
                                event.preventDefault();
                                alert('Vui lòng điền đầy đủ thông tin.');
                            }
                        }
                    });

                    textarea.addEventListener('input', function () {
                        if (textarea.value.trim() !== '') {
                            submitButton.removeAttribute('disabled');
                        } else {
                            submitButton.setAttribute('disabled', 'disabled');
                        }
                    });
                });

            </script>
        </div>
    </div>
</form:form>

<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/moment.js/2.29.4/moment.min.js"></script>

<script>
    $(document).ready(function () {
        // Biến kiểm tra trạng thái đã chọn
        var isDistrictSelected = false;
        var isWardSelected = false;
        // Gọi API để lấy dữ liệu tỉnh/thành phố
        $.ajax({
            url: 'https://dev-online-gateway.ghn.vn/shiip/public-api/master-data/province',
            method: 'GET',
            headers: {
                'Content-Type': 'application/json',
                'Token': 'a76df0d2-77a1-11ee-b1d4-92b443b7a897'
            },
            success: function (data) {
                if (data.code === 200) {
                    const select = document.getElementById('provinceSelect');
                    const input = document.getElementById('provinceName'); // Get the input element

                    data.data.forEach(province => {
                        const option = document.createElement('option');
                        option.value = province.ProvinceID;
                        option.text = province.ProvinceName;
                        select.appendChild(option);
                    });

                    $('#provinceSelect').change(function () {
                        const selectedProvinceName = $('#provinceSelect option:selected').text();
                        input.value = selectedProvinceName;
                    });
                }
            },
            error: function (error) {
                console.error(error);
            }
        });
        $("#submitButton").click(function (event) {
            // khi da chon mau sac va kich co thì hien thi c
            var confirmAddToCart = confirm("Bạn có chắc chắn muốn thanh toán đơn hàng này không?");
            if (!confirmAddToCart) {
                event.preventDefault();
                return;
            }
        });
        // Gọi API để lấy dữ liệu quận/huyện khi thay đổi tỉnh/thành phố
        $('#provinceSelect').change(function () {
            isDistrictSelected = false; // Đặt lại trạng thái khi thay đổi tỉnh/thành phố
            isWardSelected = false; // Đặt lại trạng thái khi thay đổi tỉnh/thành phố

            const provinceID = $(this).val();
            $.ajax({
                url: 'https://dev-online-gateway.ghn.vn/shiip/public-api/master-data/district',
                method: 'GET',
                headers: {
                    'Content-Type': 'application/json',
                    'Token': 'a76df0d2-77a1-11ee-b1d4-92b443b7a897'
                },
                data: {
                    province_id: provinceID
                },
                success: function (data) {
                    if (data.code === 200) {
                        const select = document.getElementById('districtSelect');
                        const input = document.getElementById('districtName');
                        select.innerHTML = '';
                        data.data.forEach(district => {
                            const option = document.createElement('option');
                            option.value = district.DistrictID;
                            option.text = district.DistrictName;
                            select.appendChild(option);
                        });
                        $('#districtSelect').change(function () {
                            const selectedDistrictName = $('#districtSelect option:selected').text();
                            input.value = selectedDistrictName;
                        });
                    }
                },
                error: function (error) {
                    console.error(error);
                }
            });
        });

        // Gọi API để lấy dữ liệu phường/xã khi thay đổi quận/huyện
        $('#districtSelect').change(function () {
            isDistrictSelected = true; // Đánh dấu trạng thái khi đã chọn quận/huyện
            isWardSelected = false; // Đặt lại trạng thái khi thay đổi quận/huyện

            const districtID = $(this).val();
            $.ajax({
                url: 'https://dev-online-gateway.ghn.vn/shiip/public-api/master-data/ward?district_id=' + districtID,
                method: 'GET',
                headers: {
                    'Content-Type': 'application/json',
                    'Token': 'a76df0d2-77a1-11ee-b1d4-92b443b7a897'
                },
                data: {
                    district_id: districtID
                },
                success: function (data) {
                    if (data.code === 200) {
                        const select = document.getElementById('wardSelect');
                        const input = document.getElementById('wardName');
                        select.innerHTML = '';
                        data.data.forEach(ward => {
                            const option = document.createElement('option');
                            option.value = ward.WardCode;
                            option.text = ward.WardName;
                            select.appendChild(option);
                        })
                        $('#wardSelect').change(function () {
                            const selectedWardName = $('#wardSelect option:selected').text();
                            input.value = selectedWardName; // Set the value of the input field
                        });
                    }
                },
                error: function (error) {
                    console.error(error);
                }
            });
        });
    });
    $(document).ready(function () {
        var phiVanChuyenContrainer  = document.getElementById('phiVanChuyenCheck');
        var phuongxa = $("#wardSelect")
        var quanHuyen = $("#districtSelect")
        phuongxa.on("change", function () {
            console.log(quanHuyen.val(), phuongxa.val())
            calculateShippingFee(quanHuyen.val(), phuongxa.val())
            phiVanChuyenContrainer.style.display='block'
        })
    });
</script>
<script>
    const calculateShippingFee = (huyen, xa) => {
        $.ajax({
            url: 'https://dev-online-gateway.ghn.vn/shiip/public-api/v2/shipping-order/fee',
            type: 'GET',
            data: {
                service_type_id: '2',
                to_district_id: huyen,
                to_ward_code: xa,
                height: '9',
                length: '29',
                weight: '5000',
                width: '18',
            },
            headers: {
                token: '442f5c30-4906-11ef-8e53-0a00184fe694',
                shop_id: '193116',
                ContentType: 'application/json',
            },
            success: (response) => {
                if (response && response.data && response.data.total !== undefined) {
                    const feeResponse = response.data.total;
                    console.log("tính phí ship: " + feeResponse);
                 var phiText =   parseInt(feeResponse.toString().replace(/[^\d]/g, '')).toLocaleString('en-US');
                    document.getElementById('feeInput').value = phiText+" vnđ";
                    // Hiển thị phần phí vận chuyển
                } else {
                    console.error('Lỗi khi gọi API tính phí ship: ', response);
                    // Xử lý lỗi và cập nhật giá trị mặc định hoặc 0
                    document.getElementById('feeInput').value = '0';
                }
            },
            error: (xhr, status, error) => {
                console.error('Lỗi khi gọi API tính phí ship:', error);
                // Xử lý lỗi và cập nhật giá trị mặc định hoặc 0
                document.getElementById('feeInput').value = '0';
            }
        });
    };
</script>
<script>
    const findProvinceIdByName = (tenThanh) => {
        $.ajax({
            url: 'https://dev-online-gateway.ghn.vn/shiip/public-api/master-data/province',
            type: 'GET',
            headers: {
                token: 'a76df0d2-77a1-11ee-b1d4-92b443b7a897',
                ContentType: 'application/json',
            },
            success: (response) => {
                if (response && response.data) {
                    const provinces = response.data;
                    const province = provinces.find(d => d.ProvinceName === tenThanh);
                    if (province) {
                        const provinceId = province.ProvinceID;
                        console.log(provinceId);
                        const huyen = '${khachHang.quanHuyen}';
                        findDistrictIdByName(huyen, provinceId);
                        // Sau khi lấy được districtId, gọi hàm tính phí vận chuyển
                    } else {
                        console.error('Không tìm thấy thành có tên: ', tenThanh);
                    }
                } else {
                    console.error('Lỗi khi lấy danh sách thành từ API');
                }
            },
            error: (xhr, status, error) => {
                console.error('Lỗi khi gọi API lấy danh sách thành:', error);
            }
        });
    };
    const findDistrictIdByName = (tenHuyen, thanhId) => {
        $.ajax({
            url: 'https://dev-online-gateway.ghn.vn/shiip/public-api/master-data/district',
            type: 'GET',
            headers: {
                token: 'a76df0d2-77a1-11ee-b1d4-92b443b7a897',
                province_id: thanhId.toString(),
                ContentType: 'application/json',
            },
            success: (response) => {
                if (response && response.data) {
                    const districts = response.data;
                    const district = districts.find(d => d.DistrictName === tenHuyen);
                    if (district) {
                        const xa = '${khachHang.xaPhuong}';
                        const districtId = district.DistrictID;
                        console.log(districtId);
                        findWardByName(xa, districtId);
                        // Sau khi lấy được districtId, gọi hàm tính phí vận chuyển
                    } else {
                        console.error('Không tìm thấy huyện có tên: ', tenHuyen);
                    }
                } else {
                    console.error('Lỗi khi lấy danh sách huyện từ API');
                }
            },
            error: (xhr, status, error) => {
                console.error('Lỗi khi gọi API lấy danh sách huyện:', error);
            }
        });
    };
    const findWardByName = (tenXa, huyenID) => {
        $.ajax({
            url: 'https://dev-online-gateway.ghn.vn/shiip/public-api/master-data/ward?district_id=' + huyenID.toString(),
            type: 'GET',
            headers: {
                token: 'a76df0d2-77a1-11ee-b1d4-92b443b7a897',
                ContentType: 'application/json',
            },
            success: (response) => {
                if (response && response.data) {
                    const wards = response.data;
                    const ward = wards.find(d => d.WardName === tenXa);
                    if (ward) {
                        const wardId = ward.WardCode;
                        console.log(wardId);
                        calculateShippingFee(huyenID, wardId);
                        // Sau khi lấy được districtId, gọi hàm tính phí vận chuyển
                    } else {
                        console.error('Không tìm thấy xã có tên: ', tenXa);
                    }
                } else {
                    console.error('Lỗi khi lấy danh sách xã từ API');
                }
            },
            error: (xhr, status, error) => {
                console.error('Lỗi khi gọi API lấy danh sách xã:', error);
            }
        });
    };
</script>