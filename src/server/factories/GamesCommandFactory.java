package server.factories;

import server.commands.games.*;

/**
 * A factory for creating instances of IGamesCommand corresponding to a given
 * request. The factory uses a string to determine the appropriate command type,
 * then returns an instance of that type.
 */
public class GamesCommandFactory {

	private static GamesCommandFactory instance;

	private GamesCommandFactory() {

	}

	public static GamesCommandFactory getInstance() {
		if (instance == null) {
			instance = new GamesCommandFactory();
		}
		return instance;
	}

	/**
	 * Parses the given type and returns an instance of a corresponding command
	 *
	 * @param type
	 *            A String containing the type of the command
	 * @param json
	 *            A JSON blob containing the information for the command
	 * @return
	 */
	public AbstractGamesCommand getCommand(String type, String json) {
		AbstractGamesCommand cmd = null;

		switch (type) {
		case "list":
			cmd = new GamesListCommand(json);
			break;
		case "create":
			cmd = new GamesCreateCommand(json);
			break;
		case "join":
			cmd = new GamesJoinCommand(json);
			break;
		case "save":
			cmd = new GamesSaveCommand(json);
			break;
		case "load":
			cmd = new GamesLoadCommand(json);
			break;
		}

		return cmd;
	}
}
