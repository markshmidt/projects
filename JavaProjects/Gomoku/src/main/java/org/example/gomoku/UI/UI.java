import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import Board.*;

public class GomokuUI extends Application {
    private final int BOARD_SIZE = 9;
    private Board board;
    private Button[][] buttons;

    @Override
    public void start(Stage primaryStage) {
        board = new Board(BOARD_SIZE);
        buttons = new Button[BOARD_SIZE][BOARD_SIZE];

        GridPane grid = new GridPane();
        for (int row = 0; row < BOARD_SIZE; row++) {
            for (int col = 0; col < BOARD_SIZE; col++) {
                Button btn = new Button(".");
                btn.setPrefSize(40, 40);
                final int r = row, c = col;
                btn.setOnAction(e -> handleMove(r, c));
                buttons[row][col] = btn;
                grid.add(btn, col, row);
            }
        }

        VBox root = new VBox(10, new Label("Gomoku - Two Player Mode"), grid);
        Scene scene = new Scene(root, 400, 450);

        primaryStage.setTitle("Gomoku Game");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }