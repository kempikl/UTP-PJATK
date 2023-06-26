package UTP3Win4;

import java.util.NoSuchElementException;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;

public class Maybe<T> {

    private final T value;

    private Maybe(T value) {
        this.value = value;
    }

    public static <T> Maybe<T> of(T value) {
        return new Maybe<>(value);
    }

    public static <T> Maybe<T> empty() {
        return new Maybe<>(null);
    }

    public void ifPresent(Consumer<? super T> consumer) {
        if (value != null) {
            consumer.accept(value);
        }
    }

    public <U> Maybe<U> map(Function<? super T, ? extends U> function) {
        if (value == null) {
            return empty();
        } else {
            return of(function.apply(value));
        }
    }

    public T get() {
        if (value == null) {
            throw new NoSuchElementException("maybe is empty");
        } else {
            return value;
        }
    }

    private boolean isPresent() {
        return value != null;
    }

    public T orElse(T defVal) {
        return (value != null) ? value : defVal;
    }

    public Maybe<T> filter(Predicate<? super T> predicate) {
        if (value == null || predicate.test(value)) {
            return this;
        } else {
            return empty();
        }
    }

    @Override
    public String toString() {
        if (!isPresent()) {
            return "Maybe is empty";
        } else {
            return "Maybe has value " + value;
        }
    }
}
