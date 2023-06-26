package UTP3Win1;

import java.util.function.Function;

public class InputConverter<T> {

    private final T inputData;

    public InputConverter(T inputData) {
        this.inputData = inputData;
    }

    public <R> R convertBy(Function... functions) {
        R result = (R) inputData;

        for (Function f : functions) {
            result = (R) f.apply(result);
        }
        return result;
    }
}
