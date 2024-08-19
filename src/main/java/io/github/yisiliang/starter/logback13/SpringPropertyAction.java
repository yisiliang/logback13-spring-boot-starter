package io.github.yisiliang.starter.logback13;

import ch.qos.logback.core.joran.action.BaseModelAction;
import ch.qos.logback.core.joran.spi.SaxEventInterpretationContext;
import ch.qos.logback.core.model.Model;
import org.xml.sax.Attributes;

class SpringPropertyAction extends BaseModelAction {

    private static final String SOURCE_ATTRIBUTE = "source";

    private static final String DEFAULT_VALUE_ATTRIBUTE = "defaultValue";

    @Override
    protected Model buildCurrentModel(SaxEventInterpretationContext interpretationContext, String name,
                                      Attributes attributes) {
        SpringPropertyModel model = new SpringPropertyModel();
        model.setName(attributes.getValue(NAME_ATTRIBUTE));
        model.setSource(attributes.getValue(SOURCE_ATTRIBUTE));
        model.setScope(attributes.getValue(SCOPE_ATTRIBUTE));
        model.setDefaultValue(attributes.getValue(DEFAULT_VALUE_ATTRIBUTE));
        return model;
    }

}
