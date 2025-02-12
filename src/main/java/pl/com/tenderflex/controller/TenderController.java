package pl.com.tenderflex.controller;

import static pl.com.tenderflex.security.SecurityRoles.*;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pl.com.tenderflex.model.User;
import pl.com.tenderflex.payload.Page;
import pl.com.tenderflex.payload.request.TenderRequest;
import pl.com.tenderflex.payload.response.TenderCountResponse;
import pl.com.tenderflex.payload.response.TenderResponse;
import lombok.RequiredArgsConstructor;
import pl.com.tenderflex.service.RoleBasedActionExecutor;
import pl.com.tenderflex.service.TenderService;

@RestController
@RequestMapping("/api/v1/tenders")
@RequiredArgsConstructor
public class TenderController {

    public static final String URI_TENDERS = "";
    public static final String URI_TENDERS_ALL = "/all";
    public static final String URI_TENDERS_ID = "/{id}";
    public static final String URI_TENDERS_COUNT = "/count";
    
    private final TenderService tenderService;
    private final RoleBasedActionExecutor roleBasedActionExecutor;
    
    @Secured(CONTRACTOR)
    @PostMapping(URI_TENDERS)
    public TenderResponse create(@RequestBody TenderRequest tenderRequest) {
         return tenderService.save(tenderRequest);
    }
    
    @Secured({ CONTRACTOR, BIDDER })
    @GetMapping(URI_TENDERS_ALL)
    public Page<TenderResponse> findPage(@AuthenticationPrincipal User user,
            @RequestParam(defaultValue = "1") Integer currentPage,
            @RequestParam(defaultValue = "10") Integer tendersPerPage) {
        return roleBasedActionExecutor.executeRoleBasedAction(user,
                contractor -> tenderService.findByContractorWithPagination(user.getId(), currentPage, tendersPerPage),
                bidder -> tenderService.findWithPagination(currentPage, tendersPerPage));
    }

    @Secured({ CONTRACTOR, BIDDER })
    @GetMapping(URI_TENDERS_ID)
    public TenderResponse findById(@PathVariable("id") Integer tenderId) {
        return tenderService.findById(tenderId);
    }
    
    @Secured({ CONTRACTOR, BIDDER })
    @GetMapping(URI_TENDERS_COUNT)
    public TenderCountResponse count(@AuthenticationPrincipal User user) {
        return roleBasedActionExecutor.executeRoleBasedAction(user,
                contractor -> tenderService.countByContractor(contractor.getId()), 
                bidder -> tenderService.countAll());    
    }
    
}