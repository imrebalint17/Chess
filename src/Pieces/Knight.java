package Pieces;

import Game.Table;

import javax.swing.*;

public class Knight extends Piece{
    public Knight(int xpos, int ypos, Color color) {
        super(xpos, ypos, color);
    }
    @Override
    public boolean vaildMove(Table table,int x, int y) {
        if (table.getpiece(x,y)!=null && table.getpiece(x,y).color == super.color){
            return false;
        }
        if (Math.abs(x-xpos)==2 && Math.abs(y-ypos)==1){
            return true;
        }
        return Math.abs(x - xpos) == 1 && Math.abs(y - ypos) == 2;
    }
    public String getCharacter(){
        return "n";
    }
    @Override
    public String toString() {
        if (this.color == Color.White) {
            return "♘";
        }
        return "♞";
    }
    @Override
    public ImageIcon getImageIcon(){
        if (this.color == Color.Black){
            return new ImageIcon(getClass().getResource("../PiecePictures/BKnight.png"));
        }
        else{
            return new ImageIcon(getClass().getResource("../PiecePictures/WKnight.png"));
        }
    }
    @Override
    public int getScore(){
        return 3;
    }
}
