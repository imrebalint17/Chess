package GameFrame;

import Pieces.Piece;

import java.io.File;
import java.io.IOException;


public interface EngineInterface {
    InterfaceMoveWrapper newRound(int[][] prevStep, Piece promotion);
    void close();
    default String convertStepstoUCI(int[][] prevStep, Piece promotion){
        String[][] tmp = new String[2][2];
        tmp[0][0] = String.valueOf((char) (prevStep[0][1] + 'a'));
        tmp[0][1] = String.valueOf(Math.abs(prevStep[0][0]-8));
        tmp[1][0] = String.valueOf((char) (prevStep[1][1] + 'a'));
        tmp[1][1] = String.valueOf(Math.abs(prevStep[1][0] -8));
        String prev = tmp[0][0] + tmp[0][1] + tmp[1][0] + tmp[1][1];
        System.out.println(prev);
        if (promotion != null) {
            prev += promotion.getCharacter();
        }
        return prev;
    }
    default InterfaceMoveWrapper convertUCItoSteps(String uci){
        String[] tmp = uci.split(" ");
        String[] tmp2 = tmp[1].split("");
        int[][] ret = new int[2][2];
        ret[0][0] = Math.abs(Integer.parseInt(tmp2[1]) - 8);
        ret[0][1] = tmp2[0].charAt(0) - 'a';
        ret[1][0] = Math.abs(Integer.parseInt(tmp2[3]) - 8);
        ret[1][1] = tmp2[2].charAt(0) - 'a';
        System.out.println(ret[0][0] + " " + ret[0][1] + " " + ret[1][0] + " " + ret[1][1]);
        char promote = 0;
        if (tmp[1].length() == 5) {
            promote = tmp[1].charAt(4);
        }
        return new InterfaceMoveWrapper(ret, promote);
    }
}
