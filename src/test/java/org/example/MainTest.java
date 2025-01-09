package org.example;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MainTest {

    @Test
    @DisplayName("Addition")
    void add() {
        assertAll(() -> assertEquals(4, Main.evaluate("2 + 2")),
                () -> assertEquals(2, Main.evaluate("-2 + 4")),
                () -> assertEquals(12, Main.evaluate("2 + 2 + 2 + 2 + 2 + 2")));
    }

    @Test
    @DisplayName("Subtraction")
    void sub() {
        assertAll(() -> assertEquals(0, Main.evaluate("2 - 2")),
                () -> assertEquals(6, Main.evaluate("2 - -4")),
                () -> assertEquals(-8, Main.evaluate("2 - 2 - 2 - 2 - 2 - 2")));
    }

    @Test
    @DisplayName("Multiplication")
    void mul() {
        assertAll(() -> assertEquals(9, Main.evaluate("3 * 3")),
                () -> assertEquals(-9, Main.evaluate("-3 * 3")));
    }

    @Test
    @DisplayName("Division")
    void div() {
        assertAll(() -> assertEquals(2, Main.evaluate("4 / 2")),
                () -> assertEquals(-2, Main.evaluate("4 / -2")));
    }

    @Test
    @DisplayName("Add. and Sub.")
    void addSub() {
        assertAll(() -> assertEquals(1, Main.evaluate("2 + 3 - 4")),
                () -> assertEquals(-4, Main.evaluate("4 + -2 - 6")));
    }

    @Test
    @DisplayName("Mul. and Div.")
    void mulDiv() {
        assertAll(() -> assertEquals(1.5, Main.evaluate("2 * 3 / 4")),
                () -> assertEquals(2, Main.evaluate("-2 * -4 / 4")));
    }

    @Test
    @DisplayName("All operators at once")
    void all() {
        assertAll(() -> assertEquals(0.6000000000000001, Main.evaluate("1 + 2 - 3 * 4 / 5")),
                () -> assertEquals(2.2, Main.evaluate("1 + (-2 - -4) * 3 / 5")));
    }
}