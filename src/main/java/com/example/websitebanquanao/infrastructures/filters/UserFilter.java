package com.example.websitebanquanao.infrastructures.filters;

import com.example.websitebanquanao.infrastructures.responses.KhachHangResponse;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

public class UserFilter implements Filter {
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        HttpSession session = httpRequest.getSession();
        KhachHangResponse khachHang = (KhachHangResponse) session.getAttribute("khachHang");

        if (httpRequest.getRequestURI().equals(httpRequest.getContextPath() + "/dang-nhap")) {
            chain.doFilter(request, response);
        } else if (khachHang == null) {
            httpResponse.sendRedirect(httpRequest.getContextPath() + "/dang-nhap");
        } else {
            chain.doFilter(request, response);
        }
    }
}
