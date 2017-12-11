package kawakuticode.com.ilks.Model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by russeliusernestius on 01/11/17.
 */

public class OptionEvent implements Parcelable {

    private int imgSource;
    private String option;


    public OptionEvent() {
    }


    public int getImgSource() {
        return imgSource;
    }

    public void setImgSource(int imgSource) {
        this.imgSource = imgSource;
    }

    public String getOption() {
        return option;
    }

    public void setOption(String option) {
        this.option = option;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.imgSource);
        dest.writeString(this.option);
    }



    protected OptionEvent(Parcel in) {
        this.imgSource = in.readInt();
        this.option = in.readString();
    }

    public static final Creator<OptionEvent> CREATOR = new Creator<OptionEvent>() {
        @Override
        public OptionEvent createFromParcel(Parcel source) {
            return new OptionEvent(source);
        }

        @Override
        public OptionEvent[] newArray(int size) {
            return new OptionEvent[size];
        }
    };

    @Override
    public String toString() {
        return "OptionEvent{" +
                "imgSource=" + imgSource +
                ", option='" + option + '\'' +
                '}';
    }
}
