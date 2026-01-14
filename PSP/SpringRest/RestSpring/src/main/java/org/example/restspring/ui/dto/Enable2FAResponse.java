package org.example.restspring.ui.dto;

public record Enable2FAResponse(
        String secret,
        String qrCodeUri,
        String message
) {
}
