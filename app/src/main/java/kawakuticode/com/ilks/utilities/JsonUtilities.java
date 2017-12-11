package kawakuticode.com.ilks.utilities;

import android.util.Log;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import kawakuticode.com.ilks.Model.EventAlbum;
import kawakuticode.com.ilks.Model.FacebookEvent;

/**
 * Created by russeliusernestius on 29/10/17.
 */

public class JsonUtilities {

    private static final String CONTENT_EVENT_JSON = "content_event";

    public static List<FacebookEvent> parserJsonResponseFacebookEvents(String response) throws JsonProcessingException, ParseException {

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(SerializationFeature.ORDER_MAP_ENTRIES_BY_KEYS, true);
        objectMapper.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);
        objectMapper.configure(SerializationFeature.INDENT_OUTPUT, true);

        JsonNode rootNode;
        List<FacebookEvent> mListEvents = new ArrayList<>();

        if (isValidJSON(response)) {
            try {
                rootNode = objectMapper.readTree(response);
                Iterator<JsonNode> list_events = rootNode.elements();
                while (list_events.hasNext()) {
                    JsonNode json_event = list_events.next().get(CONTENT_EVENT_JSON);
                    FacebookEvent tmp_event = new ObjectMapper().readValue(json_event.toString(), FacebookEvent.class);
                  mListEvents.add(tmp_event);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            Log.d("ERROR ", " Invalid Json");
            return mListEvents;
        }
        return mListEvents;
    }

    public static List<EventAlbum> parserJsonResponseEventAlbums(String data) throws JsonProcessingException {

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(SerializationFeature.ORDER_MAP_ENTRIES_BY_KEYS, true);
        objectMapper.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);
        objectMapper.configure(SerializationFeature.INDENT_OUTPUT, true);

        JsonNode rootNode;
        List<EventAlbum> mListAlbums = new ArrayList<>();

        if (isValidJSON(data)) {
            try {
                rootNode = objectMapper.readTree(data);
                Iterator<JsonNode> list_events = rootNode.elements();

                while (list_events.hasNext()) {
                    JsonNode json_event = list_events.next();
                    EventAlbum tmp_album = new ObjectMapper().readValue(json_event.toString(), EventAlbum.class);
                    mListAlbums.add(tmp_album);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            Log.d("ERROR ", " Invalid Json");

        }

        return mListAlbums;
    }

    public static boolean isValidJSON(final String json) throws JsonProcessingException {
        boolean valid = true;

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.enable(DeserializationFeature.FAIL_ON_READING_DUP_TREE_KEY);
        try {
            objectMapper.readTree(json);
        } catch (IOException e) {
            valid = false;
        }
        return valid;
    }


}
