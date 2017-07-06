package br.ufsc.framework.controller.filter;

import com.sun.faces.context.FacesFileNotFoundException;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.faces.application.ViewExpiredException;
import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ViewExpiredFilter implements Filter {

    @SuppressWarnings("unused")
    private static final Logger LOGGER = LoggerFactory.getLogger(ViewExpiredFilter.class);

    @Override
    public void destroy() {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException,
        ServletException {

        try {
            chain.doFilter(request, response);
        } catch (ServletException ex) {

            if (ex.getCause() instanceof ViewExpiredException) {

                String ctxPath = ((HttpServletRequest) request).getContextPath();
                if (StringUtils.isBlank(ctxPath)) {
                    ctxPath = "/";
                }
                if (!ctxPath.endsWith("/")) {
                    ctxPath += "/";
                }

                ((HttpServletResponse) response).sendRedirect(ctxPath);
                return;
            } else {
                throw ex;
            }
        } catch (FacesFileNotFoundException ffnfex) {
            ((HttpServletResponse) response).sendError(404);
        }

    }


    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }
}
