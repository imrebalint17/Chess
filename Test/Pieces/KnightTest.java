package Pieces;
import Game.Table;
import org.junit.Assert;
import org.junit.Test;

public class KnightTest {
    Testinit init = new Testinit();
    @Test public void validMoveForward2() {
        Table table = init.initTable();
        Knight knight = new Knight(4,4, Piece.Color.White);
        table.setPiece(4,4,4,4,knight);
        Assert.assertTrue(knight.vaildMove(table,2,3));
    }
    @Test public void validMoveForward1() {
        Table table = init.initTable();
        Knight knight = new Knight(4,4, Piece.Color.White);
        table.setPiece(4,4,4,4,knight);
        Assert.assertTrue(knight.vaildMove(table,3,2));
    }
    @Test public void invalidCaptureForward2() {
        Table table = init.initTable();
        Knight knight = new Knight(4,4, Piece.Color.White);
        table.setPiece(4,4,4,4,knight);
        table.setPiece(2,3,2,3,new Pawn(2,3, Piece.Color.White));
        Assert.assertFalse(knight.vaildMove(table,2,3));
    }
}
