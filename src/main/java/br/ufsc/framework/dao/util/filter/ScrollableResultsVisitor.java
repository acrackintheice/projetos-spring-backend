package br.ufsc.framework.dao.util.filter;

import org.hibernate.ScrollableResults;

import java.util.Map;

public interface ScrollableResultsVisitor {

    public void visit(ScrollableResults s) throws Exception;

    public void visitMap(Map<String, Object> m) throws Exception;

    public void scrollFinished() throws Exception;

}
