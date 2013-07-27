package org.whiskeysierra.primal.internal;

import org.whiskeysierra.primal.Stream;

import java.lang.ProcessBuilder.Redirect;
import java.util.Map;

interface Redirector {

    public Map<Stream, Redirect> getDefaults();

    public Redirect fromNullDevice();

    public Redirect toNullDevice();

}
