package server;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.Arrays;

import com.sun.net.httpserver.HttpServer;

import server.core.CentralCortex;
import server.core.CortexFactory;
import server.handlers.GameHandler;
import server.handlers.GamesHandler;
import server.handlers.MovesHandler;
import server.handlers.UserHandler;
import server.persistence.IPersistenceProvider;
import server.persistence.PersistenceProviderFactory;
import server.util.Handlers;

/**
 * An encapsulation of the server for Catan. Each of the four groups of commands
 * has its own handler which handles routing to the appropriate destination.
 *
 */
public class CatanServer {
	private static CatanServer instance;
	private HttpServer server;
	private UserHandler userHandler;
	private GamesHandler gamesHandler;
	private GameHandler gameHandler;
	private MovesHandler movesHandler;

	private static final int MAX_WAITING_CONNECTIONS = 10;

	private CatanServer(int portNum, IPersistenceProvider persistenceProvider) {
		try {
			InetSocketAddress addr = new InetSocketAddress(portNum);
			this.server = HttpServer.create(addr, MAX_WAITING_CONNECTIONS);
			this.server.setExecutor(null);

			//Server endpoints
			this.userHandler = new UserHandler();
			this.gamesHandler = new GamesHandler();
			this.gameHandler = new GameHandler();
			this.movesHandler = new MovesHandler();
			this.server.createContext("/user", this.userHandler);
			this.server.createContext("/games", this.gamesHandler);
			this.server.createContext("/game", this.gameHandler);
			this.server.createContext("/moves", this.movesHandler);

			//Swagger endpoints
			this.server.createContext("/docs/api/data", new Handlers.JSONAppender(""));
			this.server.createContext("/docs/api/view", new Handlers.BasicFile(""));

			//Initialize Cortex
			CentralCortex.getInstance().setPersistenceProvider(persistenceProvider);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static CatanServer getInstance(int portNum, IPersistenceProvider persistenceProvider) {
		if (instance == null) {
			instance = new CatanServer(portNum, persistenceProvider);
		}
		return instance;
	}

	public UserHandler getUserHandler() {
		return this.userHandler;
	}

	public void setUserHandler(UserHandler userHandler) {
		this.userHandler = userHandler;
	}

	public GamesHandler getGamesHandler() {
		return this.gamesHandler;
	}

	public void setGamesHandler(GamesHandler gamesHandler) {
		this.gamesHandler = gamesHandler;
	}

	public GameHandler getGameHandler() {
		return this.gameHandler;
	}

	public void setGameHandler(GameHandler gameHandler) {
		this.gameHandler = gameHandler;
	}

	public MovesHandler getMovesHandler() {
		return this.movesHandler;
	}

	public void setMovesHandler(MovesHandler movesHandler) {
		this.movesHandler = movesHandler;
	}

	/**
	 * Start the server
	 */
	public void start() {
		this.server.start();
	}

	public static void main(String args[]) {
		int portNum = args.length > 0 ? Integer.parseInt(args[0]) : 8081;
		ArrayList<String> argsList = new ArrayList<String>(Arrays.asList(args));
		boolean testingEnabled = argsList.contains("true");
		CortexFactory.setTestEnabled(testingEnabled);
		
		PersistenceProviderFactory persistenceProviderFactory = new PersistenceProviderFactory();
		IPersistenceProvider persistenceProvider = persistenceProviderFactory.getPersistenceProvider(args[2]);
		
		CatanServer server = CatanServer.getInstance(portNum, persistenceProvider);
		server.start();
	}
}
