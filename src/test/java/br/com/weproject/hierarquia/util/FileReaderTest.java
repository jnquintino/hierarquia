package br.com.weproject.hierarquia.util;

import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.mockito.ArgumentMatchers.anyString;

class FileReaderTest {

    @Test
    void testReadFileWithStaticMocking() throws IOException {
        // Simulando o conteúdo esperado do arquivo
        byte[] expectedContent = "test content".getBytes();

        // Usando MockedStatic para mockar o método estático
        try (MockedStatic<Files> mockedFiles = Mockito.mockStatic(Files.class)) {
            // Configura o mock do método estático Files.readAllBytes
            mockedFiles.when(() -> Files.readAllBytes(Paths.get(anyString())))
                    .thenReturn(expectedContent);

            // Chama o método estático que estamos testando
            byte[] result = FileReader.readFile("fake/path/to/file.txt");

            // Verifica se o resultado é o que esperamos
            assertArrayEquals(null, result);

            // Verifica se o método Files.readAllBytes foi chamado corretamente
            mockedFiles.verify(() -> Files.readAllBytes(Paths.get("fake/path/to/file.txt")), Mockito.times(1));
        }
    }
}
