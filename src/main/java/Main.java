import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.interactions.components.Button;

import javax.annotation.Nonnull;
import javax.annotation.OverridingMethodsMustInvokeSuper;
import javax.security.auth.login.LoginException;
import java.util.Locale;
import java.awt.*;
import java.util.*;
import java.util.List;
import java.io.*;
import com.google.common.base.Splitter;

public class Main extends ListenerAdapter {

  private static Map<User, Integer> unsortedMap = new HashMap<User, Integer>();
  private static JDA jda;
  private static EmbedBuilder leader;

  public static void main(String[] args) throws LoginException, IOException {
    String token = "OTEwNzI3MTQ0MTg1OTM3OTgw.YZXCvw.reEMgHggsW1gpSnmXdzLiLSYRI0";
    JDABuilder builder = JDABuilder.createDefault(token);
    builder.setActivity(Activity.watching("college students suffer with Visual Studio"));
    builder.addEventListeners(new Main());
    jda = builder.build();

    String str = "";
    try {
      File myObj = new File("database.txt");
      Scanner myReader = new Scanner(myObj);
      while (myReader.hasNextLine()) {
        String data = myReader.nextLine();
        str+=data;
      }
      myReader.close();
    } catch (FileNotFoundException e) {
      System.out.println("An error occurred.");
      e.printStackTrace();
    }

    Map<String, String> properties = Splitter.on(",")
            .withKeyValueSeparator("=")
            .split(str);
    for (Map.Entry<String, String> entry : properties.entrySet()) {
      String id = (String) entry.getKey();
      id = id.substring(id.indexOf("(") + 1);
      id = id.substring(0, id.indexOf(")"));
      User u = jda.retrieveUserById(id).complete();
      unsortedMap.put(u,Integer.parseInt(entry.getValue().toString().replaceAll("[^0-9.]", "")));
    }
  }

  @Override
  public void onMessageReceived(@Nonnull MessageReceivedEvent event) {
    super.onMessageReceived(event);
    if (event.getMessage().getContentDisplay().toUpperCase(Locale.ROOT).equals("JANAKA")) {
      // event.getChannel().sendMessage("Jak").queue();
      // reply is better, pings user, looks better
      event.getMessage().reply("Jak").queue();
    }

    if (event.getMessage().getContentDisplay().toUpperCase(Locale.ROOT).equals("JED")) {
      event.getMessage().reply("Jed is superior").queue();
    }

    if (event.getMessage().getContentDisplay().toUpperCase(Locale.ROOT).equals("GAME")) {
      if (!unsortedMap.containsKey(event.getAuthor())) {
        if (!event.getAuthor().isBot()) {
          unsortedMap.put(event.getAuthor(), 0);
        }
      }
      event.getMessage().reply("Which game would you like to play?").setActionRow(Button.primary("rps", "🪨📰✂️"),Button.primary("bj", "🃏"),Button.secondary("deny", "❌")).queue();
      event.getJDA().addEventListener(new Game(event.getAuthor()));
    }

    if (event.getMessage().getContentDisplay().toUpperCase(Locale.ROOT).equals("POINTS"))
    {
      EmbedBuilder bal = new EmbedBuilder();
      //bal.setTitle(event.getAuthor().getName());
      bal.setAuthor(event.getAuthor().getName(), event.getAuthor().getAvatarUrl(), event.getAuthor().getAvatarUrl());
      //bal.setThumbnail(event.getAuthor().getAvatarUrl());
      bal.setDescription("Points : " + unsortedMap.get(event.getAuthor()));
      event.getMessage().replyEmbeds(bal.build()).queue();
    }

    if (event.getMessage().getContentDisplay().toUpperCase(Locale.ROOT).equals("LEADERBOARD")) {
      Map<User, Integer> sortedMap = sortByValue(unsortedMap);
      leader = new EmbedBuilder();
      leader.setColor(Color.getColor("#9d9287"));
      leader.setTitle("Leaderboard");
      printMap(sortedMap);
      event.getChannel().sendMessageEmbeds(leader.build()).queue();
    }
  }

  public static void addPoint(User userID, int points) {
    unsortedMap.put(userID, unsortedMap.get(userID) + points);
    try {
      FileWriter myWriter = new FileWriter("database.txt");
      myWriter.write(unsortedMap.toString());
      myWriter.close();
    } catch (Exception e) {

    }
  }

  private static Map<User, Integer> sortByValue(Map<User, Integer> unsortMap) {
    List<Map.Entry<User, Integer>> list = new LinkedList<Map.Entry<User, Integer>>(unsortMap.entrySet());

    Collections.sort(list, new Comparator<Map.Entry<User, Integer>>() {
      public int compare(Map.Entry<User, Integer> o1, Map.Entry<User, Integer> o2) {
        return (o2.getValue()).compareTo(o1.getValue());
      }
    });

    Map<User, Integer> sortedMap = new LinkedHashMap<User, Integer>();
    for (Map.Entry<User, Integer> entry : list) {
      sortedMap.put(entry.getKey(), entry.getValue());
    }

    return sortedMap;
  }

  /**
   * prints the sorted map as a discord embed message
   * @param map sorted map
   */
  public static <K, V> void printMap(Map<K, V> map) {
    int i = 1;
    for (Map.Entry<K, V> entry : map.entrySet()) {
      User user = (User) entry.getKey();
      int points = (int) entry.getValue();
      //System.out.println("Key : " + entry.getKey() + " Value : " + entry.getValue());
      switch (i) {
        case 1:
          leader.appendDescription("\uD83D\uDC51 -> `" + user.getName() + "` : " + points + "\n");
          break;
        case 2:
          leader.appendDescription("\uD83E\uDD48 -> `" + user.getName() + "` : " + points + "\n");
          break;
        case 3:
          leader.appendDescription("\uD83E\uDD49 -> `" + user.getName() + "` : " + points + "\n");
          break;
        case 4:
          leader.appendDescription("\u0034\uFE0F\u20E3 -> `" + user.getName() + "` : " + points + "\n");
          break;
        case 5:
          leader.appendDescription("\u0035\uFE0F\u20E3 -> `" + user.getName() + "` : " + points + "\n");
          break;
        case 6:
          leader.appendDescription("\u0036\uFE0F\u20E3 -> `" + user.getName() + "` : " + points + "\n");
          break;
        case 7:
          leader.appendDescription("\u0037\uFE0F\u20E3 -> `" + user.getName() + "` : " + points + "\n");
          break;
        case 8:
          leader.appendDescription("\u0038\uFE0F\u20E3 -> `" + user.getName() + "` : " + points + "\n");
          break;
        case 9:
          leader.appendDescription("\u0039\uFE0F\u20E3 -> `" + user.getName() + "` : " + points + "\n");
          break;
        case 10:
          leader.setTitle("Leaderboard top 10");
          leader.appendDescription("\uD83D\uDD1F -> `" + user.getName() + "` : " + points + "\n");
          break;
      }
      i++;
    }
  }

  public static Map<User, Integer> getUnsortedMap() {
    return unsortedMap;
  }
}
