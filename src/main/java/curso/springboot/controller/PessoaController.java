package curso.springboot.controller;

import curso.springboot.model.Pessoa;
import curso.springboot.repository.PessoaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Optional;

@Controller
public class PessoaController {
    @Autowired
    private PessoaRepository pessoaRepository;


    @RequestMapping(method = RequestMethod.GET, value = "cadastropessoa")
    public ModelAndView inicio() {
        ModelAndView andView = new ModelAndView("cadastro/cadastropessoa");
        andView.addObject("pessoaobj", new Pessoa());
        return andView;
    }

    @RequestMapping(method = RequestMethod.POST, value = "**/salvarpessoa")
    public ModelAndView salvar(Pessoa pessoa) {
        pessoaRepository.save(pessoa);
        ModelAndView andView = new ModelAndView("cadastro/cadastropessoa");
        List<Pessoa> pessoaIterable = pessoaRepository.findAll();
        andView.addObject("pessoas", pessoaIterable);
        andView.addObject("pessoaobj", new Pessoa());
        return andView;
    }


    @RequestMapping(method = RequestMethod.GET, value = "/listapessoas")
    public ModelAndView pessoas() {
        ModelAndView andView = new ModelAndView("cadastro/listapessoas");
        List<Pessoa> pessoaIterable = pessoaRepository.findAll();
        andView.addObject("pessoas", pessoaIterable);
        andView.addObject("pessoaobj", new Pessoa());

        return andView;
    }

    @GetMapping("/editarpessoa/{idpessoa}")
    public ModelAndView editar(@PathVariable("idpessoa") Long idpessoa) {
        ModelAndView andView = new ModelAndView("cadastro/cadastropessoa");
        Optional<Pessoa> pessoa = pessoaRepository.findById(idpessoa);
        andView.addObject("pessoaobj", pessoa.get());

        return andView;
    }

    @GetMapping("/excluirpessoa/{idpessoa}")
    public ModelAndView excluir(@PathVariable("idpessoa") Long idpessoa) {
        pessoaRepository.deleteById(idpessoa);

        ModelAndView andView = new ModelAndView("cadastro/cadastropessoa");
        andView.addObject("pessoas", pessoaRepository.findAll());
        andView.addObject("pessoaobj", new Pessoa());
        return andView;
    }

    @PostMapping("**/pesquisarpessoa")
    public ModelAndView buscar(@RequestParam("nomepesquisa") String nomepesquisa) {
        ModelAndView andView = new ModelAndView("cadastro/listapessoas");

        andView.addObject("pessoas", pessoaRepository.findPessoaByName(nomepesquisa));
        andView.addObject("pessoaobj", new Pessoa());

        return andView;
    }

    @GetMapping("**/dadospessoa/{idpessoa}")
    public ModelAndView listaDados(@PathVariable("idpessoa") Long idpessoa) {
        ModelAndView andView = new ModelAndView("cadastro/dadospessoa");
        Optional<Pessoa> pessoa = pessoaRepository.findById(idpessoa);

        andView.addObject("pessoaobj", pessoa.get());

        return andView;
    }

}
