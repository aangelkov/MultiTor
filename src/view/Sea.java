package view;

import javafx.scene.paint.Color;

public class Sea extends Tile{

    public static final Color SEA_COLOR=Color.LIGHTBLUE;

     public Sea(int xCoord, int yCoord, boolean isProcessed){
         super(xCoord,yCoord,TileType.SEA, isProcessed);
     }

     public void onAction(Tile[][] map, boolean isProcessedFlag){
         setIsProcessed(isProcessedFlag);
     }
}
