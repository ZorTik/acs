package me.zort.acs.util;

import lombok.experimental.UtilityClass;
import me.zort.acs.http.entity.ResponseEntityWrapper;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@UtilityClass
public final class Responses {

    public static @NotNull <T> ResponseEntityWrapper<T> ok() {
        return ok(null);
    }

    public static @NotNull <T> ResponseEntityWrapper<T> ok(T body) {
        return new ResponseEntityWrapper<>(200, "OK", body);
    }

    public static @NotNull <T> ResponseEntityWrapper<T> notFound() {
        return notFound("Not Found");
    }

    public static @NotNull <T> ResponseEntityWrapper<T> notFound(@Nullable String message) {
        return new ResponseEntityWrapper<>(404, message, null);
    }

    public static @NotNull <T> ResponseEntityWrapper<T> conflict(@Nullable String message) {
        return new ResponseEntityWrapper<>(409, message, null);
    }
}
