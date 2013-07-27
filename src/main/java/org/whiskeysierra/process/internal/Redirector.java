package org.whiskeysierra.process.internal;

import org.whiskeysierra.process.Stream;

import java.lang.ProcessBuilder.Redirect;
import java.util.Map;

interface Redirector {

    public Map<Stream, Redirect> getDefaults();

    public Redirect fromNullDevice();

    public Redirect toNullDevice();

}
