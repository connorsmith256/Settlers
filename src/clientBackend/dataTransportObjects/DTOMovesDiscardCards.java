package clientBackend.dataTransportObjects;

import java.util.HashMap;
import java.util.Map;

public class DTOMovesDiscardCards {
	String type = "discardCards";
	int playerIndex;
	Map<String, Integer> discardedCards;
	
	public DTOMovesDiscardCards(int playerIndex, int brick, int ore, int sheep, int wheat, int wood) {
		this.playerIndex = playerIndex;
		
		discardedCards = new HashMap<>();
		discardedCards.put("brick", brick);
		discardedCards.put("ore", ore);
		discardedCards.put("sheep", sheep);
		discardedCards.put("wheat", wheat);
		discardedCards.put("wood", wood);
	}
}
