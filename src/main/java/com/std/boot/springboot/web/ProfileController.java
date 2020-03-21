package com.std.boot.springboot.web;

import lombok.RequiredArgsConstructor;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@RequiredArgsConstructor
@RestController
public class ProfileController {
    private final Environment env;

    @GetMapping("/profile")
    public String profile() {
        /*
        현재 실해중인 ActiveProfile을 모두 가져옴
        real, oauth, real-db등이 활성화되어 있다면 3개가 모두 담겨 있음
        real, real1, real2는 모두 배포에 사용될 profile이라 이 중 하나라도 있으면 그 값을 반환함
        여기서는 real1, real2만 사용되지만 real도 사용할 수 있으니 남겨둠
         */
        List<String> profiles = Arrays.asList(env.getActiveProfiles());
        List<String> realProfiles = Arrays.asList("real", "real1", "real2");
        String defaultProfile = profiles.isEmpty() ? "default" : profiles.get(0);

        return profiles.stream()
                .filter(realProfiles::contains)
                .findAny()
                .orElse(defaultProfile);
    }
}
