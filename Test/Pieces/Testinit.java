package Pieces;

import Game.Table;

public class Testinit {
    public Table initTable(){
        Table table = new Table();
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                table.setPiece(i,j,i,j,null);
            }
        }
        return table;
    }
}
