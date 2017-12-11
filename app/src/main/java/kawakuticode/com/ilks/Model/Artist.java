package kawakuticode.com.ilks.Model;


import android.os.Parcel;
import android.os.Parcelable;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by russeliusernestius on 20/09/17.
 */
@JsonSerialize
public class Artist implements Parcelable {

  
    private String name;
    private int type_of_artist;
    private List<String> list_of_classes;


    public Artist() {
        list_of_classes = new ArrayList<>();
    }

  

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getType_of_artist() {
        return type_of_artist;
    }

    public void setType_of_artist(int type_of_artist) {
        this.type_of_artist = type_of_artist;
    }

    public List<String> getList_of_classes() {
        return list_of_classes;
    }

    public void setList_of_classes(List<String> list_of_classes) {
        this.list_of_classes = list_of_classes;
    }




    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.name);
        dest.writeInt(this.type_of_artist);
        dest.writeStringList(this.list_of_classes);
    }

    protected Artist(Parcel in) {
        this.name = in.readString();
        this.type_of_artist = in.readInt();
        this.list_of_classes = in.createStringArrayList();
    }

    public static final Creator<Artist> CREATOR = new Creator<Artist>() {
        @Override
        public Artist createFromParcel(Parcel source) {
            return new Artist(source);
        }

        @Override
        public Artist[] newArray(int size) {
            return new Artist[size];
        }
    };

    @Override
    public String toString() {
        return "Artist{" +
                ", name='" + name + '\'' +
                ", type_of_artist=" + type_of_artist +
                ", list_of_classes=" + list_of_classes.size() +
                '}';
    }
}
