package pieces;
import game.Table;
import org.junit.Assert;
import org.junit.Test;

public class KingTest {
    Testinit init = new Testinit();

    @Test public void validMove() {
        Table table = init.initTable();
        King king = new King(4,4, Piece.Color.White);
        table.setPiece(4,4,4,4,king);
        Assert.assertTrue(king.vaildMove(table,5,5));
    }
    @Test public void invalidCapture() {
        Table table = init.initTable();
        King king = new King(4,4, Piece.Color.White);
        table.setPiece(4,4,4,4,king);
        table.setPiece(4,5,4,5,new Pawn(4,5, Piece.Color.White));
        Assert.assertFalse(king.vaildMove(table,4,5));
    }
    @Test public void validShortCastling() {
        Table table = init.initTable();
        King king = new King(0,4, Piece.Color.Black);
        table.setPiece(0,4,0,4,king);
        table.setPiece(0,7,0,7,new Rook(0,7, Piece.Color.Black));
        table.rkmoves[Piece.Color.Black.ordinal()][0] = table.rkmoves[Piece.Color.Black.ordinal()][1] = false;
        Assert.assertTrue(king.vaildMove(table,0,6));
    }
    @Test public void validLongCastling() {
        Table table = init.initTable();
        King king = new King(0,4, Piece.Color.Black);
        table.setPiece(0,4,0,4,king);
        table.setPiece(0,0,0,0,new Rook(0,0, Piece.Color.Black));
        table.rkmoves[Piece.Color.Black.ordinal()][0] = table.rkmoves[Piece.Color.Black.ordinal()][1] = false;
        Assert.assertTrue(king.vaildMove(table,0,2));
    }


}
