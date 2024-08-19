package io.github.yisiliang.starter.logback13;

import ch.qos.logback.classic.joran.JoranConfigurator;
import ch.qos.logback.core.joran.spi.ElementSelector;
import ch.qos.logback.core.joran.spi.RuleStore;
import ch.qos.logback.core.model.processor.DefaultProcessor;
import org.springframework.boot.logging.LoggingInitializationContext;

class SpringBootJoranConfigurator extends JoranConfigurator {

    private final LoggingInitializationContext initializationContext;

    SpringBootJoranConfigurator(LoggingInitializationContext initializationContext) {
        this.initializationContext = initializationContext;
    }

    @Override
    protected void addModelHandlerAssociations(DefaultProcessor defaultProcessor) {
        defaultProcessor.addHandler(SpringPropertyModel.class,
                (handlerContext, handlerMic) -> new SpringPropertyModelHandler(this.context,
                        this.initializationContext.getEnvironment()));
        defaultProcessor.addHandler(SpringProfileModel.class,
                (handlerContext, handlerMic) -> new SpringProfileModelHandler(this.context,
                        this.initializationContext.getEnvironment()));
        super.addModelHandlerAssociations(defaultProcessor);
    }

    @Override
    public void addElementSelectorAndActionAssociations(RuleStore ruleStore) {
        super.addElementSelectorAndActionAssociations(ruleStore);
        ruleStore.addRule(new ElementSelector("configuration/springProperty"), SpringPropertyAction::new);
        ruleStore.addRule(new ElementSelector("*/springProfile"), SpringProfileAction::new);
        ruleStore.addTransparentPathPart("springProfile");
    }
}

