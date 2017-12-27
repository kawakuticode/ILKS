package kawakuticode.com.ilks.Model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by russeliusernestius on 27/12/17.
 */

public class Music implements Parcelable {

    public static final Creator<Music> CREATOR = new Creator<Music>() {
        @Override
        public Music createFromParcel(Parcel source) {
            return new Music(source);
        }

        @Override
        public Music[] newArray(int size) {
            return new Music[size];
        }
    };
    private String name;
    private Long duration;

    public Music() {
    }

    protected Music(Parcel in) {
        this.name = in.readString();
        this.duration = (Long) in.readValue(Long.class.getClassLoader());
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.name);
        dest.writeValue(this.duration);
    }
}
