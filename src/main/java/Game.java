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
    if (event.getComponentId().equals("accept") && event.getUser().equals(user)) {
      event.editMessage("Rock, paper, scissors?").queue(InteractionHook -> {
        InteractionHook.editOriginalComponents().setActionRow(Button.primary("rock", "🪨"),
            Button.success("paper", "📰"), Button.secondary("scissors", "✂️")).queue();
      });
      event.getJDA().addEventListener(new rps(user));
      event.getJDA().removeEventListener(this);
    }
    if (event.getComponentId().equals("deny") && event.getUser().equals(user)) {
      event.editMessage("Alright, no game").queue(InteractionHook -> {
        InteractionHook.editOriginalComponents().setActionRows().queue();
      });
      event.getJDA().removeEventListener(this);
    }
  }
}

class rps extends ListenerAdapter {
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
          Main.addPoint(user, 1);
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
          Main.addPoint(user, 1);
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

  @Override
  public void onMessageReceived(@Nonnull MessageReceivedEvent event) {
    super.onMessageReceived(event);
    if (event.getAuthor().equals(user)) {
      String[] choices = { "ROCK", "PAPER", "SCISSORS" };
      String chosen = choices[(int) Math.floor(Math.random() * choices.length)];
      String userChoice = event.getMessage().getContentDisplay().toUpperCase(Locale.ROOT);
      if (userChoice.equals("ROCK")) {
        if (chosen.equals("PAPER")) {
          event.getMessage().reply("Janaka: 📰\n" + user.getName() + ": 🪨\nJanaka Wins").queue();
        }
        if (chosen.equals("SCISSORS")) {
          event.getMessage().reply("Janaka: ✂️\n" + user.getName() + ": 🪨\nYou Win").queue();
          Main.addPoint(user, 1);
        }
        if (chosen.equals("ROCK")) {
          event.getMessage().reply("Janaka: 🪨\n" + user.getName() + ": 🪨\nTie").queue();
          Main.addPoint(user, 1);
        }
        event.getJDA().removeEventListener(this);
      } else if (userChoice.equals("PAPER")) {
        if (chosen.equals("ROCK")) {
          event.getMessage().reply("Janaka: 🪨\n" + user.getName() + ": 📰\nYou Win").queue();
          Main.addPoint(user, 1);
        }
        if (chosen.equals("SCISSORS")) {
          event.getMessage().reply("Janaka: ✂️\n" + user.getName() + ": 📰\nJanaka Wins").queue();
        }
        if (chosen.equals("PAPER")) {
          event.getMessage().reply("Janaka: 📰\n" + user.getName() + ": 📰\nTie").queue();
          Main.addPoint(user, 1);
        }
        event.getJDA().removeEventListener(this);
      } else if (userChoice.equals("SCISSORS")) {
        if (chosen.equals("PAPER")) {
          event.getMessage().reply("Janaka: 📰\n" + user.getName() + ": ✂️\nYou Win").queue();
          Main.addPoint(user, 1);
        }
        if (chosen.equals("ROCK")) {
          event.getMessage().reply("Janaka: 🪨\n" + user.getName() + ": ✂️\nJanaka Wins").queue();
        }
        if (chosen.equals("SCISSORS")) {
          event.getMessage().reply("Janaka: ✂️\n" + user.getName() + ": ✂️\nTie").queue();
        }
        event.getJDA().removeEventListener(this);
      } else {
        event.getMessage().reply("That's not a choice, Janaka wins").queue();
        event.getJDA().removeEventListener(this);
      }
    }
  }
}
