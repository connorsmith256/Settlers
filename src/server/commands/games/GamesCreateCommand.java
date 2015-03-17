package server.commands.games;

import server.CommandResponse;
import server.certificates.GameCertificate;
import server.certificates.UserCertificate;
import server.commands.moves.AbstractMovesCommand;
import server.core.ICortex;

/**
 * Games command created when the user attempts to create a game
 *
 */
public class GamesCreateCommand extends AbstractGamesCommand {

	public GamesCreateCommand(String json, ICortex cortex) {
		super(cortex);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean authenticate(UserCertificate userCert) {
		return false;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public CommandResponse execute() {
		return null;
	}

}
