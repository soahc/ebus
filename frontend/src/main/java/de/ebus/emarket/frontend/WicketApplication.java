package de.ebus.emarket.frontend;

import org.apache.wicket.protocol.http.WebApplication;

public class WicketApplication extends WebApplication
{
    public WicketApplication() {
    }

    @Override
    public Class<Homepage> getHomePage() {
        return Homepage.class;
    }
}