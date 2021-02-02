package cubex2.cs3.util;

public interface MyBiConsumer<T, U>
{

    /**
     * Performs this operation on the given arguments.
     *
     * @param t the first input argument
     * @param u the second input argument
     */
    void accept(T t, U u);
}
