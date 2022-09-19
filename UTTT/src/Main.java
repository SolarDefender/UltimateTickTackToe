import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import javax.swing.*;
import java.util.ArrayList;




public class Main extends Application {

final int cellSize=100;

public int currentField=10;
public int previousField=-1;
public boolean Xturn=true;
ArrayList<Field> board=new ArrayList<>();
Text turn=new Text("X turn");
ListView<String> history = new ListView<String>();
boolean PVP=false;

    public Main(boolean PVP) {
        this.PVP = PVP;
    }
    public Main()
    {}

    @Override
    public void start(Stage stage) throws Exception {
        stage.setX(500);
        stage.setY(50);
        Pane root=new Pane();
        Scene game=new Scene(root,1080,900);
        Rectangle[][] grid =new Rectangle[3][3];
        stage.setTitle("Ultimate tic tac toe");
        {
            int fieldsCounter = 0;
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    board.add( new Field(fieldsCounter));
                    board.get(fieldsCounter).setTranslateX(j *board.get(fieldsCounter).field[0][0].border.getWidth()*3);
                    board.get(fieldsCounter).setTranslateY(i * board.get(fieldsCounter).field[0][0].border.getHeight()*3);
                    root.getChildren().add(board.get(fieldsCounter));
                    fieldsCounter++;

                }
            }
        }

        turn.setX(cellSize*9+50);
        turn.setY(25);
        turn.setFont(Font.font(16));
        history.setTranslateX(cellSize*9+7);
        history.setTranslateY(40);
        history.setMaxWidth(180);
        history.setMinHeight(cellSize*9-history.getTranslateY());
        history.setMaxHeight(cellSize*9-history.getTranslateY());
        root.getChildren().addAll(turn,history);
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                grid[i][j]=new Rectangle(cellSize*3,cellSize*3);
                grid[i][j].setFill(null);
                grid[i][j].setStroke(Color.BLACK);
                grid[i][j].setStrokeWidth(15);

                grid[i][j].setTranslateX(j*grid[i][j].getWidth());
                grid[i][j].setTranslateY(i*grid[i][j].getHeight());
                root.getChildren().add(grid[i][j]);
            }
        }

        setAvailable();
        stage.setScene(game);
        stage.show();
    }

    public void setAvailable()
    {
        if (currentField==10)
        {
            for (Field f: board) {
                if (f.statment==null)
                f.changeColor(Color.GREEN);
            }
        }
        else if (previousField==10)
        {
            for(Field f: board)
                f.changeColor(null);

            board.get(currentField).changeColor(Color.GREEN);
        }
        else
        {
            board.get(previousField).changeColor(null);
            board.get(currentField).changeColor(Color.GREEN);
        }

    }

    public Cell getRandomAvailableCell()
    {
        while (true)
        {
            if (currentField==10)
            {
                int randomField = (int) (Math.random() * 9);
                if (board.get(randomField).statment == null) {
                    while (true) {
                        int randomCell = (int) (Math.random() * 9);
                        if (board.get(randomField).fieldList.get(randomCell).statment == null) {
                            return board.get(randomField).fieldList.get(randomCell);
                        }
                    }
                }
            }
            else
            {
                int randomCell = (int) (Math.random() * 9);
                System.out.println(randomCell);
                if (board.get(currentField).fieldList.get(randomCell).statment == null) {
                    return board.get(currentField).fieldList.get(randomCell);
                }
            }

        }
    }

    public boolean isWin()
    {
       Field[][] boardArray=new Field[3][3];
        {
            int fieldsCounter=0;
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                  boardArray[i][j]=board.get(fieldsCounter);
                    fieldsCounter++;
                }
            }
        }
        for (int i = 0; i < 3; i++) {
            if (boardArray[0][i].statment!=null&&boardArray[1][i].statment!=null&&boardArray[2][i].statment!=null&&
                    boardArray[0][i].statment.getText().equals(boardArray[1][i].statment.getText())&&boardArray[0][i].statment.getText().equals(boardArray[2][i].statment.getText()))
            {
                if (boardArray[0][i].statment.getText().equals("X"))
                    for (Field f: board) {
                        f.setX();
                    }
                else
                    for (Field f: board) {
                        f.setO();
                    }
                   return true;
            }
            if ( boardArray[i][0].statment!=null&& boardArray[i][1].statment!=null&&boardArray[i][2].statment!=null &&
                    boardArray[i][0].statment.getText().equals(boardArray[i][1].statment.getText())&&boardArray[i][0].statment.getText().equals(boardArray[i][2].statment.getText()))
            {
                if (boardArray[i][0].statment.getText().equals("X"))
                    for (Field f: board) {
                        f.setX();
                    }
                else
                    for (Field f: board) {
                        f.setO();
                    }
                return true;
            }
        }
        if (boardArray[0][0].statment!=null&& boardArray[1][1].statment!=null && boardArray[2][2].statment!=null&&
                boardArray[0][0].statment.getText().equals(boardArray[1][1].statment.getText())&&boardArray[0][0].statment.getText().equals(boardArray[2][2].statment.getText())||
                boardArray[0][2].statment!=null&&boardArray[1][1].statment!=null &&boardArray[2][0].statment!=null &&
                        boardArray[0][2].statment.getText().equals(boardArray[1][1].statment.getText())&&boardArray[0][2].statment.getText().equals(boardArray[2][0].statment.getText()))
        {
            if (boardArray[1][1].statment.getText().equals("X"))
                for (Field f:
                        board) {
                    f.setX();
                }
            else
                for (Field f: board) {
                    f.setO();
                }

            return true;
        }
        return false;
    }





    public class Field extends StackPane{
        boolean current=false;
        Text statment=null;
        int coordinate;
        Cell field[][]=new Cell[3][3];
        ArrayList<Cell> fieldList=new ArrayList<>();
        Rectangle border=new Rectangle(new Cell().border.getHeight()*3,new Cell().border.getWidth()*3);

        public void changeColor(Color color)
        {
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    field[i][j].border.setFill(color);
                }
            }
        }

        public Field(int coordinate)
        {
            this.coordinate=coordinate;
            this.border.setFill(null);
            this.border.setStroke(Color.BLUE);
            this.border.setStrokeWidth(3);
            //setAlignment(Pos.CENTER);
            border.setTranslateX(this.getTranslateX());
            border.setTranslateY(this.getTranslateY());

            //getChildren().add(border);
            {
                int cellCounter=0;
                for (int i = 0; i < 3; i++) {
                    for (int j = 0; j < 3; j++) {
                        field[i][j] = new Cell(cellCounter, this);
                        field[i][j].setTranslateX(j * field[i][j].border.getWidth());
                        field[i][j].setTranslateY(i  * field[i][j].border.getHeight());
                        fieldList.add(field[i][j]);
                        cellCounter++;
                        getChildren().add(field[i][j]);
                    }
                }
            }
        }

        public boolean isComplete()
        {

            for (int i = 0; i < 3; i++) {
                if (field[0][i].statment!=null&&field[1][i].statment!=null&&field[2][i].statment!=null&&
                        field[0][i].statment.getText().equals(field[1][i].statment.getText())&&field[0][i].statment.getText().equals(field[2][i].statment.getText()))
                {
                    if (field[0][i].statment.getText().equals("X"))
                        setX();

                    else
                        setO();

                }
                if ( field[i][0].statment!=null&& field[i][1].statment!=null&&field[i][2].statment!=null &&
                        field[i][0].statment.getText().equals(field[i][1].statment.getText())&&field[i][0].statment.getText().equals(field[i][2].statment.getText()))
                {
                    if (field[i][0].statment.getText().equals("X"))
                        setX();
                    else
                        setO();

                }
            }
            if (field[0][0].statment!=null&& field[1][1].statment!=null && field[2][2].statment!=null&&
            field[0][0].statment.getText().equals(field[1][1].statment.getText())&&field[0][0].statment.getText().equals(field[2][2].statment.getText())||
                    field[0][2].statment!=null&&field[1][1].statment!=null &&field[2][0].statment!=null &&
                            field[0][2].statment.getText().equals(field[1][1].statment.getText())&&field[0][2].statment.getText().equals(field[2][0].statment.getText()))
            {
                if (field[1][1].statment.getText().equals("X"))
                    setX();

                else
                    setO();

                return true;
            }
            return false;
        }


        public void setX()
        {
            this.statment=new Text("X");
            this.statment.setFont(Font.font(36));
            this.statment.setFill(Color.RED);
            this.statment.setTranslateX(field[1][1].getTranslateX());
            this.statment.setTranslateY(field[1][1].getTranslateY());
            this.getChildren().add(statment);
            for (int k = 0; k < 3; k++)
                for (int j = 0; j < 3; j++) {
                    field[k][j].setX();
                    field[k][j].statment.setFill(Color.RED);
                }
        }

        public void setO()
        {
            this.statment=new Text("O");
            this.statment.setFont(Font.font(36));
            this.statment.setFill(Color.RED);
            this.statment.setTranslateX(field[1][1].getTranslateX());
            this.statment.setTranslateY(field[1][1].getTranslateY());
            this.getChildren().add(statment);
            for (int i = 0; i < 3; i++)
                for (int j = 0; j < 3; j++) {
                    field[i][j].setO();
                    field[i][j].statment.setFill(Color.RED);
                }
        }
    }




    public class Cell extends StackPane {
        Field relatedField;
        Text statment=null;
        int coordinate;
        Rectangle border=new Rectangle(cellSize,cellSize);
        public Cell(int coordinate,Field relatedField){
            this.relatedField=relatedField;
            this.coordinate=coordinate;
            border.setFill(null);
            border.setStroke(Color.BLACK);
            getChildren().addAll(border);
            mouseClicked();

        }

        public Cell() {
        }

        public void mouseClicked()
        {
            setOnMouseClicked(mouseEvent -> {
                if (mouseEvent.getButton()== MouseButton.PRIMARY) {
                    if (PVP) {
                        if (this.relatedField.coordinate == currentField && this.statment == null || currentField == 10 && this.statment == null) {
                            previousField = currentField;
                            if (Xturn) {
                                setX();
                            } else {
                                setO();
                            }
                            if (board.get(this.coordinate).statment == null) {
                                currentField = this.coordinate;
                                if (board.get(currentField).isComplete())
                                    currentField = 10;
                            } else
                                currentField = 10;
                            history.getItems().add((Xturn ? "X" : "O")+" Field: "+this.relatedField.coordinate+" Cell: "+this.coordinate);
                            Xturn = !Xturn;
                            relatedField.isComplete();
                            setAvailable();
                            System.out.println("Field: " + " " + relatedField.coordinate);
                            turn.setText(Xturn ? "X turn" : "O turn");

                            if (isWin()) {
                                JFrame frame = new JFrame("WIN!");
                                if (Xturn)
                                    JOptionPane.showMessageDialog(frame, "O WON!!!");
                                else
                                    JOptionPane.showMessageDialog(frame, "X WON!!!");
                                System.exit(0);
                            }
                        }
                    } else {
                        ///////////////////////////////////////////////  PLAYER's TURN
                        if (this.relatedField.coordinate == currentField && this.statment == null || currentField == 10 && this.statment == null) {
                            previousField = currentField;
                            setX();
                            if (board.get(this.coordinate).statment == null) {
                                currentField = this.coordinate;
                                if (board.get(currentField).isComplete())
                                    currentField = 10;
                            } else
                                currentField = 10;
                            relatedField.isComplete();
                            setAvailable();
                            System.out.println("Field: " + " " + relatedField.coordinate);
                            history.getItems().add((Xturn ? "X" : "O")+" Field: "+this.relatedField.coordinate+" Cell: "+this.coordinate);
                            Xturn = !Xturn;
                            turn.setText(Xturn ? "X turn" : "O turn");


                            ///////////////////////////////////////////////       BOT's TURN
                            previousField = currentField;
                            Cell botTurn = getRandomAvailableCell();
                            int botTurnField = botTurn.relatedField.coordinate;
                            int botTurnCell = botTurn.coordinate;

                            board.get(botTurnField).fieldList.get(botTurnCell).setO();
                            if (board.get(botTurnCell).statment == null) {
                                currentField = botTurnCell;
                                if (board.get(currentField).isComplete())
                                    currentField = 10;
                            } else
                                currentField = 10;
                            board.get(botTurnField).isComplete();
                            setAvailable();
                            history.getItems().add((Xturn ? "X" : "O")+" Field: "+botTurnField+" Cell: "+botTurnCell);
                            Xturn = !Xturn;
                            turn.setText(Xturn ? "X turn" : "O turn");


                        }
                    }
                    if (isWin()) {
                        JFrame frame = new JFrame("WIN!");
                        if (Xturn)
                        JOptionPane.showMessageDialog(frame, "X WON!!!");
                        else
                            JOptionPane.showMessageDialog(frame, "O WON!!!");
                        System.exit(0);
                    }
                }
            });

        }

        public void setX()
        {
            statment=new Text("X");
            statment.setFont(Font.font(36));
            getChildren().add(statment);
        }
        public void setO()
        {
            statment=new Text("O");
            statment.setFont(Font.font(36));
            getChildren().add(statment);
        }
    }

}
