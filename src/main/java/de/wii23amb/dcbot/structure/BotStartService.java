package de.wii23amb.dcbot.structure;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;

@Configuration
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class BotStartService {

  @Getter private final JDA jda;

  @Value("${discord.mainguild.id}")
  private long mainGuildId;

  @Getter @Setter private Guild mainGuild;

  @EventListener
  public void contextRefreshed(ContextRefreshedEvent event) {
    List<Guild> guilds = this.jda.getGuilds();

    if (guilds.isEmpty()) {
      System.out.println(
          "The bot instance is currently not in a guild. Invite: "
              + this.jda.getInviteUrl(Permission.ADMINISTRATOR));
      this.jda.shutdownNow();
      return;
    }
    System.out.println("The bot instance is currently in " + guilds.size() + " guild(s).");
    this.mainGuild = this.jda.getGuildById(this.mainGuildId);
    if (this.mainGuild == null) {
      System.out.println(
          "The bot instance is not a part of the defined main guild. The instance will shut down.");
      this.jda.shutdownNow();
      return;
    }

    ApplicationContext context = event.getApplicationContext();
    this.jda.addEventListener(context.getBeansOfType(ListenerAdapter.class).values().toArray());
  }
}
