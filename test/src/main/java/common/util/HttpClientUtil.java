package common.util;

import common.http.tools.http.RrxHttpClient;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.entity.StringEntity;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.io.IOException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by woodle on 16/5/6.
 *
 */
public class HttpClientUtil {
    private static final Logger log = LoggerFactory.getLogger(HttpClientUtil.class);

    private static HttpClient client = RrxHttpClient.createDefaultClient();

    public  static String sendGetRequest(String url) throws Exception {
        return sendGetRequest(url, null);
    }

    public  static String sendGetRequest(String url, String decodeCharset) throws Exception {
        long start = System.currentTimeMillis();
        String responseContent = null;

        HttpGet httpGet = new HttpGet(url);
        HttpEntity entity = null;
        try {
            HttpResponse response = client.execute(httpGet);
            entity = response.getEntity();
            if (null != entity) {
                responseContent = EntityUtils.toString(entity, decodeCharset == null ? "UTF-8" : decodeCharset);
            }
            log.info("调用接口{}耗时为{}毫秒", new Object[]{url, System.currentTimeMillis() - start});
        } catch (Exception e) {
            log.error("访问" + url + "异常,信息如下",  e);
            throw e;
        } finally {
            try {
                EntityUtils.consume(entity);
            } catch (Exception ex) {
                log.error("net io exception ", ex);
            }
        }

        if(responseContent == null) {
            log.info("url[{}] failed", url);
        } else {
            log.info("url[{}] ret[{}]", new String[] {url, responseContent});
        }

        return responseContent;
    }

    public static String sendPostRequest(String url, Map<String, String> params) throws Exception {
        return sendPostRequest(url, params, null);
    }

    public static String sendPostRequest(String url, Map<String, String> params, String decodeCharset) throws Exception {
        long start = System.currentTimeMillis();
        HttpPost post = new HttpPost(url);
        List<BasicNameValuePair> postData = new ArrayList();
        for (Map.Entry<String, String> entry : params.entrySet()) {
            postData.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
        }

        HttpEntity httpEntity = null;
        String responseContent = null;
        try {
            UrlEncodedFormEntity entity = new UrlEncodedFormEntity(postData, decodeCharset);
            post.setEntity(entity);
            HttpResponse response = client.execute(post);

            httpEntity = response.getEntity();
            if (httpEntity != null) {
                responseContent = EntityUtils.toString(httpEntity, decodeCharset == null ? "UTF-8" : decodeCharset);
            }

            log.info("调用接口{}耗时为{}毫秒", new Object[]{url, System.currentTimeMillis() - start});
        } catch (Exception ex) {
            log.error("访问" + url + "异常,信息如下",  ex);
            throw  ex;
        } finally {
            try {
                EntityUtils.consume(httpEntity);
            } catch (Exception ex) {
                log.error("net io exception ", ex);
            }
        }
        return responseContent;
    }

    public static String sendPostRequest(String url, String requestContent, String decodeCharset) {
        long start = System.currentTimeMillis();
        HttpPost post = new HttpPost(url);

        HttpEntity httpEntity = null;
        String responseContent = null;
        try {
            post.setEntity(new StringEntity(requestContent, "UTF-8"));
            HttpResponse response = client.execute(post);
            httpEntity = response.getEntity();
            if (httpEntity != null) {
                responseContent = EntityUtils.toString(httpEntity, decodeCharset == null ? "UTF-8" : decodeCharset);
            }

            log.info("调用接口{}耗时为{}毫秒", new Object[]{url, System.currentTimeMillis() - start});
        } catch (Exception ex) {
            log.error("访问" + url + "异常,信息如下",  ex);
        } finally {
            try {
                EntityUtils.consume(httpEntity);
            } catch (Exception ex) {
                log.error("net io exception ", ex);
            }
        }
        return responseContent;
    }

    public static String sendGetSSLRequest(String reqURL) throws Exception {
        return sendGetSSLRequest(reqURL, null);
    }

    /**
     * 发送HTTPS_GET请求
     *
     * @param reqURL        请求地址（含参数）
     * @param decodeCharset 解码字符集,解析响应数据时用之,其为null时默认采用UTF-8解码
     */
    public static String sendGetSSLRequest(String reqURL, String decodeCharset) throws Exception {
        long start = System.currentTimeMillis();
        String responseContent = "";
        HttpEntity entity = null;
        X509TrustManager xtm = new X509TrustManager() {
            public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
            }

            public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
            }

            public X509Certificate[] getAcceptedIssuers() {
                return null;
            }
        };
        try {
            SSLContext ctx = SSLContext.getInstance("TLS");
            ctx.init(null, new TrustManager[]{xtm}, null);
            SSLSocketFactory socketFactory = new SSLSocketFactory(ctx, SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
            client.getConnectionManager().getSchemeRegistry().register(new Scheme("https", 443, socketFactory));

            HttpGet httpGet = new HttpGet(reqURL);

            client.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT, RrxHttpClient.DEFAULT_TIMEOUT);
            HttpResponse response = client.execute(httpGet);
            entity = response.getEntity();
            if (null != entity) {
                responseContent = EntityUtils.toString(entity,
                        decodeCharset == null ? "UTF-8" : decodeCharset);
            }
        } catch (Exception e) {
            log.error("与[{}]通信过程中发生异常,信息为{}", reqURL, e);
            throw e;
        } finally {
            try {
                EntityUtils.consume(entity);
                log.info("调用接口{}耗时{}毫秒", reqURL, System.currentTimeMillis() - start);
            } catch (IOException ex) {
                log.warn("net io exception {}", ex);
            }
        }
        return responseContent;
    }

    private static String sendPostSSLRequest(String reqURL, Map<String, String> params, int timeout) throws Exception {
        return sendPostSSLRequest(reqURL, params, null, null, timeout);
    }

    /**
     * 发送HTTPS_POST请求
     *
     * @param reqURL        请求地址
     * @param params        请求参数
     * @param encodeCharset 编码字符集,编码请求数据时用之,其为null时默认采用UTF-8解码
     * @param decodeCharset 解码字符集,解析响应数据时用之,其为null时默认采用UTF-8解码
     */
    private static String sendPostSSLRequest(String reqURL, Map<String, String> params, String encodeCharset,
                                             String decodeCharset, int timeout) throws Exception {
        long start = System.currentTimeMillis();
        String responseContent = "";
        HttpEntity entity = null;
        X509TrustManager xtm = new X509TrustManager() {
            public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
            }

            public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
            }

            public X509Certificate[] getAcceptedIssuers() {
                return null;
            }
        };
        try {
            SSLContext ctx = SSLContext.getInstance("TLS");
            ctx.init(null, new TrustManager[]{xtm}, null);
            SSLSocketFactory socketFactory = new SSLSocketFactory(ctx, SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
            client.getConnectionManager().getSchemeRegistry().register(new Scheme("https", 443, socketFactory));

            if (timeout>0) {
                client.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT, timeout);
            }

            HttpPost httpPost = new HttpPost(reqURL);
            if (null != params) {
                List<NameValuePair> formParams = new ArrayList<NameValuePair>();
                for (Map.Entry<String, String> entry : params.entrySet()) {
                    formParams.add(new BasicNameValuePair(entry.getKey(), entry
                            .getValue()));
                }
                httpPost.setEntity(new UrlEncodedFormEntity(formParams, encodeCharset == null ? "UTF-8" : encodeCharset));
            }
            HttpResponse response = client.execute(httpPost);
            client.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT, RrxHttpClient.DEFAULT_TIMEOUT);
            entity = response.getEntity();
            if (null != entity) {
                responseContent = EntityUtils.toString(entity,
                        decodeCharset == null ? "UTF-8" : decodeCharset);
            }
        } catch (Exception e) {
            log.error("与[{}]通信过程中发生异常,信息为:{}", reqURL, e);
            throw e;
        } finally {
            try {
                EntityUtils.consume(entity);
                log.info("调用接口{}耗时{}毫秒", reqURL, System.currentTimeMillis() - start);
            } catch (IOException ex) {
                log.warn("net io exception {}", ex);
            }
        }

        return responseContent;
    }

    public static void main(String... strings) {
        try {
            System.out.println(sendGetSSLRequest("http://www.baidu.com"));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
