package kr.ac.jejunu.myproject.AuthUser;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
public class JwtExceptionFilter extends OncePerRequestFilter {
    private final ObjectMapper objectMapper;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException{
        response.setCharacterEncoding("UTF-8");

        try{
            filterChain.doFilter(request, response);
        }catch (ExpiredJwtException e) {
            //만료 에러
            request.setAttribute("exception", Code.EXPIRED_TOKEN.getCode());
        }catch(MalformedJwtException e) {
            //변조 에러
            request.setAttribute("exception", Code.WRONG_TYPE_TOKEN.getCode());
        }catch (SignatureException e){
            //형식, 길이 에러
            request.setAttribute("exception", Code.WRONG_TYPE_TOKEN.getCode());
        }
        filterChain.doFilter(request, response);
    }
}
