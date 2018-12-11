import net.rithms.riot.api.ApiConfig;
import net.rithms.riot.api.RiotApi;
import net.rithms.riot.api.RiotApiException;
import net.rithms.riot.api.endpoints.match.dto.MatchList;
import net.rithms.riot.api.endpoints.match.dto.MatchReference;
import net.rithms.riot.api.endpoints.summoner.dto.Summoner;
import net.rithms.riot.constant.Platform;

/**
 * This example demonstrates using the RiotApi to request the match list for a given summoner name and iterating over the match list
 */
public class MatchListExample {

	public static void main(String[] args) throws RiotApiException {
		ApiConfig config = new ApiConfig().setKey("RGAPI-eafa8693-6d7d-45c2-aa53-d78672a0a954");
		RiotApi api = new RiotApi(config);

		// First we need to request the summoner because we will need it's account ID
		Summoner summoner = api.getSummonerByName(Platform.NA, "TSM Grig");

		// Then we can use the account ID to request the summoner's match list
		MatchList matchList = api.getMatchListByAccountId(Platform.NA, summoner.getAccountId());

		System.out.println("Total Games in requested match list: " + matchList.getTotalGames());

		// We can now iterate over the match list to access the data
		if (matchList.getMatches() != null) {
			for (MatchReference match : matchList.getMatches()) {
				System.out.println("GameID: " + match.getGameId());
			}
		}
	}
}