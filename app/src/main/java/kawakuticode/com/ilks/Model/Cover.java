package kawakuticode.com.ilks.Model;

import android.os.Parcel;
import android.os.Parcelable;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

/**
 * Created by russeliusernestius on 21/09/17.
 */



@JsonSerialize
@JsonPropertyOrder({ "id", "offset_x","offset_y" , "source",})
public class Cover implements Parcelable{

	private Long id;
    private float offset_x;
    private float offset_y;
    private String source;
   
    public Cover() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public float getOffset_x() {
        return offset_x;
    }

    public void setOffset_x(float offset_x) {
        this.offset_x = offset_x;
    }

    public float getOffset_y() {
        return offset_y;
    }

    public void setOffset_y(float offset_y) {
        this.offset_y = offset_y;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }



    @Override
    public String toString() {
        return "Cover{" +
                "id=" + id +
                ", offset_x=" + offset_x +
                ", offset_y=" + offset_y +
                ", source='" + source + '\'' +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(this.id);
        dest.writeFloat(this.offset_x);
        dest.writeFloat(this.offset_y);
        dest.writeString(this.source);
    }

    protected Cover(Parcel in) {
        this.id = (Long) in.readValue(Long.class.getClassLoader());
        this.offset_x = in.readFloat();
        this.offset_y = in.readFloat();
        this.source = in.readString();
    }

    public static final Creator<Cover> CREATOR = new Creator<Cover>() {
        @Override
        public Cover createFromParcel(Parcel source) {
            return new Cover(source);
        }

        @Override
        public Cover[] newArray(int size) {
            return new Cover[size];
        }
    };
}
