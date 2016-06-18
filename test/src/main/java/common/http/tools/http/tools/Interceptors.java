package common.http.tools.http.tools;

import org.apache.http.impl.client.DefaultHttpClient;

/**
 * Created by woodle on 15/1/24.
 *
 */
public class Interceptors {

    public static void debug(DefaultHttpClient client, boolean open){
        client.removeRequestInterceptorByClass(DebugInterceptor.class);
        client.removeResponseInterceptorByClass(DebugInterceptor.class);
        if (open) {
            client.addRequestInterceptor(DebugInterceptor.instance);
            client.addResponseInterceptor(DebugInterceptor.instance);
        }
    }

    public static void gzip(DefaultHttpClient client, boolean open) {
        client.removeRequestInterceptorByClass(DeflateInterceptor.class);
        client.removeResponseInterceptorByClass(DeflateInterceptor.class);
        if (open) {
            client.addRequestInterceptor(DeflateInterceptor.instance);
            client.addResponseInterceptor(DeflateInterceptor.instance);
        }
    }
}
