package ca.ised.sts.integration;

import org.apache.camel.test.spring.CamelSpringRunner;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;

@SpringBootTest
@TestPropertySource(locations = {"classpath:env-variables-test.properties","classpath:application-test.properties"})
@ContextConfiguration( classes = Application.class)
@ExtendWith(MockitoExtension.class)
@RunWith(CamelSpringRunner.class)
public abstract class BaseTest {

}
