package pcm.open_movie.config;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

@Slf4j
public class LoginCheckInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        String requestURI = request.getRequestURI();
        HttpSession session = request.getSession(false);
        if(session.getAttribute(SessionConst.LOGIN_MEMBER_ID).equals("") || session.getAttribute(SessionConst.LOGIN_MEMBER_ID) == null) {
            response.sendRedirect("/member/login?redirectURL=" + requestURI);
            return false;
        }
        return true;
    }

}
