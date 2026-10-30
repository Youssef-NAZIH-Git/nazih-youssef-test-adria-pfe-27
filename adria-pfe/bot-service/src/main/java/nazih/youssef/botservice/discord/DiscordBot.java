package nazih.youssef.botservice.discord;

import jakarta.annotation.PreDestroy;
import nazih.youssef.botservice.service.BankAssistantService;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.requests.GatewayIntent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Component
@ConditionalOnProperty(name = "discord.enabled", havingValue = "true", matchIfMissing = true)
public class DiscordBot extends ListenerAdapter {
    private static final Logger log = LoggerFactory.getLogger(DiscordBot.class);
    private final BankAssistantService assistantService;
    private final ExecutorService executor = Executors.newVirtualThreadPerTaskExecutor();
    private final JDA jda;

    public DiscordBot(BankAssistantService assistantService, @Value("${discord.token}") String token) {
        this.assistantService = assistantService;
        this.jda = JDABuilder.createDefault(token)
                .enableIntents(GatewayIntent.MESSAGE_CONTENT, GatewayIntent.DIRECT_MESSAGES)
                .setEnableShutdownHook(false)
                .addEventListeners(this)
                .build();
    }

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        if (event.getAuthor().isBot() || event.isWebhookMessage()) {
            return;
        }

        String query = event.getMessage().getContentRaw().trim();
        if (query.equals("!bank") || query.startsWith("!bank ")) {
            query = query.substring(5).trim();
        } else if (event.isFromGuild()) {
            if (!event.getMessage().getMentions().isMentioned(event.getJDA().getSelfUser())) {
                return;
            }
            String botId = event.getJDA().getSelfUser().getId();
            query = query.replace("<@" + botId + ">", "").replace("<@!" + botId + ">", "").trim();
        }

        if (query.isBlank()) {
            event.getChannel().sendMessage("Posez une question, par exemple : !bank Liste les comptes").queue();
            return;
        }

        String message = query;
        String conversationId = "discord:" + event.getChannel().getId() + ":" + event.getAuthor().getId();
        executor.submit(() -> {
            event.getChannel().sendTyping().queue();
            try {
                String response = assistantService.chat(message, conversationId);
                int start = 0;
                while (start < response.length()) {
                    int end = Math.min(start + 2000, response.length());
                    if (end < response.length() && Character.isHighSurrogate(response.charAt(end - 1))) {
                        end--;
                    }
                    event.getChannel().sendMessage(response.substring(start, end))
                            .setAllowedMentions(Collections.emptyList())
                            .queue();
                    start = end;
                }
            } catch (RuntimeException exception) {
                log.warn("Discord chat failed", exception);
                event.getChannel().sendMessage("L'assistant est momentanément indisponible. Réessayez plus tard.").queue();
            }
        });
    }

    @PreDestroy
    public void shutdown() {
        executor.shutdownNow();
        jda.shutdownNow();
    }
}
