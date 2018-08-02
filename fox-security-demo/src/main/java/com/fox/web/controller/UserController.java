package com.fox.web.controller;

import java.util.ArrayList;
import java.util.List;
import javax.validation.Valid;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.annotation.JsonView;
import com.fox.dto.User;
import com.fox.dto.User.UserDetailView;
import com.fox.dto.User.UserSimpleView;
import com.fox.dto.UserQueryCondition;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequestMapping("user")
@RestController
public class UserController {


  @GetMapping
  @JsonView(UserSimpleView.class)
  public ResponseEntity<?> list(@Valid UserQueryCondition condition, @PageableDefault(size = 10,
      direction = Sort.Direction.DESC, sort = "age") Pageable pageable) {
    log.info("--------------condition:{}", condition);
    log.info("--------------pageable:{}", JSON.toJSONString(pageable));
    ArrayList<User> list = new ArrayList<>();
    list.add(new User());
    list.add(new User());
    list.add(new User());

    return new ResponseEntity<List<User>>(list, HttpStatus.OK);
  }

  @GetMapping("{id:\\d+}")
  @JsonView(UserDetailView.class)
  public ResponseEntity<?> getUser(@PathVariable String id) {
    log.info("------------id:{}", id);

    User user = new User();
    user.setUsername("test");
    user.setPassword("123456");

    return new ResponseEntity<User>(user, HttpStatus.OK);
  }

  @PostMapping
  @JsonView(UserDetailView.class)
  public ResponseEntity<?> postUser(@Valid @RequestBody User user) {
    log.info("------------user:{}", user);
    user.setId(1L);
    return new ResponseEntity<User>(user, HttpStatus.OK);
  }

  @PutMapping("{id:\\d+}")
  @JsonView(UserDetailView.class)
  public ResponseEntity<?> putUser(@Valid @RequestBody User user, BindingResult errors) {
    log.info("------------user:{}, errors:{}", user, errors);
    if (errors.hasErrors()) {

    }
    user.setId(1L);
    return new ResponseEntity<User>(user, HttpStatus.OK);
  }

  @DeleteMapping("{id:\\d+}")
  @JsonView(UserDetailView.class)
  public ResponseEntity<?> deleteUser(@PathVariable String id) {
    log.info("------------id:{}", id);
    return new ResponseEntity<Void>(HttpStatus.OK);
  }
}
