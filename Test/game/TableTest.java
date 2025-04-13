package game;
import pieces.Pawn;
import pieces.Piece;
import org.junit.Assert;
import org.junit.Test;

public class TableTest {

    @Test public void testSetPiece() {
        Table table = new Table();
        Pawn testPawn = new Pawn(0,0, Piece.Color.White);
        table.setPiece(0,0,0,0,testPawn);
        Assert.assertTrue(System.identityHashCode(table.getpiece(0,0)) == System.identityHashCode(testPawn));
    }
    @Test public void gameFoolsMate(){
        Table table = new Table();
        table.round(new int[][]{{6,5},{5,5}});
        table.round(new int[][]{{1,4},{3,4}});
        table.round(new int[][]{{6,6},{4,6}});
        table.round(new int[][]{{0,3},{4,7}});
        Assert.assertTrue(table.getState() == Table.State.CHECKMATE);
    }
    @Test public void gameAlmostFoolsMate(){
        Table table = new Table();
        table.round(new int[][]{{6,5},{5,5}});
        table.round(new int[][]{{1,4},{3,4}});
        table.round(new int[][]{{7,6},{5,7}});
        table.round(new int[][]{{0,3},{4,7}});
        Assert.assertTrue(table.getState() == Table.State.CHECK);
        Assert.assertTrue(table.hitting(7,4, Piece.Color.White)!=null);
    }
    @Test public void gameAlmostFoolsMateKingMove(){
        Table table = new Table();
        table.round(new int[][]{{6,5},{5,5}});
        table.round(new int[][]{{1,4},{3,4}});
        table.round(new int[][]{{6,3},{5,3}});
        table.round(new int[][]{{0,3},{4,7}});
        Assert.assertTrue(table.getState() == Table.State.CHECK);
        Assert.assertTrue(table.hitting(7,4, Piece.Color.White)!=null);
    }

    @Test public void  gameDefenderCanHitAttacker(){
        Table table = new Table();
        table.round(new int[][]{{7,1},{5,2}});
        table.round(new int[][]{{0,1},{2,0}});
        table.round(new int[][]{{5,2},{4,4}});
        table.round(new int[][]{{2,0},{0,1}});
        table.round(new int[][]{{4,4},{2,5}});
        Assert.assertTrue(table.getState() == Table.State.CHECK);
        Assert.assertTrue(table.hitting(0,4, Piece.Color.Black)!=null);
    }

    @Test public void gameWhiteShortBlackLongCastling(){
        Table table = new Table();
        Piece whiteKing = table.getpiece(7,4);
        Piece whiteRook = table.getpiece(7,7);
        Piece blackKing = table.getpiece(0,4);
        Piece blackRook = table.getpiece(0,0);
        table.round(new int[][]{{7,6},{5,5}});
        table.round(new int[][]{{1,2},{2,2}});
        table.round(new int[][]{{6,6},{5,6}});
        table.round(new int[][]{{1,1},{2,1}});
        table.round(new int[][]{{7,5},{6,6}});
        table.round(new int[][]{{0,1},{2,0}});
        table.round(new int[][]{{7,4},{7,6}});
        table.round(new int[][]{{0,2},{1,1}});
        table.round(new int[][]{{5,6},{4,6}});
        table.round(new int[][]{{0,3},{1,2}});
        table.round(new int[][]{{4,6},{3,6}});
        table.round(new int[][]{{0,4},{0,2}});
        Assert.assertTrue(System.identityHashCode(whiteKing) == System.identityHashCode(table.getpiece(7,6)));
        Assert.assertTrue(System.identityHashCode(whiteRook) == System.identityHashCode(table.getpiece(7,5)));
        Assert.assertTrue(System.identityHashCode(blackKing) == System.identityHashCode(table.getpiece(0,2)));
        Assert.assertTrue(System.identityHashCode(blackRook) == System.identityHashCode(table.getpiece(0,3)));

    }
    @Test public void gameEnPassant(){
        Table table = new Table();
        Piece blackPawn = table.getpiece(1,1);
        table.round(new int[][]{{6,6},{4,6}});
        table.round(new int[][]{{1,1},{3,1}});
        table.round(new int[][]{{4,6},{3,6}});
        table.round(new int[][]{{3,1},{4,1}});
        table.round(new int[][]{{6,2},{4,2}});
        table.round(new int[][]{{4,1},{5,2}});
        Assert.assertTrue(table.getpiece(4,2) == null);
        Assert.assertTrue(System.identityHashCode(table.getpiece(5,2)) == System.identityHashCode(blackPawn));
        Assert.assertTrue(table.hitting(0,4, Piece.Color.Black)==null);
    }
}
