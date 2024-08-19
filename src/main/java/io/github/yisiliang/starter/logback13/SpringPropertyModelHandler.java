package io.github.yisiliang.starter.logback13;

import ch.qos.logback.core.Context;
import ch.qos.logback.core.joran.action.ActionUtil;
import ch.qos.logback.core.model.Model;
import ch.qos.logback.core.model.ModelUtil;
import ch.qos.logback.core.model.processor.ModelHandlerBase;
import ch.qos.logback.core.model.processor.ModelHandlerException;
import ch.qos.logback.core.model.processor.ModelInterpretationContext;
import ch.qos.logback.core.util.OptionHelper;
import org.springframework.core.env.Environment;

class SpringPropertyModelHandler extends ModelHandlerBase {

    private final Environment environment;

    SpringPropertyModelHandler(Context context, Environment environment) {
        super(context);
        this.environment = environment;
    }

    @Override
    public void handle(ModelInterpretationContext intercon, Model model) throws ModelHandlerException {
        SpringPropertyModel propertyModel = (SpringPropertyModel) model;
        ActionUtil.Scope scope = ActionUtil.stringToScope(propertyModel.getScope());
        String defaultValue = propertyModel.getDefaultValue();
        String source = propertyModel.getSource();
        if (OptionHelper.isNullOrEmpty(propertyModel.getName()) || OptionHelper.isNullOrEmpty(source)) {
            addError("The \"name\" and \"source\" attributes of <springProperty> must be set");
        }
        ModelUtil.setProperty(intercon, propertyModel.getName(), getValue(source, defaultValue), scope);
    }

    private String getValue(String source, String defaultValue) {
        if (this.environment == null) {
            addWarn("No Spring Environment available to resolve " + source);
            return defaultValue;
        }
        return this.environment.getProperty(source, defaultValue);
    }

}
