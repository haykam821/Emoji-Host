package io.github.haykam821.emojihost.definition;

import java.util.Map;

import com.google.gson.JsonElement;

import io.github.haykam821.emojihost.EmojiHostPackets;
import net.minecraft.network.Packet;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;

public class EmojiDefinition {
	private static final int MAGIC_NUMBER = 0xEF04;
	private static final String MAGIC_TRANSLATION_KEY = "%1$s%" + EmojiDefinition.MAGIC_NUMBER + "$s";

	private final String key;

	private final Text text;
	private final Packet<?> registerPacket;

	public EmojiDefinition(String key, String texture) {
		this.key = key;

		this.text = new TranslatableText(MAGIC_TRANSLATION_KEY, ":" + this.key + ":", this.key);
		this.registerPacket = EmojiHostPackets.createRegisterEmojiPacket(this.key, texture);
	}

	public String getKey() {
		return this.key;
	}

	public Text getText() {
		return this.text;
	}

	public Packet<?> getRegisterPacket() {
		return this.registerPacket;
	}

	public boolean shouldRegisterAsEmote(ServerCommandSource source) {
		return true;
	}

	protected static EmojiDefinition fromJson(Map.Entry<String, JsonElement> entry) {
		String key = entry.getKey();
		JsonElement value = entry.getValue();

		String texture;
		if (value.isJsonObject()) {
			texture = value.getAsJsonObject().get("texture").getAsString();
		} else {
			texture = value.getAsString();
		}

		return new EmojiDefinition(key, texture);
	}

	@Override
	public String toString() {
		return "EmojiDefinition{key=" + this.key + ", text=" + this.text + ", registerPacket=" + this.registerPacket + "}";
	}
}
