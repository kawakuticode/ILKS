package kawakuticode.com.ilks.Beans;

import android.os.Parcel;
import android.os.Parcelable;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.util.Date;

/**
 * Created by russeliusernestius on 05/12/17.
 */

@JsonSerialize
@JsonPropertyOrder({ "id", "message","story" , "updated_time",})
public class Feed implements Parcelable {

    private String id;
    private String message;
    private String story;
    private Date updated_time;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getStory() {
        return story;
    }

    public void setStory(String story) {
        this.story = story;
    }

    public Date getUpdated_time() {
        return updated_time;
    }

    public void setUpdated_time(Date updated_time) {
        this.updated_time = updated_time;
    }

    @Override
    public String toString() {
        return "Feed{" +
                "id=" + id +
                ", message='" + message + '\'' +
                ", story='" + story + '\'' +
                ", updated_time=" + updated_time +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.message);
        dest.writeString(this.story);
        dest.writeLong(this.updated_time != null ? this.updated_time.getTime() : -1);
    }

    public Feed() {
    }

    protected Feed(Parcel in) {
        this.id = in.readString();
        this.message = in.readString();
        this.story = in.readString();
        long tmpUpdated_time = in.readLong();
        this.updated_time = tmpUpdated_time == -1 ? null : new Date(tmpUpdated_time);
    }

    public static final Creator<Feed> CREATOR = new Creator<Feed>() {
        @Override
        public Feed createFromParcel(Parcel source) {
            return new Feed(source);
        }

        @Override
        public Feed[] newArray(int size) {
            return new Feed[size];
        }
    };
}
