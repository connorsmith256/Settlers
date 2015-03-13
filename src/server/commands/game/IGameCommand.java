package server.commands.game;

import shared.model.User;

/**
* Represents the notion of executing the appropriate action for a given server
* endpoint that begins with /game/
*/

public interface IGameCommand {
	
	/**
     * Executes the command specified in the provided JSON
     * 
     * @param user
     *            An encapsulation of a user cookie
     * @param gameId
     *            A game cookie
     * @param json
     *            A JSON blob containing the required information for the
     *            desired command
     */
	public void execute(User user, int gameId, String json);

}
