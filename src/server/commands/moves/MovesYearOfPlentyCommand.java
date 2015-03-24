package server.commands.moves;

import com.google.gson.JsonParseException;

import client.backend.CatanSerializer;
import client.serverCommunication.ServerException;
import server.core.CortexFactory;
import server.core.ICortex;
import shared.dataTransportObjects.DTOMovesYearOfPlenty;
import shared.definitions.PlayerNumber;
import shared.definitions.ResourceType;
import shared.model.CatanException;
import shared.transport.TransportModel;

/**
 * Moves command created when a user attempts to play a Year of Plenty card.
 *
 */
public class MovesYearOfPlentyCommand extends AbstractMovesCommand {

	private PlayerNumber playerIndex;
	private ResourceType resource1;
	private ResourceType resource2;

	public MovesYearOfPlentyCommand(String json) {
		DTOMovesYearOfPlenty dto = (DTOMovesYearOfPlenty) CatanSerializer.getInstance()
				.deserializeObject(json, DTOMovesYearOfPlenty.class);

		if (dto.playerIndex == null || dto.resource1 == null || dto.resource2 == null) {
			throw new JsonParseException("JSON parse error");
		}

		this.playerIndex = dto.playerIndex;
		this.resource1 = dto.resource1;
		this.resource2 = dto.resource2;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public TransportModel performMovesCommand() throws CatanException, ServerException {
		ICortex cortex = CortexFactory.getInstance().getCortex();
		return cortex.movesYearOfPlenty(this.playerIndex, this.resource1, this.resource2);
	}

}
