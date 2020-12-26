package com.github.hjdeepsleep.toy.study;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.exc.UnrecognizedPropertyException;
import com.github.hjdeepsleep.toy.domain.study.Animal;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class ObjectMapperTest {

    private static ObjectMapper om;
    private static final String JSON_STR = "{\"name\":\"tiger\",\"weight\":30,\"age\":2}";
    private static final Animal tiger = new Animal("tiger", 30, 2);

    @BeforeAll
    public static void init() {
        om = new ObjectMapper();
    }

    @Test
    public void 생성자이용() throws Exception {
        //when
        Animal animal = om.readValue(JSON_STR, Animal.class);

        //then
        Assertions.assertTrue(animal.equals(tiger));
    }

    @Test
    public void 출력() throws Exception {
        //when
        String result = om.writeValueAsString(tiger);

        //then
        System.out.println("result : " + result);
        System.out.println("JSON_STR : " + JSON_STR);
        Assertions.assertTrue(result.equals(JSON_STR));
    }

    @Test
    public void 객체로_변환() throws Exception {
        //given
        String str = "{\"name\":\"tiger\",\"weight\":30,\"age\":2,\"height\":50}";

        //then
        Assertions.assertThrows(UnrecognizedPropertyException.class,
                () -> om.readValue(str, Animal.class));
    }

    @Test
    public void 객체로_변환2() throws Exception {
        //given
        ObjectMapper om2 = new ObjectMapper();
        om2.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        String str = "{\"name\":\"tiger\",\"weight\":30,\"age\":2,\"height\":50}";

        //when
        Animal result = om2.readValue(str, Animal.class);

        //then
        Assertions.assertTrue(result.equals(tiger));
    }

    @Test
    public void 필드이용() throws Exception {
        //given
        ObjectMapper om2 = new ObjectMapper();
        om2.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);

        //when
        Animal result = om2.readValue(JSON_STR, Animal.class);

        //then
        Assertions.assertTrue(result.equals(tiger));
    }

    @Test
    public void 필드이용하여_출력() throws Exception {
        //given
        ObjectMapper om2 = new ObjectMapper();
        om2.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.NONE);
        om2.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);

        //when
        String result = om2.writeValueAsString(tiger);

        //then
        Assertions.assertTrue(result.equals(JSON_STR));
    }
}
