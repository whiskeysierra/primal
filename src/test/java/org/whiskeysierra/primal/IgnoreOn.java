package org.whiskeysierra.primal;

import org.junit.Assume;
import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface IgnoreOn {

    Class<? extends Exception>[] value();

    public static final TestRule RULE = new TestRule() {

        @Override
        public Statement apply(final Statement base, Description desc) {
            final IgnoreOn annotation = desc.getAnnotation(IgnoreOn.class);
            return annotation == null ? base : new IgnoreStatement(base, annotation);
        }

    };

    static final class IgnoreStatement extends Statement {

        private final Statement base;
        private final IgnoreOn annotation;

        public IgnoreStatement(Statement base, IgnoreOn annotation) {
            this.base = base;
            this.annotation = annotation;
        }

        @Override
        public void evaluate() throws Throwable {
            try {
                base.evaluate();
            } catch (Throwable e) {
                for (Class<? extends Exception> type : annotation.value()) {
                    if (type.isInstance(e)) {
                        Assume.assumeNoException(e);
                        return;
                    }
                }
                throw e;
            }
        }

    }

}
