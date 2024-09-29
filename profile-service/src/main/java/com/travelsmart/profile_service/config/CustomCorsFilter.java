//package com.travelsmart.profile_service.config;
//
//import jakarta.servlet.FilterChain;
//import jakarta.servlet.ServletException;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import org.springframework.stereotype.Component;
//import org.springframework.web.filter.OncePerRequestFilter;
//
//
//import java.io.IOException;
//
//@Component
//public class CustomCorsFilter extends OncePerRequestFilter {
//
//
//
//
//    @Override
//    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
//        // Allow credentials
//        response.setHeader("Access-Control-Allow-Credentials", "true");
//
//        // Set allowed origins (adjust to your frontend's URL)
//        response.setHeader("Access-Control-Allow-Origin", "http://localhost:3000");
//
//        // Set allowed headers
//        response.setHeader("Access-Control-Allow-Headers", "*");
//
//        // Set allowed methods
//        response.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
//
//        // Handle preflight requests (OPTIONS)
//        if ("OPTIONS".equalsIgnoreCase(request.getMethod())) {
//            response.setStatus(HttpServletResponse.SC_OK);
//            return;
//        }
//
//        filterChain.doFilter(request, response); // Continue the filter chain
//    }
//}
