package kawakuticode.com.ilks.Beans;


import android.os.Parcel;
import android.os.Parcelable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonSetter;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

/**
 * Created by russeliusernestius on 18/09/17.
 */
@JsonSerialize
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonPropertyOrder({ "id", "name","description" , "cover","artists","start_time"
	                , "end_time","place","ticket_uri" , "isCanceled","interested_count", "attending_count" })

public class FacebookEvent implements Parcelable, Comparator<FacebookEvent>{

    private Long id;
    private String name;
    private String description; 
	private Cover cover;
    private List<Artist> artists;
    private Date start_time;
    private Date end_time;
    private Place place;
    private String ticket_uri;
    private boolean isCanceled;
    private int interested_count;
    private int attending_count;

    public FacebookEvent() {
        artists = new ArrayList<>();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Cover getCover() {
        return cover;
    }

    public void setCover(Cover cover) {
        this.cover = cover;
    }
    public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

    @JsonSetter("artists")
    public List <Artist> getArtists() {
        return artists;
    }

    public void setArtists(List<Artist> artists) {
        this.artists = artists;
    }

    public Date getStart_time() {
        return start_time;
    }

    public void setStart_time(Date start_time) {
        this.start_time = start_time;
    }

    public Date getEnd_time() {
        return end_time;
    }

    public void setEnd_time(Date end_time) {
        this.end_time = end_time;
    }

    public Place getPlace() {
        return place;
    }

    public void setPlace(Place place) {
        this.place = place;
    }

    public String getTicket_uri() {
        return ticket_uri;
    }

    public void setTicket_uri(String ticket_uri) {
        this.ticket_uri = ticket_uri;
    }

    public boolean isCanceled() {
        return isCanceled;
    }
    @JsonSetter("is_canceled")
    public void setCanceled(boolean canceled) {
        isCanceled = canceled;
    }

    public int getInterested_count() {
        return interested_count;
    }

    public void setInterested_count(int interested_count) {
        this.interested_count = interested_count;
    }

    public int getAttending_count() {
        return attending_count;
    }

    public void setAttending_count(int attending_count) {
        this.attending_count = attending_count;
    }


   

    @Override
    public String toString() {
        return "Event{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", cover=" + cover +
                ", artists=" + artists +
                ", start_time=" + start_time +
                ", end_time=" + end_time +
                ", place=" + place +
                ", ticket_uri='" + ticket_uri + '\'' +
                ", isCanceled=" + isCanceled +
                ", interested_count=" + interested_count +
                ", attending_count=" + attending_count +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(this.id);
        dest.writeString(this.name);
        dest.writeString(this.description);
        dest.writeParcelable(this.cover, flags);
        dest.writeList(this.artists);
        dest.writeLong(this.start_time != null ? this.start_time.getTime() : -1);
        dest.writeLong(this.end_time != null ? this.end_time.getTime() : -1);
        dest.writeParcelable(this.place, flags);
        dest.writeString(this.ticket_uri);
        dest.writeByte(this.isCanceled ? (byte) 1 : (byte) 0);
        dest.writeInt(this.interested_count);
        dest.writeInt(this.attending_count);
    }

    protected FacebookEvent(Parcel in) {
        this.id = (Long) in.readValue(Long.class.getClassLoader());
        this.name = in.readString();
        this.description = in.readString();
        this.cover = in.readParcelable(Cover.class.getClassLoader());
        this.artists = new ArrayList<Artist>();
        in.readList(this.artists, Artist.class.getClassLoader());
        long tmpStart_time = in.readLong();
        this.start_time = tmpStart_time == -1 ? null : new Date(tmpStart_time);
        long tmpEnd_time = in.readLong();
        this.end_time = tmpEnd_time == -1 ? null : new Date(tmpEnd_time);
        this.place = in.readParcelable(Place.class.getClassLoader());
        this.ticket_uri = in.readString();
        this.isCanceled = in.readByte() != 0;
        this.interested_count = in.readInt();
        this.attending_count = in.readInt();
    }

    public static final Creator<FacebookEvent> CREATOR = new Creator<FacebookEvent>() {
        @Override
        public FacebookEvent createFromParcel(Parcel source) {
            return new FacebookEvent(source);
        }

        @Override
        public FacebookEvent[] newArray(int size) {
            return new FacebookEvent[size];
        }
    };

    @Override
    public int compare(FacebookEvent fbEvent1, FacebookEvent fbEvent2) {
        return fbEvent2.start_time.compareTo(fbEvent1.start_time);
    }
}
