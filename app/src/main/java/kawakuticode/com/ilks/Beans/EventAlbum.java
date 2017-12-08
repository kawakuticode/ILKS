package kawakuticode.com.ilks.Beans;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonSetter;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.util.Date;
import java.util.List;

/**
 * Created by russeliusernestius on 08/12/17.
 */

@JsonSerialize
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonPropertyOrder({"id", "name", "created_time"})
public class EventAlbum {


    private long id;
    private String name;
    private Date created_time;
    private List<PictureSource> sources;


    public long getId() {
        return id;
    }

    @JsonSetter("id")
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

    public void setSources(List<PictureSource> sources) {
        this.sources = sources;
    }

    @Override
    public String toString() {
        return "Album [id=" + id + ", name=" + name + ", created_time=" + created_time + "]";
    }
}

