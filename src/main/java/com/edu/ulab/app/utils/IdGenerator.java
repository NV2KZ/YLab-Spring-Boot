package com.edu.ulab.app.utils;

import java.util.concurrent.atomic.AtomicLong;

public class IdGenerator {
    private static final AtomicLong genericId = new AtomicLong(0L);

    public static Long nextId() {
        return genericId.incrementAndGet();
    }
}
