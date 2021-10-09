package com.my.multimodule;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ModuleStudyLibraryApplicationTests {

    @Autowired
    private MyService myService;

    @DisplayName("context load 테스트")
    @Test
    public void contextLoads() {
        assertThat(myService.message())
            .isNotNull();
    }

    @DisplayName("yml 설정 값 비교")
    @Test
    public void contextCompare() {
        assertThat(myService.message())
            .isEqualTo("테스트 메시지");
    }

    @SpringBootApplication
    static class TestConfiguration {
    }

}
