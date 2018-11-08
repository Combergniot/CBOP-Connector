import java.io.IOException;
import java.time.LocalDateTime;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

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
                methods.getResponse();
//                    String answer = response.body().string();
//                    BufferedWriter writer = new BufferedWriter(new FileWriter("C:\\Baza ofert\\CBOP_Output.txt"));
//                    writer.write(answer);
//                    writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        };
        scheduler.schedule(runnable, 10, TimeUnit.HOURS);

    }
}
