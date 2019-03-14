package com.orangehrmlive.demo.utils;

import com.orangehrmlive.demo.annotations.Steps;
import com.orangehrmlive.demo.pages.Page;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

import java.lang.reflect.Field;

public abstract class StepsFactory {

    public static <T extends BaseTest> void initPageSteps(T test, WebDriver driver) throws IllegalAccessException, InstantiationException {
        for (Field field : test.getClass().getDeclaredFields()) {
            if (field.isAnnotationPresent(Steps.class)) {
                    Object steps;
                    if (PageSteps.class.isAssignableFrom(field.getType())) {
                        steps = StepsFactory.create(driver, (Class<? extends PageSteps>) field.getType());
                    } else {
                        steps = StepsFactory.create(field.getType());
                    }
                    field.setAccessible(true);
                    field.set(test, steps);
            }
        }
    }

    private static <T extends PageSteps> T create(WebDriver driver, Class<? extends PageSteps> stepsClass) throws
            IllegalAccessException, InstantiationException {
        T steps = (T) stepsClass.newInstance();
        steps.driver = driver;
        for (Field field : stepsClass.getDeclaredFields()) {
            if (Page.class.isAssignableFrom(field.getType())) {
                Object page = PageFactory.initElements(driver, field.getType());
                field.setAccessible(true);
                field.set(steps, page);
            }
        }

        return steps;
    }

    private static <T> T create(Class<T> stepClass) throws IllegalAccessException, InstantiationException {
        T steps = stepClass.newInstance();
        return steps;
    }

}
