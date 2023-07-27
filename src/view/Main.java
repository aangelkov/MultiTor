package view;

import javafx.application.Application;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.Group;
import javafx.scene.SnapshotParameters;
import javafx.scene.image.WritableImage;
import javafx.stage.Stage;
import javafx.util.Pair;
import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import static java.lang.System.currentTimeMillis;

public class Main extends Application {

    // броят редове трябва да се дели на грануларност
    // броят редове трябва да е по-малък или равен на брой нишки * грануларност

    final public static int ROW_COUNT=600;
    final public static int COLUMN_COUNT=600;
    final public static int GRANULARITY=60;
    final public static int THREAD_COUNT=3;
    final public static int STARTING_FISH_COUNT=6000;
    final public static int STARTING_SHARK_COUNT=6000;
    final public static int ITERATIONS_COUNT=300;
    final public static boolean EXPORT_TO_PNG=true;
    final public static int ROWS_TO_SKIP=THREAD_COUNT*GRANULARITY;

    final private static Random randomValueInit = new Random();

    enum Phase{
        FIRST,
        SECOND,
        THIRD
    }

    public void populateMap(Tile[][] map){

        ArrayList<Pair<Integer,Integer>> positions=new ArrayList<>();
        for(int i=0;i<ROW_COUNT;i++){
            for(int j=0;j<COLUMN_COUNT;j++){
                positions.add(new Pair<>(i,j));
            }
        }
        Collections.shuffle(positions);

        int currentStartingFishCount=STARTING_FISH_COUNT;
        int currentStartingSharkCount=STARTING_SHARK_COUNT;

        for(int i=0;i<positions.size();i++){
            if(currentStartingFishCount>0){
                int currRow=positions.get(i).getKey();
                int currCol=positions.get(i).getValue();
                map[currRow][currCol]=new Fish(currRow,currCol,false);
                currentStartingFishCount--;
            }
            else if(currentStartingSharkCount>0){
                int currRow=positions.get(i).getKey();
                int currCol=positions.get(i).getValue();
                map[currRow][currCol]=new Shark(currRow,currCol,false);
                currentStartingSharkCount--;
            }
            else{
                int currRow=positions.get(i).getKey();
                int currCol=positions.get(i).getValue();
                map[currRow][currCol]=new Sea(currRow,currCol,false);
            }
        }
    }

    public void work(Tile[][] map, Phase phase, boolean isProcessedFlag) throws InterruptedException{

        int startingRow;
        int numberOfRowsToProcess=switch(phase){
            case FIRST -> GRANULARITY-2;
            case SECOND,THIRD -> 1;
        };

        int numberOfSkips = ROW_COUNT/(THREAD_COUNT*GRANULARITY);
        int extraSkipCount = (ROW_COUNT%(THREAD_COUNT*GRANULARITY))/GRANULARITY;

        Thread[] threads=new Thread[THREAD_COUNT];

        for(int i=0;i<THREAD_COUNT;i++){
            startingRow=switch(phase){
                case FIRST -> i*GRANULARITY+1;
                case SECOND -> i*GRANULARITY;
                case THIRD -> (i+1)*GRANULARITY-1;
            };
            int numbOfSkips = i<extraSkipCount?numberOfSkips+1:numberOfSkips;
            threads[i] = new Thread(new Worker(startingRow,numberOfRowsToProcess,numbOfSkips,map,isProcessedFlag));
        }
        for(Thread thread:threads){
            thread.start();
        }
        for(Thread thread:threads){
            thread.join();
        }
    }

    @Override
    public void start(Stage primaryStage) throws InterruptedException {

        Tile[][] map=new Tile[ROW_COUNT][COLUMN_COUNT];

        populateMap(map);

        if(EXPORT_TO_PNG) exportToPNG(map,"image"+0);

        Long startingTime = currentTimeMillis();

        for(int i=1;i<=ITERATIONS_COUNT;i++){

            boolean currentIsProcessedFlag = !map[0][0].getIsProcessed();

            work(map,Phase.FIRST,currentIsProcessedFlag);
            work(map,Phase.SECOND,currentIsProcessedFlag);
            work(map,Phase.THIRD,currentIsProcessedFlag);

            if(EXPORT_TO_PNG) exportToPNG(map,"image"+i);
        }

        Long finishTime = currentTimeMillis();

        System.out.println("Execution time: " + (finishTime-startingTime));

        System.exit(0);
    }

    private void exportToPNG(Tile[][] map,String name) {

        Group group=new Group();
        for(int i=0;i<ROW_COUNT;i++){
            for(int j=0;j<COLUMN_COUNT;j++){
                group.getChildren().add(map[i][j].getRectangle());
            }
        }

        WritableImage image = group.snapshot(new SnapshotParameters(), null);

        File file = new File(String.format("C:\\AOOP\\WaTor\\Pictures\\%s.png",name));

        try {
            ImageIO.write(SwingFXUtils.fromFXImage(image, null), "png", file);
            System.out.println("Scene exported to PNG: " + file.getAbsolutePath());
        } catch (IOException e) {
            System.out.println("Error exporting scene to PNG: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}