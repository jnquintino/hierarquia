package br.com.weproject.hierarquia.util;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

class WordNormalizerTest {

    @Test
    void testNormalizeWordWithSimplePlural() {
        assertThat(WordNormalizer.normalizeWord("cavalos")).isEqualTo("cavalo");
        assertThat(WordNormalizer.normalizeWord("livros")).isEqualTo("livro");
    }

    @Test
    void testNormalizeWordWithPluralEndingInEs() {
        assertThat(WordNormalizer.normalizeWord("aves")).isEqualTo("ave");
        assertThat(WordNormalizer.normalizeWord("leões")).isEqualTo("leão");
    }

    @Test
    void testNormalizeWordWithExceptions() {
        assertThat(WordNormalizer.normalizeWord("passos")).isEqualTo("passo");
        assertThat(WordNormalizer.normalizeWord("ossos")).isEqualTo("osso");
    }

    @Test
    void testNormalizeWordWithNoPlural() {
        assertThat(WordNormalizer.normalizeWord("cachorro")).isEqualTo("cachorro");
        assertThat(WordNormalizer.normalizeWord("gato")).isEqualTo("gato");
    }

    @Test
    void testNormalizeWordWithEmptyString() {
        assertThat(WordNormalizer.normalizeWord("")).isEqualTo("");
    }
}
