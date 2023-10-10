package de.wii23amb.dcbot.app;

import de.wii23amb.dcbot.structure.BotStartService;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.internal.interactions.CommandDataImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.event.ContextStartedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

@Service // <- Diese Annotation unbedingt verwenden, damit die Klasse vom System erfasst wird
@DependsOn("botStartService")
public class Example extends ListenerAdapter
/* extends ListenerAdapter <- notwendig, sofern der Discord Events empfangen werden */ {

  private final BotStartService botStartService;
  private final JDA jda;

  @Autowired
  public Example(BotStartService botStartService, JDA jda) {
    this.botStartService =
        botStartService; // <- über den BotStartService kann auf verschiedene Grundfunktionen
    // zugegriffen werden

    this.jda = jda;

    this.jda.getGuilds().get(0).upsertCommand(
                    new CommandDataImpl("helloworld", "Example command")
                            .addOption(OptionType.STRING, "name", "Enter your name", true))
            .queue();
  }

  @Override
  public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {
    if (event.getFullCommandName().equals("helloworld")) {
      event.deferReply().queue();
      String name = event.getOption("name").getAsString();
      event.getHook().editOriginal("Hello, " + name).queue();
    }
  }


}
