import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import java.util.Locale;
import javax.annotation.Nonnull;
import net.dv8tion.jda.api.interactions.components.Button;
import net.dv8tion.jda.api.events.interaction.ButtonClickEvent;
import java.util.*;
import java.util.List;

public class blackjack extends ListenerAdapter {
    private final User user;
    private String[] cards = {
            "A♠", "2♠", "3♠", "4♠", "5♠", "6♠", "7♠", "8♠", "9♠", "10♠", "J♠", "Q♠", "K♠",
            "A♣", "2♣", "3♣", "4♣", "5♣", "6♣", "7♣", "8♣", "9♣", "10♣", "J♣", "Q♣", "K♣",
            "A♡", "2♡", "3♡", "4♡", "5♡", "6♡", "7♡", "8♡", "9♡", "10♡", "J♡", "Q♡", "K♡",
            "A♢", "2♢", "3♢", "4♢", "5♢", "6♢", "7♢", "8♢", "9♢", "10♢", "J♢", "Q♢", "K♢"};
    private int player1 = 0;
    private int player2 = 0;
    private String p1;
    private String p2;
    private String hidden;
    private EmbedBuilder game;
    private int bet;

    public blackjack(User user) {
        this.user = user;
    }

    @Override
    public void onButtonClick(@Nonnull ButtonClickEvent event) {
        super.onButtonClick(event);
        if (event.getUser().equals(user)) {
            if (event.getComponentId().equals("five") && event.getUser().equals(user)) {
                if (hasAmount(5,event)) {
                    bet = 5;
                    play(event);
                }
            }
            if (event.getComponentId().equals("ten") && event.getUser().equals(user)) {
                if (hasAmount(10,event)) {
                    bet = 10;
                    play(event);
                }
            }
            if (event.getComponentId().equals("twentyfive") && event.getUser().equals(user)) {
                if (hasAmount(25,event)) {
                    bet = 25;
                    play(event);
                }
            }
            if (event.getComponentId().equals("fifty") && event.getUser().equals(user)) {
                if (hasAmount(50,event)) {
                    bet = 50;
                    play(event);
                }
            }
            if (event.getComponentId().equals("deny") && event.getUser().equals(user)) {
                event.editMessage("Alright, no game").setActionRows().queue();
                event.getJDA().removeEventListener(this);
            }
            if (event.getComponentId().equals("hit") && event.getUser().equals(user)) {
                String card = cards[(int) Math.floor(Math.random() * cards.length)];
                p2 += "`" + card + "` ";
                player2 += getValue(card);
                game.clearFields();
                game.addField("Janaka", p1 + "`**`", true);
                game.addField(user.getName(), p2, true);
                if (player2 > 21) {
                    if (p2.contains("A")) {
                        for (int i = 0; i < p2.length(); i++) {
                            if (p2.charAt(i) == 'A')
                                player2 -= 10;
                        }
                    }
                    if (player2 > 21) {
                        game.clearFields();
                        game.addField("Janaka", p1 + hidden, true);
                        game.addField(user.getName(), p2, true);
                        game.setDescription("Bust - Janaka wins");
                        event.editMessage("").setEmbeds(game.build()).setActionRows().queue();
                        event.getJDA().removeEventListener(this);
                    }
                }
                if (player2 == 21) {
                    if (player2 > player1) {
                        game.clearFields();
                        game.addField("Janaka", p1 + hidden, true);
                        game.addField(user.getName(), p2, true);
                        game.setDescription(user.getName() + " Wins");
                        Main.addPoint(user,bet*2);
                    }
                    if (player1 == player2) {
                        game.clearFields();
                        game.addField("Janaka", p1 + hidden, true);
                        game.addField(user.getName(), p2, true);
                        game.setDescription("Tie");
                    }
                    event.editMessage("").setEmbeds(game.build()).setActionRows().queue();
                    event.getJDA().removeEventListener(this);
                }
                if (player2 < 21) {
                    if (p2.contains("A")) {
                        for (int i = 0; i < p2.length(); i++) {
                            if (p2.charAt(i) == 'A')
                                player2 += 10;
                        }
                    }
                    event.editMessage("").setEmbeds(game.build()).queue();
                }
            }
            if (event.getComponentId().equals("stand") && event.getUser().equals(user)) {
                p1 += hidden;
                while(player1 < 17) {
                    String card = cards[(int) Math.floor(Math.random() * cards.length)];
                    p1 += "`" + card + "` ";
                    player1 += getValue(card);
                }
                game.clearFields();
                game.addField("Janaka", p1,true);
                game.addField(user.getName(),p2,true);
                if (player1 > player2) {
                    if (player1 <= 21) {
                        game.setDescription("Janaka Wins");
                    }
                    if (player1 > 21) {
                        game.setDescription("Bust - " + user.getName() + " wins");
                        Main.addPoint(user,bet*2);
                    }
                }
                if (player2 > player1) {
                    if (player2 == 21) {
                        game.setDescription("Blackjack!!" + user.getName() + " Wins");
                        Main.addPoint(user,(int)Math.floor(bet*2.2));
                    } else {
                        game.setDescription(user.getName() + " Wins");
                        Main.addPoint(user,bet*2);
                    }
                }
                if (player1 == player2) {
                    Main.addPoint(user,bet);
                    game.setDescription("Draw");
                }
                event.editMessage("").setEmbeds(game.build()).setActionRows().queue();
                event.getJDA().removeEventListener(this);
            }
        }
    }

    public void play(ButtonClickEvent event) {
        Main.addPoint(user,-bet);
        String card = cards[(int) Math.floor(Math.random() * cards.length)];
        p1 = "`" + card + "` ";
        player1 += getValue(card);
        card = cards[(int) Math.floor(Math.random() * cards.length)];
        player1 += getValue(card);
        hidden = "`" + card + "` ";
        card = cards[(int) Math.floor(Math.random() * cards.length)];
        p2 = "`" + card + "` ";
        player2 += getValue(card);
        card = cards[(int) Math.floor(Math.random() * cards.length)];
        p2 += "`" + card + "` ";
        player2 += getValue(card);
        game = new EmbedBuilder();
        game.setTitle("BlackJack | bet: " + bet);
        game.addField("Janaka", p1 + "`**`", true);
        game.addField(user.getName(), p2, true);
        event.editMessage("").setEmbeds(game.build()).setActionRow(
                Button.success("hit", "hit"),
                Button.danger("stand", "stand")
        ).queue();
    }

    public int getValue(String card) {
        switch(card.charAt(0)) {
            case 'A':
                return 11;
            case '2':
                return 2;
            case '3':
                return 3;
            case '4':
                return 4;
            case '5':
                return 5;
            case '6':
                return 6;
            case '7':
                return 7;
            case '8':
                return 8;
            case '9':
                return 9;
            case '1':
                return 10;
            case 'J':
                return 10;
            case 'Q':
                return 10;
            case 'K':
                return 10;
            default:
                return 0;
        }
    }

    public boolean hasAmount(int amount, ButtonClickEvent event) {
        Map<User, Integer> map = Main.getUnsortedMap();
        if (map.get(user) < amount) {
            event.editMessage("You don't have enough points").setActionRows().queue();
            event.getJDA().removeEventListener(this);
            return false;
        }
        return true;
    }
}