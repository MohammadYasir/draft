package com.forkbrainz.netcomp.security.web;

import com.forkbrainz.netcomp.security.model.User;
import com.forkbrainz.netcomp.security.service.UserService;
import com.forkbrainz.netcomp.security.web.dto.UserRegistrationDto;
import java.util.Map;
import java.util.UUID;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import javax.validation.Valid;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class UserRegistrationController {

    @Autowired
    private UserService userService;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @ModelAttribute("user")
    public UserRegistrationDto userRegistrationDto() {
        return new UserRegistrationDto();
    }

    @GetMapping("/registration")
    public String showRegistrationForm(Model model) {
        return "registration";
    }

    @GetMapping("/forgotpassword")
    public ModelAndView showForgotPasswordPage(ModelAndView modelAndView) {
        modelAndView.addObject("reset", "reset");
        modelAndView.setViewName("forgotpwd");
        return modelAndView;
    }

    @GetMapping("/confirm")
    public ModelAndView showConfirmPage(ModelAndView modelAndView, @RequestParam("token") String token) {
        User user = userService.findByConfirmationToken(token);

        if (user == null) { // No token found in DB
            modelAndView.addObject("invalidToken", "Oops!  This is an invalid confirmation link.");
        } else { // Token found
            modelAndView.addObject("confirmationToken", user.getConfirmationToken());
        }

        modelAndView.setViewName("confirm");
        return modelAndView;
    }

    @PostMapping("/forgotpassword")
    public ModelAndView processForgotPwdReq(ModelAndView modelAndView, BindingResult bindingResult, @RequestParam Map requestParams) {
        modelAndView.setViewName("forgotpwd");
        User user = userService.findByEmail((String) requestParams.get("email"));
        if (user == null) {
            modelAndView.addObject("error", "error");
            modelAndView.addObject("reset", "reset");
        } else {
            String confirmToken = UUID.randomUUID().toString();
            user.setConfirmationToken(confirmToken);
            userService.save(user);
            String link = "/confirm?token=" + confirmToken;
            System.out.println("Confirmation link is :");
            System.out.println(link);
            modelAndView.addObject("success", "success");
        }

        return modelAndView;
    }

    @PostMapping("/confirm")
    public ModelAndView processConfirmationForm(ModelAndView modelAndView, BindingResult bindingResult, @RequestParam Map requestParams, RedirectAttributes redir) {
        modelAndView.setViewName("confirm");

        User user = userService.findByConfirmationToken((String) requestParams.get("token"));
        System.out.println((CharSequence) requestParams.get("newpassword"));
        System.out.println(user);
        user.setPassword(passwordEncoder.encode((CharSequence) requestParams.get("newpassword")));

        // Save user
        userService.save(user);
        modelAndView.addObject("successMessage", "Your password has been set!");
        System.out.println(user);
        return modelAndView;
    }

    @PostMapping("/registration")
    public String registerUserAccount(@ModelAttribute("user") @Valid UserRegistrationDto userDto,
            BindingResult result, HttpServletRequest request) {

        User existing = userService.findByEmail(userDto.getEmail());
        if (existing != null) {
            result.rejectValue("email", null, "There is already an account registered with that email");
        }

        if (result.hasErrors()) {
            return "registration";
        }
        String confirmToken = UUID.randomUUID().toString();
        userService.save(userDto, confirmToken);
        String appUrl = request.getScheme() + "://" + request.getServerName();
        String link = appUrl + "/confirm?token=" + confirmToken;
        System.out.println("Confirmation link is :");
        System.out.println(link);
        return "redirect:/registration?success";
    }

}
