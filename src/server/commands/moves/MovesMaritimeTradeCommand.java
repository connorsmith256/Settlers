package server.commands.moves;

import server.CommandResponse;
import server.cookies.GameCookie;
import server.cookies.UserCookie;

/**
 * Moves command created when a user attempts to use Maritime Trade.
 *
 */
public class MovesMaritimeTradeCommand implements IMovesCommand {
	/**
	 * {@inheritDoc}
	 */
	@Override
	public CommandResponse execute(UserCookie user, GameCookie game, String json) {
		return null;
	}
}
