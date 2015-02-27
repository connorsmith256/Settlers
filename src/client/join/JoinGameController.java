package client.join;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Timer;
import java.util.TimerTask;

import shared.definitions.CatanColor;
import shared.definitions.PlayerNumber;
import client.base.*;
import client.data.*;
import client.misc.*;
import clientBackend.dataTransportObjects.DTOGame;
import clientBackend.dataTransportObjects.DTOPlayer;
import clientBackend.model.CatanException;
import clientBackend.model.Facade;

/**
 * Implementation for the join game controller
 */
public class JoinGameController extends Controller implements IJoinGameController {

	private INewGameView newGameView;
	private ISelectColorView selectColorView;
	private IMessageView messageView;
	private IAction joinAction;

	private Facade facade;
	private GameInfo curGame;
	boolean isPolling = false;
	boolean showHub = true;

	/**
	 * JoinGameController constructor
	 *
	 * @param view
	 *            Join game view
	 * @param newGameView
	 *            New game view
	 * @param selectColorView
	 *            Select color view
	 * @param messageView
	 *            Message view (used to display error messages that occur while
	 *            the user is joining a game)
	 */
	public JoinGameController(IJoinGameView view, INewGameView newGameView,
			ISelectColorView selectColorView, IMessageView messageView) {

		super(view);

		this.facade = Facade.getInstance();
		this.setNewGameView(newGameView);
		this.setSelectColorView(selectColorView);
		this.setMessageView(messageView);
	}

	public IJoinGameView getJoinGameView() {
		return (IJoinGameView) super.getView();
	}

	/**
	 * Returns the action to be executed when the user joins a game
	 *
	 * @return The action to be executed when the user joins a game
	 */
	public IAction getJoinAction() {
		return this.joinAction;
	}

	/**
	 * Sets the action to be executed when the user joins a game
	 *
	 * @param value
	 *            The action to be executed when the user joins a game
	 */
	public void setJoinAction(IAction value) {
		this.joinAction = value;
	}

	public INewGameView getNewGameView() {
		return this.newGameView;
	}

	public void setNewGameView(INewGameView newGameView) {
		this.newGameView = newGameView;
	}

	public ISelectColorView getSelectColorView() {
		return this.selectColorView;
	}

	public void setSelectColorView(ISelectColorView selectColorView) {
		this.selectColorView = selectColorView;
	}

	public IMessageView getMessageView() {
		return this.messageView;
	}

	public void setMessageView(IMessageView messageView) {
		this.messageView = messageView;
	}

	@Override
	public void start() {
		if (!this.isPolling) {
			this.isPolling = true;

			new Timer().schedule(new TimerTask() {
				@Override
				public void run() {
					JoinGameController.this.setGamesList();
				}
			}, 0, 3000); // Period delayed is in milliseconds. (e.g. 3000 ms = 3 sec)
		}

		this.setGamesList();
	}

	public void setGamesList() {
		if (this.showHub == false) {
			return;
		}

		Collection<DTOGame> gamesList = this.facade.getGamesList();
		Collection<GameInfo> gameInfoList = new ArrayList<GameInfo>();

		for (DTOGame game : gamesList) {
			GameInfo curGame = new GameInfo();

			curGame.setId(game.id);
			curGame.setTitle(game.title);

			for (DTOPlayer player : game.players) {
				if (player.id == -1) {
					continue;
				}

				int id = player.id;
				String name = player.name;
				CatanColor color = player.color;
				PlayerNumber index = PlayerNumber.BANK;

				PlayerInfo curPlayer = new PlayerInfo(id, index, name, color);
				curGame.addPlayer(curPlayer);
			}
			gameInfoList.add(curGame);
		}

		GameInfo[] gameInfoArray = gameInfoList.toArray(new GameInfo[0]);
		this.getJoinGameView().closeModal();
		this.getJoinGameView().setGames(gameInfoArray, this.facade.getClientPlayer());
		this.getJoinGameView().showModal();
	}

	@Override
	public void startCreateNewGame() {
		this.showHub = false;
		this.getNewGameView().showModal();
	}

	@Override
	public void cancelCreateNewGame() {
		this.showHub = true;
		this.getNewGameView().closeModal();
	}

	@Override
	public void createNewGame() {
		boolean randomTiles;
		boolean randomNumbers;
		boolean randomPorts;
		String gameName;

		randomTiles = this.newGameView.getRandomlyPlaceHexes();
		randomNumbers = this.newGameView.getRandomlyPlaceNumbers();
		randomPorts = this.newGameView.getUseRandomPorts();
		gameName = this.newGameView.getTitle();

		try {
			this.facade.createGame(randomTiles, randomNumbers, randomPorts, gameName);
			this.getNewGameView().closeModal();
			this.showHub = true;
			this.setGamesList();
		} catch (CatanException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void startJoinGame(GameInfo game) {
		this.showHub = false;
		this.curGame = game;
		for (PlayerInfo player : game.getPlayers()) {
			CatanColor color = player.getColor();
			if (player.getId() != this.facade.getClientPlayer().getId()) {
				this.getSelectColorView().setColorEnabled(color, false);
			}
		}
		this.getSelectColorView().showModal();
	}

	@Override
	public void cancelJoinGame() {
		this.showHub = true;
		this.getSelectColorView().closeModal();
	}

	@Override
	public void joinGame(CatanColor color) {
		boolean success = this.facade.joinGame(this.curGame.getId(), color);
		if (success) {
			this.getSelectColorView().closeModal();
			this.getJoinGameView().closeModal();
			this.joinAction.execute();
		}
	}
}
