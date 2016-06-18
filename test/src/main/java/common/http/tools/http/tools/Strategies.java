package common.http.tools.http.tools;

import org.apache.http.impl.client.DefaultHttpClient;

/**
 * Created by woodle on 15/1/24.
 *
 */
public class Strategies {

    public static void keepAlive(DefaultHttpClient client, long time){
        client.setKeepAliveStrategy(new ForceKeepAliveStrategy(time));
    }
}
