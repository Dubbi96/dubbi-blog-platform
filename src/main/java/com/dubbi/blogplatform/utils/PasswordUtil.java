package com.dubbi.blogplatform.utils;

import lombok.extern.slf4j.Slf4j;

import java.util.Random;

@Slf4j
public class PasswordUtil {
    // 클래스 레벨에서 한 번만 Random 인스턴스 생성
    private static final Random random = new Random();

    /**Utility Class를 외부에서 Instance화 시키는 것을 방지하기 위해, instance화 시킬 경우 Exception 발생*/
    private PasswordUtil() {
        throw new IllegalStateException("Cannot construct utility class");
    }

    public static String generateRandomPassword() {
        int index;
        char[] charSet = new char[]{
                '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
                'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M',
                'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z',
                'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm',
                'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z'
        };
        StringBuilder password = new StringBuilder();

        for (int i = 0; i < 8; i++) {
            index = random.nextInt(charSet.length);
            password.append(charSet[index]);
        }
        log.warn("new OAuth2.0 user detected : {}", password);
        return password.toString();
    }
}
