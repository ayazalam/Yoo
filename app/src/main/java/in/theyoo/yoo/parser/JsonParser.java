package in.theyoo.yoo.parser;

import org.json.JSONException;
import org.json.JSONObject;

import in.theyoo.yoo.extra.Keys;
import in.theyoo.yoo.pojo.SimplePojo;

/**
 * Class for parsing json response
 */
public class JsonParser {
    //Method to parse simple JsonResponse
    public static SimplePojo SimpleParser(String jsonString) throws JSONException {
        JSONObject jo = new JSONObject(jsonString);
        SimplePojo current = new SimplePojo();
        current.setMessage(jo.getString(Keys.KEY_COM_MESSAGE));
        current.setReturned(jo.getBoolean(Keys.KEY_COM_RETURN));
        return current;
    }

}
