package com.deveshmittal.data.remote.client;


import android.util.Log;

import com.deveshmittal.data.BuildConfig;

import java.io.EOFException;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.concurrent.TimeUnit;

import okhttp3.Connection;
import okhttp3.Headers;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Protocol;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okio.Buffer;
import okio.BufferedSource;

public class LogInterceptor implements Interceptor {
    private static final String TAG = "LogHttp: ";
    private static final Charset UTF8 = Charset.forName("UTF-8");

    private static final boolean LOG_REQUEST_HEADERS = true;
    private static final boolean LOG_RESPONSE_HEADERS = false;
    private static final boolean NED_LOG = BuildConfig.DEBUG;


    @Override
    public Response intercept (Chain chain) throws IOException {
        Request request = chain.request();

        Response response = null;

        String url = request.url().url().toString();
        if( !url.endsWith(".jpg") &&
                !url.endsWith(".jpeg") &&
                !url.endsWith(".png") &&
                !url.endsWith(".gif") ) {

            RequestBody requestBody = request.body();
            boolean hasRequestBody = requestBody != null;

            Connection connection = chain.connection();
            Protocol protocol = connection != null ? connection.protocol() : Protocol.HTTP_1_1;
            String requestStartMessage = "--> " + request.method() + ' ' + request.url() + ' ' + protocol;
            if( hasRequestBody ) {
                requestStartMessage += " (" + requestBody.contentLength() + "-byte body)";
            }
            log(requestStartMessage);
            Headers headers = request.headers();
            if( LOG_REQUEST_HEADERS ) {
                log("request headers: ");
                for (int i = 0, count = headers.size(); i < count; i++) {
                    log(headers.name(i) + ": " + headers.value(i));
                }
            }
            if( hasRequestBody ) {

                Buffer buffer = new Buffer();
                requestBody.writeTo(buffer);

                Charset charset = UTF8;
                MediaType contentType = requestBody.contentType();
                if( contentType != null ) {
                    charset = contentType.charset(UTF8);
                }
                log("");
                if( isPlaintext(buffer) ) {
                    log("body: " + buffer.readString(charset));
                    log("--> END " + request.method() + " (" + requestBody.contentLength() + "-byte body)");
                } else {
                    log("--> END " + request.method() + " (binary " + requestBody.contentLength() + "-byte body omitted)");
                }
            }
            long startNs = System.nanoTime();
            response = chain.proceed(request);

            long tookMs = TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - startNs);

            if( LOG_RESPONSE_HEADERS ) {
                headers = response.headers();
                log("responce headers: ");
                for (int i = 0, count = headers.size(); i < count; i++) {
                    log(headers.name(i) + ": " + headers.value(i));
                }
            }
            ResponseBody responseBody = response.body();
            if( responseBody.contentLength() != 0 ) {
                Charset charset = UTF8;
                MediaType contentType = responseBody.contentType();
                if( contentType != null ) {
                    charset = contentType.charset(UTF8);
                }
                BufferedSource source = responseBody.source();
                source.request(Long.MAX_VALUE); // Buffer the entire body.
                String responce = source.buffer().clone().readString(charset);
                String message = "<-- " + response.code() + " " + url + " (" + tookMs + "ms)" +
                        "(" + responce.getBytes().length/8 + " bite)" + "\n " + responce;
                if( response.isSuccessful() )
                    log(message);
                else logErorr(message);
            } else {
                log("empty response");
            }
        } else {
            response = chain.proceed(request);
        }
        return response;
    }

    private static void log (String message) {
        if( NED_LOG )
            Log.d(TAG, message);
    }

    private static void logErorr (String message) {
        if( NED_LOG )
            Log.e(TAG, message);
    }

    static boolean isPlaintext (Buffer buffer) throws EOFException {
        try {
            Buffer prefix = new Buffer();
            long byteCount = buffer.size() < 64 ? buffer.size() : 64;
            buffer.copyTo(prefix, 0, byteCount);
            for (int i = 0; i < 16; i++) {
                if( prefix.exhausted() ) {
                    break;
                }
                int codePoint = prefix.readUtf8CodePoint();
                if( Character.isISOControl(codePoint) && !Character.isWhitespace(codePoint) ) {
                    return false;
                }
            }
            return true;
        } catch (EOFException e) {
            return false; // Truncated UTF-8 sequence.
        }
    }

}