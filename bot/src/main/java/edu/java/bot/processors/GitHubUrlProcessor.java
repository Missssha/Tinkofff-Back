package edu.java.bot.processors;

public class GitHubUrlProcessor extends UrlProcessor {

    public GitHubUrlProcessor(UrlProcessor urlProcessor) {
        super(urlProcessor);
    }

    @Override
    protected String getValidHostName() {
        return "github.com";
    }
}
