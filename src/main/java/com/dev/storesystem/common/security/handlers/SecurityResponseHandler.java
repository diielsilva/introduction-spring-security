package com.dev.storesystem.common.security.handlers;

import com.dev.storesystem.common.dtos.error.ErrorDetailsDto;
import com.dev.storesystem.common.security.dtos.AccessProviderDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;

import java.io.IOException;
import java.time.OffsetDateTime;

@ControllerAdvice
public class SecurityResponseHandler {

    public void handleSecurityError(HttpServletResponse response, String message, String path)
            throws IOException {
        var mapper = new ObjectMapper();
        var errorDetails = generateErrorDetails(message, path);
        mapper.registerModule(new JavaTimeModule());
        mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        response.setStatus(401);
        response.setContentType("application/json");
        response.getWriter().write(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(errorDetails));
        response.getWriter().flush();
    }

    public void handleSuccessAuthentication(HttpServletResponse response, String jwt) throws IOException {
        var mapper = new ObjectMapper();
        var accessProvider = generateAccessProvider(jwt);
        mapper.registerModule(new JavaTimeModule());
        mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        response.setStatus(200);
        response.setContentType("application/json");
        response.getWriter().write(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(accessProvider));
        response.getWriter().flush();
    }

    private ErrorDetailsDto generateErrorDetails(String message, String path) {
        var errorDetails = new ErrorDetailsDto();
        errorDetails.setTimestamps(OffsetDateTime.now());
        errorDetails.setStatus(401);
        errorDetails.setMessage(message);
        errorDetails.setPath(path);
        return errorDetails;
    }

    private AccessProviderDto generateAccessProvider(String jwt) {
        var accessProvider = new AccessProviderDto();
        accessProvider.setAccessToken(jwt);
        accessProvider.setIssuedAt(OffsetDateTime.now());
        return accessProvider;
    }
}
