package bolk_app.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class OrderFormController {

    @GetMapping("/form")
    public String form() {
        return "order-form";
    }

}
