package io.github.haykam821.emojihost;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import io.github.haykam821.emojihost.definition.HostedEmojis;
import net.fabricmc.api.ModInitializer;

public class Main implements ModInitializer {
	public static final String MOD_ID = "emojihost";

	public static final Logger LOGGER = LogManager.getLogger("Emoji Host");

	@Override
	public void onInitialize() {
		EmojiHostPackets.register();
		HostedEmojis.load();
	}
}
