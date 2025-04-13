package pieces;

import game.Table;

import javax.swing.*;
import java.util.Objects;

public class Pawn extends Piece{
    public Pawn(int xpos, int ypos, Piece.Color color) {super(xpos, ypos, color);}
    @Override
    public boolean vaildMove(Table table, int x, int y) {
        if (table.getpiece(x,y)!=null && table.getpiece(x,y).color == super.color){
            return false;
        }
        if (super.color==Color.Black){
            if(x == xpos +1 && y == ypos && table.getpiece(x, y) == null){
                return true;
            } else if (xpos == 1 && ypos==y&& x ==3 && table.getpiece(x, y) == null && table.getpiece(x-1,y) == null) {
                return true;
            } else if (table.getPasant()!=null&&Math.abs(table.getPasant().ypos-ypos)==1&&table.getPasant().xpos==xpos&&table.getpiece(xpos+1,table.getPasant().ypos)==null&&table.getPasant().getColor()!=super.color&&x==xpos+1&&y==table.getPasant().ypos) {
                return true;
            } else return (Math.abs(y - ypos)) == 1 && x == xpos + 1 && table.getpiece(x, y) != null;
        }
        else {
            if(x == xpos -1 && y == ypos && table.getpiece(x, y) == null){
                return true;
            }else if (xpos==6 && ypos==y&& x ==4 && table.getpiece(x, y) == null && table.getpiece(x+1, y) == null) {
                return true;
            }else if (table.getPasant()!=null&&Math.abs(table.getPasant().ypos-ypos)==1&&table.getPasant().xpos==xpos&&table.getpiece(xpos-1,table.getPasant().ypos)==null&&table.getPasant().getColor()!=super.color&&x==xpos-1&&y==table.getPasant().ypos) {
                return true;
            } else return (Math.abs(y - ypos)) == 1 && x == xpos - 1 && table.getpiece(x, y) != null;
        }
    }
    @Override
    public String getCharacter(){
        return "p";
    }
    @Override
    public String toString() {
        if (this.color == Color.White) {
            return "♙";
        }
        return "♟";
    }
    @Override
    public ImageIcon getImageIcon(){
        if (this.color == Color.Black){
            return new ImageIcon(Objects.requireNonNull(getClass().getResource("/PiecePictures/BPawn.png")));
        }
        else{
            return new ImageIcon(Objects.requireNonNull(getClass().getResource("/PiecePictures/WPawn.png")));
        }
    }
    @Override
    public int getScore(){
        return 1;
    }

}
