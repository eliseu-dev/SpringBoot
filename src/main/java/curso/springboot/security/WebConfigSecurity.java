package curso.springboot.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class WebConfigSecurity extends WebSecurityConfigurerAdapter {
    @Override //Configura as solicitações de acesso por http
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf()
                .disable() // desativa as configurações padrão de memoria do spring
                .authorizeRequests() // permite restringir acessos
                .antMatchers(HttpMethod.GET, "/").permitAll() //qualquer usuario acessa a página principal
                .anyRequest().authenticated()
                .and().formLogin().permitAll()// permite qualquer usuario
                .and().logout() //mapeia URL de logout e invalida usuario autenticado
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"));
    }

    //cria autenticação do usuario com banco de dados ou em memoria
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication().passwordEncoder(NoOpPasswordEncoder.getInstance())
                .withUser("")
                .password("")
                .roles("");

    }

    //ignora URL especificas
    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/materialize/**");
    }
}