package com.miljanilic;

import com.google.inject.Guice;
import com.google.inject.Injector;
import module.SQLModule;

public class Main {
    public static void main(String[] args) {
        Injector injector = Guice.createInjector(new SQLModule());
        Application app = injector.getInstance(Application.class);

        app.run();
    }
}