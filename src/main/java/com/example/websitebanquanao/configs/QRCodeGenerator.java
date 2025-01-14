package com.example.websitebanquanao.configs;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import org.springframework.stereotype.Component;

import java.io.File;
import java.nio.file.Paths;

@Component
public class QRCodeGenerator {

    public void generateQRCode(String data, String filePath) {
        try {
            // Create directories if they don't exist
            File file = new File(filePath);
            file.getParentFile().mkdirs();

            // Tạo mã QR code
            QRCodeWriter qrCodeWriter = new QRCodeWriter();
            BitMatrix bitMatrix = qrCodeWriter.encode(data, BarcodeFormat.QR_CODE, 200, 200);

            // Lưu mã QR code vào một tệp ảnh
            MatrixToImageWriter.writeToPath(bitMatrix, "PNG", Paths.get(filePath));
        } catch (Exception e) {
            e.printStackTrace();
        }
}
}

