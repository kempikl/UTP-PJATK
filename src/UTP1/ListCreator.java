package UTP1;


import java.util.ArrayList;
import java.util.List;

public class ListCreator<T> { // Uwaga: klasa musi być sparametrtyzowana

    private List<T> list;

    private ListCreator(List<T> list) {
        this.list = new ArrayList<>(list);
    }

    public static <T> ListCreator<T> collectFrom(List<T> list) {
        return new ListCreator<>(list);
    }

    public ListCreator<T> when(Selector<T> selector) {
        ArrayList<T> tempList = new ArrayList<>();

        for (T element : list) {
            if (selector.select(element)) tempList.add(element);
        }

        list = tempList;

        return this;
    }

    public <U> List<U> mapEvery(Mapper<U, T> mapper) {
        ArrayList<U> tempList = new ArrayList<>();

        for (T element : list) {
            tempList.add(mapper.map(element));
        }

        return tempList;
    }
}
