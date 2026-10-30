package nazih.youssef.botservice.web.rest;

import jakarta.servlet.http.HttpSession;
import nazih.youssef.botservice.service.BankAssistantService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ChatController {
    private final BankAssistantService assistantService;

    public ChatController(BankAssistantService assistantService) {
        this.assistantService = assistantService;
    }

    @GetMapping(value = "/chat", produces = MediaType.TEXT_PLAIN_VALUE)
    public String chat(@RequestParam String query, HttpSession session) {
        return assistantService.chat(query, "web:" + session.getId());
    }

    @PostMapping(value = "/chat", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.TEXT_PLAIN_VALUE)
    public String chat(@RequestBody ChatRequest request, HttpSession session) {
        return assistantService.chat(request.query(), "web:" + session.getId());
    }

    public record ChatRequest(String query) {
    }
}
