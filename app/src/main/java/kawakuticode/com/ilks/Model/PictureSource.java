package kawakuticode.com.ilks.Model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonSetter;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

/**
 * Created by russeliusernestius on 08/12/17.
 */

@JsonSerialize
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonPropertyOrder({"id", "source"})
public class PictureSource {

    private Long id;
    private String source;


    public Long getId() {
        return id;
    }

    @JsonSetter("id")
    public void setId(Long id) {
        this.id = id;
    }

    public String getSource() {
        return source;
    }

    @JsonSetter("source")
    public void setSource(String source) {
        this.source = source;
    }

    @Override
    public String toString() {
        return "PictureSource [id=" + id + ", source=" + source + "]";
    }


}
