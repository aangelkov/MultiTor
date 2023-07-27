package view;

import javafx.scene.paint.Color;

public class Shark extends Tile{

    final public static int SHARK_TIME_TO_REPRODUCE=8;
    final public static int SHARK_TIME_TO_LIVE=7;
    final public static int DEVIATION=2;
    final public static Color SHARK_COLOR=Color.RED;

    private int timeToReproduce;
    private int timeToLive;
    private int currentTimeToReproduce;
    private int currentTimeToLive;
    private Direction previousDirection;

    Shark(int xCoord, int yCoord, boolean isProcessed){
        super(xCoord,yCoord,TileType.SHARK, isProcessed);
        timeToReproduce=Random.getSharkReproduce(xCoord,yCoord);
        timeToLive=Random.getSharkLive(xCoord,yCoord);
        currentTimeToReproduce=this.timeToReproduce;
        currentTimeToLive=this.timeToLive;
        previousDirection=Direction.NONE;
    }

    Shark(int xCoord, int yCoord, int timeToReproduce, int currentTimeToReproduce, int timeToLive, int currentTimeToLive, Direction prevDirection, boolean isProcessed){
        super(xCoord,yCoord,TileType.SHARK, isProcessed);
        setTimeToReproduce(timeToReproduce);
        setTimeToLive(timeToLive);
        setCurrentTimeToReproduce(currentTimeToReproduce);
        setCurrentTimeToLive(currentTimeToLive);
        this.previousDirection=prevDirection;
    }

    public void setTimeToLive(int timeToLive) {
        if(timeToLive>=0)
        this.timeToLive = timeToLive;
        else this.timeToLive=0;
    }

    public void setCurrentTimeToReproduce(int currentTimeToReproduce) {
        if(currentTimeToReproduce>=0)
            this.currentTimeToReproduce = currentTimeToReproduce;
        else this.currentTimeToReproduce=0;
    }

    public void setCurrentTimeToLive(int currentTimeToLive) {
        if(currentTimeToLive>=0)
            this.currentTimeToLive = currentTimeToLive;
        else this.currentTimeToLive=0;
    }

    public void setTimeToReproduce(int timeToReproduce) {
        if(timeToReproduce>=0)
        this.timeToReproduce = timeToReproduce;
        else this.timeToReproduce=0;
    }

    public boolean move(Tile[][] map, Direction direction, boolean isProcessedFlag){
        int newXCoord = getxCoord();
        int newYCoord = getyCoord();
        switch(direction){
            case UP: newXCoord = (Main.ROW_COUNT+getxCoord()-1)%Main.ROW_COUNT; break;
            case RIGHT: newYCoord = (getyCoord()+1)%Main.COLUMN_COUNT; break;
            case DOWN: newXCoord = (getxCoord()+1)%Main.ROW_COUNT; break;
            case LEFT: newYCoord = (Main.COLUMN_COUNT+getyCoord()-1)%Main.COLUMN_COUNT; break;
        }
        if(map[newXCoord][newYCoord].getTileType()==TileType.SEA){
            if(currentTimeToReproduce>0){
                map[newXCoord][newYCoord]=new Shark(newXCoord,newYCoord,timeToReproduce,currentTimeToReproduce-1,timeToLive,currentTimeToLive-1, direction, isProcessedFlag);
                map[getxCoord()][getyCoord()]=new Sea(getxCoord(),getyCoord(),isProcessedFlag);
            }else{
                map[newXCoord][newYCoord]=new Shark(newXCoord,newYCoord,timeToReproduce, timeToReproduce,timeToLive,currentTimeToLive-1, direction, isProcessedFlag);
                map[getxCoord()][getyCoord()]=new Shark(getxCoord(),getyCoord(),isProcessedFlag);
            }
            return true;
        }else return false;
    }

    public boolean eat(Tile[][] map, Direction direction, boolean isProcessedFlag){
        int newXCoord = getxCoord();
        int newYCoord = getyCoord();
        switch(direction){
            case UP: newXCoord = (Main.ROW_COUNT+getxCoord()-1)%Main.ROW_COUNT; break;
            case RIGHT: newYCoord = (getyCoord()+1)%Main.COLUMN_COUNT; break;
            case DOWN: newXCoord = (getxCoord()+1)%Main.ROW_COUNT; break;
            case LEFT: newYCoord = (Main.COLUMN_COUNT+getyCoord()-1)%Main.COLUMN_COUNT; break;
        }
        if(map[newXCoord][newYCoord].getTileType()==TileType.FISH){
            if(currentTimeToReproduce>0){
                map[newXCoord][newYCoord]=new Shark(newXCoord,newYCoord,timeToReproduce,currentTimeToReproduce-1,timeToLive,timeToLive, direction, isProcessedFlag);
                map[getxCoord()][getyCoord()]=new Sea(getxCoord(),getyCoord(),isProcessedFlag);
            }else{
                map[newXCoord][newYCoord]=new Shark(newXCoord,newYCoord,timeToReproduce,timeToReproduce,timeToLive,timeToLive, direction, isProcessedFlag);
                map[getxCoord()][getyCoord()]=new Shark(getxCoord(),getyCoord(),isProcessedFlag);
            }
            return true;
        }else return false;
    }

    public void onAction(Tile[][] map, boolean isProcessedFlag){

        for(int i=0; i<4;i++){
            Direction direction = Random.getSharkEat(getxCoord(),getyCoord()).get(i);
            if(this.previousDirection!=oppositeDirection(direction)) {
                if(eat(map,direction,isProcessedFlag)) return;
            }
        }
        if(eat(map,oppositeDirection(this.previousDirection),isProcessedFlag)) return;
        if(currentTimeToLive<=0){
            map[getxCoord()][getyCoord()]=new Sea(getxCoord(),getyCoord(),isProcessedFlag);
            return;
        }
        for(int i=0; i<4;i++){
            Direction direction = Random.getSharkMove(getxCoord(),getyCoord()).get(i);
            if(this.previousDirection!=oppositeDirection(direction)) {
                if(move(map,direction,isProcessedFlag)) return;
            }
        }
        if(move(map,oppositeDirection(this.previousDirection),isProcessedFlag)) return;
        if(currentTimeToReproduce>0) currentTimeToReproduce--;
        currentTimeToLive--;
        setIsProcessed(isProcessedFlag);
    }
}
