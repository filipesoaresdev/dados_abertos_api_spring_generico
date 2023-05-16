package br.ufpi.dadosabertosapi.doc;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.Tag;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {
	public static final String DADOS_ABERTOS= "DADOS_ABERTOS";
	public static final String CURRICULO = "CURRICULO";
	@Bean
	public Docket api() {
		return new Docket(DocumentationType.SWAGGER_2).select().apis(RequestHandlerSelectors.any())
				.paths(PathSelectors.any())
				.build()
				.tags(new Tag(DADOS_ABERTOS, "Endpoint para disponibilização dos dados abertos da UFPI"))
				.tags(new Tag(CURRICULO, "Endpoint de API para consulta de dados dos currículos dos integrantes da comunidade acadêmica da UFPI. Os dados são importados do CNPq lattes."));
	}
 
}
