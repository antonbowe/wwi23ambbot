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
public class EightBall extends ListenerAdapter {

    private final BotStartService botStartService;
    private final JDA jda;
    private static final String[] answers = {
            "I honestly couldn't care less.",
            "Good question! Make a better one.",
            "ERROR: Question not found.",
            "Make anodah one.",
            "You call that a question? < THIS is a question.",
            "QUESTION ME YOU FILTHY ANIMAL",
            "Great question! Do it again.",
            "This ain't no question mate.",
            "Ok, now ask me a real question.",
            "Next time try an actual question.",
            "What do you and a banana have in common? Both can't ask questions.",
            "English modahfuckah, do you speak it?",
            "NEXT QUESTION!",
            "Ask that fucker Bixby. You know, Siri's cousin twice removed.",
            "You should ask that bitch Alexa.",
            "Haven't you been in school?",
            "Try a non-stupid question.",
            "yes",
            "no",
            "definitely",
            "probably not",
            "How do you know about it?",
            "Ask later when no children are listening",
            "Who told you that?",
            "I guess so",
            "Why would someone say yes?",
            "Are you serious? YES!",
            "Why would someone care?",
            "What do you expect me to say?",
            "I guess no",
            "I would say yes",
            "I would say no",
            "I am not really sure",
            "Why would anyone ask such a stupid question?",
            "If you ask such a stupid question again I will ban you",
            "It is certain",
            "It is decidedly so",
            "Without a doubt",
            "Without a doubt",
            "Signs point to yes",
            "Not even close",
            "Ben's mom told me yes",
            "Better not tell you now",
            "My sources say no",
            "You want me to scream?",
            "I will kill you all",
            "No, they're off the wine",
            "Tie me to the roof",
            "You've Got To Be Kidding...",
            "You Wish",
            "Yeah Right",
            "Yeah And I'm The Fucking Pope",
            "Who Cares?",
            "Yes... You Prick",
            "What Do You Think?",
            "Well Maybe",
            "That's Ridiculous",
            "Sure",
            "Oh Please",
            "All signs point to yes. But on second thought, go fuck yourself.",
            "My sources say no. They also tell me they hate you and hope you burn in hell.",
            "IDIOT WARNING!!! We got an idiot over here!",
            "Just so you know, they're watching every question you make, every grammar mistake, every retarded question, as you keep trying to see what would I answer if you just didn't ask anything worthwhile.",
            "Please step away from the computer and tell that to someone that gives a shit.",
            "I only answer to proper grammar.",
            "Oh what's that? I can't understand your horrible punctuation.",
            "I really hope your grammar teacher isn't seeing this.",
            "Fun fact: I was programmed to not answer your primitive word usage.",
            "Did you know that 4/5 doctors recommend proper grammar? The last one is actually a dentist!",
            "So is that the result of dropping school?",
            "I apologize my good sir, but your language does not seem to suit the criteria required for I, the 8 Bot, to accordingly reward you with a proper response. I advise using proper punctuation in your request.",
            "Bitch are you even trying to spell?",
            "Spell with me; Q-U-E-S-T-I-O-N M-A-R-K.",
            "I'll have you know that my creator works for Discord and your lack of basic grammar triggers me.",
            "Watch your language, children are seeing this.",
            "Roses are red, Violets are blue, You didn't ask me anything, So I shall not answer you.",
            "Without a doubt.",
            "It is certain.",
            "It is decidedly so.",
            "Yes, definitely.",
            "You may rely on it.",
            "As I see it, yes.",
            "Most likely.",
            "Outlook good.",
            "Signs point to yes.",
            "Ask again later.",
            "Reply hazy try again.",
            "Try again later.",
            "Better not tell you now.",
            "Cannot predict now.",
            "Concentrate and ask again.",
            "Don't count on it.",
            "My reply is no.",
            "My sources say no.",
            "Outlook not so good.",
            "Very doubtful.",
            "Yes.",
            "No.",
            "Yep.",
            "Nope.",
            "y35.",
            "n0.",
            "Only the prophecy will tell.",
            "Who cares? We all die in the end.",
            "Isn't it obvious?",
            "Obviously, yes.",
            "Yes, duh?",
            "I don't think so, no.",
            "Who gives a fuck?",
            "You wish.",
            "Is this a joke?",
            "Ask me if I care.",
            "Fuck do I know, I'm just a magic ball.",
            "No God, please, no.",
            "Just google it.",
            "Bitch, I don't know your life.",
            "Google might have the answer.",
            "Help! I'm trapped!",
            "Perhaps.",
            "Maybe, just maybe.",
            "You bet!",
            "Grow up and make your own decisions, idiot.",
            "Trust me, you don't want to know.",
            "No Ron.",
            "Ask Michael.",
            "Hell if I know.",
            "Barely possible.",
            "It's a secret to everybody.",
            "It depends.",
            "Don't take my word for it.",
            "Yeah, right.",
            "Sure, if you think so.",
            "In your dreams.",
            "Sounds good to me.",
            "Not yet.",
            "Probably.",
            "Very likely.",
            "Very unlikely.",
            "Not advisable.",
            "Give it time.",
            "When the planets align.",
            "You already know the answer to that.",
            "Maybe, in a few weeks, if you're lucky.",
            "Never in a million years, maybe in fewer.",
            "It smells like it.",
            "Possibly, but possibly it is impossible.",
            "They didn't allow me to tell you.",
            "No fucking way.",
            "Oh hell no!",
            "Kill them, kill all of them.",
            "Anything is possible.",
            "In theory, yes.",
            "I don't even know what to answer you.",
            "I don't know and I don't care.",
            "The answer is C.",
            "Ask me again tomorrow.",
            "I strongly believe so.",
            "It looks like it.",
            "I wouldn't worry about it.",
            "Go fish.",
            "NEXT QUESTION!",
            "yo that sounds lit fam",
            "Don't let your dreams be dreams.",
            "Follow your heart, I wouldn't trust your mind though.",
            "Sure, why not?",
            "Just ask yourself: \"Would John Cena do it?\"",
            "JUST DO IT!",
            "I might know the answer, but maybe a bribe can help me remember better...",
            "It depends, does it give you EXP?",
            "I rolled a dice to answer you, and it said the answer is C.",
            "\"Anything is possible.\" Said the blind boy as it proceeded to walk into a pit.",
            "42.",
            "It ain't worth it, m8.",
            "Would it make life better?",
            "Why are we still here?",
            "No! Think of the children!",
            "please save me she's forcing me to lie",
            "Short answer: Yes. Long answer: No.",
            "It all depends if you have a good kda ratio.",
            "I don't know, can a match box?",
            "If Leo won the Oscar and we got Finding Nemo 2, It's probably possible.",
            "What could possibly go wrong?",
            "What's the worse that could happen?",
            "Ah, I see you're an entity of culture as well."
    };

    @Autowired
    public EightBall(BotStartService botStartService, JDA jda) {
        this.botStartService = botStartService;

        this.jda = jda;

        this.jda.getGuilds().get(0).upsertCommand(
                        new CommandDataImpl("8ball", "Ask the magic miracle for help.")
                                .addOption(OptionType.STRING, "question", "The question", true))
                .queue();
    }

    @Override
    public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {
        if (event.getFullCommandName().equals("8ball")) {
            String question = event.getOption("question").getAsString();
            int randomIndex = new Random().nextInt(answers.length);
            String randomAnswer = answers[randomIndex];

            EmbedBuilder eb = new EmbedBuilder();
            eb.setColor(Color.CYAN);
            eb.setTitle("\"" + question + "\"");
            eb.setAuthor("Requested by " + event.getMember().getUser().getAsTag(), event.getMember().getUser().getAvatarUrl());
            eb.setDescription("`" + randomAnswer + "`");
            eb.setThumbnail("https://images-ext-1.discordapp.net/external/F3wT0ODh1hfdnZqnk0vzT5Seg3wIr551w0iuWvfGeoM/https/i.imgur.com/nXanD2e.png");
            eb.setTimestamp(Instant.now());

            event.replyEmbeds(eb.build()).queue();
        }

    }
}
