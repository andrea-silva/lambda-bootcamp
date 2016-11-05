package it.andreasilva.methodreference;

@FunctionalInterface
public interface TetraFunction<A,B,C,D,E> {
    E apply(A a, B b, C c, D d);
}
