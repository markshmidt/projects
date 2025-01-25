import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.MockedStatic;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.verify;



import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class HippodromeTest {
    @Test
    public void nullException(){
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class,
                ()-> new Hippodrome(null));
        assertEquals("Horses cannot be null.", e.getMessage());
    }
    @Test
    public void constructorEmptyException() {
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> new Hippodrome(Collections.emptyList())
        );
        assertEquals("Horses cannot be empty.", exception.getMessage());
    }
    @Test
    public void getHorses() {
        // Arrange
        List<Horse> horses = new ArrayList<>();
        for (int i = 0; i < 30; i++) {
            horses.add(new Horse(" "+i, i, i));
        }
        Hippodrome hippodrome = new Hippodrome(horses);


        // Assert
        assertEquals(horses, hippodrome.getHorses());
    }
    @Test
    public void move(){
        // Arrange
        List<Horse> horses = new ArrayList<>();
        for (int i = 0; i < 50; i++) {
            horses.add(mock(Horse.class));
        }
        Hippodrome hippodrome = new Hippodrome(horses);
        hippodrome.move();
        for (Horse horse : horses) {
            verify(horse).move();
        }

    }
    @Test
    public void getWinner(){
        Horse horse1 = new Horse("Horse 1", 10, 100);
        Horse horse2 = new Horse("Horse 2", 10, 200);
        Horse horse3 = new Horse("Horse 3", 10, 150);

        List<Horse> horses = Arrays.asList(horse1, horse2, horse3);
        Hippodrome hippodrome = new Hippodrome(horses);
        Horse winner = hippodrome.getWinner();
        assertEquals(horse2, winner);
    }


}
