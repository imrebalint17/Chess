package gameFrame;
import engine.EngineInterface;
import engine.LocalEngineInterface;
import engine.MultiplayerInterface;
import engine.OnlineEngineInterface;
import game.Table;
import pieces.*;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

/** A játék ablak osztálya.*/
public class GameFrame extends JFrame {
    /** A tábla gombjainak listája.*/
    private final List<ChessButton> chessButtonList = new ArrayList<>();
    /** A sakktábla panelja.*/
    private final JPanel chessPanel = new JPanel();
    /** A kattintás állapota.*/
    protected enum ClickState{FIRST_CLICK, SECOND_CLICK}
    /** A kattintás állapota.*/
    protected ClickState clickState;
    /** A cserélendő bábu kezdő és végpozíciója.*/
    private final int[][] finalstate = new int[2][2];
    /** A tábla.*/
    private Table table1;

    private  File engineLocation;

    protected enum EngineState{NO_ENGINE,WHITE,BLACK}
    protected EngineState enginestate;
    private EngineInterface engineInterface;

    /** A játék ablak konstruktora.
     * @param table A tábla, amelyen a játék van.
     */
    public GameFrame(Table table) throws HeadlessException {
        init(table);
    }
    /** A játék ablak inicializálása.
     * @param table A tábla, amelyen a játék van.
     */
    private void init(Table table) throws HeadlessException{
        setSize(new Dimension(800,800));
        setMaximumSize(new Dimension(800,800));
        setMinimumSize(new Dimension(800,800));
        jmenuInit();
        engineLocation = null;
        enginestate = EngineState.NO_ENGINE;
        chessPanel.setVisible(true);
        this.add(chessPanel);
        table1 = table;
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        clickState = ClickState.FIRST_CLICK;
        newtable(enginestate,0);
    }
    /** A végeredmény mentése fájlba.*/
    public void saveToFile(){
        int dialogButton = JOptionPane.YES_NO_OPTION;
        int dialogResult = JOptionPane.showConfirmDialog (null, "Szeretnéd menteni a végeredményt?","Mentés",dialogButton);
        if (dialogResult == JOptionPane.YES_OPTION){
            JPanel savePanel = new JPanel();
            JTextField white = new JTextField(10);
            JTextField black = new JTextField(10);
            savePanel.add(new JLabel("Világos játékos neve:"));
            savePanel.add(white);
            savePanel.add(new JLabel("Sötét játékos neve:"));
            savePanel.add(black);
            int result = JOptionPane.showConfirmDialog(null, savePanel, "Adja meg a neveket", JOptionPane.OK_CANCEL_OPTION);
            if (result == JOptionPane.OK_OPTION) {
                try {
                    boolean exists = new File("src/ScoreBoard.txt").exists();
                    FileOutputStream fos = new FileOutputStream("src/ScoreBoard.txt",true);
                    BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fos));
                    if (exists){
                        bw.newLine();
                    }
                    String blacktext = black.getText().replace(";",";");
                    String whitetext = white.getText().replace(";",";");
                    if ((table1.getRound()%2==0&&table1.getState()!= Table.State.CHECKMATE)||(table1.getState()== Table.State.CHECKMATE &&table1.getRound()%2==0)){
                        bw.write(blacktext+";"+table1.getWhiteScore()+";"+whitetext+";"+table1.getBlackScore());
                    }
                    else {
                        bw.write(whitetext+";"+table1.getWhiteScore()+";"+blacktext+";"+table1.getBlackScore());
                    }
                    bw.close();
                    fos.close();
                }catch (Exception b){
                    b.printStackTrace();
                }
            }
        }else {
            JOptionPane.showMessageDialog(null, "Világos pont:" + table1.getWhiteScore() + "\t Sötét pont:" + table1.getBlackScore());
        }
    }
    /** A menü inicializálása.*/
    private void jmenuInit(){
        JMenuBar menuBar = new JMenuBar();
        JMenu menu = new JMenu("Menu");
        JMenu settings = new JMenu("Settings");
        JMenu engineMenu = new JMenu("engine");
        JMenu onlineMenu = new JMenu("Online");
        JMenuItem login = new JMenuItem("Login");
        JMenuItem menuFeladas = new JMenuItem("Feladom");
        JMenuItem menuMentes = new JMenuItem("Mentés");
        JMenuItem menuBetoltes = new JMenuItem("Betöltés");
        JMenuItem menuRanglista = new JMenuItem("Ranglista");
        JMenuItem menuUjJatek = new JMenuItem("Új játék");
        JMenuItem engineEngine = new JMenuItem("Engine betöltés");
        JMenuItem engineSettings = new JMenuItem("Engine beállítás");
        JMenuItem engineOnline = new JMenuItem("Online beállítás");
        menuFeladas.addActionListener(new ForfeitItemListener());
        menuMentes.addActionListener(new SaveItemListener());
        menuBetoltes.addActionListener(new LoadItemListener());
        menuRanglista.addActionListener(new ScoreboardItemListener());
        menuUjJatek.addActionListener(new NewGameItemListener());
        engineEngine.addActionListener(new EngineItemListener());
        engineSettings.addActionListener(new SettingsItemListener());
        login.addActionListener(e -> new LoginFrame());
        menuBar.add(menu);
        menuBar.add(settings);
        menuBar.add(login);
        menu.add(menuMentes);
        menu.add(menuBetoltes);
        menu.add(menuRanglista);
        menu.add(menuFeladas);
        menu.add(menuUjJatek);
        settings.add(engineMenu);
        settings.add(onlineMenu);
        engineMenu.add(engineEngine);
        engineMenu.add(engineSettings);
        onlineMenu.add(engineOnline);
        this.add(menuBar,BorderLayout.NORTH);
    }
    /** A tábla grafikus frissítése, a gombokkal együtt.
     * @param table A tábla, amelyen a játék van.
     */
    public void refresh(Table table){
        table1 = table;
        for (ChessButton element:chessButtonList) {
            element.refresh(table1);
            SwingUtilities.updateComponentTreeUI(chessPanel);
        }
    }
    /** Az új tábla létrehozása.*/
    public void newtable(EngineState engineState,int online){
        table1 = new Table();
        chessPanel.removeAll();
        chessPanel.setLayout(new GridLayout(8,8));
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                ChessButton tmp = new ChessButton(i,j,table1);
                tmp.refresh(table1);
                tmp.addActionListener(new BoardButtonListener(this));
                chessPanel.add(tmp);
                chessButtonList.add(tmp);
            }
        }
        SwingUtilities.updateComponentTreeUI(this);
        if (engineState==EngineState.WHITE){
            enginestate = engineState;
            if (online==1){
                engineInterface = new OnlineEngineInterface("127.0.0.1",8080,true);
            }
            else if (online==2){
                engineInterface = new MultiplayerInterface("127.0.0.1",30000,true);
            }
            else {
                engineInterface = new LocalEngineInterface(engineLocation, true);
            }
            table1.round(engineInterface.newRound(null,null).getMove());
            refresh(table1);
        }
        else if(engineState == EngineState.BLACK){
            enginestate = engineState;

            if(online==1){
                engineInterface = new OnlineEngineInterface("127.0.0.1",8080,false);

            }else if (online==2){
                engineInterface = new MultiplayerInterface("127.0.0.1",30000,false);
            }
            else{
                engineInterface = new LocalEngineInterface(engineLocation,false);
            }
        }
        else {
            enginestate = EngineState.NO_ENGINE;
            if (engineInterface!=null) {
                engineInterface.close();
                engineInterface = null;
            }
        }
    }

    private class SettingsItemListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e){
            new SettingsDialog();
        }
    }

    /** A feladás menüpont ActionListener-je.*/
    private class ForfeitItemListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            if (table1.getRound()==0){
                JOptionPane.showMessageDialog(null, "Ne add fel az első körben");
                return;
            }
            int dialogButton = JOptionPane.YES_NO_OPTION;
            int dialogResult = JOptionPane.showConfirmDialog (null, "Biztosan feladod?","Feladás",dialogButton);
            if(dialogResult == JOptionPane.YES_OPTION){
                saveToFile();
                //TODO: Nem tudom, hogy ugyan ugy onlineal induljon-e
                newtable(enginestate,0);
            }
        }
    }
    /** A ranglista menüpont ActionListener-je.*/
    private class ScoreboardItemListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            new ScoreBoardFrame();
        }
    }
    /** A mentés menüpont ActionListener-je.*/
    private class SaveItemListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() instanceof JMenuItem){
                JFileChooser fileChooser = new JFileChooser();
                FileNameExtensionFilter filter = new FileNameExtensionFilter("Chess files", "chess");
                fileChooser.setFileFilter(filter);
                fileChooser.setDialogTitle("Mentés");
                int userSelection = fileChooser.showSaveDialog(null);
                if (userSelection == JFileChooser.APPROVE_OPTION) {
                    try {
                        FileOutputStream fos = new FileOutputStream(fileChooser.getSelectedFile().getAbsolutePath().endsWith(".chess")?fileChooser.getSelectedFile().getAbsolutePath():fileChooser.getSelectedFile().getAbsolutePath()+".chess");
                        ObjectOutputStream oos = new ObjectOutputStream(fos);
                        oos.writeObject(table1);
                        oos.close();
                        fos.close();
                    }catch (Exception b){
                        b.printStackTrace();
                    }
                }
            }
        }
    }
    /** Az engine menüpont ActionListener-je.*/
    private class EngineItemListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() instanceof JMenuItem){
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setCurrentDirectory(new File("/opt/homebrew/Cellar/stockfish/16/bin/"));
                fileChooser.setDialogTitle("Válaszd ki az engine-t");
                int userSelection = fileChooser.showOpenDialog(null);
                if (userSelection == JFileChooser.APPROVE_OPTION) {
                    try {
                        engineLocation = fileChooser.getSelectedFile();
                    }catch (Exception b){
                        b.printStackTrace();
                    }
                }
            }
        }
    }
    /** A betöltés menüpont ActionListener-je.*/
    private class LoadItemListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                FileNameExtensionFilter filter = new FileNameExtensionFilter("Chess files", "chess");
                fileChooser.setFileFilter(filter);
                fileChooser.setDialogTitle("Betöltés");
                int userSelection = fileChooser.showOpenDialog(null);
                if (userSelection == JFileChooser.APPROVE_OPTION) {
                    try {
                        FileInputStream fis = new FileInputStream(fileChooser.getSelectedFile());
                        ObjectInputStream ois = new ObjectInputStream(fis);
                        Table tbtemp = (Table)ois.readObject();
                        table1 = tbtemp;
                        ois.close();
                        fis.close();
                        refresh(table1);
                    }catch (Exception b){
                        b.printStackTrace();
                    }
                }
        }
    }
    /** Az új játék menüpont ActionListener-je.*/
    private class NewGameItemListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e)
        {
            new NewGameDialog(GameFrame.this);
            ///TODO egymas ellen jatszanak a botok :D
        }
    }
    /** A tábla gombjainak ActionListener-je.*/
    private class BoardButtonListener implements ActionListener {
        GameFrame frame;
        public BoardButtonListener(GameFrame frame) {
            this.frame = frame;
        }
        @Override
        public void actionPerformed(ActionEvent e) {
            if (enginestate == EngineState.NO_ENGINE || enginestate.ordinal() - 1 != table1.getRound() % 2) {
                if (e.getSource() instanceof ChessButton) {
                    if (clickState == ClickState.FIRST_CLICK && table1.getpState() == Table.PState.NORMAL) {
                        firstClickEvent(e);
                    } else if (clickState == ClickState.SECOND_CLICK) {
                        secondClickEvent(e);
                        if (enginestate != EngineState.NO_ENGINE && table1.getRound() % 2 == enginestate.ordinal() -1){
                            engineRound();
                            refresh(table1);
                        }
                    }
                }
            }
        }
        private void engineRound (){
            if (table1.getpState() == Table.PState.CHANGE){
                InterfaceMoveWrapper tmp = engineInterface.newRound(finalstate,table1.getpiece(finalstate[1][0],finalstate[1][1]));
                engineRoundPromotion(tmp);
                table1.setpState(Table.PState.NORMAL);
            }
            else {
                InterfaceMoveWrapper tmp = engineInterface.newRound(finalstate,null);
                engineRoundPromotion(tmp);
            }
        }
        private void engineRoundPromotion(InterfaceMoveWrapper tmp){
            if (tmp.getPromote()==0){
                table1.round(tmp.getMove());
            }
            else {
                table1.round(tmp.getMove());
                switch (tmp.getPromote()){
                    case 'q':
                        table1.setPiece(finalstate[1][0],finalstate[1][1],finalstate[1][0],finalstate[1][1],new Queen(finalstate[1][0],finalstate[1][1],table1.getpiece(finalstate[1][0],finalstate[1][1]).getColor()));
                        break;
                    case 'r':
                        table1.setPiece(finalstate[1][0],finalstate[1][1],finalstate[1][0],finalstate[1][1],new Rook(finalstate[1][0],finalstate[1][1],table1.getpiece(finalstate[1][0],finalstate[1][1]).getColor()));
                        break;
                    case 'b':
                        table1.setPiece(finalstate[1][0],finalstate[1][1],finalstate[1][0],finalstate[1][1],new Bishop(finalstate[1][0],finalstate[1][1],table1.getpiece(finalstate[1][0],finalstate[1][1]).getColor()));
                        break;
                    case 'n':
                        table1.setPiece(finalstate[1][0],finalstate[1][1],finalstate[1][0],finalstate[1][1],new Knight(finalstate[1][0],finalstate[1][1],table1.getpiece(finalstate[1][0],finalstate[1][1]).getColor()));
                        break;
                }
            }
        }
        private void firstClickEvent(ActionEvent e){
            int[] tmpstate = ((ChessButton)e.getSource()).getPos();
            List<int[]> avaliabletiles = table1.availableMoves(tmpstate);
            if (avaliabletiles!=null) {
                for (ChessButton element : chessButtonList) {
                    for (int[] apos : avaliabletiles) {
                        if (element.getPos()[0] == apos[0] && element.getPos()[1] == apos[1]) {
                            if ((apos[0]+apos[1])%2==0) {
                                element.setBackground(new Color(144, 238, 144));
                            }
                            else {
                                element.setBackground(new Color(83, 196, 111));
                            }
                        }
                    }
                }
            }
            finalstate[0] = tmpstate;
            clickState = ClickState.SECOND_CLICK;
        }
        private void secondClickEvent(ActionEvent e){
            int[] tmpstate = ((ChessButton)e.getSource()).getPos();
            finalstate[1] = tmpstate;
            if (table1.getState()!= Table.State.CHECKMATE) {
                table1.round(finalstate);
                refresh(table1);
            }
            if (table1.getState()== Table.State.CHECKMATE) {
                refresh(table1);
                JOptionPane.showMessageDialog(null, "Sakk Matt");
                saveToFile();
                //TODO: Nem tudom, hogy ugyan ugy onlineal induljon-e
                newtable(enginestate,0);
            }
            if (table1.getpState() == Table.PState.CHANGE){
                new ChooseDialog(finalstate,table1,frame);
            }
            clickState = ClickState.FIRST_CLICK;
        }
    }

}
