package org.example.restspring.ui.config.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.restspring.ui.service.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class Interceptor implements HandlerInterceptor {

    private final AuthService authService;

    public Interceptor(AuthService authService) {
        this.authService = authService;
    }

    @Override
    public boolean preHandle(@NonNull HttpServletRequest request,
                             @NonNull HttpServletResponse response,
                             @NonNull Object handler){
        if(!(handler instanceof HandlerMethod handlerMethod)){
            return true;
        }

        RequiresAuth requiresAuth = handlerMethod.getMethodAnnotation(RequiresAuth.class);

        if(requiresAuth != null){
            if(!authService.isAuthenticated(request.getSession())){
                response.setStatus(HttpStatus.UNAUTHORIZED.value());
                return false;
            }

            if(requiresAuth.admin() && !authService.isAdmin(request.getSession())){
                response.setStatus(HttpStatus.FORBIDDEN.value());
                return false;
            }

            return true;
        }

        return true;

    }
}
