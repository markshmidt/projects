import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.MockedStatic;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.verify;



import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class HorseTest {

    @Test
    public void nullNameException(){
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class, ()-> new Horse(null, 1, 1));
        assertEquals("Name cannot be null.", e.getMessage());
    }
    @ParameterizedTest
    @ValueSource(strings = {"", " ", "   ", "\t", "\n"})
    public void blankNameException(String invalidName) {
        IllegalArgumentException e = assertThrows(
                IllegalArgumentException.class,
                () -> new Horse(invalidName, 1, 1)
        );
        assertEquals("Name cannot be blank.", e.getMessage());
    }

    @Test
    public void speedException(){
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class, ()-> new Horse("name", -1, 1));
        assertEquals("Speed cannot be negative.", e.getMessage());
    }
    @Test
    public void distanceException(){
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class, ()-> new Horse("name", 1, -1));
        assertEquals("Distance cannot be negative.", e.getMessage());
    }

    @Test
    public void getName() {
        // Arrange
        Horse horse = new Horse("qwerty", 1, 1);

        // Act
        String name = horse.getName();

        // Assert
        assertEquals("qwerty", name, "getName() should return the correct name.");
    }
    @Test
    public void getSpeed() {
        // Arrange
        Horse horse = new Horse("qwerty", 1, 1);

        // Act
        double speed = horse.getSpeed();

        // Assert
        assertEquals(1, speed);
    }
    @Test
    public void getDistance()  {
        // Arrange
        Horse horse = new Horse("qwerty", 1, 1);

        // Act
        double distance = horse.getDistance();

        // Assert
        assertEquals(1, distance);
    }
    @Test
    public void getZeroDistance()  {
        // Arrange
        Horse horse = new Horse("qwerty", 1);

        // Act
        double distance = horse.getDistance();

        // Assert
        assertEquals(0, distance);
    }
    @Test
    public void moveTest(){
        // Arrange
        Horse horse = new Horse("TestHorse", 5, 10);

        // Mock
        try (MockedStatic<Horse> mockedStatic = mockStatic(Horse.class)) {
            // Act
            horse.move();

            // Assert
            mockedStatic.verify(() -> Horse.getRandomDouble(0.2, 0.9));
        }
    }
    @Test
    public void moveDistanceTest() {
        try (MockedStatic<Horse> mockedStatic = mockStatic(Horse.class)) {
            Horse horse = new Horse("TestHorse", 10, 20);

            mockedStatic.when(() -> Horse.getRandomDouble(0.2, 0.9)).thenReturn(0.5);
            horse.move();

            assertEquals(20 + 10 * 0.5, horse.getDistance());
        }


    }
}
