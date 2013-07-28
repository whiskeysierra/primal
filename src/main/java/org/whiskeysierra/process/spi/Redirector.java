package org.whiskeysierra.process.spi;

import org.whiskeysierra.process.Redirection;
import org.whiskeysierra.process.Stream;

import java.lang.ProcessBuilder.Redirect;
import java.util.Map;

public interface Redirector {

    public Map<Stream, Redirection> getDefaults();

    public Redirect fromNullDevice();

    public Redirect toNullDevice();

}
