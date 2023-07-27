package view;

import java.util.ArrayList;
import java.util.Collections;

public class Random {

    private static ArrayList<ArrayList<Direction>> fishMove=new ArrayList<>();
    private static ArrayList<Integer> fishReproduce = new ArrayList<>();
    private static ArrayList<Integer> fishLive = new ArrayList<>();
    private static ArrayList<ArrayList<Direction>> sharkMove=new ArrayList<>();
    private static ArrayList<ArrayList<Direction>> sharkEat=new ArrayList<>();
    private static ArrayList<Integer> sharkReproduce=new ArrayList<>();
    private static ArrayList<Integer> sharkLive=new ArrayList<>();

    Random(){
        for(int i=0;i<Main.COLUMN_COUNT*Main.ROW_COUNT;i++){
            fishMove.add(new ArrayList<>());
            fishMove.get(i).add(Direction.UP);
            fishMove.get(i).add(Direction.RIGHT);
            fishMove.get(i).add(Direction.DOWN);
            fishMove.get(i).add(Direction.LEFT);
            Collections.shuffle(fishMove.get(i));
            sharkMove.add(new ArrayList<>());
            sharkMove.get(i).add(Direction.UP);
            sharkMove.get(i).add(Direction.RIGHT);
            sharkMove.get(i).add(Direction.DOWN);
            sharkMove.get(i).add(Direction.LEFT);
            Collections.shuffle(sharkMove.get(i));
            sharkEat.add(new ArrayList<>());
            sharkEat.get(i).add(Direction.UP);
            sharkEat.get(i).add(Direction.RIGHT);
            sharkEat.get(i).add(Direction.DOWN);
            sharkEat.get(i).add(Direction.LEFT);
            Collections.shuffle(sharkEat.get(i));
            fishReproduce.add((i%2==0?-1:1)*(i%(Fish.DEVIATION+1))+Fish.FISH_TIME_TO_REPRODUCE);
            fishLive.add((i%2==0?-1:1)*(i%(Fish.DEVIATION+1))+Fish.FISH_TIME_TO_LIVE);
            sharkReproduce.add((i%2==0?-1:1)*(i%(Shark.DEVIATION+1))+Shark.SHARK_TIME_TO_REPRODUCE);
            sharkLive.add((i%2==0?-1:1)*(i%(Shark.DEVIATION+1))+Shark.SHARK_TIME_TO_LIVE);
        }
        Collections.shuffle(fishReproduce);
        Collections.shuffle(fishLive);
        Collections.shuffle(sharkReproduce);
        Collections.shuffle(sharkLive);
    }

    public static ArrayList<Direction> getFishMove(int x, int y){
        return fishMove.get(x*Main.COLUMN_COUNT+y);
    }
    public static int getFishReproduce(int x, int y) { return fishReproduce.get(x*Main.COLUMN_COUNT+y);}
    public static int getFishLive(int x, int y) { return fishLive.get(x*Main.COLUMN_COUNT+y);}
    public static ArrayList<Direction> getSharkMove(int x, int y){
        return sharkMove.get(x*Main.COLUMN_COUNT+y);
    }
    public static ArrayList<Direction> getSharkEat(int x, int y){return sharkEat.get(x*Main.COLUMN_COUNT+y);}
    public static int getSharkReproduce(int x, int y){
        return sharkReproduce.get(x*Main.COLUMN_COUNT+y);
    }
    public static int getSharkLive(int x, int y){
        return sharkLive.get(x*Main.COLUMN_COUNT+y);
    }
}
