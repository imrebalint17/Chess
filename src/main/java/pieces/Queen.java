package pieces;

import game.Table;

import javax.swing.*;
import java.util.Objects;

public class Queen extends Piece{

    public Queen(int xpos, int ypos, Color color) {
        super(xpos, ypos, color);
    }
    @Override
    public boolean vaildMove(Table table, int x, int y) {
        if (table.getpiece(x,y)!=null && table.getpiece(x,y).color == super.color){
            return false;
        }
        return new Rook(xpos, ypos, super.color).vaildMove(table,x, y) || new Bishop(xpos, ypos, super.color).vaildMove(table,x, y);
    }
    @Override
    public String getCharacter(){
        return "q";
    }
    @Override
    public String toString() {
        if (this.color == Color.White) {
            return "♕";
        }
        return "♛";
    }
    @Override
    public ImageIcon getImageIcon(){
        if (this.color == Color.Black){
            return new ImageIcon(Objects.requireNonNull(getClass().getResource("/PiecePictures/BQueen.png")));
        }
        else{
            return new ImageIcon(Objects.requireNonNull(getClass().getResource("/PiecePictures/WQueen.png")));
        }
    }
    @Override
    public int getScore(){
        return 9;
    }
}
