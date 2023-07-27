package view;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

enum TileType{
    SHARK,
    FISH,
    SEA
};

enum Direction{
    UP,
    RIGHT,
    DOWN,
    LEFT,
    NONE
}

public abstract class Tile {

    public static final int TILE_SIZE=2;

    private boolean isProcessed;
    private int xCoord;
    private int yCoord;
    private TileType tileType;
    private Color color;
    private Rectangle rectangle;

    public Tile(int xCoord, int yCoord, TileType tileType, boolean isProcessed){
        setxCoord(xCoord);
        setyCoord(yCoord);
        setTileType(tileType);
        setColor(tileType);
        setIsProcessed(isProcessed);
        setRectangle();
    }


    public void setTileType(TileType tileType){
        this.tileType=tileType;
    }

    public void setColor(TileType tileType){
        color=switch(tileType){
            case FISH -> Fish.FISH_COLOR;
            case SHARK -> Shark.SHARK_COLOR;
            default -> Sea.SEA_COLOR;
        };
    }

    public void setRectangle(){
        this.rectangle=new Rectangle(xCoord*TILE_SIZE,yCoord*TILE_SIZE,TILE_SIZE,TILE_SIZE);
        this.rectangle.setFill(color);
    }

    public void setIsProcessed(boolean isProcessed){
        this.isProcessed=isProcessed;
    }

    public void setxCoord(int xCoord){
        if(xCoord>=0) this.xCoord=xCoord;
        else this.xCoord=0;
    }

    public void setyCoord(int yCoord){
        if(yCoord>=0) this.yCoord=yCoord;
        else this.yCoord=0;
    }

    public Direction oppositeDirection(Direction direction){
        return switch(direction){
            case UP -> Direction.DOWN;
            case RIGHT -> Direction.LEFT;
            case DOWN -> Direction.UP;
            case LEFT -> Direction.RIGHT;
            case NONE -> Direction.NONE;
        };
    }

    public int getxCoord(){
        return xCoord;
    }

    public int getyCoord(){
        return yCoord;
    }

    public boolean getIsProcessed(){return isProcessed;}

    public TileType getTileType(){
        return tileType;
    }

    public Rectangle getRectangle(){return rectangle;}

    public abstract void onAction(Tile[][] tileMatrix, boolean isProcessedFlag);
}
