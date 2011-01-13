package org.jpos.ee.pm.vaadin;

import com.vaadin.event.Action.*;
import org.jpos.ee.pm.core.*;
import org.jpos.ee.pm.vaadin.converters.VaadinShowStringConverter;

/**
 *
 * @author jpaoletti
 */
public class PMVaadinService extends PMService {

    @Override
    protected void initService() throws Exception {
        super.initService();
        if(getDefaultConverter()==null){
            setDefaultConverter(new VaadinShowStringConverter());
        }
    }

    
}
