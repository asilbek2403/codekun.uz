package dasturlashasil.uz.controller;

import dasturlashasil.uz.Dto.sms.SmsHistoryDto;
import dasturlashasil.uz.sms.SmsHistoryService;
import dasturlashasil.uz.util.PageUtil;
import dasturlashasil.uz.util.PhoneUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/v1/sms-history")
public class SmsHistoryController {
    @Autowired
    private SmsHistoryService smsHistoryService;

    @GetMapping("/by-phone")
    public ResponseEntity<List<SmsHistoryDto>> getSmsHistoryByEmail(@RequestParam("phone") String phone) {
        List<SmsHistoryDto> history = smsHistoryService.getSmsHistoryByPhone(PhoneUtil.toLocalPhone(phone));
        return ResponseEntity.ok(history);
    }

    @GetMapping("/by-date")
    public ResponseEntity<List<SmsHistoryDto>> getEmailHistoryByDate(@RequestParam("date") LocalDate date) {
        List<SmsHistoryDto> history = smsHistoryService.getSmsHistoryByDate(date);
        return ResponseEntity.ok(history);
    }

    @GetMapping("/pagination")
    public ResponseEntity<Page<SmsHistoryDto>> getPaginatedPhoneHistory(@RequestParam(defaultValue = "0") int page,
                                                                        @RequestParam(defaultValue = "10") int size) {
        Page<SmsHistoryDto> paginatedHistory = smsHistoryService.getPaginatedSmsHistory(PageUtil.page(page), size);
        return ResponseEntity.ok(paginatedHistory);
    }

}
