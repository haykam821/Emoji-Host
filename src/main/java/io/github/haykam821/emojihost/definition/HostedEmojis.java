package io.github.haykam821.emojihost.definition;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.nio.file.Path;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import io.github.haykam821.emojihost.Main;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.fabricmc.loader.api.FabricLoader;

public final class HostedEmojis {
	private static final Set<EmojiDefinition> DEFINITIONS = new HashSet<>();

	private HostedEmojis() {
		return;
	}

	public static Iterable<EmojiDefinition> getDefinitions() {
		return DEFINITIONS;
	}

	public static void sendDefinitions(PacketSender sender) {
		for (EmojiDefinition definition : HostedEmojis.getDefinitions()) {
			sender.sendPacket(definition.getRegisterPacket());
		}
	}

	public static void load() {
		try {
			Path configDir = FabricLoader.getInstance().getConfigDir();
			File file = new File(configDir.toFile(), Main.MOD_ID + ".json");;
			
			BufferedReader reader = new BufferedReader(new FileReader(file));
			JsonObject json = JsonParser.parseReader(reader).getAsJsonObject();

			JsonObject definitionsJson = json.getAsJsonObject("definitions");
			Set<EmojiDefinition> newDefinitions = new HashSet<>();

			for (Map.Entry<String, JsonElement> entry : definitionsJson.entrySet()) {
				EmojiDefinition definition = EmojiDefinition.fromJson(entry);
				newDefinitions.add(definition);
			}
			
			DEFINITIONS.clear();
			DEFINITIONS.addAll(newDefinitions);

			Main.LOGGER.info("Registered {} emoji definitions", DEFINITIONS.size());
		} catch (Exception exception) {
			Main.LOGGER.warn("Failed to read Emoji Host config, so no emoji will be usable", exception);
		}
	}
}
