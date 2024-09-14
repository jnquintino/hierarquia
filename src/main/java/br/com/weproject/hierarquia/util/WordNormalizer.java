package br.com.weproject.hierarquia.util;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class WordNormalizer {

    public static String normalizeWord(String word) {
        // Regra simples para remover plurais
        if (word.endsWith("s")) {
            // Exceções que não devem ser convertidas
            if (word.endsWith("ss")) {
                return word;  // Casos como "passos"
            }
            // Casos de plural simples
            if (word.endsWith("es")) {
                // Casos como "aves", "leões"
                if (word.endsWith("ões")) {
                    return word.substring(0, word.length() - 3) + "ão";
                }
                return word.substring(0, word.length() - 1);
            }
            return word.substring(0, word.length() - 1); // Remove o "s" final
        }
        return word;
    }

    public static String normalizeSentence(String sentence) {
        sentence = sentence.toLowerCase().replaceAll("[.,;!?]", "");
        String[] words = sentence.split("\\s+");
        StringBuilder normalizedSentence = new StringBuilder();

        for (String word : words) {
            normalizedSentence.append(normalizeWord(word)).append(" ");
        }

        return normalizedSentence.toString().trim();
    }
}
