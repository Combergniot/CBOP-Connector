import java.time.format.DateTimeFormatter;

public abstract class Settings {

    protected int proxyPort = 8080;
    protected String proxyHost = "10.51.55.34";
    protected String username = "wernerowiczm";
    protected String password = "!Panaceum10";

    protected static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
}
