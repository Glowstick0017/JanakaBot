import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import java.util.Locale;
import javax.annotation.Nonnull;
import net.dv8tion.jda.api.interactions.components.Button;
import net.dv8tion.jda.api.events.interaction.ButtonClickEvent;

public class Game extends ListenerAdapter {
  private final User user;

  public Game(User user) {
    this.user = user;
  }

  @Override
  public void onButtonClick(@Nonnull ButtonClickEvent event) {
    super.onButtonClick(event);
    if (event.getComponentId().equals("rps") && event.getUser().equals(user)) {
      event.editMessage("Rock, paper, scissors?").setActionRow(
              Button.primary("rock", "🪨"),
              Button.success("paper", "📰"),
              Button.secondary("scissors", "✂️")
      ).queue();
      event.getJDA().addEventListener(new rps(user));
      event.getJDA().removeEventListener(this);
    }
    if (event.getComponentId().equals("bj") && event.getUser().equals(user)) {
      event.editMessage("Enter Bet Amount").queue(InteractionHook -> {
        InteractionHook.editOriginalComponents().setActionRow(
                Button.primary("five", "💵5💵"),
                Button.primary("ten", "💶10💶"),
                Button.primary("twentyfive", "💷25💷"),
                Button.primary("fifty", "💴50💴"),
                Button.secondary("deny", "❌")
        ).queue();
      });
      event.getJDA().addEventListener(new blackjack(user));
      event.getJDA().removeEventListener(this);
    }
    if (event.getComponentId().equals("deny") && event.getUser().equals(user)) {
      event.editMessage("Alright, no game").setActionRows().queue();
      event.getJDA().removeEventListener(this);
    }
  }
}

