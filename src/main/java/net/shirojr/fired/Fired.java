package net.shirojr.fired;

import net.fabricmc.api.ModInitializer;

import net.minecraft.util.Identifier;

import net.shirojr.fired.event.FiredDatapackRegistrations;
import net.shirojr.fired.init.FiredEventRegistration;
import net.shirojr.fired.init.FiredGameRules;
import net.shirojr.fired.init.FiredItems;
import net.shirojr.fired.init.FiredTags;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Fired implements ModInitializer {
	public static final String MOD_ID = "fired";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		FiredItems.initialize();
		FiredTags.initialize();
		FiredGameRules.initialize();
		FiredDatapackRegistrations.registerServer();
		FiredEventRegistration.registerCommon();

		LOGGER.info("It's getting hot!");
	}

	public static Identifier id(String path) {
		return new Identifier(MOD_ID, path);
	}
}
