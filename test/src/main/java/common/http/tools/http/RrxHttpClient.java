package common.http.tools.http;

import org.apache.http.HttpVersion;
import org.apache.http.client.params.HttpClientParams;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.PoolingClientConnectionManager;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;

import java.util.concurrent.TimeUnit;


/**
 * Created by woodle on 16/5/6.
 * http client客户端
 */
public class RrxHttpClient extends DefaultHttpClient {

    public static int DEFAULT_TIMEOUT = 5000;

    public RrxHttpClient(ClientConnectionManager conman, HttpParams params) {
        super(conman, params);
    }

    public RrxHttpClient(HttpParams params) {
        this(null, params);
    }

    public RrxHttpClient() {
        this(null);
    }

    public static RrxHttpClient createDefaultClient() {
        HttpParams params = new BasicHttpParams();

        // 默认的超时时间
        HttpConnectionParams.setConnectionTimeout(params, 5000);
        HttpConnectionParams.setSoTimeout(params, 10000);

        HttpProtocolParams.setVersion(params, HttpVersion.HTTP_1_1);
        HttpProtocolParams.setUserAgent(params, "Mozilla/5.0 (Windows NT 6.1; WOW64) Chrome/27.0.1453.94 Safari/537.36 jiedaibaohc/8.0.1");

        HttpClientParams.setCookiePolicy(params, "ignoreCookies");

        SchemeRegistry schemeRegistry = new SchemeRegistry();
        schemeRegistry.register(new Scheme("http", 80, PlainSocketFactory.getSocketFactory()));

        PoolingClientConnectionManager cm = new PoolingClientConnectionManager(schemeRegistry, 1L, TimeUnit.MINUTES);
        cm.setMaxTotal(200);
        cm.setDefaultMaxPerRoute(50);

        RrxHttpClient client = new RrxHttpClient(cm, params);


        return client;
    }

}