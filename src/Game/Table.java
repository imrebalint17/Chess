package Game;
import Pieces.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * A Tábla osztálya.
 * Felelős a játék logikájáért
 */

public class Table implements Serializable{
    /** Maga a tábla Listából, mérete 8x8 */
    private List<List<Piece>> table = new ArrayList<>(8);
    /** A játék körének száma */
    int round = 0;
    /** A sötét játékos pontszáma
     * Csak a játék végén van jelentősége*/
    int blackscore = 0;
    /** A világos játékos pontszáma
     * Csak a játék végén van jelentősége*/
    int whitescore = 0;
    /** Az en passant bábu, mindíg csak az aktuális körben lévő*/
    private Pawn pasant = null;
    /** A játék állapota */
    public enum State{NORMAL, CHECK, CHECKMATE}
    /** A paraszt állapota */
    public enum PState{NORMAL,CHANGE}
    protected State state = State.NORMAL;
    protected PState pState = PState.NORMAL;
    /** A király és a bal és jobb bástya mozgását jelző boolean tömb */
    public boolean[][] rkmoves = {{false,false},{false,false}};

    /** A tábla konstruktora
     * Létrehozza a táblát
     * */
    public Table(){
        initTable();
    }

    /** A tábla klónozó metódusa
     * @param table A tábla amit klónozni akarunk
     * @return A klónozott tábla
     * */
    protected Table tableClone(Table table){
        Table t = new Table();
        t.round = table.round;
        t.state = table.state;
        t.blackscore = table.blackscore;
        t.whitescore = table.whitescore;
        t.pState = table.pState;
        for (int i = 0; i < 8 ; i++) {
            for (int j = 0; j < 8; j++) {
                if (table.table.get(i).get(j)!=null) {
                    Piece p = table.table.get(i).get(j).clone();
                    t.table.get(i).set(j, p);
                }
                else {
                    t.table.get(i).set(j,null);
                }
            }
        }
        return t;
    }
    /** A sakk ellenőrző metódus
     * @param color A játékos színe, akire ellenőrizzük a sakk meglétét
     * @param first Megadja, hogy ez az első ellenőrzés-e a körön belül, ezzel elkerülve a rekurziót
     *              Ha igen, akkor a checkMateCheck metódust is meghívja.
     * */
    private void checkCheck(Piece.Color color,boolean first){
        for (int i = 0; i < 8; i++){
            for (int j = 0; j < 8; j++){
                Piece piece = table.get(i).get(j);
                if (piece instanceof King && piece.getColor() == color && hitting(i,j,color) != null){
                    state = State.CHECK;
                    if (first) {
                        checkMateCheck(i, j, piece.getColor(), hitting(i, j, color));
                    }
                }
            }
        }
    }
    /** A sakk matt ellenőrző metódus
     * @param kposx A király x koordinátája
     * @param kposy A király y koordinátája
     * @param color A játékos színe, akire ellenőrizzük a sakk matt meglétét
     * @param p A bábu, ami a királyt ütötte
     * */
    private void checkMateCheck(int kposx, int kposy, Piece.Color color, Piece p){
        if (canMoveToSafePosition(kposx, kposy, color) || canIntercept(p)) {
            return;
        }
        this.state = State.CHECKMATE;
    }
    /** A király ellépésére szolgáló metódus
     * @param kposx A király x koordinátája
     * @param kposy A király y koordinátája
     * @param color A játékos színe, akire ellenőrizzük a sakk matt meglétét
     * @return True, ha a király tud lépni egy biztonságos pozícióba
     * */
    private boolean canMoveToSafePosition(int kposx, int kposy, Piece.Color color) {
        for (int i = kposx-1; i <= kposx+1; i++){
            for (int j = kposy-1; j <= kposy+1; j++){
                if (i >= 0 && j >= 0 && i <= 7 && j <= 7){
                    if (table.get(kposx).get(kposy).vaildMove(this,i,j) && hitting(i,j,color) == null){
                        return true;
                    }
                }
            }
        }
        return false;
    }
    /** A metódus ellenőrzi, hogy meg lehet-e szakítani a sakkot egy másik bábuval
     * @param p A bábu, ami a királyt üti
     * @return True, ha a bábut meg lehet akadályozni a király ütésében
     * */
    private boolean canIntercept(Piece p) {
        for (int i = 0; i < 8; i++){
            for (int j = 0; j < 8; j++){
                if (canCapture(i,j,p)||canBlock(i,j,p)){
                    return true;
                }
            }
        }
        return false;
    }

    /** A metódus ellenőrzi, hogy le lehet-e ütni a támadó bábut a védővel
     * @param i A bábu x koordinátája
     * @param j A bábu y koordinátája
     * @param p A bábu, ami a királyt üti
     * @return True, ha a bábut le lehet ütni
     */
    private boolean canCapture(int i, int j, Piece p){
        Piece piece = table.get(i).get(j);
        if (piece != null && piece.getColor() != p.getColor()){
            if (piece.vaildMove(this,p.getXpos(), p.getYpos())){
                Table t = tableClone(this);
                t.setState(State.NORMAL);
                t.setPiece(i, j, p.getXpos(), p.getYpos(), t.table.get(i).get(j));
                t.checkCheck(piece.getColor(),false);
                if (t.getState() == State.NORMAL /* t.state == State.Check*/) {
                    return true;
                }
            }
        }
        return false;
    }

    /** A metódus ellenőrzi, hogy be lehet-e lépni a támadó bábu és a király közé a védővel
     * @param i A bábu x koordinátája
     * @param j A bábu y koordinátája
     * @param p A bábu, ami a királyt üti
     * @return True, ha a védő bábu be tud lépni
     */
    private boolean canBlock(int i, int j, Piece p){
        Piece piece = table.get(i).get(j);
        if (piece != null && piece.getColor() != p.getColor()) {
            for (int[] element : availableMoves(new int[]{i, j})) {
                Table t = tableClone(this);
                t.setState(State.NORMAL);
                t.setPiece(i, j, element[0], element[1], t.table.get(i).get(j));
                t.checkCheck(piece.getColor(),false);
                if (t.getState() == State.NORMAL) {
                    return true;
                }
            }
        }
        return false;
    }

    /** A metódus visszaadja azt a bábut, amely a paraméterként megadott bábut üti
     * @param kposx A bábu x koordinátája
     * @param kposy A bábu y koordinátája
     * @param color A játékos színe, akire ellenőrizzük a sakk meglétét
     * @return A bábu, ami a paraméterként megadott bábut üti
     */
    public Piece hitting(int kposx, int kposy, Piece.Color color){
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (table.get(i).get(j)!=null&&table.get(i).get(j).getColor()!=color&&table.get(i).get(j).vaildMove(this,kposx,kposy)){
                    table.get(i).get(j).setXpos(i);
                    table.get(i).get(j).setYpos(j);
                    return table.get(i).get(j);
                }
            }
        }
        return null;
    }
    /** A tábla inicializáló metódusa
     * Először feltölti null-okkal a listákat, majd a bábukat a megfelelő helyre teszi
     * */
    private void initTable(){
        for (int i = 0; i < 8;i++){
            table.add(new ArrayList<>());
            for (int j = 0; j < 8;j++){
                table.get(i).add(null);
            }
        }
        for (int i = 0; i < 8;i++){
            table.get(1).set(i,new Pawn(1,i, Piece.Color.Black));
            table.get(6).set(i,new Pawn(6,i, Piece.Color.White));
        }
        for (int i = 0; i <8;i++){
            for (int j=2;j<6;j++){
                table.get(j).set(i,null);
            }
        }
        table.get(0).set(0,new Rook(0,0, Piece.Color.Black));
        table.get(0).set(1,new Knight(0,1, Piece.Color.Black));
        table.get(0).set(2,new Bishop(0,2, Piece.Color.Black));
        table.get(0).set(3,new Queen(0,3, Piece.Color.Black));
        table.get(0).set(4,new King(0,4, Piece.Color.Black));
        table.get(0).set(5,new Bishop(0,5, Piece.Color.Black));
        table.get(0).set(6,new Knight(0,6, Piece.Color.Black));
        table.get(0).set(7,new Rook(0,7, Piece.Color.Black));
        table.get(7).set(0,new Rook(7,0, Piece.Color.White));
        table.get(7).set(1,new Knight(7,1, Piece.Color.White));
        table.get(7).set(2,new Bishop(7,2, Piece.Color.White));
        table.get(7).set(3,new Queen(7,3, Piece.Color.White));
        table.get(7).set(4,new King(7,4, Piece.Color.White));
        table.get(7).set(5,new Bishop(7,5, Piece.Color.White));
        table.get(7).set(6,new Knight(7,6, Piece.Color.White));
        table.get(7).set(7,new Rook(7,7, Piece.Color.White));

    }

    /** A fő függvény, ami a köröket kezeli a táblán
     * @param move A lépés, amit meg akarunk tenni, kezdő és végponttal
     */
    public void round(int[][] move){
        int tx = move[0][0];
        int ty = move[0][1];
        int dx = move[1][0];
        int dy = move[1][1];
        checkPassants();
        Piece tmp = table.get(tx).get(ty);
        if (tmp != null && tmp.vaildMove(this,dx,dy)&&tmp.getColor().ordinal()==round%2){
            Table t = tableClone(this);
            Piece tmpclone = t.table.get(tx).get(ty);
            t.setState(State.NORMAL);
            t.setPiece(tx, ty, dx, dy,tmpclone);
            t.checkCheck(tmpclone.getColor(),false);
            if (t.getState() == State.NORMAL){
                if (tmp instanceof Pawn&&Math.abs(tx-dx)==2){
                    pasant = (Pawn) tmp;
                }
            setPiece(tx,ty,dx,dy,tmp);
            round++;
            printTable();
            state = State.NORMAL;
            checkCheck(tmp.getColor()== Piece.Color.Black? Piece.Color.White: Piece.Color.Black,true);}
        }
        else {
            System.out.println("Invalid Move");
        }
    }
    /** Kitörli a pasant-ból a bábut ha lejárt a köre */
    public void checkPassants(){
        if (pasant!=null){
            if ((pasant.getColor()== Piece.Color.White && round%2==0)||(pasant.getColor()== Piece.Color.Black && round%2==1)){
                pasant = null;
            }
        }

    }
    /** A bábuk mozgatásáért felelős metódus
     * @param tx A bábu kezdő x koordinátája
     * @param ty A bábu kezdő y koordinátája
     * @param dx A bábu cél x koordinátája
     * @param dy A bábu cél y koordinátája
     * @param tmp A bábu, amit mozgatni akarunk
     */
    public void setPiece(int tx, int ty,int dx, int dy,Piece tmp){
        if (table.get(dx).get(dy) != null){
            if (table.get(dx).get(dy).getColor() == Piece.Color.Black){
                whitescore += table.get(dx).get(dy).getScore();
            }
            else {
                blackscore += table.get(dx).get(dy).getScore();
            }
        }
        if (tmp instanceof Pawn){
            if (tmp.getColor()== Piece.Color.Black&&dx==7){
                pState = PState.CHANGE;
            }
            if (tmp.getColor()== Piece.Color.White&&dx==0){
                pState = PState.CHANGE;
            }
            //En passant check
            if (table.get(dx).get(dy)==null && ty!=dy){
                table.get(tx).set(dy,null);
            }
        }
        if (tmp instanceof Rook){
            if (tx == 0 || tx==7){
                if (ty== 0){
                    rkmoves[tmp.getColor().ordinal()][0] = true;
                }
                if (ty==7){
                    rkmoves[tmp.getColor().ordinal()][1] = true;
                }
            }
        }
        if(tmp instanceof King){
            rkmoves[tmp.getColor().ordinal()][0] = rkmoves[tmp.getColor().ordinal()][1] = true;
        }
        /*if (tmp instanceof King && table.get(dx).get(dy) instanceof Rook && table.get(dx).get(dy).getColor() == tmp.getColor()){
            if (dy<ty){
                table.get(tx).set(ty,null);
                table.get(dx).set(2,tmp);
                tmp.setXpos(dx);
                tmp.setYpos(2);
                Piece rooktmp = table.get(dx).get(dy);
                table.get(dx).set(dy,null);
                table.get(dx).set(3,rooktmp);
                rooktmp.setXpos(dx);
                rooktmp.setYpos(3);
            }
            else {
                table.get(tx).set(ty,null);
                table.get(dx).set(6,tmp);
                tmp.setXpos(dx);
                tmp.setYpos(6);
                Piece rooktmp = table.get(dx).get(dy);
                table.get(dx).set(dy,null);
                table.get(dx).set(5,rooktmp);
                rooktmp.setXpos(dx);
                rooktmp.setYpos(5);
            }
            return;
        }
        table.get(tx).set(ty,null);
        table.get(dx).set(dy,tmp);
        if (tmp!=null) {
            tmp.setXpos(dx);
            tmp.setYpos(dy);
        }*/
        if (tmp instanceof King && table.get(dx).get(dy) == null && Math.abs(dy-ty)==2){
            if (dy<ty){
                table.get(tx).set(ty,null);
                table.get(dx).set(2,tmp);
                tmp.setXpos(dx);
                tmp.setYpos(2);
                Piece rooktmp = table.get(dx).get(dy-2);
                table.get(dx).set(dy-2,null);
                table.get(dx).set(3,rooktmp);
                rooktmp.setXpos(dx);
                rooktmp.setYpos(3);
            }
            else {
                table.get(tx).set(ty,null);
                table.get(dx).set(6,tmp);
                tmp.setXpos(dx);
                tmp.setYpos(6);
                Piece rooktmp = table.get(dx).get(dy+1);
                table.get(dx).set(dy+1,null);
                table.get(dx).set(5,rooktmp);
                rooktmp.setXpos(dx);
                rooktmp.setYpos(5);
            }
            return;
        }
        table.get(tx).set(ty,null);
        table.get(dx).set(dy,tmp);
        if (tmp!=null) {
            tmp.setXpos(dx);
            tmp.setYpos(dy);
        }
    }
    /** A metódus, ami visszaadja az összes lehetséges lépést egy bábu számára
     * @param pos A bábu pozíciója
     * @return A lehetséges lépések listája
     */
    public List<int[]> availableMoves(int[] pos){
        Piece tmp = table.get(pos[0]).get(pos[1]);
        List<int[]> available = new ArrayList<>();
        if (tmp == null||tmp.getColor().ordinal()!=round%2){
            return null;
        }
        for (int i = 0; i < 8;i++){
            for (int j = 0; j < 8;j++){
                if (tmp.vaildMove(this,i,j)){
                    available.add(new int[]{i,j});
                }
            }
        }
        return available;
    }
    /** A metódus, ami kiírja a táblát a konzolra */
    private void printTable(){
        for (int i = 0; i < 8;i++){
            for (int j = 0; j < 8;j++){
                if (table.get(i).get(j) == null){
                    System.out.print("  ");
                }
                else {
                    System.out.print(table.get(i).get(j).toString());
                }
            }
            System.out.println();
        }
    }
    public int getBlackScore() {
        return blackscore;
    }

    public int getWhiteScore() {
        return whitescore;
    }

    public int getRound(){
        return round;
    }
    public State getState(){
        return state;
    }
    public void setState(State state) {
        this.state = state;
    }
    public PState getpState(){
        return pState;
    }
    public void setpState(PState pState) {
        this.pState = pState;
    }
    public Piece getpiece(int posx, int posy){
        return table.get(posx).get(posy);
    }
    public Pawn getPasant(){
        return pasant;
    }
}
