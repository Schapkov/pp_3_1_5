package com.andrey.pp_3_1_5.controller;


import com.andrey.pp_3_1_5.exceptions.ExceptionInfo;
import com.andrey.pp_3_1_5.exceptions.UserUsernameExistException;
import com.andrey.pp_3_1_5.model.User;
import com.andrey.pp_3_1_5.service.RoleService;
import com.andrey.pp_3_1_5.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;


import javax.validation.Valid;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@org.springframework.web.bind.annotation.RestController
@RequestMapping("/admin")
public class RestController {

    private final UserService userService;
    private final RoleService roleService;


    @Autowired
    public RestController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;

    }




    @GetMapping("/users/all")
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> list = userService.findAll();

        return (list != null && !list.isEmpty())
                ? new ResponseEntity<>(list, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<User> getUserById(@PathVariable long id) {
        User user = userService.getUserById(id);

        return (user != null)
                ? new ResponseEntity<>(user, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping("/users")
    public ResponseEntity<?> createUser(@RequestBody @Valid User user, BindingResult bindingResult) {
        User checkUserName = userService.findByUsername(user.getUsername());
        if (bindingResult.hasErrors()) {
            String error = getErrorsFromBindingResult(bindingResult);
            return new ResponseEntity<>(new ExceptionInfo(error), HttpStatus.BAD_REQUEST);
        }
        if (checkUserName != null) {
            FieldError error = new FieldError("user", "username", "Username is already in use ");
            bindingResult.addError(error);
            String error1 = getErrorsFromBindingResult(bindingResult);
            return new ResponseEntity<>(new ExceptionInfo(error1), HttpStatus.BAD_REQUEST);
        }

        userService.saveUser(user);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @PutMapping("/users/{id}")
    public ResponseEntity<?> updateUser(@RequestBody @Valid User user, BindingResult bindingResult, @PathVariable long id) {
        User checkUserName = userService.findByUsername(user.getUsername());

        if (bindingResult.hasErrors()) {
            String error = getErrorsFromBindingResult(bindingResult);
            return new ResponseEntity<>(new ExceptionInfo(error), HttpStatus.BAD_REQUEST);
        }
        if (checkUserName != null &&
                !Objects.equals(checkUserName.getId(), user.getId())) {
            FieldError error = new FieldError("user", "username", "Username is already in use ");
            bindingResult.addError(error);
            String error1 = getErrorsFromBindingResult(bindingResult);
            return new ResponseEntity<>(new ExceptionInfo(error1), HttpStatus.BAD_REQUEST);

        }
        user.setId(user.getId());
        userService.saveUser(user);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity<User> deleteUser(@PathVariable long id) {
        userService.deleteById(id);

        return new ResponseEntity<>(HttpStatus.OK);
    }


    private String getErrorsFromBindingResult(BindingResult bindingResult) {
        return bindingResult.getFieldErrors()
                .stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .collect(Collectors.joining("; "));
    }


    @ExceptionHandler
    public ResponseEntity<ExceptionInfo> createUser(ExceptionInfo exceptionInfo) {
        ExceptionInfo exceptionInfo1 = new ExceptionInfo();
        exceptionInfo1.setInfo(exceptionInfo.getInfo());
        return new ResponseEntity<>(exceptionInfo1, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    public ResponseEntity<ExceptionInfo> createUser(UserUsernameExistException exceptionInfo) {
        ExceptionInfo exceptionInfo1 = new ExceptionInfo();
        exceptionInfo1.setInfo(exceptionInfo.getMessage());
        return new ResponseEntity<>(exceptionInfo1, HttpStatus.BAD_REQUEST);
    }


}
