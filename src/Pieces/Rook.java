package Pieces;

import Game.Table;

import javax.swing.*;

public class Rook extends Piece{
    public Rook(int xpos, int ypos, Color color) {
        super(xpos, ypos, color);
    }
    @Override
    public boolean vaildMove(Table table,int x, int y) {
        if ((table.getpiece(x,y)!=null && table.getpiece(x,y).color == super.color)){
                return false;
        }
        if (super.xpos!=x&&super.ypos!=y){
            return false;
        }
        if (super.ypos==y){
            if (x<xpos){
                for (int i = x + 1; i < xpos;i++){
                    if (table.getpiece(i,y)!= null){
                        return false;
                    }
                }
            }
            else {
                for (int i = xpos + 1; i < x; i++) {
                    if (table.getpiece(i, y) != null) {
                        return false;
                    }
                }
            }
        }
        if (super.xpos == x){
            if(y<ypos){
                for (int i = y + 1; i < ypos;i++){
                    if (table.getpiece(x,i)!= null){
                        return false;
                    }
                }
            }
            else {
                for (int i = ypos + 1; i < y;i++){
                    if (table.getpiece(x,i)!= null){
                        return false;
                    }
                }
            }
        }
        return true;
    }
    @Override
    public String getCharacter(){
        return "r";
    }
    @Override
    public String toString() {
        if (this.color == Color.White) {
            return "♖";
        }
        return "♜";
    }
    @Override
    public ImageIcon getImageIcon(){
        if (this.color == Color.Black){
            return new ImageIcon(getClass().getResource("../PiecePictures/BRook.png"));
        }
        else{
            return new ImageIcon(getClass().getResource("../PiecePictures/WRook.png"));
        }
    }
    @Override
    public int getScore(){
        return 5;
    }
}
