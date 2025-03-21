package GameFrame;

import Pieces.Piece;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class LocalEngineInterface implements EngineInterface {
    private BufferedReader reader;
    private BufferedWriter writer;
    private List<String> prevMoves;

    private int threads = 2;
    private int hash = 1024;
    private int skill = 10;
    private int depth = 10;
    private Process process;

    public LocalEngineInterface(File engineLocation,boolean starts) {
        try {
            init(engineLocation,starts);
        }catch (IOException e){
            e.printStackTrace();
        }

    }
    private void init(File engineLocation,boolean starts) throws IOException {
        configRead();
        if (engineLocation==null){
            throw new IOException("Nem található engine!");
        }
        prevMoves = new ArrayList<>();
        process = Runtime.getRuntime().exec(engineLocation.getAbsolutePath());
        reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
        writer = new BufferedWriter(new OutputStreamWriter(process.getOutputStream()));
        writer.write("uci\n");
        String read = reader.readLine();
        writer.flush();
        while (!read.equals("uciok")){
            System.out.println(read);
            read = reader.readLine();
        }
        writer.write("setoption name Threads value "+threads+"\nsetoption name Hash value "+hash+"\nsetoption name Clear Hash\nsetoption name Skill Level value "+skill+"\n");
        writer.write("setoption name SyzygyPath value "+ new File(".").getAbsolutePath() + "\n");
        writer.write("ucinewgame\n");
        writer.write("isready\n");
        writer.flush();
        read = reader.readLine();
        while (!read.equals("readyok")){
            System.out.println(read);
            writer.write("isready\n");
            writer.flush();
            read = reader.readLine();
        }
        writer.flush();
        if (prevMoves!=null){
        prevMoves.clear();
        }
        if (starts){
            prevMoves.add("position startpos ");
        }else {
            prevMoves.add("position startpos moves ");
        }
    }
    private void configRead(){
        int[] tmp = new ConfigManager().read();
        hash = tmp[0];
        threads = tmp[1];
        skill = tmp[2];
        depth = tmp[3];

    }
    @Override
    public InterfaceMoveWrapper newRound(int[][] prevStep,Piece promotion){
        try {
            configRead();
            if (prevStep != null) {
                /*String[][] tmp = new String[2][2];
                tmp[0][0] = String.valueOf((char) (prevStep[0][1] + 'a'));
                tmp[0][1] = String.valueOf(Math.abs(prevStep[0][0]-8));
                tmp[1][0] = String.valueOf((char) (prevStep[1][1] + 'a'));
                tmp[1][1] = String.valueOf(Math.abs(prevStep[1][0] -8));
                String prev = tmp[0][0] + tmp[0][1] + tmp[1][0] + tmp[1][1];
                System.out.println(prev);
                if (promotion != null) {
                    prev += promotion.getCharacter();
                }
                prevMoves.add(prev + " ");*/
                prevMoves.add(convertStepstoUCI(prevStep,promotion) + " ");
            }
            for (String s : prevMoves) {
                System.out.print(s);
                writer.write(s);
            }
            System.out.print('\n');
            writer.write("\n");
            writer.write("go depth "+depth+"\n");
            writer.flush();
            String read = reader.readLine();
            while (!read.startsWith("bestmove")){
                System.out.println(read);
                read = reader.readLine();
            }
            System.out.println(read);
            if (prevStep == null){
                prevMoves.add("moves ");
            }
            String[] tmp = read.split(" ");
            prevMoves.add(tmp[1] +" ");
            /*String[] tmp2 = tmp[1].split("");
            int[][] ret = new int[2][2];
            ret[0][0] = Math.abs(Integer.parseInt(tmp2[1]) - 8);
            ret[0][1] = tmp2[0].charAt(0) - 'a';
            ret[1][0] = Math.abs(Integer.parseInt(tmp2[3]) - 8);
            ret[1][1] = tmp2[2].charAt(0) - 'a';
            prevMoves.add(tmp[1] +" ");
            System.out.println(ret[0][0] + " " + ret[0][1] + " " + ret[1][0] + " " + ret[1][1]);
            char promote = 0;
            if (tmp[1].length() == 5) {
                promote = tmp[1].charAt(4);
            }*/
            return convertUCItoSteps(read);
        }catch (IOException e){
            e.printStackTrace();
            return null;
        }
    }
    @Override
    public void close(){
        try {
            writer.write("quit\n");
            writer.flush();
            writer.close();
            reader.close();
            process.destroy();
        }catch (IOException e){
            e.printStackTrace();
        }
    }
}
