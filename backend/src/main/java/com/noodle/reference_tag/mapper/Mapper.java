package com.noodle.reference_tag.mapper;

public interface Mapper<A,B> {
    B mapTo(A a);

    A mapFrom(B b);
}
