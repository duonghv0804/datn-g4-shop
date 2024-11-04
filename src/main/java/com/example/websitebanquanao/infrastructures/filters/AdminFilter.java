package com.example.websitebanquanao.infrastructures.filters;

import com.example.websitebanquanao.infrastructures.requests.NhanVienRequest;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

public class AdminFilter implements Filter {
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        HttpSession session = httpRequest.getSession();
        NhanVienRequest nhanVien = (NhanVienRequest) session.getAttribute("admin");

        if (httpRequest.getRequestURI().equals(httpRequest.getContextPath() + "/login-admin")) {
            // cho phep di qua
            chain.doFilter(request, response);
        } else if (nhanVien == null) {
            // neu chua dang nhap thi chuyen sang trang dang nhap
            httpResponse.sendRedirect(httpRequest.getContextPath() + "/login-admin");
        } else {
            chain.doFilter(request, response);
        }
    }
}
