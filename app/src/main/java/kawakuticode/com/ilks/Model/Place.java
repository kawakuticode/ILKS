package kawakuticode.com.ilks.Model;

import android.os.Parcel;
import android.os.Parcelable;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonSetter;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

/**
 * Created by russeliusernestius on 20/09/17.
 */
@JsonSerialize
@JsonPropertyOrder({ "id", "name","location"})
public class Place implements Parcelable {
	
    private Long id; 
    private String name;
    private MLocation location;


    public Place() {
        location = new MLocation();
    }


    public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public String getName_place() {
        return name;
    }

    @JsonSetter("name")
    public void setName_place(String name_place) {
        this.name = name_place;
    }

    public MLocation getLocation() {
        return location;
    }
    
    @JsonSetter("location")
    public void setLocation(MLocation location) {
        this.location = location;
    }


	@Override
	public String toString() {
		return "Place [id=" + id + ", name=" + name + ", location=" + location + "]";
	}


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(this.id);
        dest.writeString(this.name);
        dest.writeParcelable(this.location, flags);
    }

    protected Place(Parcel in) {
        this.id = (Long) in.readValue(Long.class.getClassLoader());
        this.name = in.readString();
        this.location = in.readParcelable(MLocation.class.getClassLoader());
    }

    public static final Creator<Place> CREATOR = new Creator<Place>() {
        @Override
        public Place createFromParcel(Parcel source) {
            return new Place(source);
        }

        @Override
        public Place[] newArray(int size) {
            return new Place[size];
        }
    };
}
