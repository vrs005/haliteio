import java.util.ArrayList;
import java.util.Random;

public class MyBot5 {
    public static void main(String[] args) throws java.io.IOException {
        InitPackage iPackage = Networking.getInit();
        int myID = iPackage.myID;
        GameMap gameMap = iPackage.map;
        
        Random rand = new Random();

        Networking.sendInit("JavaBot5");

        while(true) {
            ArrayList<Move> moves = new ArrayList<Move>();

            Networking.updateFrame(gameMap);
            
            for(int y = 0; y < gameMap.height; y++) {
                for(int x = 0; x < gameMap.width; x++) {
                	final Location location = gameMap.getLocation(x, y);
                    final Site site = location.getSite();
                    if(site.owner == myID) {

                        boolean movedPiece = false;                     
                        boolean border = false;

                        for(Direction d : Direction.CARDINALS) {
                            if(gameMap.getSite(location,d).owner != myID ) {
                            		border = true;
                            		if(gameMap.getSite(location,d).strength < site.strength) {
                                moves.add(new Move(location, d));
                                movedPiece = true;
                                break;
                            		}
                            	}
                            }
                            
                        if(!movedPiece && site.strength < site.production * 5) {
                            moves.add(new Move(location, Direction.STILL));
                            movedPiece = true;
                        }

                        if(!movedPiece) {
                        	if(!border){
                        		moves.add(new Move(location, rand.nextBoolean() ? Direction.NORTH : Direction.WEST));
                        		movedPiece = true;
                        	} else{
                            moves.add(new Move(location, Direction.STILL));
                        	}
                        }
                        
                    }
                }
            }

            Networking.sendFrame(moves);
        }
    }
}