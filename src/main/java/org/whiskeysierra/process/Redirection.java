package org.whiskeysierra.process;

import com.google.common.base.Preconditions;
import org.whiskeysierra.process.Stream.Output;

import java.lang.ProcessBuilder.Redirect;
import java.nio.file.Path;

/**
 * @see Redirect
 */
public abstract class Redirection {

    public static final Redirection PIPE = new PipeRedirection();
    public static final Redirection INHERIT = new InheritRedirection();
    public static final Redirection NULL = new NullRedirection();

    /**
     * The type of a {@link Redirect}.
     */
    public enum Type {

        /**
         * The type of {@link Redirect#PIPE Redirect.PIPE}.
         */
        PIPE,

        /**
         * The type of {@link Redirect#INHERIT Redirect.INHERIT}.
         */
        INHERIT,

        /**
         *
         */
        NULL,

        /**
         * The type of redirects returned from
         * {@link Redirect#from Redirect.from(File)}.
         */
        READ,

        /**
         * The type of redirects returned from
         * {@link Redirect#to Redirect.to(File)}.
         */
        WRITE,

        OUTPUT, /**
         * The type of redirects returned from
         * {@link Redirect#appendTo Redirect.appendTo(File)}.
         */
        APPEND

    }

    private Redirection() {

    }

    public abstract Type type();

    public Path path() {
        return null;
    }

    @Override
    public String toString() {
        return type().toString();
    }

    public static Redirection from(Path path) {
        // TODO null check
        // TODO exists check?
        return new PathRedirection(Type.READ, path);
    }

    public static Redirection to(Path path) {
        // TODO null check
        return new PathRedirection(Type.WRITE, path);
    }

    public static Redirection appendTo(Path path) {
        // TODO null check
        return new PathRedirection(Type.APPEND, path);
    }

    public static Redirection to(Output output) {
        // TODO null check
        Preconditions.checkArgument(output == Stream.OUTPUT, "Unable to redirect to %s", output);
        return OutputRedirection.INSTANCE;
    }

    private static class PipeRedirection extends Redirection {

        @Override
        public Type type() {
            return Type.PIPE;
        }

    }

    private static class InheritRedirection extends Redirection {

        @Override
        public Type type() {
            return Type.INHERIT;
        }

    }

    private static class NullRedirection extends Redirection {

        @Override
        public Type type() {
            return Type.NULL;
        }

    }

    private static final class PathRedirection extends Redirection {

        private final Type type;
        private final Path path;

        private PathRedirection(Type type, Path path) {
            this.type = type;
            this.path = path;
        }

        @Override
        public Type type() {
            return type;
        }

        @Override
        public Path path() {
            return path;
        }

        @Override
        public String toString() {
            return String.format("%s [%s]", path, type);
        }

    }

    private static class OutputRedirection extends Redirection {

        private static final Redirection INSTANCE = new OutputRedirection();

        private OutputRedirection() {

        }

        @Override
        public Type type() {
            return Type.OUTPUT;
        }

    }

}
