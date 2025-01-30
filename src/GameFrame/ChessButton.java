package GameFrame;

import Game.Table;

import javax.swing.*;
import java.awt.*;
/** A sakktábla gombjainak osztálya.*/
public class ChessButton extends JButton{
    /** A gomb x koordinátája a táblán.*/
    private final int x;
    /** A gomb y koordinátája a táblán.*/
    private final int y;
    /** A gomb konstruktora.Ez is színezi be a gombokat.
     * @param x A gomb x koordinátája a táblán.
     * @param y A gomb y koordinátája a táblán.
     * @param table A tábla, amelyen a gomb áll.
     * */
    public ChessButton(int x, int y,Table table) {
        this.x = x;
        this.y = y;
        this.setBorderPainted(false);
        this.setOpaque(true);
        refresh(table);
    }
    /** A gomb frissítése, a gomb képeinek beállítása és a háttér színezése.
     * @param table A tábla, amelyen a gomb áll.
     * */
    public void refresh(Table table){
        if (table.getpiece(x,y)==null){
            this.setIcon(null);
        }
        else {
            this.setIcon(new ImageIcon(table.getpiece(x, y).getImageIcon().getImage().getScaledInstance(100,100,Image.SCALE_SMOOTH)));
        }
        if ((x+y)%2==0){
            this.setBackground(new Color(164,116,73));
        }
        else {
            this.setBackground(new Color(103, 74, 40));
        }
    }
    /** A gomb koordinátáinak lekérdezése.
     * @return A gomb x és y koordinátatömbje.
     * */
    public int[] getPos(){
        return new int[]{x,y};
    }

}

