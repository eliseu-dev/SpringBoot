package curso.springboot.controller;

import curso.springboot.model.Dados;
import curso.springboot.model.Pessoa;
import curso.springboot.repository.DadosRepository;
import curso.springboot.repository.PessoaRepository;
import curso.springboot.repository.ProfissaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
public class PessoaController {
    @Autowired
    private PessoaRepository pessoaRepository;

    @Autowired
    private DadosRepository dadosRepository;

    @Autowired
    private ReportUtil reportUtil;

    @Autowired
    private ProfissaoRepository profissaoRepository;

    @RequestMapping(method = RequestMethod.GET, value = "cadastropessoa")
    public ModelAndView inicio() {
        ModelAndView andView = new ModelAndView("cadastro/cadastropessoa");
        andView.addObject("pessoaobj", new Pessoa());
        List<Pessoa> pessoaIterable = pessoaRepository.findAll();
        andView.addObject("pessoas", pessoaIterable);
        andView.addObject("profissoes", profissaoRepository.findAll());
        return andView;
    }

    @RequestMapping(method = RequestMethod.POST, value = "**/salvarpessoa"
            , consumes = {"multipart/form-data"})
    public ModelAndView salvar(@Valid Pessoa pessoa, BindingResult bindingResult, final MultipartFile file) throws IOException {


        pessoa.setTelefones(dadosRepository.listaDados(pessoa.getId()));
        if (bindingResult.hasErrors()) {
            ModelAndView andView = new ModelAndView("cadastro/cadastropessoa");
            List<Pessoa> pessoaIterable = pessoaRepository.findAll();
            andView.addObject("pessoas", pessoaIterable);
            andView.addObject("profissoes", profissaoRepository.findAll());
            andView.addObject("pessoaobj", pessoa);

            List<String> msg = new ArrayList<String>();
            for (ObjectError objectError :
                    bindingResult.getAllErrors()) {
                msg.add(objectError.getDefaultMessage());
            }
            andView.addObject("msg", msg);
            return andView;
        } else {
            if (file.getSize() > 0) {
                pessoa.setCurriculo(file.getBytes());
                pessoa.setTipoFileCurriculo(file.getContentType());
                pessoa.setNomeFileCurriculo(file.getOriginalFilename());
            } else {
                Long idTemp = pessoa.getId();
                if (idTemp != null && pessoa.getId() > 0) {
                    byte[] pessoaTemp = pessoaRepository.findById(pessoa.getId()).get().getCurriculo();
                    pessoa.setCurriculo(file.getBytes());
                    pessoa.setTipoFileCurriculo(file.getContentType());
                    pessoa.setNomeFileCurriculo(file.getOriginalFilename());
                }
            }

            pessoaRepository.save(pessoa);
            ModelAndView andView = new ModelAndView("cadastro/cadastropessoa");
            List<Pessoa> pessoaIterable = pessoaRepository.findAll();
            andView.addObject("pessoas", pessoaIterable);
            andView.addObject("profissoes", profissaoRepository.findAll());
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
        andView.addObject("profissoes", profissaoRepository.findAll());
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
    public ModelAndView buscar(@RequestParam("nomepesquisa") String nomepesquisa,
                               @RequestParam("pesquisasexo") String pesquisasexo) {


        List<Pessoa> pessoas = new ArrayList<Pessoa>();
        if (pesquisasexo != null && !pesquisasexo.isEmpty()) {
            pessoas = pessoaRepository.findPessoaByNameAndGender(nomepesquisa, pesquisasexo);
        } else {
            pessoas = pessoaRepository.findPessoaByName(nomepesquisa);
        }

        ModelAndView andView = new ModelAndView("cadastro/listapessoas");

        andView.addObject("pessoas", pessoas);
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

    @GetMapping("**/pesquisarpessoa")
    public void baixarPDF(@RequestParam("nomepesquisa") String nomepesquisa,
                          @RequestParam("pesquisasexo") String pesquisasexo
            , HttpServletRequest request, HttpServletResponse response) throws Exception {


        List<Pessoa> pessoas = new ArrayList<Pessoa>();
        if (pesquisasexo != null && !pesquisasexo.isEmpty() && nomepesquisa != null && !nomepesquisa.isEmpty()) {
            pessoas = pessoaRepository.findPessoaByNameAndGender(nomepesquisa, pesquisasexo);
        } else if (nomepesquisa != null && !nomepesquisa.isEmpty()) {
            pessoas = pessoaRepository.findPessoaByName(nomepesquisa);
        } else if (pesquisasexo != null && !pesquisasexo.isEmpty()) {
            pessoas = pessoaRepository.findPessoaByGender(pesquisasexo);
        } else {
            Iterable<Pessoa> iterable = pessoaRepository.findAll();
            for (Pessoa pessoa :
                    iterable) {
                pessoas.add(pessoa);
            }
        }

        byte[] pdf = reportUtil.gerarRelatorio(pessoas, "Pessoa", request.getServletContext());

        //Tamanho da resposta pro navegador
        response.setContentLength(pdf.length);

        //definir o tipo de arquivo
        response.setContentType("application/octet-stream");

        //cabecalho da resposta
        String headerKey = "Content-Disposition";
        String headerValue = String.format("attachment; filename=\"%s\"", "relatorio.pdf");
        response.setHeader(headerKey, headerValue);

        //finaliza resposta pro navegador
        response.getOutputStream().write(pdf);
    }

    @GetMapping("**/baixarcurriculo/{idpessoa}")
    public void baixarCurriculo(@PathVariable("idpessoa") Long idpessoa, HttpServletResponse response) throws IOException {
        Pessoa pessoa = pessoaRepository.findById(idpessoa).get();
        if (pessoa.getCurriculo() != null) {
            response.setContentLength(pessoa.getCurriculo().length);
            response.setContentType(pessoa.getTipoFileCurriculo());

            String headerKey = "Content-Disposition";
            String headerValue = String.format("attachment; filename=\"%s\"", pessoa.getNomeFileCurriculo());
            response.setHeader(headerKey, headerValue);
            response.getOutputStream().write(pessoa.getCurriculo());
        }
    }


}
