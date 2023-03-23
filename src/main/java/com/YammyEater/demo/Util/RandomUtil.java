package com.YammyEater.demo.Util;

import com.YammyEater.demo.domain.Food;
import com.YammyEater.demo.dto.FoodDetailResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.function.Predicate;
import java.util.function.UnaryOperator;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import javax.annotation.PostConstruct;
import org.springframework.stereotype.Component;

@Component
public class RandomUtil {
    List<String> randomStrCandidate;
    @PostConstruct
    public void init() {
        randomStrCandidate = new ArrayList<>();
        for(int i=48;i<=57;i++) {
            randomStrCandidate.add(String.valueOf((char)i));
        }
        for(int i=65;i<=90;i++) {
            randomStrCandidate.add(String.valueOf((char)i));
        }
        for(int i=97;i<=122;i++) {
            randomStrCandidate.add(String.valueOf((char)i));
        }

    }
    //정의한 candidate의 문자를 이용해 len 길이만큼의 무작위 문자열 생성.
    public String getRandomString(int len) {
        Random rand = new Random();
        return rand.ints(0,randomStrCandidate.size() - 1)
                .limit(len)
                .mapToObj(x -> randomStrCandidate.get(x))
                .collect(Collectors.joining());
    }
}
