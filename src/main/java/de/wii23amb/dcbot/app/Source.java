package de.wii23amb.dcbot.app;

import de.wii23amb.dcbot.structure.BotStartService;
import net.dv8tion.jda.api.EmbedBuilder;
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

import java.awt.*;
import java.time.Instant;
import java.util.Random;

@Service
@DependsOn("botStartService")
public class Source extends ListenerAdapter {

    private final BotStartService botStartService;
    private final JDA jda;

    @Autowired
    public Source(BotStartService botStartService, JDA jda) {
        this.botStartService = botStartService;

        this.jda = jda;

        this.jda.getGuilds().get(0).upsertCommand(
                        new CommandDataImpl("source", "Get Repo"))
                .queue();
    }

    @Override
    public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {
        if (event.getFullCommandName().equals("source")) {
            EmbedBuilder eb = new EmbedBuilder();
            eb.setColor(Color.CYAN);
            eb.setAuthor("Requested by " + event.getMember().getUser().getName(), event.getMember().getUser().getAvatarUrl());
            eb.setDescription("https://github.com/antonbowe/wwi23ambbot");
            eb.setTimestamp(Instant.now());

            event.replyEmbeds(eb.build()).queue();
        }
    }


}
