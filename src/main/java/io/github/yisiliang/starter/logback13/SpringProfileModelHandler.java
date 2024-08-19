package io.github.yisiliang.starter.logback13;

import ch.qos.logback.core.Context;
import ch.qos.logback.core.model.Model;
import ch.qos.logback.core.model.processor.ModelHandlerBase;
import ch.qos.logback.core.model.processor.ModelHandlerException;
import ch.qos.logback.core.model.processor.ModelInterpretationContext;
import ch.qos.logback.core.spi.ScanException;
import ch.qos.logback.core.util.OptionHelper;
import org.springframework.core.env.Environment;
import org.springframework.core.env.Profiles;
import org.springframework.util.StringUtils;

class SpringProfileModelHandler extends ModelHandlerBase {

    private final Environment environment;

    SpringProfileModelHandler(Context context, Environment environment) {
        super(context);
        this.environment = environment;
    }

    @Override
    public void handle(ModelInterpretationContext intercon, Model model) throws ModelHandlerException {
        SpringProfileModel profileModel = (SpringProfileModel) model;
        if (!acceptsProfiles(intercon, profileModel)) {
            model.markAsSkipped();
        }
    }

    private boolean acceptsProfiles(ModelInterpretationContext ic, SpringProfileModel model) {
        if (this.environment == null) {
            return false;
        }
        String[] profileNames = StringUtils
                .trimArrayElements(StringUtils.commaDelimitedListToStringArray(model.getName()));
        if (profileNames.length == 0) {
            return false;
        }
        for (int i = 0; i < profileNames.length; i++) {
            try {
                profileNames[i] = OptionHelper.substVars(profileNames[i], ic, this.context);
            }
            catch (ScanException ex) {
                throw new RuntimeException(ex);
            }
        }
        return this.environment.acceptsProfiles(Profiles.of(profileNames));
    }

}
