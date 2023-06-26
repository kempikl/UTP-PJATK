package UTP3Win5;

import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.stream.Collectors;

public class XList<T> extends ArrayList<T> {
    public XList() {
        super();
    }

    public XList(Collection<? extends T> c) {
        super(c);
    }

    @SafeVarargs
    public XList(T... elements) {
        super(Arrays.asList(elements));
    }

    @SafeVarargs
    public static <T> XList<T> of(T... elements) {
        return new XList<>(elements);
    }

    public static <T> XList<T> of(Collection<? extends T> collection) {
        return new XList<>(collection);
    }

    public static XList<String> charsOf(String s) {
        return new XList<>(s.chars().mapToObj(c -> String.valueOf((char) c)).collect(Collectors.toList()));
    }

    public static XList<String> tokensOf(String s) {
        return tokensOf(s, "\\s+");
    }

    public static XList<String> tokensOf(String s, String delimiter) {
        return new XList<>(Arrays.asList(s.split(delimiter)));
    }

    public XList<T> union(Collection<? extends T> collection) {
        XList<T> result = new XList<>(this);
        result.addAll(collection);
        return result;
    }

    public XList<T> union(T[] array) {
        XList<T> result = new XList<>(this);
        Collections.addAll(result, array);
        return result;
    }


    public XList<T> diff(Collection<? extends T> collection) {
        XList<T> result = new XList<>(this);
        result.removeAll(collection);
        return result;
    }

    public XList<T> unique() {
        return new XList<>(new HashSet<>(this));
    }

    public <R> XList<R> collect(Function<? super T, ? extends R> mapper) {
        return new XList<>(this.stream().map(mapper).collect(Collectors.toList()));
    }

    public String join() {
        return join("");
    }

    public String join(String separator) {
        return this.stream().map(Object::toString).collect(Collectors.joining(separator));
    }

    public void forEachWithIndex(BiConsumer<? super T, Integer> action) {
        for (int i = 0; i < this.size(); i++) {
            action.accept(this.get(i), i);
        }
    }

    public XList<XList<T>> combine() {
        List<XList<T>> result = new ArrayList<>();
        combineHelper(result, 0, new XList<T>());
        return new XList<>(result);
    }

    private void combineHelper(List<XList<T>> result, int depth, XList<T> current) {
        if (depth == size()) {
            result.add(current);
            return;
        }

        List<T> elementList = ((List<T>) get(depth));
        for (T element : elementList) {
            XList<T> newCurrent = new XList<>(current);
            newCurrent.add(element);
            combineHelper(result, depth + 1, newCurrent);
        }
    }

}

