package com.melwin.qrmenu.service.menu;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

@Service
public class QrService {
    public byte[] getQrCode(Long restoId) throws WriterException, IOException {
        String baseUrl = "http://192.168.0.105:5173/menu";
        String data = baseUrl + "?restoId=" + restoId;
        int width = 400;
        int height = 400;
        BitMatrix matrix = new MultiFormatWriter()
                .encode(
                        data,
                        BarcodeFormat.QR_CODE,
                        width,
                        height
                );
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        MatrixToImageWriter.writeToStream(matrix, "png", baos);
        return baos.toByteArray();
    }
}
