package kawakuticode.com.ilks.Model;

import android.os.Parcel;
import android.os.Parcelable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonSetter;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by russeliusernestius on 08/12/17.
 */

@JsonSerialize
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonPropertyOrder({"id", "name", "created_time", "pictures_source"})
public class EventAlbum implements Parcelable {


    private long id;
    private String name;
    private Date created_time;
    private List<PictureSource> sources;


    public long getId() {
        return id;
    }

    @JsonSetter("id_album")
    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    @JsonSetter("name")
    public void setName(String name) {
        this.name = name;
    }

    public Date getCreated_time() {
        return created_time;
    }

    @JsonSetter("created_time")
    public void setCreated_time(Date created_time) {
        this.created_time = created_time;
    }

    public List<PictureSource> getSources() {
        return sources;
    }

    @JsonSetter("pictures_source")
    public void setSources(List<PictureSource> sources) {
        this.sources = sources;
    }

    @Override
    public String toString() {
        return "EventAlbum{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", created_time=" + created_time +
                ", sources=" + sources +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(this.id);
        dest.writeString(this.name);
        dest.writeLong(this.created_time != null ? this.created_time.getTime() : -1);
        dest.writeList(this.sources);
    }

    public EventAlbum() {
    }

    protected EventAlbum(Parcel in) {
        this.id = in.readLong();
        this.name = in.readString();
        long tmpCreated_time = in.readLong();
        this.created_time = tmpCreated_time == -1 ? null : new Date(tmpCreated_time);
        this.sources = new ArrayList<PictureSource>();
        in.readList(this.sources, PictureSource.class.getClassLoader());
    }

    public static final Creator<EventAlbum> CREATOR = new Creator<EventAlbum>() {
        @Override
        public EventAlbum createFromParcel(Parcel source) {
            return new EventAlbum(source);
        }

        @Override
        public EventAlbum[] newArray(int size) {
            return new EventAlbum[size];
        }
    };
}

