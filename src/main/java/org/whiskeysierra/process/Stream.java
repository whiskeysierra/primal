package org.whiskeysierra.process;

import org.whiskeysierra.process.Streams.DefaultInput;
import org.whiskeysierra.process.Streams.DefaultOutput;

public interface Stream {

    public static final Input INPUT = DefaultInput.INPUT;
    public static final Output OUTPUT = DefaultOutput.OUTPUT;
    public static final Output ERROR = DefaultOutput.ERROR;

    public interface Input extends Stream {

    }

    public interface Output extends Stream {

    }

}
