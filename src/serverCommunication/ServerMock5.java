package serverCommunication;

import java.util.ArrayList;
import java.util.Collection;

import shared.definitions.CatanColor;
import shared.definitions.ResourceType;
import shared.locations.EdgeLocation;
import shared.locations.HexLocation;
import shared.locations.VertexLocation;
import clientBackend.CatanSerializer;
import clientBackend.model.CatanException;
import clientBackend.model.Facade;
import clientBackend.model.Game;

public class ServerMock5 implements ServerInterface {
	private int version;
	private Facade facade;

	public ServerMock5(Facade facade) {
		this.version = 1;
		this.facade = facade;
	}

	public void sendDummyData(boolean incrementVersion) {
		try {
			String json = "{\"deck\":{\"yearOfPlenty\":2,\"monopoly\":2,\"soldier\":14,\"roadBuilding\":2,\"monument\":5},\"map\":{\"hexes\":[{\"location\":{\"x\":0,\"y\":-2}},{\"resource\":\"brick\",\"location\":{\"x\":1,\"y\":-2},\"number\":4},{\"resource\":\"wood\",\"location\":{\"x\":2,\"y\":-2},\"number\":11},{\"resource\":\"brick\",\"location\":{\"x\":-1,\"y\":-1},\"number\":8},{\"resource\":\"wood\",\"location\":{\"x\":0,\"y\":-1},\"number\":3},{\"resource\":\"ore\",\"location\":{\"x\":1,\"y\":-1},\"number\":9},{\"resource\":\"sheep\",\"location\":{\"x\":2,\"y\":-1},\"number\":12},{\"resource\":\"ore\",\"location\":{\"x\":-2,\"y\":0},\"number\":5},{\"resource\":\"sheep\",\"location\":{\"x\":-1,\"y\":0},\"number\":10},{\"resource\":\"wheat\",\"location\":{\"x\":0,\"y\":0},\"number\":11},{\"resource\":\"brick\",\"location\":{\"x\":1,\"y\":0},\"number\":5},{\"resource\":\"wheat\",\"location\":{\"x\":2,\"y\":0},\"number\":6},{\"resource\":\"wheat\",\"location\":{\"x\":-2,\"y\":1},\"number\":2},{\"resource\":\"sheep\",\"location\":{\"x\":-1,\"y\":1},\"number\":9},{\"resource\":\"wood\",\"location\":{\"x\":0,\"y\":1},\"number\":4},{\"resource\":\"sheep\",\"location\":{\"x\":1,\"y\":1},\"number\":10},{\"resource\":\"wood\",\"location\":{\"x\":-2,\"y\":2},\"number\":6},{\"resource\":\"ore\",\"location\":{\"x\":-1,\"y\":2},\"number\":3},{\"resource\":\"wheat\",\"location\":{\"x\":0,\"y\":2},\"number\":8}],\"roads\":[{\"owner\":1,\"location\":{\"direction\":\"S\",\"x\":-1,\"y\":-1}},{\"owner\":3,\"location\":{\"direction\":\"SW\",\"x\":-1,\"y\":1}},{\"owner\":3,\"location\":{\"direction\":\"SW\",\"x\":2,\"y\":-2}},{\"owner\":2,\"location\":{\"direction\":\"S\",\"x\":1,\"y\":-1}},{\"owner\":0,\"location\":{\"direction\":\"S\",\"x\":0,\"y\":1}},{\"owner\":2,\"location\":{\"direction\":\"S\",\"x\":0,\"y\":0}},{\"owner\":1,\"location\":{\"direction\":\"SW\",\"x\":-2,\"y\":1}},{\"owner\":0,\"location\":{\"direction\":\"SW\",\"x\":2,\"y\":0}}],\"cities\":[],\"settlements\":[{\"owner\":3,\"location\":{\"direction\":\"SW\",\"x\":-1,\"y\":1}},{\"owner\":3,\"location\":{\"direction\":\"SE\",\"x\":1,\"y\":-2}},{\"owner\":2,\"location\":{\"direction\":\"SW\",\"x\":0,\"y\":0}},{\"owner\":2,\"location\":{\"direction\":\"SW\",\"x\":1,\"y\":-1}},{\"owner\":1,\"location\":{\"direction\":\"SW\",\"x\":-2,\"y\":1}},{\"owner\":0,\"location\":{\"direction\":\"SE\",\"x\":0,\"y\":1}},{\"owner\":1,\"location\":{\"direction\":\"SW\",\"x\":-1,\"y\":-1}},{\"owner\":0,\"location\":{\"direction\":\"SW\",\"x\":2,\"y\":0}}],\"radius\":3,\"ports\":[{\"ratio\":2,\"resource\":\"brick\",\"direction\":\"NE\",\"location\":{\"x\":-2,\"y\":3}},{\"ratio\":3,\"direction\":\"SW\",\"location\":{\"x\":3,\"y\":-3}},{\"ratio\":3,\"direction\":\"SE\",\"location\":{\"x\":-3,\"y\":0}},{\"ratio\":2,\"resource\":\"sheep\",\"direction\":\"NW\",\"location\":{\"x\":3,\"y\":-1}},{\"ratio\":2,\"resource\":\"ore\",\"direction\":\"S\",\"location\":{\"x\":1,\"y\":-3}},{\"ratio\":3,\"direction\":\"NW\",\"location\":{\"x\":2,\"y\":1}},{\"ratio\":3,\"direction\":\"N\",\"location\":{\"x\":0,\"y\":3}},{\"ratio\":2,\"resource\":\"wood\",\"direction\":\"NE\",\"location\":{\"x\":-3,\"y\":2}},{\"ratio\":2,\"resource\":\"wheat\",\"direction\":\"S\",\"location\":{\"x\":-1,\"y\":-2}}],\"robber\":{\"x\":0,\"y\":-2}},\"players\":[{\"resources\":{\"brick\":0,\"wood\":3,\"sheep\":1,\"wheat\":1,\"ore\":0},\"oldDevCards\":{\"yearOfPlenty\":0,\"monopoly\":0,\"soldier\":0,\"roadBuilding\":0,\"monument\":0},\"newDevCards\":{\"yearOfPlenty\":0,\"monopoly\":0,\"soldier\":0,\"roadBuilding\":0,\"monument\":0},\"roads\":13,\"cities\":4,\"settlements\":3,\"soldiers\":0,\"victoryPoints\":2,\"monuments\":0,\"playedDevCard\":false,\"discarded\":false,\"playerID\":0,\"playerIndex\":0,\"name\":\"Sam\",\"color\":\"orange\"},{\"resources\":{\"brick\":1,\"wood\":0,\"sheep\":1,\"wheat\":5,\"ore\":1},\"oldDevCards\":{\"yearOfPlenty\":0,\"monopoly\":0,\"soldier\":0,\"roadBuilding\":0,\"monument\":0},\"newDevCards\":{\"yearOfPlenty\":0,\"monopoly\":0,\"soldier\":0,\"roadBuilding\":0,\"monument\":0},\"roads\":13,\"cities\":4,\"settlements\":3,\"soldiers\":0,\"victoryPoints\":2,\"monuments\":0,\"playedDevCard\":false,\"discarded\":false,\"playerID\":1,\"playerIndex\":1,\"name\":\"Brooke\",\"color\":\"blue\"},{\"resources\":{\"brick\":0,\"wood\":3,\"sheep\":1,\"wheat\":1,\"ore\":0},\"oldDevCards\":{\"yearOfPlenty\":0,\"monopoly\":0,\"soldier\":0,\"roadBuilding\":0,\"monument\":0},\"newDevCards\":{\"yearOfPlenty\":0,\"monopoly\":0,\"soldier\":0,\"roadBuilding\":0,\"monument\":0},\"roads\":13,\"cities\":4,\"settlements\":3,\"soldiers\":0,\"victoryPoints\":2,\"monuments\":0,\"playedDevCard\":false,\"discarded\":false,\"playerID\":10,\"playerIndex\":2,\"name\":\"Pete\",\"color\":\"red\"},{\"resources\":{\"brick\":2,\"wood\":1,\"sheep\":1,\"wheat\":0,\"ore\":1},\"oldDevCards\":{\"yearOfPlenty\":0,\"monopoly\":0,\"soldier\":0,\"roadBuilding\":0,\"monument\":0},\"newDevCards\":{\"yearOfPlenty\":0,\"monopoly\":0,\"soldier\":0,\"roadBuilding\":0,\"monument\":0},\"roads\":13,\"cities\":4,\"settlements\":3,\"soldiers\":0,\"victoryPoints\":2,\"monuments\":0,\"playedDevCard\":false,\"discarded\":false,\"playerID\":11,\"playerIndex\":3,\"name\":\"Mark\",\"color\":\"green\"}],\"log\":{\"lines\":[{\"source\":\"Pete\",\"message\":\"Pete'sturnjustended\"},{\"source\":\"Mark\",\"message\":\"Markbuiltaroad\"},{\"source\":\"Mark\",\"message\":\"Markbuiltasettlement\"},{\"source\":\"Mark\",\"message\":\"Mark'sturnjustended\"},{\"source\":\"Mark\",\"message\":\"Markbuiltaroad\"},{\"source\":\"Mark\",\"message\":\"Markbuiltasettlement\"},{\"source\":\"Mark\",\"message\":\"Mark'sturnjustended\"},{\"source\":\"Pete\",\"message\":\"Petebuiltaroad\"},{\"source\":\"Pete\",\"message\":\"Petebuiltasettlement\"},{\"source\":\"Pete\",\"message\":\"Pete'sturnjustended\"},{\"source\":\"Brooke\",\"message\":\"Brookebuiltaroad\"},{\"source\":\"Brooke\",\"message\":\"Brookebuiltasettlement\"},{\"source\":\"Brooke\",\"message\":\"Brooke'sturnjustended\"},{\"source\":\"Sam\",\"message\":\"Sambuiltaroad\"},{\"source\":\"Sam\",\"message\":\"Sambuiltasettlement\"},{\"source\":\"Sam\",\"message\":\"Sam'sturnjustended\"},{\"source\":\"Sam\",\"message\":\"Samrolleda2.\"},{\"source\":\"Sam\",\"message\":\"Samrolleda2.\"},{\"source\":\"Sam\",\"message\":\"Samrolleda2.\"},{\"source\":\"Sam\",\"message\":\"Samrolleda2.\"},{\"source\":\"Sam\",\"message\":\"Samrolleda2.\"},{\"source\":\"Sam\",\"message\":\"Samrolleda4.\"},{\"source\":\"Sam\",\"message\":\"Samrolleda4.\"},{\"source\":\"Sam\",\"message\":\"Samrolleda7.\"},{\"source\":\"Sam\",\"message\":\"Samrolleda7.\"},{\"source\":\"Sam\",\"message\":\"Samrolleda7.\"},{\"source\":\"Sam\",\"message\":\"Samrolleda7.\"},{\"source\":\"Sam\",\"message\":\"Samrolleda7.\"},{\"source\":\"Sam\",\"message\":\"Samrolleda7.\"},{\"source\":\"Sam\",\"message\":\"Samrolleda7.\"}]},\"chat\":{\"lines\":[]},\"bank\":{\"brick\":21,\"wood\":17,\"sheep\":20,\"wheat\":17,\"ore\":22},\"turnTracker\":{\"status\":\"Discarding\",\"currentTurn\":0,\"longestRoad\":-1,\"largestArmy\":-1},\"winner\":-1,\"version\":0}";
			if (incrementVersion) {
				this.version++;
			}
			int idx = json.indexOf("version") + "version".length() + 2;
			json = json.substring(0, idx) + this.version + "}";
			CatanSerializer.getInstance().setFacade(this.facade);
			CatanSerializer.getInstance().deserializeModel(json);
		} catch (CatanException e) {
			e.printStackTrace();
		}
	}

	@Override
	public boolean userLogin(String username, String password) throws ServerException {
		return true;
	}

	@Override
	public boolean userRegister(String username, String password) throws ServerException {
		return true;
	}

	@Override
	public Collection<Game> gamesList() throws ServerException {
		//		String json = "[{'title':'DefaultGame','id':0,'players':[{'color':'orange','name':'Sam','id':0},{'color':'blue','name':'Brooke','id':1},{'color':'red','name':'Pete','id':10},{'color':'green','name':'Mark','id':11}]},{'title':'AIGame','id':1,'players':[{'color':'orange','name':'Pete','id':10},{'color':'puce','name':'Steve','id':-2},{'color':'blue','name':'Squall','id':-3},{'color':'yellow','name':'Quinn','id':-4}]},{'title':'EmptyGame','id':2,'players':[{'color':'orange','name':'Sam','id':0},{'color':'blue','name':'Brooke','id':1},{'color':'red','name':'Pete','id':10},{'color':'green','name':'Mark','id':11}]}]";
		return new ArrayList<Game>();
	}

	@Override
	public void gamesCreate(boolean randomTiles, boolean randomNumbers, boolean randomPorts,
			String name) throws ServerException {
		//		String json = "{'title':'dummy','id':3,'players':[{},{},{},{}]}";
	}

	@Override
	public boolean gamesJoin(int gameId, CatanColor color) throws ServerException {
		return true;
	}

	@Override
	public boolean gamesSave(int gameId, String name) throws ServerException {
		return true;
	}

	@Override
	public boolean gamesLoad(String name) throws ServerException {
		return true;
	}

	@Override
	public void gameModel(int version) throws ServerException {
		this.sendDummyData(true);
	}

	@Override
	public void gameReset() throws ServerException {
		this.sendDummyData(true);
	}

	@Override
	public void gameCommands() throws ServerException {

	}

	@Override
	public void gameCommands(String commandList) throws ServerException {
		//		String json = "[]";
	}

	@Override
	public void gameAddAI(String AIType) throws ServerException {

	}

	@Override
	public void gameListAI() throws ServerException {
		//		String json = "['LARGEST_ARMY']";
	}

	@Override
	public void movesSendChat(int playerIndex, String content) throws ServerException {
		this.sendDummyData(true);
	}

	@Override
	public void movesRollNumber(int playerIndex, int number) throws ServerException {
		this.sendDummyData(true);
	}

	@Override
	public void movesRobPlayer(int playerIndex, int victimIndex,
			HexLocation location) throws ServerException {
		this.sendDummyData(true);
	}

	@Override
	public void movesFinishTurn(String type, int playerIndex) throws ServerException {
		this.sendDummyData(true);
	}

	@Override
	public void movesBuyDevCard(String type, int playerIndex) throws ServerException {
		this.sendDummyData(true);
	}

	@Override
	public void movesYear_of_Plenty(int playerIndex, ResourceType resource1,
			ResourceType resource2) throws ServerException {
		this.sendDummyData(true);
	}

	@Override
	public void movesRoad_Building(int playerIndex, EdgeLocation spot1, EdgeLocation spot2)
			throws ServerException {
		this.sendDummyData(true);
	}

	@Override
	public void movesSoldier(int playerIndex, int victimIndex,
			HexLocation location) throws ServerException {
		this.sendDummyData(true);
	}

	@Override
	public void movesMonopoly(ResourceType resource, int playerIndex)
			throws ServerException {
		this.sendDummyData(true);
	}

	@Override
	public void movesMonument(String type, int playerIndex) throws ServerException {
		this.sendDummyData(true);
	}

	@Override
	public void movesBuildRoad(int playerIndex, EdgeLocation location, boolean free)
			throws ServerException {
		this.sendDummyData(true);
	}

	@Override
	public void movesBuildSettlement(int playerIndex, VertexLocation location, boolean free)
			throws ServerException {
		this.sendDummyData(true);
	}

	@Override
	public void movesBuildCity(int playerIndex, VertexLocation location)
			throws ServerException {
		this.sendDummyData(true);
	}

	@Override
	public void movesOfferTrade(int playerIndex, int brick, int ore, int sheep, int wheat,
			int wood, int receiver) throws ServerException {
		this.sendDummyData(true);
	}

	@Override
	public void movesAcceptTrade(int playerIndex, boolean willAccept)
			throws ServerException {
		this.sendDummyData(true);
	}

	@Override
	public void movesMaritimeTrade(int playerIndex, int ratio, ResourceType inputResource,
			ResourceType outputResource) throws ServerException {
		this.sendDummyData(true);
	}

	@Override
	public void movesDiscardCards(int playerIndex, int brick, int ore, int sheep,
			int wheat, int wood) throws ServerException {
		this.sendDummyData(true);
	}

	@Override
	public boolean utilChangeLogLevel(String logLevel) throws ServerException {
		return false;
	}

}
