package fr.sopra.pox3.api;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;

import fr.sopra.pox3.entities.DiscogsSearchArtistsResponse;

public interface DiscogsWebService {

	@Path("/database/search")
	@GET
	@Produces ("application/json")
	public DiscogsSearchArtistsResponse searchArtist(
			@QueryParam ("q") String term, 
			@QueryParam ("type") String MUSTBEARTIST, 
			@QueryParam ("key") String key,
			@QueryParam ("secret") String secret);
}
