package com.agentcoon.news.app.dropwizard;

import com.agentcoon.news.app.dropwizard.configuration.NewsConfiguration;
import com.agentcoon.news.app.dropwizard.inject.ApplicationHealthCheck;
import com.google.inject.Stage;
import com.hubspot.dropwizard.guice.GuiceBundle;
import io.dropwizard.Application;
import io.dropwizard.assets.AssetsBundle;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import org.eclipse.jetty.servlets.CrossOriginFilter;

import javax.servlet.DispatcherType;
import javax.servlet.FilterRegistration;
import java.util.EnumSet;

public class NewsApplication extends Application<NewsConfiguration> {

    private GuiceBundle<NewsConfiguration> guiceBundle;

    public static void main(String[] args) throws Exception {
        new NewsApplication().run(args);
    }

    @Override
    public String getName() {
        return "news-application";
    }

    @Override
    public void initialize(Bootstrap<NewsConfiguration> bootstrap) {

        guiceBundle = GuiceBundle.<NewsConfiguration>newBuilder()
                .addModule(new NewsApiAccessModule())
                .enableAutoConfig("com.agentcoon.news.rest",
                        "com.agentcoon.news.app.dropwizard.inject")
                .setConfigClass(NewsConfiguration.class)
                .build(Stage.DEVELOPMENT);

        bootstrap.addBundle(guiceBundle);

        bootstrap.addBundle(new AssetsBundle("/raml", "/raml", null, "raml"));
        bootstrap.addBundle(new AssetsBundle("/api/", "/api", "index.html"));
        bootstrap.addBundle(new AssetsBundle("/api/styles", "/styles", null, "styles"));
        bootstrap.addBundle(new AssetsBundle("/api/scripts", "/scripts", null, "scripts"));
        bootstrap.addBundle(new AssetsBundle("/api/fonts", "/fonts", null, "fonts"));
    }

    @Override
    public void run(NewsConfiguration config, Environment env) {

        if (config.getAllowCORS()) {
            final FilterRegistration.Dynamic cors = env.servlets().addFilter("CORS", CrossOriginFilter.class);

            cors.setInitParameter("allowedOrigins", "*");
            cors.setInitParameter("allowedHeaders", "X-Requested-With,Content-Type,Accept,Origin");
            cors.setInitParameter("allowedMethods", "OPTIONS,GET,PUT,POST,DELETE,HEAD");

            cors.addMappingForUrlPatterns(EnumSet.allOf(DispatcherType.class), true, "/*");
        }

        env.healthChecks().register("News Application HealthCheck", new ApplicationHealthCheck());
    }
}
