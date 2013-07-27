package org.whiskeysierra.process.internal;

import org.whiskeysierra.process.Redirection;
import org.whiskeysierra.process.Stream;

import java.lang.ProcessBuilder.Redirect;
import java.util.Map;

interface Redirector {

    public Map<Stream, Redirection> getDefaults();

    public Redirection fromNullDevice();

    public Redirection toNullDevice();

}
