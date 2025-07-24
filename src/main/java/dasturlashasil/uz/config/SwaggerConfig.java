package dasturlashasil.uz.config;


//import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
//import io.swagger.v3.oas.models.servers.Server;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
import io.swagger.v3.oas.models.servers.Server;
import java.util.List;

//import static sun.net.www.protocol.http.AuthCacheValue.Type.Server;


@Configuration
public class SwaggerConfig {

//    @Bean
//    public OpenAPI customOpenAPI() {
//        // general info
//        Info info = new Info()
//                .title("G'iybat.uz API-lar")
//                .version("1.0.0")
//                .description("Quyida G'iybat.uz loyihasi uchun API hujjatlar tagdim qilingan.")
//                .contact(new Contact()
//                        .name("Bobur")
//                        .email("bigmangcom@gmail.com")
//                        .url("https://t.me/Jumaniyazov_Bobur")
//                )
//                .license(new License()
//                        .name("Videohub.uz")
//                        .url("https://videohub.uz/")
//                )
//                .termsOfService("Savol javob guruhi: https://t.me/code_uz_group");
//
//        // servers (ishlatiladigan serverlar)
//        Server server1 = new Server()
//                .description("Local")
//                .url("http://localhost:8080");
//
//        Server server2 = new Server()
//                .description("DEV")
//                .url("http://api.mazgi.uz");
//
//        Server server3 = new Server()
//                .description("PROD")
//                .url("http://api.giybat.uz");
//
//        // security type (bizning holatda JWT)
//        SecurityRequirement securityRequirement = new SecurityRequirement();
//        securityRequirement.addList("bearerAuth");
//
//        SecurityScheme securityScheme = new SecurityScheme();
//        securityScheme.setName("bearerAuth");
//        securityScheme.setType(SecurityScheme.Type.HTTP);
//        securityScheme.bearerFormat("JWT");
//        securityScheme.setIn(SecurityScheme.In.HEADER);
//        securityScheme.setScheme("bearer");
//
//        Components components = new Components();
//        components.addSecuritySchemes("bearerAuth", securityScheme);
//
//        // collect all together
//        OpenAPI openAPI = new OpenAPI();
//        openAPI.setInfo(info);
//        openAPI.setServers(List.of(server1, server2, server3));
//        openAPI.setSecurity(List.of(securityRequirement));
//        openAPI.components(components);
//
//        // return-xe
//        return openAPI;
//    }

    @Bean
    public OpenAPI customOpenAPI() {
        // general info
        Info info = new Info()
                .title("G'iybat.uz API-lar")
                .version("1.0.0")
                .description("Quyida G'iybat.uz loyihasi uchun API hujjatlar tagdim qilingan.")
                .contact(new Contact()
                        .name("Bobur")
                        .email("bigmangcom@gmail.com")
                        .url("https://t.me/Jumaniyazov_Bobur")
                )
                .license(new License()
                        .name("Videohub.uz")
                        .url("https://videohub.uz/")
                )
                .termsOfService("Savol javob guruhi: https://t.me/code_uz_group");

        // servers (ishlatiladigan serverlar)
        Server server1 = new Server()
                .description("Local")
                .url("http://localhost:8080");

        Server server2 = new Server()
                .description("DEV")
                .url("http://api.mazgi.uz");

        Server server3 = new Server()
                .description("PROD")
                .url("http://api.giybat.uz");

        // security type (bizning holatda JWT)
        SecurityRequirement securityRequirement = new SecurityRequirement();
        securityRequirement.addList("bearerAuth");

        SecurityScheme securityScheme = new SecurityScheme();
        securityScheme.setName("bearerAuth");
        securityScheme.setType(SecurityScheme.Type.HTTP);
        securityScheme.bearerFormat("JWT");
        securityScheme.setIn(SecurityScheme.In.HEADER);
        securityScheme.setScheme("bearer");

        Components components = new Components();
        components.addSecuritySchemes("bearerAuth", securityScheme);

        // collect all together
        OpenAPI openAPI = new OpenAPI();
        openAPI.setInfo(info);
        openAPI.setServers(List.of(server1, server2, server3));
//        openAPI.setSecurity(List.of(securityRequirement));
//        openAPI.components(components);

        // return-xe
        return openAPI;
    }


}
