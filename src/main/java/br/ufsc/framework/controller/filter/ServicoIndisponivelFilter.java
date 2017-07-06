package br.ufsc.framework.controller.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.remoting.RemoteAccessException;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.UnknownHostException;

public class ServicoIndisponivelFilter implements Filter {

    @SuppressWarnings("unused")
    private static final Logger LOGGER = LoggerFactory.getLogger(ServicoIndisponivelFilter.class);

    @Override
    public void destroy() {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        try {
            chain.doFilter(request, response);
        } catch (RemoteAccessException | UnknownHostException ex) {
            redirecionar(request, response);
            return;
        }
    }

    private void redirecionar(ServletRequest request, ServletResponse response) throws IOException {
        String ctxPath = ((HttpServletRequest) request).getContextPath();
        ctxPath += "/erros/indisponivel.xhtml";
        ((HttpServletResponse) response).sendRedirect(ctxPath);
    }


    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }
}
