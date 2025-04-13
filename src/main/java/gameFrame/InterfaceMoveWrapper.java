package gameFrame;

public class InterfaceMoveWrapper {
    private int[][] move;
    private char promote;
    public InterfaceMoveWrapper(int[][] move, char promote) {
        this.move = move;
        this.promote = promote;
    }

    public int[][] getMove() {
        return move;
    }

    public char getPromote() {
        return promote;
    }
}
