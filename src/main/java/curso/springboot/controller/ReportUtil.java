package curso.springboot.controller;

import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.springframework.stereotype.Component;

import javax.servlet.ServletContext;
import java.io.File;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;

@Component
public class ReportUtil implements Serializable {
    //Retorna pdf em byte para download no navegador
    public byte[] gerarRelatorio(List listDados, String relatorio, ServletContext servletContext) throws Exception {
        //cria lista de dados para relatorio com nosa lista de objetos para imprimir
        JRBeanCollectionDataSource jrBeanCollectionDataSource = new JRBeanCollectionDataSource((listDados));

        //Carregar o caminho do jasper compilado
        String caminhoJasper = servletContext.getRealPath("relatorios") + File.separator + relatorio + ".jasper";

        //Carrega o arquivo jasper passando os dados
        JasperPrint impressoraJasper = JasperFillManager
                .fillReport(caminhoJasper, new HashMap(), jrBeanCollectionDataSource);

        return JasperExportManager.exportReportToPdf(impressoraJasper);
    }
}
