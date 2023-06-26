package UTP1;


public interface Mapper<T, U> { // Uwaga: interfejs musi być sparametrtyzowany
    T map (U value);
}
