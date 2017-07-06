package br.ufsc.framework.controller.filter;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class FixIE9Filter implements Filter {


    @Override
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws IOException, ServletException {

        if (resp instanceof HttpServletResponse && this.isIE(req)) {
            HttpServletResponse response = (HttpServletResponse) resp;

            response.setHeader("X-UA-Compatible", "IE=EmulateIE8");
        }
        chain.doFilter(req, resp);
    }

    private boolean isIE(ServletRequest req) {

        try {

            HttpServletRequest r = (HttpServletRequest) req;
            return r.getHeader("User-Agent").contains("MSIE");

        } catch (Exception ex) {
            return false;
        }

    }


    @Override
    public void destroy() {

    }


    @Override
    public void init(FilterConfig arg0) throws ServletException {

    }

}
