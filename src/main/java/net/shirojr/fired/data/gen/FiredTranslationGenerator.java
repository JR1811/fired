package net.shirojr.fired.data.gen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricLanguageProvider;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;
import net.shirojr.fired.Fired;
import net.shirojr.fired.init.FiredItems;
import net.shirojr.fired.init.FiredTags;
import org.jetbrains.annotations.Nullable;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class FiredTranslationGenerator extends FabricLanguageProvider {
    public FiredTranslationGenerator(FabricDataOutput dataOutput) {
        super(dataOutput);
    }

    @Override
    public void generateTranslations(TranslationBuilder builder) {
        FiredItems.ALL.forEach(item -> builder.add(item, cleanString(Registries.ITEM.getId(item), false)));

        try {
            Path existingFilePath = dataOutput.getModContainer().findPath("assets/%s/lang/en_us.existing.json".formatted(Fired.MOD_ID)).orElseThrow();
            builder.add(existingFilePath);
        } catch (Exception e) {
            throw new RuntimeException("Failed to add existing language file!", e);
        }
    }

    public static String cleanString(@Nullable Identifier identifier, boolean reverse) {
        if (identifier == null) throw new NullPointerException("Not a valid Identifier for clean String Translation");
        List<String> input = List.of(identifier.getPath().split("/"));
        List<String> words = Arrays.asList(input.get(input.size() - 1).split("_"));
        return cleanMergedString(words, reverse);
    }

    public static String cleanMergedString(List<String> input, boolean reverse) {
        List<String> words = new ArrayList<>(input);
        if (reverse) Collections.reverse(words);
        StringBuilder output = new StringBuilder();
        for (int i = 0; i < words.size(); i++) {
            String word = words.get(i);
            char capitalized = Character.toUpperCase(word.charAt(0));
            output.append(capitalized).append(word.substring(1));
            if (i < words.size() - 1) {
                output.append(" ");
            }
        }
        return output.toString();
    }
}
