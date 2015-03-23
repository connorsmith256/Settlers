package server.commands.moves;

import com.google.gson.JsonParseException;

import client.backend.CatanSerializer;
import client.serverCommunication.ServerException;
import server.core.CortexFactory;
import server.core.ICortex;
import shared.dataTransportObjects.DTOMovesRollNumber;
import shared.definitions.PlayerNumber;
import shared.model.CatanException;
import shared.transport.TransportModel;

/**
 * Moves command created when a user attempts to roll the dice.
 */
public class MovesRollNumberCommand extends AbstractMovesCommand {

	private PlayerNumber playerIndex;
	private int number;

	public MovesRollNumberCommand(String json) {
		DTOMovesRollNumber dto = (DTOMovesRollNumber) CatanSerializer.getInstance()
				.deserializeObject(json, DTOMovesRollNumber.class);

		if (dto.playerIndex == null) {
			throw new JsonParseException("JSON parse error");
		}

		this.playerIndex = dto.playerIndex;
		this.number = dto.number;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public TransportModel performMovesCommand() throws CatanException, ServerException {
		ICortex cortex = CortexFactory.getInstance().getCortex();
		return cortex.movesRollNumber(this.playerIndex, this.number);
	}

}
