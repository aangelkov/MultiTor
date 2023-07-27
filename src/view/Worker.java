package view;


public class Worker implements Runnable {

    private int startingRow;
    private int numberOfRowsToProcess;
    private Tile[][] map;
    private boolean isProcessedFlag;
    private int numberOfSkips;

    public Worker(int startingRow, int numberOfRowsToProcess, int numberOfSkips, Tile[][] map, boolean isProcessedFlag){
        this.startingRow=startingRow;
        this.numberOfRowsToProcess=numberOfRowsToProcess;
        this.numberOfSkips=numberOfSkips;
        this.map=map;
        this.isProcessedFlag=isProcessedFlag;
    }

    public void run(){
        while(numberOfSkips>0){
            for(int i=startingRow;i<startingRow+numberOfRowsToProcess;i++){
                for(int j=0;j<Main.COLUMN_COUNT;j++){
                    if(map[i][j].getIsProcessed()==isProcessedFlag) continue;
                    map[i][j].onAction(map,isProcessedFlag);
                }
            }
            numberOfSkips--;
            startingRow+=Main.ROWS_TO_SKIP;
        }
    }
}
