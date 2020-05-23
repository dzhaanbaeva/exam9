package kg.exam9.forum.controller;

import kg.exam9.forum.dto.ThemeDTO;
import kg.exam9.forum.service.PropertiesService;
import kg.exam9.forum.service.ThemeService;
import kg.exam9.forum.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.security.Principal;
import java.time.LocalDateTime;

@RestController
@RequestMapping("/theme")
public class ThemeController {

    @Autowired
    private final ThemeService themeService;
    private final PropertiesService propertiesService;
    private final UserService userService;

    public ThemeController(ThemeService themeService, PropertiesService propertiesService, UserService userService) {
        this.themeService = themeService;
        this.propertiesService = propertiesService;
        this.userService = userService;
    }

    private static <T> void constructPageable(Page<T> list, int pageSize, Model model, String uri) {
        if (list.hasNext()) {
            model.addAttribute("nextPageLink", constructPageUri(uri, list.nextPageable().getPageNumber(), list.nextPageable().getPageSize()));
        }

        if (list.hasPrevious()) {
            model.addAttribute("prevPageLink", constructPageUri(uri, list.previousPageable().getPageNumber(), list.previousPageable().getPageSize()));
        }

        model.addAttribute("hasNext", list.hasNext());
        model.addAttribute("hasPrev", list.hasPrevious());
        model.addAttribute("items", list.getContent());
        model.addAttribute("defaultPageSize", pageSize);
    }

    private static String constructPageUri(String uri, int page, int size) {
        return String.format("%s?page=%s&size=%s", uri, page, size);
    }

    @RequestMapping("/theme")
    public String getMainPageTest(Model model, Pageable pageable, HttpServletRequest uriBuilder) {
        var theme = themeService.getTheme(pageable);
        var uri = uriBuilder.getRequestURI();
        constructPageable(theme, propertiesService.getDefaultPageSize(), model, uri);
        model.addAttribute("products", themeService.getThemes());
        return "theme";
    }


    @PostMapping("add/theme")
    public ThemeDTO themeSave(
            @RequestParam(defaultValue = "--no-value--") Integer themeText, Principal principal) throws IOException {
        var userEmail = userService.getByEmail(principal.getName());
        var user = userService.getUser(userEmail);
        LocalDateTime date = LocalDateTime.now();

//        ThemeDTO theme;
//        theme = new ThemeDTO(themeText,date,user);
//        themeService.addTheme(theme);
//
//        return theme;

    }



}
