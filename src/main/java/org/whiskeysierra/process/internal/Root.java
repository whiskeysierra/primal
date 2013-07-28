package org.whiskeysierra.process.internal;

import org.whiskeysierra.process.ProcessService;

import javax.inject.Inject;

public final class Root {

    private final ProcessService service;

    @Inject
    public Root(ProcessService service) {
        this.service = service;
    }

    public ProcessService getService() {
        return service;
    }

}
