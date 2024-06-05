package ru.netology.Test;

import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Selenide.open;

public class IbankBuildFoTest {


    @Test
    void shouldIbankBuild() {
        open("http://localhost:9999/");
    }

}
