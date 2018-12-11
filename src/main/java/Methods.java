import okhttp3.*;
import okio.BufferedSink;
import okio.Okio;

import java.io.File;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

public class Methods extends Settings {

    private Authenticator buildProxyAuthenticator() {
        Authenticator proxyAuthenticator = (route, response) -> {
            String credential = Credentials.basic(username, password);
            return response
                    .request()
                    .newBuilder()
                    .header("Proxy-Authorization", credential)
                    .build();
        };
        return proxyAuthenticator;
    }

    private OkHttpClient buildClient() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder()
                .connectTimeout(60, TimeUnit.SECONDS)
                .writeTimeout(60, TimeUnit.SECONDS)
                .readTimeout(60, TimeUnit.SECONDS)
                .proxy(new Proxy(Proxy.Type.HTTP, new InetSocketAddress(proxyHost, proxyPort)))
                .proxyAuthenticator(buildProxyAuthenticator());
        OkHttpClient client = builder.build();
        return client;
    }

    private Request buildRequest() {
        Request request = new Request.Builder()
                .url("http://oferty.praca.gov.pl/integration/services/oferta?wsdl=")
                .post(createRequestBody())
                .addHeader("content-type", "application/xml")
                .addHeader("cache-control", "no-cache")
                .addHeader("postman-token", "7e4b8260-76bd-06b2-8d5f-5ac33d84f42f")
                .build();
        return request;
    }

    //  TODO: 2018-12-11  Fill relevant data, eg. <Partner> and <Kryterium> tag.
    //   See documentation on: https://www.dane.gov.pl/dataset/538/resource/8010
    private RequestBody createRequestBody() {
        MediaType mediaType = MediaType.parse("application/xml");
        RequestBody body = RequestBody.create(mediaType,
                "<soapenv:Envelope xmlns:soapenv=\"" +
                        "http://schemas.xmlsoap.org/soap/envelope/\"" +
                        "\r\nxmlns:ofer=\"http://oferty.praca.gov.pl/oferta\">\r\n " +
                        "<soapenv:Header/>\r\n " +
                        "<soapenv:Body>\r\n" +
                        " <ofer:Dane>\r\n" +
                        " <pytanie>\r\n" +
                        " <Partner>STAT_Bydgoszcz</Partner>\r\n " +
                        "<Kryterium>\r\n " +
                        "<Wszystkie>true</Wszystkie>\r\n " +
                        "</Kryterium>\r\n " +
                        "</pytanie>\r\n " +
                        "</ofer:Dane>\r\n " +
                        "</soapenv:Body>\r\n" +
                        "</soapenv:Envelope>");
        return body;
    }

    protected Response callAndExecuteRequest() throws IOException {
        Response response = buildClient()
                .newCall(buildRequest()).execute();
        return response;

    }

    //    Set file path for downloaded files
    protected void downloadResponseFile() throws IOException {
        File downloadedFile = new File("C:\\Baza ofert\\", "CBOP");
        BufferedSink sink = Okio.buffer(Okio.sink(downloadedFile));
        sink.writeAll(Objects.requireNonNull(callAndExecuteRequest()
                .body()
                .source()));
        sink.close();
    }
}
