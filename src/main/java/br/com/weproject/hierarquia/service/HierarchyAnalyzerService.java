package br.com.weproject.hierarquia.service;

import br.com.weproject.hierarquia.util.FileReader;
import lombok.Getter;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.*;

import static br.com.weproject.hierarquia.util.WordNormalizer.normalizeSentence;

@Service
@Getter
public class HierarchyAnalyzerService {

    private Map<String, List<String>> wordHierarchy = new HashMap<>();

    public void loadDictionary() throws IOException {
        // Leitura do arquivo JSON como bytes e conversão para String
        String content = new String(FileReader.readFile("src/main/resources/dicts/dict.json"), StandardCharsets.UTF_8);
        // Criação do JSONObject a partir do conteúdo
        JSONObject json = new JSONObject(content);
        // Parsing do JSON para a estrutura de hierarquia
        parseHierarchy(json, new ArrayList<>());
    }

    private void parseHierarchy(JSONObject json, List<String> path) {
        Iterator<String> keys = json.keys();
        while (keys.hasNext()) {
            String key = keys.next();
            Object value = json.get(key);
            List<String> newPath = new ArrayList<>(path);
            newPath.add(key);

            if (value instanceof JSONArray) {
                JSONArray array = (JSONArray) value;
                for (int i = 0; i < array.length(); i++) {
                    String animal = array.getString(i).toLowerCase();
                    wordHierarchy.put(animal, newPath);
                }
            } else if (value instanceof JSONObject) {
                parseHierarchy((JSONObject) value, newPath);
            }
        }
    }

    public JSONObject analyzeSentence(String sentence) {
        sentence = normalizeSentence(sentence);
        String[] words = sentence.toLowerCase().split("\\s+");

        JSONObject hierarchyJson = new JSONObject();

        for (String word : words) {
            if (wordHierarchy.containsKey(word)) {
                List<String> hierarchy = wordHierarchy.get(word);
                addToHierarchyJson(hierarchyJson, hierarchy, word);
            }
        }

        return hierarchyJson;
    }

    private void addToHierarchyJson(JSONObject json, List<String> hierarchy, String word) {
        JSONObject currentLevel = json;
        for (int i = 0; i < hierarchy.size(); i++) {
            String level = hierarchy.get(i);
            if (i == hierarchy.size() - 1) {
                if (!currentLevel.has(level)) {
                    currentLevel.put(level, new JSONArray());
                }
                currentLevel.getJSONArray(level).put(word);
            } else {
                if (!currentLevel.has(level)) {
                    currentLevel.put(level, new JSONObject());
                }
                currentLevel = currentLevel.getJSONObject(level);
            }
        }
    }
}
