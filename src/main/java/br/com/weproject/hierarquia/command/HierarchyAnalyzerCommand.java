package br.com.weproject.hierarquia.command;

import br.com.weproject.hierarquia.service.HierarchyAnalyzerService;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class HierarchyAnalyzerCommand implements CommandLineRunner {

    @Autowired
    private HierarchyAnalyzerService analyzerService;

    @Override
    public void run(String... args) throws Exception {
        if (args.length == 0) {
            // Se nenhum argumento for passado, a aplicação web será iniciada normalmente
            return;
        }

        if (args[0].equals("analyze")) {
            // Parse dos argumentos
            boolean verbose = false;
            int depth = -1;
            String phrase = null;

            for (int i = 1; i < args.length; i++) {
                switch (args[i]) {
                    case "--depth":
                        depth = Integer.parseInt(args[++i]);
                        break;
                    case "--verbose":
                        verbose = true;
                        break;
                    default:
                        phrase = args[i];  // A frase para analisar será a última
                }
            }

            if (phrase == null) {
                log.info("Frase para análise não foi fornecida.");
                return;
            }

            // Medir o tempo de carregamento
            long startLoadTime = System.currentTimeMillis();
            analyzerService.loadDictionary();
            long endLoadTime = System.currentTimeMillis();
            long loadTime = endLoadTime - startLoadTime;

            // Medir o tempo de análise da frase
            long startAnalysisTime = System.currentTimeMillis();
            JSONObject result = analyzerService.analyzeSentence(phrase);
            long endAnalysisTime = System.currentTimeMillis();
            long analysisTime = endAnalysisTime - startAnalysisTime;

            // Mostrar resultados
            log.info(result.toString(4));
            if (verbose) {
                log.info("Tempo de carregamento dos parâmetros: " + loadTime + "ms");
                log.info("Tempo de verificação da frase: " + analysisTime + "ms");
            }
        }
    }
}
