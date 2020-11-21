package myProject;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;

import springfox.documentation.swagger2.annotations.EnableSwagger2;

import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.builders.ApiInfoBuilder;

/**
 * 
 *  @author Jacob Benton (jsbenton), Noah Cantrell (nbc)
 *
 */
@Configuration
@EnableSwagger2
public class SpringFoxConfig
{
	@Bean
	public Docket myDocket() {
		return new Docket(DocumentationType.SWAGGER_2)
				.apiInfo(apiInfo())
				.select()
				.apis(RequestHandlerSelectors.basePackage("myProject"))
				.paths(PathSelectors.any())
				.build();
	}
	
	private ApiInfo apiInfo() {
		return new ApiInfoBuilder()
				.title("KK_04 QuackManager Server API")
				.description("Hi, welcome to the backend of the Coms309 KK_04 project, here is the API document!")
				.termsOfServiceUrl("https://coms-309-kk-04.cs.iastate.edu:8080/terms")
				.contact(new Contact("Jacob Benton", "https://git.linux.iastate.edu/cs309/fall2020/kk_4","jsbenton@iastate.edu"))
				.contact(new Contact("Noah Catrell", "https://git.linux.iastate.edu/cs309/fall2020/kk_4", "nbc@iastate.edu"))
				.contact(new Contact("Jacob Beattie", "https://git.linux.iastate.edu/cs309/fall2020/kk_4", "jbeattie@iastate.edu"))
				.contact(new Contact("Ethan Lindsey", "https://git.linux.iastate.edu/cs309/fall2020/kk_4", "elindsey@iastate.edu"))
				.version("1.0-SNAPSHOT")
				.build();
	}

}
