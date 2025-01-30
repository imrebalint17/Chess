package Pieces;

import Game.Table;
import org.junit.Assert;
import org.junit.Test;

public class PawnTest {
    Testinit init = new Testinit();

    @Test public void whiteValidMove() {
        Table table = init.initTable();
        Pawn pawn = new Pawn(6,6, Piece.Color.White);
        table.setPiece(6,6,6,6,pawn);
        Assert.assertTrue(pawn.vaildMove(table,5,6));
    }
    @Test public void blackValidMove() {
        Table table = init.initTable();
        Pawn pawn = new Pawn(1,1, Piece.Color.Black);
        table.setPiece(1,1,1,1,pawn);
        Assert.assertTrue(pawn.vaildMove(table,2,1));
    }
    @Test public void whiteValidDoubleMove(){
        Table table = init.initTable();
        Pawn pawn = new Pawn(6,6, Piece.Color.White);
        table.setPiece(6,6,6,6,pawn);
        Assert.assertTrue(pawn.vaildMove(table,4,6));
    }
    @Test public void blackValidDoubleMove(){
        Table table = init.initTable();
        Pawn pawn = new Pawn(1,1, Piece.Color.Black);
        table.setPiece(1,1,1,1,pawn);
        Assert.assertTrue(pawn.vaildMove(table,3,1));
    }
    @Test public void whiteCapture() {
        Table table = init.initTable();
        Pawn pawn = new Pawn(6,6, Piece.Color.White);
        table.setPiece(6,6,6,6,pawn);
        table.setPiece(5,5,5,5,new Queen(5,5, Piece.Color.Black));
        Assert.assertTrue(pawn.vaildMove(table,5,5));
    }
    @Test public void blackCapture() {
        Table table = init.initTable();
        Pawn pawn = new Pawn(1,1, Piece.Color.Black);
        table.setPiece(1,1,1,1,pawn);
        table.setPiece(2,2,2,2,new Queen(2,2, Piece.Color.White));
        Assert.assertTrue(pawn.vaildMove(table,2,2));
    }
    @Test public void whiteInvalidCapture() {
        Table table = init.initTable();
        Pawn pawn = new Pawn(6,6, Piece.Color.White);
        table.setPiece(6,6,6,6,pawn);
        table.setPiece(5,5,5,5,new Queen(5,5, Piece.Color.White));
        Assert.assertFalse(pawn.vaildMove(table,5,5));

    }
    @Test public void blackInvalidCapture() {
        Table table = init.initTable();
        Pawn pawn = new Pawn(1,1, Piece.Color.Black);
        table.setPiece(1,1,1,1,pawn);
        table.setPiece(2,2,2,2,new Queen(2,2, Piece.Color.Black));
        Assert.assertFalse(pawn.vaildMove(table,2,2));
    }
    @Test public void whiteInvalidMoveBack() {
        Table table = init.initTable();
        Pawn pawn = new Pawn(6,6, Piece.Color.White);
        table.setPiece(6,6,6,6,pawn);
        Assert.assertFalse(pawn.vaildMove(table,7,6));
    }
    @Test public void blackInvalidMoveBack() {
        Table table = init.initTable();
        Pawn pawn = new Pawn(1,1, Piece.Color.Black);
        table.setPiece(1,1,1,1,pawn);
        Assert.assertFalse(pawn.vaildMove(table,0,1));
    }
    @Test public void whiteInvalidMoveThreeForward() {
        Table table = init.initTable();
        Pawn pawn = new Pawn(6,6, Piece.Color.White);
        table.setPiece(6,6,6,6,pawn);
        Assert.assertFalse(pawn.vaildMove(table,3,6));
    }
    @Test public void blackInvalidMoveThreeForward() {
        Table table = init.initTable();
        Pawn pawn = new Pawn(1,1, Piece.Color.Black);
        table.setPiece(1,1,1,1,pawn);
        Assert.assertFalse(pawn.vaildMove(table,4,1));
    }
    @Test public void whiteInvalidMoveTwoForward() {
        Table table = init.initTable();
        Pawn pawn = new Pawn(5,5, Piece.Color.White);
        table.setPiece(5,5,5,5,pawn);
        Assert.assertFalse(pawn.vaildMove(table,3,5));
    }
    @Test public void blackInvalidMoveTwoForward() {
        Table table = init.initTable();
        Pawn pawn = new Pawn(2,2, Piece.Color.Black);
        table.setPiece(2,2,2,2,pawn);
        Assert.assertFalse(pawn.vaildMove(table,4,2));
    }
    @Test public void whiteInvalidMoveTwoForwardBlock() {
        Table table = init.initTable();
        Pawn pawn = new Pawn(6,6, Piece.Color.White);
        table.setPiece(6,6,6,6,pawn);
        table.setPiece(5,6,5,6,new Queen(5,6, Piece.Color.White));
        Assert.assertFalse(pawn.vaildMove(table,4,6));
    }
    @Test public void blackInvalidMoveTwoForwardBlock() {
        Table table = init.initTable();
        Pawn pawn = new Pawn(1,1, Piece.Color.Black);
        table.setPiece(1,1,1,1,pawn);
        table.setPiece(2,1,2,1,new Queen(2,1, Piece.Color.Black));
        Assert.assertFalse(pawn.vaildMove(table,3,1));
    }
    @Test public void whiteValidEnPassant(){
        Table table = init.initTable();
        Pawn pawn = new Pawn(3,2, Piece.Color.White);
        table.setPiece(4,2,4,2,pawn);
        Pawn bpawn = new Pawn(1,1, Piece.Color.Black);
        table.setPiece(1,1,1,1,bpawn);
        table.round(new int[][]{{4,2},{3,2}});
        table.round(new int[][]{{1,1},{3,1}});
        Assert.assertTrue(pawn.vaildMove(table,2,1));
    }
    @Test public void blackValidEnPassant(){
        Table table = init.initTable();
        Pawn pawn = new Pawn(4,2, Piece.Color.Black);
        table.setPiece(4,2,4,2,pawn);
        Pawn wpawn = new Pawn(6,1, Piece.Color.White);
        table.setPiece(6,1,6,1,wpawn);
        table.round(new int[][]{{4,2},{3,2}});
        table.round(new int[][]{{6,1},{4,1}});
        Assert.assertTrue(pawn.vaildMove(table,5,1));
    }
}
