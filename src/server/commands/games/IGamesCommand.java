package server.commands.games;

import server.CommandResponse;
import server.certificates.UserCertificate;

/**
* Represents the notion of executing the appropriate action for a given server
* endpoint that begins with /games/
*/
public interface IGamesCommand {
	
	/**
     * Executes the command specified in the provided JSON
     * 
     * @param user
     *            An encapsulation of a user cookie
     * @param json
     *            A JSON blob containing the required information for the
     *            desired command
	 * @return TODO
     */
	public CommandResponse execute(UserCertificate user, String json);
	
}
