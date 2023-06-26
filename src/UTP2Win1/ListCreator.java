package UTP2Win1;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.function.Function;

public class ListCreator<T> {
    private List<T> list;

    public ListCreator(List<T> list) {
        this.list = list;
    }

    public static <T> ListCreator<T> collectFrom(List<T> list) {
        return new ListCreator<>(list);
    }

    public ListCreator<T> when(Predicate<T> predicate) {
        List<T> tempList = new ArrayList<>();

        for (T val : list) {
            if (predicate.test(val))
                tempList.add(val);
        }

        list = tempList;
        return this;
    }

    public List<String> mapEvery(Function<T, String> mapper) {
        List<String> tempList = new ArrayList<>();

        for (T val : list) {
            tempList.add(mapper.apply(val));
        }

        return tempList;
    }
}
