package nazih.youssef.botservice.service;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.memory.MessageWindowChatMemory;
import org.springframework.ai.tool.ToolCallbackProvider;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ResponseStatusException;

@Service
public class BankAssistantService {
    private final ChatClient chatClient;

    public BankAssistantService(ChatClient.Builder builder, ToolCallbackProvider tools) {
        ChatMemory memory = MessageWindowChatMemory.builder().maxMessages(20).build();
        this.chatClient = builder
                .defaultSystem("""
                        Tu es l'assistant de la banque. Réponds dans la langue de l'utilisateur.
                        Consulte les outils pour répondre aux questions sur les comptes et les transactions.
                        N'invente jamais de compte, de solde, de transaction ou d'identifiant.
                        Si une information est indisponible ou qu'un outil échoue, indique-le clairement.
                        Un compte null dans une transaction signifie que ses détails sont indisponibles.
                        Tu peux consulter les données, mais tu ne peux pas créer de transaction ni effectuer de virement.
                        """)
                .defaultTools(tools)
                .defaultAdvisors(MessageChatMemoryAdvisor.builder(memory).build())
                .build();
    }

    public String chat(String query, String conversationId) {
        if (!StringUtils.hasText(query)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "La question est obligatoire");
        }

        String response = chatClient.prompt()
                .user(query)
                .advisors(advisor -> advisor.param(ChatMemory.CONVERSATION_ID, conversationId))
                .call()
                .content();

        if (!StringUtils.hasText(response)) {
            throw new ResponseStatusException(HttpStatus.BAD_GATEWAY, "L'assistant n'a pas fourni de réponse");
        }
        return response;
    }
}
