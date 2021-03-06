package client.frontend.map.state;

import java.util.ArrayList;
import java.util.Collection;

import client.backend.ClientModelFacade;
import client.frontend.data.PlayerInfo;
import client.frontend.data.RobPlayerInfo;
import client.frontend.map.IMapView;
import client.frontend.map.IRobView;
import client.frontend.map.MapController;
import client.frontend.map.TypeConverter;
import shared.definitions.CatanColor;
import shared.definitions.CatanState;
import shared.definitions.HexType;
import shared.definitions.PieceType;
import shared.definitions.PlayerNumber;
import shared.definitions.PortType;
import shared.definitions.PropertyType;
import shared.definitions.ResourceType;
import shared.locations.*;
import shared.model.Board;
import shared.model.BoardFactory;
import shared.model.CatanException;
import shared.model.Chit;
import shared.model.Dwelling;
import shared.model.Harbor;
import shared.model.Road;
import shared.model.Tile;

public class DefaultMapControllerState {

	protected ClientModelFacade facade;
	protected MapController controller;
	protected IMapView view;
	protected IRobView robView;

	public DefaultMapControllerState(
			ClientModelFacade facade,
			MapController controller,
			IMapView view,
			IRobView robView) {
		this.facade = facade;
		this.controller = controller;
		this.view = view;
		this.robView = robView;
	}

	public void initFromModel() {
		if (!this.facade.isGameReady()) {
			return;
		}

		Board board = this.facade.getBoard();

		// setup tiles
		for (Tile tile : board.getTiles().values()) {
			HexLocation location = tile.getLocation();
			HexType type = TypeConverter.toHexType(tile.getResourceType());
			this.view.addHex(location, type);
		}

		// setup chits
		for (Collection<Chit> collection : board.getChits().values()) {
			for (Chit chit : collection) {
				this.view.addNumber(chit.getLocation(), chit.getNumber());
			}
		}

		// setup harbors
		for (Harbor harbor : board.getHarbors()) {
			VertexLocation[] ports = harbor.getPorts().toArray(new VertexLocation[0]);
			EdgeLocation edge = Geometer.getSharedEdge(ports[0], ports[1]);

			ArrayList<EdgeLocation> portLocations = BoardFactory.getPortLocations();
			for (EdgeLocation port : portLocations) {
				if (port.getNormalizedLocation().equals(edge.getNormalizedLocation())) {
					edge = port;
					break;
				}
			}

			PortType type = TypeConverter.toPortType(harbor.getResource());
			this.view.addPort(edge, type);
		}

		// setup roads
		for (Road road : board.getRoads().values()) {
			CatanColor color = this.facade.getPlayerColor(road.getOwner());
			this.view.placeRoad(road.getLocation(), color);
		}

		// setup dwellings
		for (Dwelling dwelling : board.getDwellings().values()) {
			CatanColor color = this.facade.getPlayerColor(dwelling.getOwner());
			if (dwelling.getPropertyType() == PropertyType.SETTLEMENT) {
				this.view.placeSettlement(dwelling.getLocation(), color);
			}
			else {
				this.view.placeCity(dwelling.getLocation(), color);
			}
		}

		// setup robber
		this.view.placeRobber(board.getRobberLocation());
	}

	public boolean canPlaceRoad(EdgeLocation edge) {
		return false;
	}

	public void placeRoad(EdgeLocation edge) {

	}

	public boolean canPlaceSettlement(VertexLocation vertex) {
		return false;
	}

	public void placeSettlement(VertexLocation vertex) {

	}

	public boolean canPlaceCity(VertexLocation vertex) {
		return false;
	}

	public void placeCity(VertexLocation vertex) {

	}

	public boolean canPlaceRobber(HexLocation hex) {
		return this.facade.canPlaceRobber(this.facade.getClientPlayerIndex(), hex,
				CatanState.PLAYING);
	}

	public void placeRobber(HexLocation hex) {
		if (this.canPlaceRobber(hex)) {
			Collection<PlayerInfo> players = this.facade.getPlayers();
			Collection<Dwelling> dwellings = this.facade.getBoard().getAdjacentDwellings(hex);
			Collection<RobPlayerInfo> robbablePlayers = new ArrayList<RobPlayerInfo>();

			for (Dwelling dwelling : dwellings) {
				PlayerNumber ownerIdx = dwelling.getOwner();

				for (PlayerInfo info : players) {
					if (info.getPlayerIndex() == this.facade.getClientPlayerIndex()) {
						continue;
					}

					if (ownerIdx == info.getPlayerIndex()) {
						RobPlayerInfo robInfo = new RobPlayerInfo();

						robInfo.setId(info.getId());
						robInfo.setPlayerIndex(info.getPlayerIndex());
						robInfo.setName(info.getName());
						robInfo.setColor(info.getColor());
						int numCards = this.facade.getResourceCount(ownerIdx, ResourceType.ALL);
						robInfo.setNumCards(numCards);

						if (numCards > 0 && !robbablePlayers.contains(robInfo)) {
							robbablePlayers.add(robInfo);
						}
					}
				}
			}

			this.view.placeRobber(hex);
			this.controller.setRobberLocation(hex);

			RobPlayerInfo[] candidateVictims = robbablePlayers.toArray(new RobPlayerInfo[0]);
			if (candidateVictims.length == 0) {
				candidateVictims = new RobPlayerInfo[1];
				RobPlayerInfo nonePlayer = new RobPlayerInfo();
				nonePlayer.setId(-1);
				nonePlayer.setPlayerIndex(PlayerNumber.BANK);
				nonePlayer.setName("None");
				nonePlayer.setColor(CatanColor.WHITE);
				candidateVictims[0] = nonePlayer;
			}
			this.robView.setPlayers(candidateVictims);
			this.robView.showModal();
		}
	}

	public void startMove(PieceType pieceType, boolean isFree, boolean allowDisconnected) {
		this.controller.setModalShowing(true);
		this.view.startDrop(pieceType, this.facade.getClientPlayerColor(), false);
	}

	public void cancelMove() {

	}

	public void playSoldierCard() {
		this.startMove(PieceType.ROBBER, false, false);
	}

	public void playRoadBuildingCard() {

	}

	public void robPlayer(RobPlayerInfo victim) {
		try {
			PlayerNumber clientIndex = this.facade.getClientPlayerIndex();
			PlayerNumber victimIndex = victim.getPlayerIndex();
			HexLocation hex = this.controller.getRobberLocation();
			this.facade.useSoldier(clientIndex, victimIndex, hex);
			this.controller.setModalShowing(false);
		} catch (CatanException e) {
			e.printStackTrace();
		}
	}
}
