package pieces;
import game.Table;
import org.junit.Assert;
import org.junit.Test;

public class QueenTest {
    Testinit init = new Testinit();

    @Test public void validMoveNW() {
        Table table = init.initTable();
        Queen queen = new Queen(4,4, Piece.Color.White);
        table.setPiece(4,4,4,4,queen);
        Assert.assertTrue(queen.vaildMove(table,2,2));
    }
    @Test public void invalidMove() {
        Table table = init.initTable();
        Queen queen = new Queen(4,4, Piece.Color.White);
        table.setPiece(4,4,4,4,queen);
        Assert.assertFalse(queen.vaildMove(table,2,3));
    }
    @Test public void captureNW(){
        Table table = init.initTable();
        Queen queen = new Queen(4,4, Piece.Color.White);
        table.setPiece(4,4,4,4,queen);
        table.setPiece(2,2,2,2,new Pawn(2,2, Piece.Color.Black));
        Assert.assertTrue(queen.vaildMove(table,2,2));
    }
    @Test public void captureBlockNW() {
        Table table = init.initTable();
        Queen queen = new Queen(4,4, Piece.Color.White);
        table.setPiece(4,4,4,4,queen);
        table.setPiece(3,3,3,3,new Pawn(3,3, Piece.Color.Black));
        table.setPiece(2,2,2,2,new Pawn(2,2, Piece.Color.Black));
        Assert.assertFalse(queen.vaildMove(table,2,2));
    }
    @Test public void captureBlockSE(){
        Table table = init.initTable();
        Queen queen = new Queen(4,4, Piece.Color.White);
        table.setPiece(4,4,4,4,queen);
        table.setPiece(5,5,5,5,new Pawn(3,3, Piece.Color.Black));
        table.setPiece(6,6,6,6,new Pawn(2,2, Piece.Color.Black));
        Assert.assertFalse(queen.vaildMove(table,6,6));

    }
    @Test public void captureBlockNE(){
        Table table = init.initTable();
        Queen queen = new Queen(4,4, Piece.Color.White);
        table.setPiece(4,4,4,4,queen);
        table.setPiece(3,5,3,5,new Pawn(3,3, Piece.Color.Black));
        table.setPiece(2,6,2,6,new Pawn(2,2, Piece.Color.Black));
        Assert.assertFalse(queen.vaildMove(table,2,6));

    }
    @Test public void captureBlockSW(){
        Table table = init.initTable();
        Queen queen = new Queen(4,4, Piece.Color.White);
        table.setPiece(4,4,4,4,queen);
        table.setPiece(5,3,5,3,new Pawn(3,3, Piece.Color.Black));
        table.setPiece(6,2,6,2,new Pawn(2,2, Piece.Color.Black));
        Assert.assertFalse(queen.vaildMove(table,6,2));

    }
    @Test public void captureOwnNW() {
        Table table = init.initTable();
        Queen queen = new Queen(4,4, Piece.Color.White);
        table.setPiece(4,4,4,4,queen);
        table.setPiece(2,2,2,2,new Pawn(2,2, Piece.Color.White));
        Assert.assertFalse(queen.vaildMove(table,2,2));
    }
    @Test public void validMoveN(){
        Table table = init.initTable();
        Queen queen = new Queen(4,4, Piece.Color.White);
        table.setPiece(4,4,4,4,queen);
        Assert.assertTrue(queen.vaildMove(table,4,2));
    }
    @Test public void captureN(){
        Table table = init.initTable();
        Queen queen = new Queen(4,4, Piece.Color.White);
        table.setPiece(4,4,4,4,queen);
        table.setPiece(4,2,4,2,new Pawn(2,2, Piece.Color.Black));
        Assert.assertTrue(queen.vaildMove(table,4,2));
    }
    @Test public void captureBlockN(){
        Table table = init.initTable();
        Queen queen = new Queen(4,4, Piece.Color.White);
        table.setPiece(4,2,4,2,new Pawn(2,2, Piece.Color.Black));
        table.setPiece(4,3,4,3,new Pawn(2,2, Piece.Color.Black));
        table.setPiece(4,4,4,4,queen);
        Assert.assertFalse(queen.vaildMove(table,4,2));
    }
    @Test public void captureBlockS(){
        Table table = init.initTable();
        Queen queen = new Queen(4,2, Piece.Color.White);
        table.setPiece(4,2,4,2,queen);
        table.setPiece(4,3,4,3,new Pawn(2,2, Piece.Color.Black));
        table.setPiece(4,4,4,4,new Pawn(2,2, Piece.Color.Black));
        Assert.assertFalse(queen.vaildMove(table,4,4));
    }
    @Test public void captureBlockE(){
        Table table = init.initTable();
        Queen queen = new Queen(2,4, Piece.Color.White);
        table.setPiece(2,4,2,4,queen);
        table.setPiece(3,4,3,4,new Pawn(2,2, Piece.Color.Black));
        table.setPiece(4,4,4,4,new Pawn(2,2, Piece.Color.Black));
        Assert.assertFalse(queen.vaildMove(table,4,4));
    }
    @Test public void captureBlockW(){
        Table table = init.initTable();
        Queen queen = new Queen(4,4, Piece.Color.White);
        table.setPiece(4,4,4,4,queen);
        table.setPiece(3,4,3,4,new Pawn(2,2, Piece.Color.Black));
        table.setPiece(2,4,2,4,new Pawn(2,2, Piece.Color.Black));
        Assert.assertFalse(queen.vaildMove(table,2,4));
    }
    @Test public void captureOwnN(){
        Table table = init.initTable();
        Queen queen = new Queen(4,4, Piece.Color.White);
        table.setPiece(4,4,4,4,queen);
        table.setPiece(4,2,4,2,new Pawn(2,2, Piece.Color.White));
        Assert.assertFalse(queen.vaildMove(table,4,2));
    }
}
