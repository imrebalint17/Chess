package pieces;

import game.Table;

import javax.swing.*;
import java.io.Serializable;

/** A bábu osztálya, amely a többi bábu ősosztálya.*/
public abstract class Piece implements Cloneable, Serializable {
    /** A clónozó metódus, amely egy új példányt hoz létre a Piece osztályból.
     * @return A klónozott példány.
     */
    public Piece clone(){
        try {
            Piece p = (Piece) super.clone();
            p.setXpos(this.getXpos());
            p.setYpos(this.getYpos());
            p.setColor(this.getColor());
            return (Piece) super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return null;
    }
    /** A bábu lehetséges színei.*/
    public enum Color{White,Black}
    /** A bábu x és y pozíciója.*/
    protected int xpos,ypos;
    /** A bábu színe.*/
    protected Color color;
    /** A bábu konstruktora.
     * @param xpos A bábu x pozíciója.
     * @param ypos A bábu y pozíciója.
     * @param color A bábu színe.
     */
    public Piece(int xpos, int ypos, Color color) {
        this.color = color;
        this.xpos = xpos;
        this.ypos = ypos;
    }
    /** A bábu lehetséges lépéseinek ellenőrzése.
     * @param table A tábla, amelyen a bábu áll.
     * @param x A bábu x pozíciója.
     * @param y A bábu y pozíciója.
     * @return Igaz, ha a lépés lehetséges, hamis, ha nem.
     */
    public abstract boolean vaildMove(Table table, int x, int y);
    /** A bábu szöveges reprezentációja.
     * @return A bábu szöveges karakterét.
     */
    public abstract String toString();
    /** A bábu x pozíciójának beállítása.
     * @param xpos A bábu új x pozíciója.
     */
    public void setXpos(int xpos) {
        this.xpos = xpos;
    }
    /** A bábu y pozíciójának beállítása.
     * @param ypos A bábu új y pozíciója.
     */
    public void setYpos(int ypos) {
        this.ypos = ypos;
    }
    /** A bábu x pozíciójának lekérdezése.
     * @return A bábu x pozíciója.
     */
    public int getXpos(){
        return xpos;
    }
    /** A bábu y pozíciójának lekérdezése.
     * @return A bábu y pozíciója.
     */
    public int getYpos(){
        return ypos;
    }
    /** A bábu színének lekérdezése.
     * @return A bábu színe.
     */
    public Color getColor() {
        return color;
    }
    /** A bábu képének lekérdezése.
     * @return A bábu képe.
     */
    public ImageIcon getImageIcon(){
        return null;
    }
    /** A bábu színének beállítása.
     * @param color A bábu új színe.
     */
    public void setColor(Color color) {
        this.color = color;
    }
    /** A bábu pontszámának lekérdezése.
     * @return A bábu pontszáma.
     */
    public int getScore(){
        return 0;
    }

    public String getCharacter(){
        return "";
    }
}
