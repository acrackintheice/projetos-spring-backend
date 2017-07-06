package br.ufsc.framework.controller.util.richfaces;

import org.richfaces.component.UIOutputPanel;

//https://issues.jboss.org/browse/RF-12222
public class UIOutputPanelWorkaround extends UIOutputPanel {

    @Override
    public boolean isKeepTransient() {

        Boolean value = (Boolean) getStateHelper().eval(Properties.keepTransient, false);

        return value;

    }

}
