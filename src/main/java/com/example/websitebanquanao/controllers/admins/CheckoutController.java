package com.example.websitebanquanao.controllers.admins;

import com.example.websitebanquanao.entities.HoaDon;
import com.example.websitebanquanao.services.HoaDonService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.example.websitebanquanao.type.Body;
import com.example.websitebanquanao.utils.Utils;
import com.fasterxml.jackson.databind.node.ObjectNode;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Controller
public class CheckoutController {
    @Value("${PAYOS_CREATE_PAYMENT_LINK_URL}")
    private String createPaymentLinkUrl;

    @Value("${PAYOS_CLIENT_ID}")
    private String clientId;

    @Value("${PAYOS_API_KEY}")
    private String apiKey;

    @Value("${PAYOS_CHECKSUM_KEY}")
    private String checksumKey;

    @RequestMapping(value = "/")
    public String Index() {
        return "index";
    }

    @Autowired
    HoaDonService hoaDonService;

    @Autowired
    HttpSession session;

    @RequestMapping(value = "/success/{idHoaDon}")
    public String Success(@PathVariable("idHoaDon") UUID idHoaDon) {
        HoaDon hoaDon = hoaDonService.getById(idHoaDon);
        hoaDon.setTrangThai(1);
        Instant instant = Instant.now();
        hoaDon.setNgayThanhToan(instant);
        hoaDon.setLoaiHoaDon(0);
        hoaDon.setHinhThucThanhToan(hoaDon.getHinhThucThanhToan());
        hoaDonService.update(hoaDon,idHoaDon);
        return "redirect:/admin/ban-hang";
    }

    @RequestMapping(value = "/cancel/{idHoaDon}")
    public String Cancel(@PathVariable("idHoaDon") UUID idHoaDon) {
        session.setAttribute("errorMessage", "Thanh toán thất bại");
        return "redirect:/admin/ban-hang/view-hoa-don/" + idHoaDon;
    }

    @RequestMapping(method = RequestMethod.POST, value = "/create-payment-link", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public void checkout(HttpServletResponse httpServletResponse,
                         @RequestParam("tongTien") int tongTien,
                         @RequestParam("ma") String maHoaDon,
                         @RequestParam("idHoaDon") UUID idHoaDon
    ) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            final String description = "Thanh toan hoa don " + maHoaDon;
            final String returnUrl = "http://localhost:8080/success/" + idHoaDon;
            final String cancelUrl = "http://localhost:8080/cancel/" + idHoaDon;
            int price = tongTien;
            // Gen order code
            String currentTimeString = String.valueOf(new Date().getTime());
            int orderCode = Integer.parseInt(currentTimeString.substring(currentTimeString.length() - 6));
            // Tạo body
            Body body = new Body();
            body.setOrderCode(orderCode);
            body.setAmount(price);
            body.setDescription(description);
            body.setReturnUrl(returnUrl);
            body.setCancelUrl(cancelUrl);
            // Tạo chữ ký
            String bodyToSignature = Utils.createSignatureOfPaymentRequest(body, checksumKey);
            body.setSignature(bodyToSignature);
            // Tạo header
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.set("x-client-id", clientId);
            headers.set("x-api-key", apiKey);
            // Gửi yêu cầu POST
            WebClient client = WebClient.create();
            Mono<String> response = client.post()
                    .uri(createPaymentLinkUrl)
                    .headers(httpHeaders -> httpHeaders.putAll(headers))
                    .body(BodyInserters.fromValue(body))
                    .retrieve()
                    .bodyToMono(String.class);

            String responseBody = response.block();
            JsonNode res = objectMapper.readTree(responseBody);
            if (!Objects.equals(res.get("code").asText(), "00")) {
                System.out.println("Fail");
                throw new Exception("Fail");
            } else {
                System.out.println("Success");
            }


            String checkoutUrl = res.get("data").get("checkoutUrl").asText();

            // Kiểm tra dữ liệu có đúng không
            String paymentLinkResSignature = Utils.createSignatureFromObj(res.get("data"), checksumKey);
            System.out.println(paymentLinkResSignature);
            if (!paymentLinkResSignature.equals(res.get("signature").asText())) {
                System.out.println("Signature is not compatible");
                throw new Exception("Signature is not compatible");
            } else {
                System.out.println("Signature is compatible");
            }
            httpServletResponse.setHeader("Location", checkoutUrl);
            httpServletResponse.setStatus(302);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}