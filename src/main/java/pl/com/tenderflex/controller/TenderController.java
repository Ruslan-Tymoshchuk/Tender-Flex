package pl.com.tenderflex.controller;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import lombok.RequiredArgsConstructor;
import pl.com.tenderflex.dto.Attachment;
import pl.com.tenderflex.dto.TenderDetailsRequest;
import pl.com.tenderflex.service.TenderService;

@RestController
@RequestMapping("/api/v1/tender")
@RequiredArgsConstructor
public class TenderController {

    private final TenderService tenderService;

    @PostMapping(consumes = { "multipart/form-data" })
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void createTender(@AuthenticationPrincipal(expression = "id") Integer contractorId,
            @ModelAttribute Attachment attachment, @RequestPart("tender") TenderDetailsRequest tenderDetails) {
        tenderService.createTender(attachment, tenderDetails, contractorId);  
    }
}