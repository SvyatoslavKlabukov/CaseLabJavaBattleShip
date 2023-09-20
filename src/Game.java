public class Game {
    private Player[] players;
    private GameState gameState;
    private int currentIndex;
    private boolean isGameOver;

    public Game(){
        players = new Player[2];
        players[0] = new Player(this);
        players[1] = new Player(this);

        this.gameState = GameState.Fill;
        currentIndex = 0;
        isGameOver = false;
    }

    private void nextStep() {
        currentIndex++;
        if( currentIndex >= players.length ) {
            currentIndex = 0;
        }
    }

    public void process(){
        if ( players[0].isMapFilled() && players[1].isMapFilled()){
            gameState = GameState.Battle;
            System.out.println("=====PLAYER "+ (currentIndex+1)+"=====");
            players[currentIndex].process();
            nextStep();
        }
        if (!players[0].isMapFilled()){
            System.out.println("=====PLAYER 1=====");
            players[0].process();
        }else if (!players[1].isMapFilled()){
            System.out.println("=====PLAYER 2=====");
            players[1].process();
        }
    }

    public GameState getState() {
        return gameState;
    }
    public Player[] getPlayers(){
        return players;
    }
    public int getCurrentIndex(){
        return currentIndex;
    }
    public void setCurrentIndex(int currentIndex){
        this.currentIndex = currentIndex;
    }

    public void setIsGameOver(){
        isGameOver = true;
    }
    public boolean getIsGameOver(){
        return isGameOver;
    }
}
