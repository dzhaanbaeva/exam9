package kg.exam9.forum.controller;

import kg.exam9.forum.exception.ResourceNotFoundException;
import kg.exam9.forum.model.AuthorRegisterForm;
import kg.exam9.forum.model.PasswordResetToken;
import kg.exam9.forum.model.User;
import kg.exam9.forum.repository.ResetRepository;
import kg.exam9.forum.repository.UserRepository;
import kg.exam9.forum.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.security.Principal;
import java.util.UUID;

@Controller
@RequestMapping
@AllArgsConstructor
public class UserController {

    private final UserService userService;
    private final UserRepository repository;
    private final ResetRepository resetRepo;

    @GetMapping("/profile")
    public String pageAuthorProfile(Model model, Principal principal)
    {
        var user = userService.getByEmail(principal.getName());
        model.addAttribute("dto", user);
        return "profile";
    }

    @GetMapping("/register")
    public String pageRegisterAuthor(Model model) {
        if (!model.containsAttribute("dto")) {
            model.addAttribute("dto", new AuthorRegisterForm());
        }
        return "register";
    }

    @PostMapping("/register")
    public String registerPage(@Valid AuthorRegisterForm authorRequestDto,
                               BindingResult validationResult,
                               RedirectAttributes attributes) {
        attributes.addFlashAttribute("dto", authorRequestDto);

        if (validationResult.hasFieldErrors()) {
            attributes.addFlashAttribute("errors", validationResult.getFieldErrors());
            return "redirect:/register";
        }
        userService.register(authorRequestDto);
        return "redirect:/login";
    }

    @GetMapping("/forgot-password")
    public String pageForgotPassword(Model model) {
        return "forgot";
    }

    @PostMapping("/forgot-password")
    public String submitForgotPasswordPage(@RequestParam("email") String email,
                               RedirectAttributes attributes) {

        if (!repository.existsByEmail(email)) {
            attributes.addFlashAttribute("errorText", "Entered email does not exist!");
            return "redirect:/";
        }

        PasswordResetToken pToken = PasswordResetToken.builder()
                .author(repository.findByEmail(email).get())
                .token(UUID.randomUUID().toString())
                .build();

        resetRepo.deleteAll();
        resetRepo.save(pToken);

        return "redirect:/forgot-success";
    }

    @GetMapping("/forgot-success")
    public String pageResetPassword(Model model) {
        return "forgot-success";
    }

    @PostMapping("/reset-password")
    public String submitResetPasswordPage(@RequestParam("token") String token,
                                          @RequestParam("newPassword") String newPassword,
                                           RedirectAttributes attributes) {

        if (!resetRepo.existsByToken(token)) {
            attributes.addFlashAttribute("errorText", "Entered email does not exist!");
            return "redirect:/reset-password";
        }
        PasswordResetToken pToken = resetRepo.findByToken(token).get();
        User author = repository.findById(pToken.getAuthor().getId()).get();
        author.setPassword(new BCryptPasswordEncoder().encode(newPassword));
        repository.save(author);
        return "redirect:/login";
    }

    @GetMapping("/login")
    public String loginPage(@RequestParam(required = false, defaultValue = "false") Boolean error, Model model) {
        model.addAttribute("error", error);
        return "login";
    }



    @ExceptionHandler(ResourceNotFoundException.class)
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    private String handleRNF(ResourceNotFoundException ex, Model model) {

        model.addAttribute("resource", ex.getResource());
        model.addAttribute("id", ex.getId());
        return "resource-not-found";
    }
}
