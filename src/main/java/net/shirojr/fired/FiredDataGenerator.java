package net.shirojr.fired;

import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.shirojr.fired.data.gen.FiredModelGenerator;
import net.shirojr.fired.data.gen.FiredRecipeGenerator;
import net.shirojr.fired.data.gen.FiredTagsGenerator;
import net.shirojr.fired.data.gen.FiredTranslationGenerator;

public class FiredDataGenerator implements DataGeneratorEntrypoint {
	@Override
	public void onInitializeDataGenerator(FabricDataGenerator generator) {
		FabricDataGenerator.Pack pack = generator.createPack();

		pack.addProvider(FiredModelGenerator::new);
		pack.addProvider(FiredTranslationGenerator::new);
		pack.addProvider(FiredRecipeGenerator::new);
		FiredTagsGenerator.registerAll(pack);
	}
}
