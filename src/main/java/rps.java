import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import java.util.Locale;
import javax.annotation.Nonnull;
import net.dv8tion.jda.api.interactions.components.Button;
import net.dv8tion.jda.api.events.interaction.ButtonClickEvent;

public class rps extends ListenerAdapter {
    private final User user;

    public rps(User user) {
        this.user = user;
    }

    @Override
    public void onButtonClick(@Nonnull ButtonClickEvent event) {
        super.onButtonClick(event);
        if (event.getUser().equals(user)) {
            String[] choices = { "ROCK", "PAPER", "SCISSORS" };
            String chosen = choices[(int) Math.floor(Math.random() * choices.length)];
            String userchoise = event.getComponentId();
            if (event.getComponentId().equals("rock")) {
                if (chosen.equals("PAPER")) {
                    event.editMessage("Janaka: 📰\n" + user.getName() + ": 🪨\nJanaka Wins").queue(InteractionHook -> {
                        InteractionHook.editOriginalComponents().setActionRows().queue();
                    });
                }
                if (chosen.equals("SCISSORS")) {
                    event.editMessage("Janaka: ✂️\n" + user.getName() + ": 🪨\nYou Win").queue(InteractionHook -> {
                        InteractionHook.editOriginalComponents().setActionRows().queue();
                    });
                    Main.addPoint(user, 1);
                }
                if (chosen.equals("ROCK")) {
                    event.editMessage("Janaka: 🪨\n" + user.getName() + ": 🪨\nTie").queue(InteractionHook -> {
                        InteractionHook.editOriginalComponents().setActionRows().queue();
                    });
                }
            }
            if (event.getComponentId().equals("paper")) {
                if (chosen.equals("ROCK")) {
                    event.editMessage("Janaka: 🪨\n" + user.getName() + ": 📰\nYou Win").queue(InteractionHook -> {
                        InteractionHook.editOriginalComponents().setActionRows().queue();
                    });
                    Main.addPoint(user, 1);
                }
                if (chosen.equals("SCISSORS")) {
                    event.editMessage("Janaka: ✂️\n" + user.getName() + ": 📰\nJanaka Wins").queue(InteractionHook -> {
                        InteractionHook.editOriginalComponents().setActionRows().queue();
                    });
                }
                if (chosen.equals("PAPER")) {
                    event.editMessage("Janaka: 📰\n" + user.getName() + ": 📰\nTie").queue(InteractionHook -> {
                        InteractionHook.editOriginalComponents().setActionRows().queue();
                    });
                }

            }
            if (event.getComponentId().equals("scissors")) {
                if (chosen.equals("PAPER")) {
                    event.editMessage("Janaka: 📰\n" + user.getName() + ": ✂️\nYou Win").queue(InteractionHook -> {
                        InteractionHook.editOriginalComponents().setActionRows().queue();
                    });
                    Main.addPoint(user, 1);
                }
                if (chosen.equals("ROCK")) {
                    event.editMessage("Janaka: 🪨\n" + user.getName() + ": ✂️\nJanaka Wins").queue(InteractionHook -> {
                        InteractionHook.editOriginalComponents().setActionRows().queue();
                    });
                }
                if (chosen.equals("SCISSORS")) {
                    event.editMessage("Janaka: ✂️\n" + user.getName() + ": ✂️\nTie").queue(InteractionHook -> {
                        InteractionHook.editOriginalComponents().setActionRows().queue();
                    });
                }

            }
            event.getJDA().removeEventListener(this);
        }
    }
}