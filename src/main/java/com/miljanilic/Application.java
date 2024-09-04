package com.miljanilic;

import com.google.inject.Inject;

public class Application {
    @Inject
    public Application() {}

    public void run() {
        System.out.println("Hello World!");
    }
}
