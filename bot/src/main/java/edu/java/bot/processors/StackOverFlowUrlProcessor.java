package edu.java.bot.processors;

public class StackOverFlowUrlProcessor extends UrlProcessor {
    public StackOverFlowUrlProcessor(UrlProcessor processor) {
        super(processor);
    }

    @Override
    protected String getValidHostName() {
        return "stackoverflow.com";
    }
}
