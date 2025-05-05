package engine;

import gameFrame.InterfaceMoveWrapper;
import pieces.Piece;

import java.util.ArrayList;
import java.util.List;

public class MultiplayerInterface implements EngineInterface{


    private List<String> prevMoves;
    public MultiplayerInterface(String ip, int port, boolean starts) {
        prevMoves = new ArrayList<>();
    }
    /**
     * @param prevStep
     * @param promotion
     * @return
     */
    @Override
    public InterfaceMoveWrapper newRound(int[][] prevStep, Piece promotion) {
        MultiplayerSession session = MultiplayerSession.getInstance();
        if (prevStep != null){
            String currstep = convertStepstoUCI(prevStep, promotion);
            prevMoves.add(currstep);
            session.getClientEndpoint().makeMove(currstep);
        }
        String response = null;
        while (response == null){
            response = session.getMove();
            try{
                Thread.sleep(500);
            }catch (InterruptedException e){
                System.err.println(e.getMessage());
            }
        }
        System.out.println(response);
        prevMoves.add(response);
        return convertUCItoSteps(response);
    }
    /**
     *
     */
    @Override
    public void close() {
        MultiplayerSession session = MultiplayerSession.getInstance();
        try {
            session.getClientEndpoint().onClose();
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }
}
