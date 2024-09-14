package br.com.weproject.hierarquia.controller;

import br.com.weproject.hierarquia.service.HierarchyAnalyzerService;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HierarchyAnalyzerController {

    @Autowired
    private HierarchyAnalyzerService analyzerService;

    @GetMapping("/analyze")
    public String analyzePhrase(@RequestParam String phrase, @RequestParam(required = false) boolean verbose) {
        try {
            // Medindo o tempo de carregamento dos parâmetros
            long startLoadTime = System.currentTimeMillis();
            analyzerService.loadDictionary();
            long endLoadTime = System.currentTimeMillis();
            long loadTime = endLoadTime - startLoadTime;

            // Medindo o tempo de verificação da frase
            long startAnalysisTime = System.currentTimeMillis();
            JSONObject result = analyzerService.analyzeSentence(phrase);
            long endAnalysisTime = System.currentTimeMillis();
            long analysisTime = endAnalysisTime - startAnalysisTime;

            // Criando o resultado com ou sem verbose
            if (verbose) {
                return result.toString(4) + "\nTempo de carregamento dos parâmetros: " + loadTime + "ms\n" +
                        "Tempo de verificação da frase: " + analysisTime + "ms";
            } else {
                return result.toString(4);
            }
        } catch (Exception e) {
            return "Erro ao processar: " + e.getMessage();
        }
    }
}
