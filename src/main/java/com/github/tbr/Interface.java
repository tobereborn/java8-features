package com.github.tbr;

import java.util.function.Supplier;

public class Interface {
    public static void main(String[] args) {
        Defaultable defaultable = DefaultableFactory.create(DefaultableImpl::new);
        System.out.println(defaultable.notRequired());
        defaultable = DefaultableFactory.create(OVerridableImpl::new);
        System.out.print(defaultable.notRequired());
    }
}


interface Defaultable {
    default String notRequired() {
        return "Default";
    }
}


class DefaultableImpl implements Defaultable {

}

class OVerridableImpl implements Defaultable {

    @Override
    public String notRequired() {
        return "Override";
    }
}

interface DefaultableFactory {
    static Defaultable create(Supplier<Defaultable> supplier) {
        return supplier.get();
    }
}
