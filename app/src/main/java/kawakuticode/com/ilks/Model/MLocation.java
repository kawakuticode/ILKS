package kawakuticode.com.ilks.Model;

import android.os.Parcel;
import android.os.Parcelable;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

/**
 * Created by russeliusernestius on 20/09/17.
 */
@JsonSerialize
@JsonPropertyOrder({ "city", "country","latitude" , "longitude","street","Zip"})
public class MLocation implements Parcelable {

  
    private String city;
    private String country;
    private String latitude;
    private String longitude;
    private String street;
    private String Zip;
    public MLocation() {
    }

   

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getZip() {
        return Zip;
    }

    public void setZip(String zip) {
        Zip = zip;
    }


    
    @Override
    public String toString() {
        return "MLocation{" +
                ", city='" + city + '\'' +
                ", country='" + country + '\'' +
                ", latitude='" + latitude + '\'' +
                ", longitude='" + longitude + '\'' +
                ", street='" + street + '\'' +
                ", Zip='" + Zip + '\'' +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.city);
        dest.writeString(this.country);
        dest.writeString(this.latitude);
        dest.writeString(this.longitude);
        dest.writeString(this.street);
        dest.writeString(this.Zip);
    }

    protected MLocation(Parcel in) {
        this.city = in.readString();
        this.country = in.readString();
        this.latitude = in.readString();
        this.longitude = in.readString();
        this.street = in.readString();
        this.Zip = in.readString();
    }

    public static final Parcelable.Creator<MLocation> CREATOR = new Parcelable.Creator<MLocation>() {
        @Override
        public MLocation createFromParcel(Parcel source) {
            return new MLocation(source);
        }

        @Override
        public MLocation[] newArray(int size) {
            return new MLocation[size];
        }
    };
}
