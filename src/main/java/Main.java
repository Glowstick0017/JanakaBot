import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import javax.annotation.Nonnull;
import javax.security.auth.login.LoginException;
import java.util.Locale;

public class Main extends ListenerAdapter {
    private static JDA jda;
    public static void main(String[] args) throws LoginException {
        String token = "ENTER TOKEN HERE";
        JDABuilder builder = JDABuilder.createDefault(token);
        builder.setActivity(Activity.watching("college students suffer with javaFX"));
        builder.addEventListeners(new Main());
        jda = builder.build();
    }

    @Override
    public void onMessageReceived(@Nonnull MessageReceivedEvent event) {
        super.onMessageReceived(event);
        if (event.getMessage().getContentDisplay().toUpperCase(Locale.ROOT).equals("JANAKA")) {
            event.getChannel().sendMessage("Jak").queue();
        }
    }
}
