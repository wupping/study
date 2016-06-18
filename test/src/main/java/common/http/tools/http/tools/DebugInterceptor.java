package common.http.tools.http.tools;

import org.apache.http.*;
import org.apache.http.protocol.HttpContext;

import java.io.IOException;

/**
 * Created by woodle on 16/5/6.
 *
 */
public class DebugInterceptor implements HttpRequestInterceptor, HttpResponseInterceptor {

    private static final String LINESEPATOR = "\n";

    public static final DebugInterceptor instance = new DebugInterceptor();

    public void process(HttpRequest request, HttpContext context) throws HttpException, IOException {
        StringBuilder sb = new StringBuilder();
        sb.append(request.getRequestLine().toString()).append(LINESEPATOR);
        for (Header h : request.getAllHeaders()) {
            sb.append(">>> ").append(h).append(";").append(LINESEPATOR);
        }
        System.out.println(sb.toString());
    }

    public void process(HttpResponse response, HttpContext context) throws HttpException, IOException {
        StringBuilder sb = new StringBuilder();
        sb.append(response.getStatusLine().toString()).append(LINESEPATOR);
        for (Header h : response.getAllHeaders())
            sb.append("<<< ").append(h).append(LINESEPATOR);
        System.out.println(sb.toString());
    }
}
