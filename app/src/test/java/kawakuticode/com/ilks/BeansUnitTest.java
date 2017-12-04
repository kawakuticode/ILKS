package kawakuticode.com.ilks;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import kawakuticode.com.ilks.Beans.Artist;
import kawakuticode.com.ilks.Beans.Cover;
import kawakuticode.com.ilks.Beans.FacebookEvent;
import kawakuticode.com.ilks.Beans.MLocation;
import kawakuticode.com.ilks.Beans.Place;

/**
 * Created by russeliusernestius on 20/09/17.
 */
public class BeansUnitTest {

    Artist artist = null;
    Cover cover = null;
    FacebookEvent event = null;
    MLocation mLocation = null;
    Place place = null;


    //TESTING BEANS
    @Before
    public void setup() {
        artist = new Artist();
        cover = new Cover();
        event = new FacebookEvent();
        mLocation = new MLocation();
        place = new Place();
    }

    @Test
    public void TestCreateBeans() {
        Assert.assertNotNull(artist);
        Assert.assertNotNull(cover);
        Assert.assertNotNull(event);
        Assert.assertNotNull(place);
        Assert.assertNotNull(mLocation);
    }


    @Test
    public void compareArtists() {
        Artist newArtist = new Artist();
        newArtist.setType_of_artist(0);
        newArtist.setName("Edson Tecas");
        newArtist.setType_of_artist(0);
        List<String> classes = new ArrayList<>();
        classes.add("kizomba");
        classes.add("semba");
        classes.add("kuduro");
        newArtist.setList_of_classes(classes);

        Assert.assertNotEquals(artist, newArtist);
    }

    @Test
    public void compareCovers() {
        Cover newCover = new Cover();
        newCover.setId(Long.valueOf(3));
        newCover.setOffset_x(2.5f);
        newCover.setOffset_y(2.5f);
        newCover.setSource("www.google.com");
        Assert.assertNotEquals(cover, newCover);
    }


    @Test
    public void compareLocations() {
        MLocation newLocation = new MLocation();
        newLocation.setCity("Luanda");
        newLocation.setCountry("Angola");
        newLocation.setLatitude("54.1039");
        newLocation.setLongitude("12.2012");
        newLocation.setStreet("Rua M.Mohamed");
        newLocation.setZip("23a34LE");
        Assert.assertNotEquals(mLocation, newLocation);
    }


    @Test
    public void comparePlaces() {
        Place newPlace = new Place();
        newPlace.setName_place("Restaurant Matrix");
        updateLocation();
        place.setLocation(mLocation);
        Assert.assertNotEquals(newPlace, place);

    }


    @Test
    public void compareEvents() {
        FacebookEvent newEvent = new FacebookEvent();
        newEvent.setId(Long.valueOf(3));

        updateArtist();
        updateLocation();
        updatePlace();
        updateCover();

        newEvent.setPlace(place);
        newEvent.setName("ILKS");
        newEvent.setAttending_count(20);
        newEvent.setInterested_count(12);
        newEvent.setCanceled(false);
        newEvent.setCover(cover);
        newEvent.getArtists().add(artist);
        place.setLocation(mLocation);
        Assert.assertNotEquals(event, newEvent);

    }


    @After
    public void updateLocation() {
        mLocation.setCity("Luanda");
        mLocation.setCountry("Angola");
        mLocation.setLatitude("54.1039");
        mLocation.setLongitude("12.2012");
        mLocation.setStreet("Rua M.Mohamed");
        mLocation.setZip("23a34LE");
    }


    @After
    public void updateCover() {
        cover.setId(Long.valueOf(5));
        cover.setOffset_x(30f);
        cover.setOffset_y(40f);
        cover.setSource("www.fb.ao");

    }


    @After
    public void updatePlace() {
        place.setName_place("Uige");
        updateLocation();
        place.setLocation(mLocation);
    }

    @After
    public void updateArtist() {
        artist.setName("Virus");
        artist.setType_of_artist(1);
        List<String> classes = new ArrayList<>();
        classes.add("Funk");
        classes.add("semba");
        classes.add("kuduro");
        artist.setList_of_classes(classes);
    }

    @Test
    public void testGetSettersArtist() {
        updateArtist();
        Assert.assertEquals(artist.getName(), "Virus");
        Assert.assertEquals(artist.getType_of_artist(), 1);
        Assert.assertEquals(artist.getList_of_classes().size(), 3);

    }

}