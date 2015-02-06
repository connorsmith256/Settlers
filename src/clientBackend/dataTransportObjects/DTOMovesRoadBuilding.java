package clientBackend.dataTransportObjects;

import java.util.HashMap;
import java.util.Map;

public class DTOMovesRoadBuilding {
	String type = "Road_Building";
	int playerIndex;
	Map<String, Object> spot1;
	Map<String, Object> spot2;
	
	public DTOMovesRoadBuilding(int playerIndex, int spot1X, int spot1Y, String spot1Direction, int spot2X, int spot2Y, String spot2Direction) {
		this.playerIndex = playerIndex;
		
		spot1 = new HashMap<>();
		spot2 = new HashMap<>();
		
		spot1.put("x", spot1X);
		spot1.put("y", spot1Y);
		spot1.put("direction", spot1Direction);
		spot2.put("x", spot2X);
		spot2.put("y", spot2Y);
		spot2.put("direction", spot2Direction);
	}
}