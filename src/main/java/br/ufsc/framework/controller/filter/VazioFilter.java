package br.ufsc.framework.controller.filter;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class VazioFilter implements Filter {

    @Override
    public void destroy() {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain fc) throws IOException, ServletException {

        @SuppressWarnings("unused")
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;

        // if(req.getRequestURI().contains(".js")){
// resp.setHeader("Content-Type", "application/javascript");
// }

        try (PrintWriter w = resp.getWriter()) {
            w.write(" ");
        }

        return;
    }


    @Override
    public void init(FilterConfig arg0) throws ServletException {

    }

}
