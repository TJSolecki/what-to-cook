package com.what.to.cook.json;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import lombok.extern.slf4j.Slf4j;

@JsonIgnoreProperties(ignoreUnknown = true)
public record RecipeJson(
    String name,
    String cookTime,
    String prepTime,
    String totalTime,
    @JsonDeserialize(using = MainEntityDeserializer.class) String mainEntityOfPage,
    @JsonDeserialize(using = AuthorDeserializer.class) String author,
    String keywords,
    NutritionJson nutrition,
    @JsonDeserialize(using = ImageDeserializer.class) List<String> image,
    @JsonDeserialize(using = RecipeYieldDeserializer.class) List<String> recipeYield,
    List<String> recipeCuisines,
    List<String> recipeCategory,
    List<InstructionJson> recipeInstructions
) {}

class ImageDeserializer extends JsonDeserializer<List<String>> {

    @Override
    public List<String> deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException {
        JsonNode node = jp.getCodec().readTree(jp);
        List<String> images = new ArrayList<>();

        if (node.isArray()) {
            // Handle array of strings
            node.forEach(n -> images.add(n.asText()));
        } else if (node.isObject()) {
            // Handle ImageObject, extract URL and add to list
            JsonNode urlNode = node.get("url");
            if (urlNode != null) {
                images.add(urlNode.asText());
            }
        } else if (node.isTextual()) {
            // Handle single string
            images.add(node.asText());
        } else {
            throw new JsonParseException(jp, "Cannot deserialize image");
        }

        return images;
    }
}

class MainEntityDeserializer extends JsonDeserializer<String> {

    @Override
    public String deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException {
        JsonNode node = jp.getCodec().readTree(jp);

        if (node.isTextual()) {
            return node.asText();
        } else if (node.isObject()) {
            // Handle ImageObject, extract URL and add to list
            JsonNode idNode = node.get("@id");
            if (idNode != null) {
                return idNode.asText();
            }
        }
        throw new JsonParseException(jp, "Cannot deserialize image");
    }
}

class RecipeYieldDeserializer extends JsonDeserializer<List<String>> {

    @Override
    public List<String> deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException {
        JsonNode node = jp.getCodec().readTree(jp);
        List<String> yields = new ArrayList<>();

        if (node.isArray()) {
            // Handle array of strings
            node.forEach(n -> yields.add(n.asText()));
        } else if (node.isTextual()) {
            // Handle single string
            yields.add(node.asText());
        } else {
            throw new JsonParseException(jp, "Cannot deserialize recipe yield");
        }

        return yields;
    }
}

@Slf4j
class AuthorDeserializer extends JsonDeserializer<String> {

    @Override
    public String deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException {
        JsonNode node = jp.getCodec().readTree(jp);

        JsonNode name = node.isArray() ? node.get(0).get("name") : node.isObject() ? node.get("name") : null;
        if (name != null) {
            return name.asText();
        }
        log.error("Author is in unknown format or does not exist.");
        return null;
    }
}
