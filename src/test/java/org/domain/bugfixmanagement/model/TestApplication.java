package org.domain.bugfixmanagement.model;


import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.TestPropertySource;

@SpringBootConfiguration
@EnableAutoConfiguration
@ComponentScan(basePackages = {"org.domain.bugfixmanagement"})
@TestPropertySource("classpath:application.properties")
public class TestApplication {

}
