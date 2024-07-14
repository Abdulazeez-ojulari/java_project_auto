package org.afripay.afripay.dashboard.controller;

import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import org.afripay.afripay.dashboard.service.DashboardService;
import org.afripay.afripay.general.dto.Response;
import org.afripay.afripay.general.service.GeneralService;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@CrossOrigin
@RestController
@RequestMapping("api/v1/dashboard")
@RequiredArgsConstructor
public class DashboardController {

    private final GeneralService generalService;
    private final DashboardService dashboardService;
    
    
    @GetMapping
    public Response getDashboard(@Parameter Principal principal, @RequestParam(defaultValue = "10") int size,
                                 @RequestParam(defaultValue = "1") int page,
                                 @RequestParam(required = false) String status,
                                 @RequestParam(required = false) String rrn,
                                 @RequestParam(required = false) String toDate,
                                 String fromDate) {
        var response = dashboardService.getDashboardTransactions(principal.getName(), size, page, status, rrn, toDate, fromDate);
        
        
        return generalService.prepareSuccessResponse(response);
    }
    
    @GetMapping("numbers-of-all-transactions")
    public Response getNumbersOfAllTransactions(){
        
        long response = dashboardService.getNumbersOfAllTransactions();
        
        return generalService.prepareSuccessResponse(response);
    }
    
    @GetMapping(value = "{staus}")
    public Response getNumbersOfTransactionsByStatus(@PathVariable String status) {
        var response =dashboardService.getDashboardNumbersOfTransactionsByStatus(status);
        
        return generalService.prepareSuccessResponse(response);
    }
}