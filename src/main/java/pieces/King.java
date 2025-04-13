package pieces;

import game.Table;

import javax.swing.*;
import java.util.Objects;

public class King extends Piece{

    public King(int xpos, int ypos, Color color) {
        super(xpos, ypos, color);
    }
    @Override
    public boolean vaildMove(Table table,int x, int y) {

        if (table.getpiece(x,y)== null&&table.getState()== Table.State.NORMAL){
            if (!table.rkmoves[color.ordinal()][1]&&y!=7&&table.getpiece(x,y+1)instanceof Rook && table.getpiece(x,y+1).color == super.color && (y>super.ypos&&table.getpiece(x,ypos+1)==null&&table.getpiece(x,ypos+2)==null&&table.hitting(x,ypos+1,super.color)==null&&table.hitting(x,ypos+2,super.color)==null&&table.getpiece(x,ypos+3)instanceof Rook && table.getpiece(x,ypos+3).color == super.color)){
                    return true;
            }
            if(!table.rkmoves[color.ordinal()][0]&&y>1&&table.getpiece(x,y-2)instanceof Rook && table.getpiece(x,y-2).color == super.color && (y < super.ypos && table.getpiece(x, ypos - 1) == null && table.getpiece(x, ypos - 2) == null && table.getpiece(x, ypos - 3) == null && table.hitting(x, ypos - 1, super.color) == null && table.hitting(x, ypos - 2, super.color) == null && table.getpiece(x, ypos - 4) instanceof Rook && table.getpiece(x, ypos - 4).color == super.color)) {
                    return true;
            }
        }
        if (table.getpiece(x,y)!=null && table.getpiece(x,y).color == super.color){
            return false;
        }
        return (Math.abs(x - xpos) == 1 && Math.abs(y - ypos) == 0) ||
                (Math.abs(x - xpos) == 0 && Math.abs(y - ypos) == 1) ||
                (Math.abs(x - xpos) == 1 && Math.abs(y - ypos) == 1);
    }
    @Override
    public String getCharacter(){
        return "k";
    }
    @Override
    public String toString() {
        if (this.color == Color.White) {
            return "♔";
        }
        return "♚";
    }
    @Override
    public ImageIcon getImageIcon(){
        if (this.color == Color.Black){
            return new ImageIcon(Objects.requireNonNull(getClass().getResource("/PiecePictures/BKing.png")));
        }
        else{
            return new ImageIcon(Objects.requireNonNull(getClass().getResource("/PiecePictures/WKing.png")));
        }
    }
    @Override
    public int getScore(){
        return 1000;
    }
}
