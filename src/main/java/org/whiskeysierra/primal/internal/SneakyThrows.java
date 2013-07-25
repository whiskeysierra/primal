package org.whiskeysierra.primal.internal;

import com.google.common.base.Preconditions;
import com.google.gag.annotation.remark.Booyah;
import com.google.gag.annotation.remark.Hack;

// TODO add test case
final class SneakyThrows {

    /**
     * Throws any checked exception without the need to declare it in the
     * throws clause.
     *
     * @param throwable the throwable to throw
     * @return never, this method <strong>always</strong> throws an exception
     * @see <a href="http://blog.jayway.com/2010/01/29/sneaky-throw">blog.jayway.com/2010/01/29/sneaky-throw</a>
     */
    @Hack
    @Booyah
    public static RuntimeException sneakyThrow(Throwable throwable) {
        SneakyThrows.<RuntimeException>doSneakyThrow(throwable);
        return null;
    }

    @SuppressWarnings("unchecked")
    private static <T extends Throwable> void doSneakyThrow(Throwable throwable) throws T {
        throw (T) Preconditions.checkNotNull(throwable, "Throwable");
    }


}
