package clientBackend.dataTransportObjects;

import java.util.HashMap;
import java.util.Map;

public class DTOMovesBuildRoad {
	String type = "buildRoad";
	int playerIndex;
	Map<String, Object> roadLocation;
	Boolean free = false;
	
	public DTOMovesBuildRoad(int playerIndex, int x, int y, String direction, Boolean free) {
		this.playerIndex = playerIndex;
		
		roadLocation = new HashMap<>();
		roadLocation.put("x", x);
		roadLocation.put("y", y);
		roadLocation.put("direction", direction);
		this.free = free;
	}
}