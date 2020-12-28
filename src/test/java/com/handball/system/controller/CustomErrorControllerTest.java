package com.handball.system.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CustomErrorControllerTest {

    @InjectMocks
    CustomErrorController customErrorController;

    @Mock
    Model model;

    @Mock
    HttpServletRequest request;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void handleError() {
        when(request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE)).thenReturn(HttpStatus.NOT_FOUND.value());
        assertEquals("error/error", customErrorController.handleError(model, request));
        when(request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE)).thenReturn(HttpStatus.FORBIDDEN.value());
        assertEquals("error/error", customErrorController.handleError(model, request));
        when(request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE)).thenReturn(HttpStatus.INTERNAL_SERVER_ERROR.value());
        assertEquals("error/error", customErrorController.handleError(model, request));
    }

    @Test
    void getErrorPath() {
        assertEquals("/error", customErrorController.getErrorPath());
    }
}