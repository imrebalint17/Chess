package gameFrame;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

/** A ranglista ablak osztálya.*/
public class ScoreBoardFrame extends JFrame {
    /** A táblázat, amelybe a ranglista kerül.*/
    JTable table = new JTable();
    /** A ranglista ablak konstruktora.*/
    public ScoreBoardFrame(){
        init();
    }
    /** Az ablak inicializálása.*/
    protected void init() {
        setSize(400, 400);
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setVisible(true);
        JScrollPane scrollPane = new JScrollPane(table);
        DefaultTableModel model = new DefaultTableModel();
        table.setModel(model);
        model.setColumnIdentifiers(new Object[]{"Nyertes","Nyertes Pontszáma","Vesztes","Vesztes Pontszáma"});
        this.add(scrollPane);
        load(model);

    }
    /** A ranglista betöltése.
     * @param model A táblázat modellje, amibe a sorokat kell betölteni.
     * */
    protected void load(DefaultTableModel model){
        File file = new File("src/ScoreBoard.txt");
        try {
            if (file.exists()) {
                BufferedReader br = new BufferedReader(new FileReader(file));
                String line = br.readLine();
                while (line != null) {
                    model.addRow(line.split(";"));
                    line = br.readLine();
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }

    }
}
