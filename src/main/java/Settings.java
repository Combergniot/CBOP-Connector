import java.time.format.DateTimeFormatter;

//TODO Fill proxy settings: proxyHost, username, password
public abstract class Settings {

    protected int proxyPort = 8080;
    protected String proxyHost = "";
    protected String username = "";
    protected String password = "";

    protected static final String OUTPUT_ZIP = "C:\\Baza ofert\\";
    protected static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
}
