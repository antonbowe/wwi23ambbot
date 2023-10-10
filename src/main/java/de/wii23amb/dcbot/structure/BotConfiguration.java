package de.wii23amb.dcbot.structure;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.SneakyThrows;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.utils.ChunkingFilter;
import net.dv8tion.jda.api.utils.MemberCachePolicy;
import net.dv8tion.jda.api.utils.cache.CacheFlag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class BotConfiguration {

    @Value("${discord.token}")
    private String botToken;

    @Bean
    @SneakyThrows
    public JDA buildJda() {
        JDABuilder jdaBuilder = JDABuilder.createDefault(this.botToken);

        jdaBuilder
                .disableCache(CacheFlag.ACTIVITY)
                .setMemberCachePolicy(MemberCachePolicy.ONLINE.or(MemberCachePolicy.VOICE))
                .setChunkingFilter(ChunkingFilter.NONE)
                .disableIntents(
                        GatewayIntent.GUILD_WEBHOOKS,
                        GatewayIntent.GUILD_PRESENCES,
                        GatewayIntent.GUILD_MESSAGE_TYPING,
                        GatewayIntent.DIRECT_MESSAGE_TYPING)
                .enableIntents(GatewayIntent.GUILD_MEMBERS);
        return jdaBuilder.build().awaitReady();
    }

}
