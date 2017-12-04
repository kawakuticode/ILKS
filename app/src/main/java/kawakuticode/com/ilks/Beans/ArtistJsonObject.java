package kawakuticode.com.ilks.Beans;

import com.fasterxml.jackson.annotation.JsonSetter;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.util.List;

@JsonSerialize
public class ArtistJsonObject {
	
	private Long id_event; 
	private List <Artist> artists;
	
	
	public Long getId() {
		return id_event;
	}
	
	@JsonSetter("id_event")
	public void setId(Long id) {
		this.id_event = id;
	}
	
	public List<Artist> getArtists() {
		return artists;
	}
	@JsonSetter("artists")
	public void setArtists_list(List<Artist> artists_list) {
		this.artists = artists_list;
	}
	@Override
	public String toString() {
		return "ArtistJsonObject [id=" + id_event + " artists_list=" + artists.size() + "]";
	} 
	

}
