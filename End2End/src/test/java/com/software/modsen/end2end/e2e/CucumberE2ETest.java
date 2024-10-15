package com.software.modsen.end2end.e2e;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = "classpath:features",
        glue = "com.software.modsen.end2end.steps"
)
public class CucumberE2ETest {
}
