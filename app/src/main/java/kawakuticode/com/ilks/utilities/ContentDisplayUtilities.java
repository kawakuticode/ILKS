package kawakuticode.com.ilks.utilities;

import java.util.ArrayList;
import java.util.List;

import kawakuticode.com.ilks.Beans.OptionEvent;
import kawakuticode.com.ilks.R;

/**
 * Created by russeliusernestius on 01/11/17.
 */

public class ContentDisplayUtilities {


    public static String truncatePlaceName(String place) {
        return place.length() > 30 ? place.substring(0, 30) : place;
    }


    public static List <OptionEvent> optionContent() {

        String [] options = {"Line-Up", "Map", "News", "Photos",
                "Festival Info", "Listen"};

         int [] icons = {R.mipmap.lineup, R.mipmap.map, R.mipmap.news, R.mipmap.photos,
                 R.mipmap.festival_info, R.mipmap.listen};

         List<OptionEvent> mEventOpions  = new ArrayList<>();

        for (int k = 0 ; k < options.length ; k++)  {

            OptionEvent opt_tmp = new OptionEvent();
            opt_tmp.setOption(options[k]);
            opt_tmp.setImgSource(icons[k]);
            mEventOpions.add(opt_tmp);
        }
        return mEventOpions;
    }
}
