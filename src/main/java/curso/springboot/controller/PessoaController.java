package curso.springboot.controller;

import curso.springboot.model.Dados;
import curso.springboot.model.Pessoa;
import curso.springboot.repository.DadosRepository;
import curso.springboot.repository.PessoaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
public class PessoaController {
    @Autowired
    private PessoaRepository pessoaRepository;

    @Autowired
    private DadosRepository dadosRepository;

    @RequestMapping(method = RequestMethod.GET, value = "cadastropessoa")
    public ModelAndView inicio() {
        ModelAndView andView = new ModelAndView("cadastro/cadastropessoa");
        andView.addObject("pessoaobj", new Pessoa());
        return andView;
    }

    @RequestMapping(method = RequestMethod.POST, value = "**/salvarpessoa")
    public ModelAndView salvar(@Valid Pessoa pessoa, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            ModelAndView andView = new ModelAndView("cadastro/cadastropessoa");
            List<Pessoa> pessoaIterable = pessoaRepository.findAll();
            andView.addObject("pessoas", pessoaIterable);
            andView.addObject("pessoaobj", pessoa);

            List<String> msg = new ArrayList<String>();
            for (ObjectError objectError :
                    bindingResult.getAllErrors()) {
                msg.add(objectError.getDefaultMessage());
            }
            andView.addObject("msg", msg);
            return andView;
        } else {
            pessoaRepository.save(pessoa);
            ModelAndView andView = new ModelAndView("cadastro/cadastropessoa");
            List<Pessoa> pessoaIterable = pessoaRepository.findAll();
            andView.addObject("pessoas", pessoaIterable);
            andView.addObject("pessoaobj", new Pessoa());
            return andView;
        }


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
        andView.addObject("dados", dadosRepository.listaDados(idpessoa));

        return andView;
    }

    @PostMapping("**/addfonePessoa/{pessoaid}")
    public ModelAndView addfonePessoa(Dados dados, @PathVariable("pessoaid") Long pessoaid) {
        Pessoa pessoa = pessoaRepository.findById(pessoaid).get();

        if (dados != null && dados.getTelefone().isEmpty() || dados.getEmail().isEmpty()) {
            ModelAndView andView = new ModelAndView("cadastro/dadospessoa");
            andView.addObject("pessoaobj", pessoa);
            andView.addObject("dados", dadosRepository.listaDados(pessoaid));
            List<String> msg = new ArrayList<String>();
            if (dados.getTelefone().isEmpty()) {
                msg.add("Numero deve ser informado");
            }
            if (dados.getEmail().isEmpty()) {
                msg.add("Email deve ser informado");
            }
            andView.addObject("msg", msg);
            return andView;
        }

        dados.setPessoa(pessoa);
        dadosRepository.save(dados);
        ModelAndView andView = new ModelAndView("cadastro/dadospessoa");
        andView.addObject("pessoaobj", pessoa);
        andView.addObject("dados", dadosRepository.listaDados(pessoaid));
        return andView;
    }

    @GetMapping("/excluirtelefone/{idfone}")
    public ModelAndView excluirTelefone(@PathVariable("idfone") Long idfone) {
        Pessoa pessoa = dadosRepository.findById(idfone).get().getPessoa();

        dadosRepository.deleteById(idfone);

        ModelAndView andView = new ModelAndView("cadastro/dadospessoa");

        andView.addObject("pessoaobj", pessoa);
        andView.addObject("dados", dadosRepository.listaDados(pessoa.getId()));

        return andView;
    }

}
