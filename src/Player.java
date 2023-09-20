import java.util.ArrayList;
import java.util.HashMap;

public class Player {
    public static final int maxEnemyDecks = 20;
    private Game game;
    private Input input;
    private Map map;
    private java.util.Map<DeckCount, Integer> shipCounter;
    private int maxShips;
    private ArrayList<Ship> ships;

    private Map enemyMap;

    private int aLiveShips;
    private int destroyedDecks;

    public Player(Game game){

        input = new Input(this);
        this.game = game;
        map = new Map();
        enemyMap = new Map();

        init();
    }

    public void init(){
        destroyedDecks = 0;
        maxShips = 0;
        aLiveShips = 0;
        shipCounter = new HashMap<>();
        for(int i = 1; i < DeckCount.values().length; i++){
            int shipCount = 5 - i;
            shipCounter.put(DeckCount.valueOf(i), shipCount);
            maxShips += shipCount;
        }
        if (maxShips > 0){
            ships = new ArrayList<Ship>();
        }
    }

    public int getFreePlaces( DeckCount dc ) {
        return shipCounter.get( dc );
    }


    public boolean possibleToAddShip(Orientation orient, DeckCount dc, Point startCoord){
        int freePlaces = getFreePlaces(dc);
        if (freePlaces>0){
            Point[] coords = Ship.getCoordsForShip(map, dc, orient, startCoord);
            if (coords == null){
                return false;
            }
            Ship ship = new Ship(map, dc, orient, coords);
            ships.add(ship);
            freePlaces -= 1;
            shipCounter.replace(dc,freePlaces);
            aLiveShips++;
            return true;
        }
        return false;
    }

    public void addShip(int x, int y, int decks, Orientation orient){
        System.out.println( "make_ship x, y (" + x + ", " + y + ") " + decks );
        possibleToAddShip( orient, DeckCount.valueOf(decks), new Point(x,y));


    }

    public void attack(int x, int y){
        int currentPlayerIndex = game.getCurrentIndex();
        int anotherPlayerIndex = currentPlayerIndex == 0 ? 1 : 0;
        Player anotherPlayer = game.getPlayers()[anotherPlayerIndex];
        Map anotherPlayerMap = anotherPlayer.getMap();
        Point point = new Point(x,y);
        Deck deck = anotherPlayerMap.getDecks()[y][x];//
        if( deck != null && enemyMap.isValidCoord(point) ) {//вариант поражения цели
            Deck enemyDeck = new Deck(new Point(x,y), 'O', 'X', false);
            enemyMap.addObject(enemyDeck);
            destroyedDecks++;
            int index = (currentPlayerIndex == 1) ? 0 : 1;
            game.setCurrentIndex(index);
            if(destroyedDecks == maxEnemyDecks){
                System.out.println("PLAYER "+(currentPlayerIndex+1)+ " WIN!!!");
                game.setIsGameOver();
            }
        }else if(deck == null && enemyMap.isValidCoord(point)){//промах
            enemyMap.addMissPoint(point);
            System.out.println("You MISS...");
        }
    }

    public void process(){
        GameState gameState = game.getState();
        if (gameState.equals(GameState.Fill)){
            map.drawMap();
            input.process(gameState);
        }else if (gameState.equals(GameState.Battle)){
            System.out.println("Enemy map:");
            enemyMap.drawMap();
            input.process(gameState);
        }

    }

    public void quitGame(){
        game.setIsGameOver();
    }

    public boolean isMapFilled(){
        return maxShips == ships.size();
    }

    public int getALiveShips(){
        return aLiveShips;
    }

    public Map getMap(){
        return map;
    }
}
