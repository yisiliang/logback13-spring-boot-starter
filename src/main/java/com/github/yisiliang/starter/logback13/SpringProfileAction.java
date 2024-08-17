package com.github.yisiliang.starter.logback13;

import ch.qos.logback.core.joran.action.BaseModelAction;
import ch.qos.logback.core.joran.spi.SaxEventInterpretationContext;
import ch.qos.logback.core.model.Model;
import org.xml.sax.Attributes;

class SpringProfileAction extends BaseModelAction {

    @Override
    protected Model buildCurrentModel(SaxEventInterpretationContext interpretationContext, String name,
                                      Attributes attributes) {
        SpringProfileModel model = new SpringProfileModel();
        model.setName(attributes.getValue(NAME_ATTRIBUTE));
        return model;
    }

}
