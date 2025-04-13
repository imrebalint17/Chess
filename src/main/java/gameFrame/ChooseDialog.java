package gameFrame;
import game.Table;
import pieces.*;
import javax.swing.*;
import java.awt.*;

/** A bábu választó ablak osztálya.*/
public class ChooseDialog extends JDialog {

    JPanel panel = new JPanel();
    /** A bábu választó ablak konstruktora.
     * @param finalstate A cserélendő paraszt pozíciója.
     * @param table A tábla, amelyen a bábu áll.
     * @param frame A játék ablaka.
     */
    public ChooseDialog(int[][] finalstate, Table table,GameFrame frame) {
        init(finalstate,table,frame);
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        setVisible(true);
    }
    /** A bábu választó ablak inicializálása és ActionListener beállítása is.
     * @param finalstate A cserélendő paraszt pozíciója.
     * @param table A tábla, amelyen a bábu áll.
     * @param frame A játék ablaka.
     */
    private void init(int[][] finalstate,Table table,GameFrame frame){
        panel.setLayout(new GridLayout(1,4));
        for (int i = 0; i < 4; i++) {
            Piece tmpp;
            JButton tmpb = new JButton();
            switch (i){
                case 0:
                    tmpp = new Queen(finalstate[1][0],finalstate[1][1],table.getpiece(finalstate[1][0],finalstate[1][1]).getColor());
                    break;
                case 1:
                    tmpp = new Rook(finalstate[1][0],finalstate[1][1],table.getpiece(finalstate[1][0],finalstate[1][1]).getColor());
                    break;
                case 2:
                    tmpp = new Bishop(finalstate[1][0],finalstate[1][1],table.getpiece(finalstate[1][0],finalstate[1][1]).getColor());
                    break;
                default:
                    tmpp = new Knight(finalstate[1][0],finalstate[1][1],table.getpiece(finalstate[1][0],finalstate[1][1]).getColor());
                    break;
            }
            tmpb.setIcon(new ImageIcon(tmpp.getImageIcon().getImage().getScaledInstance(100,100,Image.SCALE_SMOOTH)));
            tmpb.setBorderPainted(false);
            tmpb.setOpaque(true);
            tmpb.setBackground(Color.WHITE);
            tmpb.addActionListener(e -> {
                table.setPiece(finalstate[1][0],finalstate[1][1],finalstate[1][0],finalstate[1][1],tmpp);
                if (frame.enginestate == GameFrame.EngineState.NO_ENGINE){
                    table.setpState(Table.PState.NORMAL);
                }
                frame.refresh(table);
                dispose();
            });
            panel.add(tmpb);
        }
        setSize(400,120);
        setMinimumSize(new Dimension(400,120));
        setMaximumSize(new Dimension(400,120));
        panel.setVisible(true);
        add(panel);
    }
}
