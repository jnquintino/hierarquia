package br.com.weproject.hierarquia.command;

import br.com.weproject.hierarquia.service.HierarchyAnalyzerService;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.mockito.Mockito.*;

class HierarchyAnalyzerCommandTest {

    @Mock
    private HierarchyAnalyzerService analyzerService;

    @InjectMocks
    private HierarchyAnalyzerCommand command;

    private static final Logger log = LoggerFactory.getLogger(HierarchyAnalyzerCommand.class);

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testRunWithAnalyzeCommandAndVerbose() throws Exception {
        // Configura o mock para simular o serviço
        String phrase = "Eu vi leão e papagaio";
        JSONObject mockResult = new JSONObject();
        mockResult.put("Animais", new JSONObject()
                .put("Mamíferos", new JSONObject()
                        .put("Felinos", new JSONObject()
                                .put("leão", "leão"))
                )
                .put("Aves", new JSONObject()
                        .put("Psitacídeos", new JSONObject()
                                .put("papagaio", "papagaio"))
                )
        );

        when(analyzerService.analyzeSentence(phrase)).thenReturn(mockResult);

        // Simula a chamada com argumentos "analyze", "--verbose", e a frase
        String[] args = {"analyze", "--verbose", phrase};

        // Executa o comando
        command.run(args);

        // Verifica se o serviço foi chamado corretamente
        verify(analyzerService, times(1)).loadDictionary();
        verify(analyzerService, times(1)).analyzeSentence(phrase);

        // Verifica o output do log (pode ser mais elaborado usando bibliotecas como LogCaptor)
        log.info(mockResult.toString(4));
    }

    @Test
    void testRunWithAnalyzeCommandNoVerbose() throws Exception {
        // Configura o mock para simular o serviço
        String phrase = "Eu vi leão e papagaio";
        JSONObject mockResult = new JSONObject();
        mockResult.put("Animais", new JSONObject()
                .put("Mamíferos", new JSONObject()
                        .put("Felinos", new JSONObject()
                                .put("leão", "leão"))
                )
                .put("Aves", new JSONObject()
                        .put("Psitacídeos", new JSONObject()
                                .put("papagaio", "papagaio"))
                )
        );

        when(analyzerService.analyzeSentence(phrase)).thenReturn(mockResult);

        // Simula a chamada com argumentos "analyze" e a frase (sem o --verbose)
        String[] args = {"analyze", phrase};

        // Executa o comando
        command.run(args);

        // Verifica se o serviço foi chamado corretamente
        verify(analyzerService, times(1)).loadDictionary();
        verify(analyzerService, times(1)).analyzeSentence(phrase);

        // Verifica o output do log (sem o verbose)
        log.info(mockResult.toString(4));
    }

    @Test
    void testRunWithoutArguments() throws Exception {
        // Simula a chamada sem argumentos
        String[] args = {};

        // Executa o comando
        command.run(args);

        // Verifica se nenhum método do serviço foi chamado (porque não havia argumentos)
        verifyNoInteractions(analyzerService);
    }

    @Test
    void testRunWithAnalyzeCommandAndDepth() throws Exception {
        // Configura o mock para simular o serviço
        String phrase = "Eu vi leão e papagaio";
        JSONObject mockResult = new JSONObject();
        mockResult.put("Animais", new JSONObject()
                .put("Mamíferos", new JSONObject()
                        .put("Felinos", new JSONObject()
                                .put("leão", "leão"))
                )
                .put("Aves", new JSONObject()
                        .put("Psitacídeos", new JSONObject()
                                .put("papagaio", "papagaio"))
                )
        );

        when(analyzerService.analyzeSentence(phrase)).thenReturn(mockResult);

        // Simula a chamada com argumentos "analyze", "--depth", e a frase
        String[] args = {"analyze", "--depth", "3", phrase};

        // Executa o comando
        command.run(args);

        // Verifica se o serviço foi chamado corretamente
        verify(analyzerService, times(1)).loadDictionary();
        verify(analyzerService, times(1)).analyzeSentence(phrase);
    }
}
