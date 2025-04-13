package pieces;

import game.Table;

import javax.swing.*;
import java.util.Objects;

public class Bishop extends Piece{

    public Bishop(int xpos, int ypos, Color color) {
        super(xpos, ypos, color);
    }
    @Override
    public boolean vaildMove(Table table,int x, int y) {
        if (table.getpiece(x,y)!=null && table.getpiece(x,y).color == super.color){
            return false;
        }
        if (Math.abs(x-xpos)==Math.abs(y-ypos)){
            if (x<xpos){
                if (y<ypos){
                    for (int i = 1; i < xpos-x;i++){
                        if (table.getpiece(xpos-i,ypos-i)!= null){
                            return false;
                        }
                    }
                }
                else {
                    for (int i = 1; i < xpos-x;i++){
                        if (table.getpiece(xpos-i,ypos+i)!= null){
                            return false;
                        }
                    }
                }
            }
            else {
                if (y<ypos){
                    for (int i = 1; i < x-xpos;i++){
                        if (table.getpiece(xpos+i,ypos-i)!= null){
                            return false;
                        }
                    }
                }
                else {
                    for (int i = 1; i < x-xpos;i++){
                        if (table.getpiece(xpos+i,ypos+i)!= null){
                            return false;
                        }
                    }
                }
            }
            return true;
        }
        return false;
    }
    @Override
    public String getCharacter(){
        return "b";
    }
    @Override
    public String toString() {
        if (this.color == Color.White) {
            return "♗";
        }
        return "♝";
    }
    @Override
    public ImageIcon getImageIcon() {
        if (this.color == Color.Black) {
            return new ImageIcon(Objects.requireNonNull(getClass().getResource("/PiecePictures/BBishop.png")));
        } else {
            return new ImageIcon(Objects.requireNonNull(getClass().getResource("/PiecePictures/WBishop.png")));
        }
    }
    @Override
    public int getScore() {
        return 3;
    }
}
