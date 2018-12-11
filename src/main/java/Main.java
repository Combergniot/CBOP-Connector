import java.io.IOException;
import java.time.LocalDateTime;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * App send single request in XML to CBOP server after 10 hours waiting.
 * The server sends json files with data from 5:00 pm to 7:00 am
 *
 */
public class Main extends Settings {

    private static final ScheduledExecutorService scheduler =
            Executors.newSingleThreadScheduledExecutor();

    public static void main(String[] args) {
        Methods methods = new Methods();

        System.out.println(LocalDateTime.now().format(formatter) +
                ": Starting ten-hour countdown now...");

        Runnable runnable = () -> {
            System.out.println(LocalDateTime.now().format(formatter) +
                    ": Out of time! Let's go!");

            try {
                methods.callAndExecuteRequest();
                methods.downloadResponseFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        };
        scheduler.schedule(runnable, 10, TimeUnit.HOURS);

    }
}
