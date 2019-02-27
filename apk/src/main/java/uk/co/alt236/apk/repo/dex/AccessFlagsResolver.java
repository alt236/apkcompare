package uk.co.alt236.apk.repo.dex;

import com.android.dx.rop.code.AccessFlags;
import uk.co.alt236.apk.repo.dex.model.DexClass;
import uk.co.alt236.apk.repo.dex.model.DexMethod;

import javax.annotation.Nonnull;

public final class AccessFlagsResolver {

    public static String resolve(@Nonnull DexClass item) {
        final String result;
        if (item.isInnerClass()) {
            result = AccessFlags.innerClassString(item.getAccessFlags());
        } else {
            result = AccessFlags.classString(item.getAccessFlags());
        }

        return sanitise(result);
    }

    public static String resolve(@Nonnull DexMethod item) {
        final String result = AccessFlags.methodString(item.getAccessFlags());

        return sanitise(result);
    }

    private static String sanitise(final String flagString) {
        if ("0000".equals(flagString)) {
            return "none";
        } else {
            return flagString;
        }
    }
}
