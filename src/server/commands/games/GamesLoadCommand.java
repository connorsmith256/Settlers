package server.commands.games;

import server.certificates.UserCertificate;
import server.commands.CommandResponse;
import server.core.ICortex;

/**
 * Games command created when the user attempts to load a game
 *
 */
public class GamesLoadCommand extends AbstractGamesCommand {

	public GamesLoadCommand(String json, ICortex cortex) {
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
