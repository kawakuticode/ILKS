package kawakuticode.com.ilks.utilities;

import android.util.Log;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import kawakuticode.com.ilks.Beans.FacebookEvent;

/**
 * Created by russeliusernestius on 29/10/17.
 */

public class JsonUtilities {

    private static final String CONTENT_EVENT_JSON = "content_event";

    public static List<FacebookEvent> parserJsonResponseFacebook(String response) throws JsonProcessingException, ParseException {

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(SerializationFeature.ORDER_MAP_ENTRIES_BY_KEYS, true);
        List<FacebookEvent> mListEvents = new ArrayList<>();

        if (isValidJSON(response)) {
            try {

                JSONArray jsonArr = new JSONArray(response);

                for (int i = 0; i < jsonArr.length(); i++) {

                    JSONObject jsonObjTmp = jsonArr.getJSONObject(i);
                    String jsonEventContent = jsonObjTmp.get(CONTENT_EVENT_JSON).toString();

                    FacebookEvent tmp_event = objectMapper.readValue(jsonEventContent, new TypeReference<FacebookEvent>() {
                    });

                  mListEvents.add(tmp_event);
                }

            } catch (JSONException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            Log.d("ERROR ", " Invalid Json");
            return mListEvents;
        }
        Collections.sort(mListEvents, new FacebookEvent());
        return mListEvents;
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
