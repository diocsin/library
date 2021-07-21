package com.example.library.library.controller;

import com.example.library.library.model.User;
import com.example.library.library.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Controller
@RequestMapping("/users")
public class UserController {

    private static final String USER_TABLE = "users/user :: user_list";
    private static final String ERROR_ALERT = "fragments/alert :: alert";
    private static final String EDIT_MODAL = "users/modal/editUser";
    private static final String ADD_MODAL = "users/modal/addUser";

    UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/addUser")
    public String addUser(Model model) {
        try {
            model.addAttribute("predefinedRoles", userService.getAllRoles());
            model.addAttribute("user", new User());
            return ADD_MODAL;
        } catch (Exception e) {
            System.err.println(e.getMessage());
            return ADD_MODAL;
        }
    }

    @GetMapping("/checkLogin")
    public String checkLogin(@RequestParam("login") String login, Model model, HttpServletResponse response) {
        if (userService.getUserByLogin(login).isPresent()) {
            response.setStatus(HttpServletResponse.SC_CONFLICT);
            model.addAttribute("message", "Пользователь с таким логином уже существует");
            model.addAttribute("alertClass", "alert-danger");
        }
        return ERROR_ALERT;
    }

    @PostMapping("/saveUser")
    public String saveUser(User user, Model model) {
        try {
            userService.createNewUser(user);
            model.addAttribute("users", userService.getAllUsers());
            model.addAttribute("message", "Пользователь успешно добавлен");
            model.addAttribute("alertClass", "alert-success");
            return USER_TABLE;
        } catch (Exception e) {
            model.addAttribute("users", userService.getAllUsers());
            model.addAttribute("message", "Ошибка добавления пользователя");
            model.addAttribute("alertClass", "alert-danger");
            return USER_TABLE;
        }
    }

    private boolean checkUserLoginUnique(String login) {
        return !userService.getUserByLogin(login).isPresent();
    }

    @GetMapping("/edit")
    public String editUser(Long pid, Model model) {
        try {
            User user = userService.getUserById(pid);
            model.addAttribute("predefinedRoles", userService.getAllRoles());
            model.addAttribute("user", user);
            return EDIT_MODAL;
        } catch (Exception e) {
            System.err.println(e.getMessage());
            return EDIT_MODAL;
        }
    }

    @PostMapping("/updateUser")
    public String updateUser(User user, Model model) {
        try {
            userService.updateUser(user);
            model.addAttribute("users", userService.getAllUsers());
            model.addAttribute("message", "Пользователь успешно изменен");
            model.addAttribute("alertClass", "alert-success");
            return USER_TABLE;
        } catch (Exception e) {
            model.addAttribute("users", userService.getAllUsers());
            model.addAttribute("message", "Ошибка редактирования пользователя");
            model.addAttribute("alertClass", "alert-danger");
            return USER_TABLE;
        }
    }

    @RequestMapping(value = "/delete", method = {RequestMethod.DELETE, RequestMethod.GET})
    public String deleteUser(Long pid, Model model) {
        try {
            userService.deleteUserById(pid);
            model.addAttribute("users", userService.getAllUsers());
            model.addAttribute("message", "Пользователь успешно удален");
            model.addAttribute("alertClass", "alert-success");
            return USER_TABLE;
        } catch (Exception e) {
            model.addAttribute("users", userService.getAllUsers());
            model.addAttribute("message", "Ошибка удаления пользователя");
            model.addAttribute("alertClass", "alert-danger");
            return USER_TABLE;
        }
    }

    @GetMapping("/filter")
    public String filterUser(String filterText, Model model) {
        List<User> filterUsers;
        try {
            if (!StringUtils.isBlank(filterText)) {
                filterUsers = userService.filterUser(filterText);
            } else {
                filterUsers = userService.getAllUsers();
            }
            model.addAttribute("users", filterUsers);
            return USER_TABLE;
        } catch (Exception e) {
            model.addAttribute("users", userService.getAllUsers());
            model.addAttribute("message", "При работе с пользователями произошла ошибка");
            model.addAttribute("alertClass", "alert-danger");
            return USER_TABLE;
        }
    }
}
