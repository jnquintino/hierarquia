package br.com.weproject.hierarquia.controller;

import br.com.weproject.hierarquia.service.HierarchyAnalyzerService;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

class HierarchyAnalyzerControllerTest {

    @Mock
    private HierarchyAnalyzerService analyzerService;

    @InjectMocks
    private HierarchyAnalyzerController controller;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testAnalyzePhraseWithoutVerbose() throws Exception {
        // Configura o mock para retornar um JSON de teste
        String phrase = "Eu vi leão e papagaio";
        JSONObject mockResult = new JSONObject();
        mockResult.put("Animais", new JSONObject()
                .put("Mamíferos", new JSONObject()
                        .put("Felinos", new JSONObject()
                                .put("leão", new JSONArray())
                        )
                )
                .put("Aves", new JSONObject()
                        .put("Psitacídeos", new JSONArray()
                                .put("papagaio")
                        )
                )
        );

        // Configura o mock para o serviço
        when(analyzerService.analyzeSentence(phrase)).thenReturn(mockResult);

        // Chama o método diretamente
        String result = controller.analyzePhrase(phrase, false);

        // Verifica o resultado
        assertEquals(mockResult.toString(4), result);
    }

    @Test
    void testAnalyzePhraseWithVerbose() throws Exception {
        // Configura o mock para retornar um JSON de teste
        String phrase = "Eu vi leão e papagaio";
        JSONObject mockResult = new JSONObject();
        mockResult.put("Animais", new JSONObject()
                .put("Mamíferos", new JSONObject()
                        .put("Felinos", new JSONObject()
                                .put("leão", new JSONArray())
                        )
                )
                .put("Aves", new JSONObject()
                        .put("Psitacídeos", new JSONArray()
                                .put("papagaio")
                        )
                )
        );

        // Configura o mock para o serviço
        when(analyzerService.analyzeSentence(phrase)).thenReturn(mockResult);

        // Chama o método diretamente
        String result = controller.analyzePhrase(phrase, true);

        // Verifica o resultado com verbose (incluindo tempos de processamento simulados)
        String expected = mockResult.toString(4) + "\nTempo de carregamento dos parâmetros: \\d+ms\n" +
                "Tempo de verificação da frase: \\d+ms";

        assertEquals(expected, result.replaceAll("\\d+ms", "\\\\d+ms"));
    }

    @Test
    void testAnalyzePhraseWithException() throws Exception {
        // Configura o mock para lançar uma exceção
        String phrase = "Eu vi leão e papagaio";
        when(analyzerService.analyzeSentence(phrase)).thenThrow(new RuntimeException("Erro ao processar"));

        // Chama o método diretamente
        String result = controller.analyzePhrase(phrase, false);

        // Verifica o resultado
        assertEquals("Erro ao processar: Erro ao processar", result);
    }
}
