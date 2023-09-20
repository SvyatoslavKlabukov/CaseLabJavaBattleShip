import java.util.Arrays;

public class Ship {
    private Deck [] decks;
    private DeckCount deckCount;
    private Orientation orientation;
    private Point[] coords;

    public Ship(Map map, DeckCount deckCount, Orientation orientation, Point[] coords){
        this.deckCount = deckCount;
        this.orientation = orientation;
        this.coords = coords;

        decks = new Deck[deckCount.getValue()];
        for( int i = 0; i < decks.length; i++) {
            Deck deck = new Deck( coords[i], 'O', 'X' );
            decks[i] = deck;

            map.addObject(deck);
        }
    }

    public DeckCount getDeckCount() {
        return deckCount;
    }


    public static Point[] getCoordsForShip(Map map, DeckCount deckCount, Orientation orientation, Point startCoord){
        Point step = null;
        if (orientation == Orientation.Horizontal){
            step = new Point(1, 0);
        } else if (orientation == Orientation.Vertical) {
            step = new Point(0, 1);
        }else {
            return null;
        }
        boolean isPossiblePlace = true;
        Point[] coords = new Point[deckCount.getValue()];
        Point position = new Point( startCoord.x, startCoord.y );
        for( int i = 0; i < deckCount.getValue(); i++ ){
            isPossiblePlace = map.isValidCoord(position) &&
                    !map.isCollide(position) &&
                    !map.hasNeighbors(position);
            if (!isPossiblePlace){
                break;
            }
            coords[i] = new Point( position.x, position.y );
            position.x += step.x;
            position.y += step.y;
        }
        if (isPossiblePlace){
            return coords;
        }
        return null;
    }
}
