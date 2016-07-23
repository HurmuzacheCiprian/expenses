package com.api.expenses.service.util;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Pair<X,Y> {

    private final X first;
    private final Y second;

    public Pair(final X first, final Y second) {
        this.first = first;
        this.second = second;
    }

}
