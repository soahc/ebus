package de.ebus.emarket.frontend;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.protocol.http.WebApplication;

public class WicketApplication extends WebApplication
{
    public WicketApplication() {
    }

    @Override
    public Class<? extends WebPage> getHomePage() {
        return SignIn.class;
    }
}