package io.github.haykam821.emojihost;

import io.github.haykam821.emojihost.definition.HostedEmojis;
import io.netty.buffer.Unpooled;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;

public final class EmojiHostPackets {
	private static final String NAMESPACE = "heademoji";

	private static final Identifier REGISTER_EMOJI_ID = new Identifier(NAMESPACE, "register_emoji");
	private static final String EMOJI_TYPE = "head";

	private static final Identifier REQUEST_EMOJI_ID = new Identifier(NAMESPACE, "request_emoji");

	private EmojiHostPackets() {
		return;
	}

	public static void register() {
		ServerPlayNetworking.registerGlobalReceiver(EmojiHostPackets.REQUEST_EMOJI_ID, EmojiHostPackets::onRequestEmoji);
	}

	public static Packet<?> createRegisterEmojiPacket(String key, String texture) {
		PacketByteBuf buf = new PacketByteBuf(Unpooled.buffer());
		buf.writeString(EMOJI_TYPE);

		buf.writeString(key);
		buf.writeString(texture);

		return ServerPlayNetworking.createS2CPacket(EmojiHostPackets.REGISTER_EMOJI_ID, buf);
	}

	private static void onRequestEmoji(MinecraftServer server, ServerPlayerEntity player, ServerPlayNetworkHandler handler, PacketByteBuf buf, PacketSender responseSender) {
		HostedEmojis.sendDefinitions(responseSender);
	}
}

