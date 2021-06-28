package demo.hao;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Locale;

@RestController
public class LocaleMessageController {

    @Autowired
    MessageSource messageSource;

    @GetMapping("/locale-hi")
    public String hi(Locale locale) {
        return messageSource.getMessage("message.hi", null, locale);
    }

}