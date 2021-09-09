package curso.springboot.SpringBoot.controllertest;

import curso.springboot.controller.PessoaController;

import static io.restassured.module.mockmvc.RestAssuredMockMvc.*;

import curso.springboot.controller.ReportUtil;
import curso.springboot.repository.DadosRepository;
import curso.springboot.repository.PessoaRepository;
import curso.springboot.repository.ProfissaoRepository;
import curso.springboot.repository.UsuarioRepository;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.http.HttpStatus;

@WebMvcTest
@EnableCaching(proxyTargetClass = true)
public class PessoaControllerTest {
    @Autowired
    PessoaController pessoaController;

    @MockBean
    PessoaRepository pessoaRepository;

    @MockBean
    DadosRepository dadosRepository;

    @MockBean
    ReportUtil reportUtil;

    @MockBean
    ProfissaoRepository profissaoRepository;

    @MockBean
    UsuarioRepository usuarioRepository;

    @BeforeEach
    public void setup() {
        standaloneSetup(this.pessoaController);
    }

    @Test
    public void deveRetornarSucesso_QuandoListarPessoas() {
        given()
                .accept(ContentType.HTML)
                .when()
                .get("/listapessoas")
                .then()
                .statusCode(HttpStatus.OK.value());

    }
}
