package org.example.restspring.domain.service;

import com.eatthepath.otp.TimeBasedOneTimePasswordGenerator;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import jakarta.servlet.http.HttpSession;
import org.apache.commons.codec.binary.Base32;
import org.example.restspring.ui.dto.Enable2FAResponse;
import org.example.restspring.ui.dto.UsuarioDTO;
import org.springframework.cglib.core.CodeGenerationException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.io.ByteArrayOutputStream;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.SecureRandom;
import java.time.Instant;
import java.util.Base64;
import java.util.EnumMap;
import java.util.Map;

@Service
public class ToptService {

    private static final int SECRET_SIZE_BYTES = 20;
    private static final int QR_SIZE_PX = 256;
    private static final int ALLOWED_TIME_WINDOW_STEPS = 1;

    private final SecureRandom secureRandom = new SecureRandom();
    private final Base32 base32 = new Base32();
    private final TimeBasedOneTimePasswordGenerator totp;

    public ToptService() {
        try {
            this.totp = new TimeBasedOneTimePasswordGenerator();
        } catch (Exception e) {
            throw new IllegalStateException("No se pudo inicializar el generador TOTP", e);
        }
    }


    public void enable2FA(HttpSession session){
        int usuarioId = authService.getUsuarioIdFromSession(session);
        UsuarioDTO usuario = usuarios.getById(usuarioId);



        String secret = toptService.generateSecret();


        String qrCodeUri = toptService.generateQrCodeImageUri(
                secret,
                usuario.username(),
                "SpringRest" // Nombre de tu app que aparecerá en Google Authenticator
        );

        // Guardar el secreto temporalmente (aún no activado)
        usuario = usuario.set2FA(false, secret); // Aún no activado hasta confirmar
        usuarioRepository.save(usuario);

        Enable2FAResponse response = new Enable2FAResponse(
                secret,
                qrCodeUri,
                "Escanea el código QR con tu aplicación autenticadora (Google Authenticator, Authy, etc.) y confirma con un código"
        );

        return ResponseEntity.ok(response);
    }


    public String generateSecret() {
        byte[] secretBytes = new byte[SECRET_SIZE_BYTES];
        secureRandom.nextBytes(secretBytes);

        String encoded = base32.encodeToString(secretBytes);
        return stripBase32Padding(encoded);
    }

    public String generateQrCodeImageUri(String secret, String username, String issuer) {
        try {
            String otpAuthUri = buildOtpAuthUri(secret, username, issuer);

            Map<EncodeHintType, Object> hints = new EnumMap<>(EncodeHintType.class);
            hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");
            hints.put(EncodeHintType.MARGIN, 1);

            BitMatrix matrix = new QRCodeWriter().encode(
                    otpAuthUri,
                    BarcodeFormat.QR_CODE,
                    QR_SIZE_PX,
                    QR_SIZE_PX,
                    hints
            );

            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            MatrixToImageWriter.writeToStream(matrix, "PNG", baos);

            String b64 = Base64.getEncoder().encodeToString(baos.toByteArray());
            return "data:image/png;base64," + b64;

        } catch (Exception e) {
            throw new CodeGenerationException(e);
        }
    }


    public boolean verifyCode(String secret, String code) throws InvalidKeyException {
        if (secret == null || secret.isBlank() || code == null) return false;

        String normalized = code.trim().replace(" ", "");
        if (!normalized.matches("^\\d{6}$")) return false;

        SecretKey key = secretKeyFromBase32(secret);

        Instant now = Instant.now();
        for (int i = -ALLOWED_TIME_WINDOW_STEPS; i <= ALLOWED_TIME_WINDOW_STEPS; i++) {
            Instant t = now.plus(totp.getTimeStep().multipliedBy(i));
            String expected = totp.generateOneTimePasswordString(key, t);
            if (timingSafeEquals(expected, normalized)) {
                return true;
            }
        }
        return false;
    }

    public String getCurrentCode(String secret) throws CodeGenerationException {
        try {
            SecretKey key = secretKeyFromBase32(secret);
            return totp.generateOneTimePasswordString(key, Instant.now());
        } catch (Exception e) {
            throw new CodeGenerationException(e);
        }
    }


    private String buildOtpAuthUri(String secretBase32, String username, String issuer) {
        String cleanSecret = stripBase32Padding(secretBase32);

        String labelIssuer = encodePathComponent(issuer);
        String labelAccount = encodePathComponent(username);
        String label = labelIssuer + ":" + labelAccount;

        String qIssuer = encodeQueryComponent(issuer);
        String qSecret = encodeQueryComponent(cleanSecret);

        String algorithm = "SHA1";
        int digits = totp.getPasswordLength();
        long period = totp.getTimeStep().getSeconds();

        return "otpauth://totp/" + label +
                "?secret=" + qSecret +
                "&issuer=" + qIssuer +
                "&algorithm=" + algorithm +
                "&digits=" + digits +
                "&period=" + period;
    }

    private SecretKey secretKeyFromBase32(String secretBase32) {
        String normalized = stripBase32Padding(secretBase32).trim().replace(" ", "").toUpperCase();
        byte[] keyBytes = base32.decode(normalized);
        return new SecretKeySpec(keyBytes, totp.getAlgorithm());
    }

    private static boolean timingSafeEquals(String a, String b) {
        byte[] x = a.getBytes(StandardCharsets.US_ASCII);
        byte[] y = b.getBytes(StandardCharsets.US_ASCII);
        return MessageDigest.isEqual(x, y);
    }

    private static String stripBase32Padding(String s) {
        if (s == null) return null;

        return s.replace("=", "");
    }

    private static String encodeQueryComponent(String s) {

        String enc = URLEncoder.encode(s, StandardCharsets.UTF_8);
        return enc.replace("+", "%20");
    }

    private static String encodePathComponent(String s) {
        return encodeQueryComponent(s);
    }
}
