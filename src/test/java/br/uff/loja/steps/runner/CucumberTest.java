package br.uff.loja.steps.runner;

import org.junit.runner.RunWith;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = {"src/test/resources/features/carrinho.feature"},
        glue = "br.uff.loja.steps.stepDefinition",
        plugin = {"pretty", "html:target/cucumber"}
)
public class CucumberTest {
    
}
