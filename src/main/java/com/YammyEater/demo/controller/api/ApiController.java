package com.YammyEater.demo.controller.api;

import com.YammyEater.demo.domain.User;
import com.YammyEater.demo.dto.UserDto;
import com.YammyEater.demo.repository.UserRepository;
import com.YammyEater.demo.service.FoodService;
import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ApiController {
    @Autowired
    FoodService foodService;
    @Autowired
    UserRepository userRepository;

    @GetMapping("api/food")
    public String getFoodById(@RequestParam("type") String type, @RequestParam("name") String name, @RequestParam("tags") String tags){
        //Food = foodService.
        return type + "   |   " +  name + "   |   " + tags;
    }

    @GetMapping("api/user")
    public Page<UserDto> getUsers(Pageable pageable){
        var kk = userRepository.findAll(pageable).map(UserDto::from);
        return kk;
    }

    @PostConstruct
    public void init(){
        String[] tags = {"한식", "일식", "중식", "양식", "고기", "야채", "달콤한", "매운"};
        for(String tag : tags){

        }
        for(int i=0;i<100;i++){
            userRepository.save(User.builder()
                                    .email("email@" + i)
                                    .password("qqq")
                                    .username("user#" + i)
                                    .build());
        }
    }
}
