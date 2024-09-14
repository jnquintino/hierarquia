package br.com.weproject.hierarquia.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import br.com.weproject.hierarquia.util.FileReader;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;

class HierarchyAnalyzerServiceTest {

    @InjectMocks
    private HierarchyAnalyzerService hierarchyAnalyzerService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testLoadDictionary() throws IOException, JSONException {
        // Configura o mock para retornar um JSON de teste
        String jsonString = "{ \"Animais\": { \"Mamiferos\": { \"Felinos\": [\"leoes\", \"oncas\"] } } }";

        try (MockedStatic<Files> mockedFiles = Mockito.mockStatic(Files.class)) {
            // Configura o mock do método estático Files.readAllBytes
            mockedFiles.when(() -> Files.readAllBytes(Paths.get("src/test/resources/dicts/dict.json")))
                    .thenReturn(jsonString.getBytes());

            // Chama o método estático que estamos testando
            byte[] result = FileReader.readFile("src/test/resources/dicts/dict.json");

            // Verifica se o resultado é o que esperamos
            assertArrayEquals(jsonString.getBytes(), result);

            // Verifica se o método Files.readAllBytes foi chamado corretamente
            mockedFiles.verify(() -> Files.readAllBytes(Paths.get("src/test/resources/dicts/dict.json")), Mockito.times(1));
        }

        // Chama o método que deve ser testado
        hierarchyAnalyzerService.loadDictionary();

        // Verifica se o dicionário foi carregado corretamente
        assertFalse(hierarchyAnalyzerService.getWordHierarchy().containsKey("leões"));
        assertFalse(hierarchyAnalyzerService.getWordHierarchy().containsKey("onças"));
        assertEquals(null, hierarchyAnalyzerService.getWordHierarchy().get("leoes"));
    }

    @Test
    void testAnalyzeSentence() throws JSONException {
        // Configure o dicionário com dados de teste
        hierarchyAnalyzerService.getWordHierarchy().put("leao", Arrays.asList("Animais", "Mamiferos", "Felinos"));
        hierarchyAnalyzerService.getWordHierarchy().put("papagaio", Arrays.asList("Animais", "Aves", "Psitacideos"));

        // Chama o método que deve ser testado
        String sentence = "Eu vi leao e papagaio";
        JSONObject result = hierarchyAnalyzerService.analyzeSentence(sentence);

        // Verifica o JSON resultante
        JSONObject expected = new JSONObject();
        JSONObject animais = new JSONObject();
        JSONObject mamiferos = new JSONObject();
        mamiferos.put("Felinos", new JSONArray().put("leao"));
        animais.put("Mamiferos", mamiferos);
        JSONObject aves = new JSONObject();
        aves.put("Psitacideos", new JSONArray().put("papagaio"));
        animais.put("Aves", aves);
        expected.put("Animais", animais);

        assertEquals(expected.toString(), result.toString());
    }
}
