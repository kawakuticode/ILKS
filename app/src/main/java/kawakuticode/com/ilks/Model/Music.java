package kawakuticode.com.ilks.Model;

import android.os.Parcel;
import android.os.Parcelable;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.io.File;

/**
 * Created by russeliusernestius on 27/12/17.
 */

@JsonSerialize
@JsonPropertyOrder({"name", "artist", "length"})
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
    private String artist;
    private String length;
    private File file;
    private boolean isIconChanged;

    public Music() {
    }

    protected Music(Parcel in) {
        this.name = in.readString();
        this.artist = in.readString();
        this.length = in.readString();
        this.file = (File) in.readSerializable();
        this.isIconChanged = in.readByte() != 0;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public String getLength() {
        return length;
    }

    public void setLength(String length) {
        this.length = length;
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    public boolean isIconChanged() {
        return isIconChanged;
    }

    public void setIconChanged(boolean iconChanged) {
        isIconChanged = iconChanged;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.name);
        dest.writeString(this.artist);
        dest.writeString(this.length);
        dest.writeSerializable(this.file);
        dest.writeByte(this.isIconChanged ? (byte) 1 : (byte) 0);
    }
}
