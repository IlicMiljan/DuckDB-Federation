package com.miljanilic;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.miljanilic.module.SQLModule;

public class Main {
    public static void main(String[] args) {
        Injector injector = Guice.createInjector(new SQLModule());
        Application app = injector.getInstance(Application.class);

        app.run();
    }
}