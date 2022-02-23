package io.github.haykam821.emojihost.mixin;

import java.util.Map;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import eu.pb4.styledchat.config.Config;
import io.github.haykam821.emojihost.definition.EmojiDefinition;
import io.github.haykam821.emojihost.definition.HostedEmojis;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.Text;

@Mixin(value = Config.class, remap = false)
public class ConfigMixin {
	@Inject(method = "getEmotes", at = @At("TAIL"))
	public void addHostedEmojiEmotes(ServerCommandSource source, CallbackInfoReturnable<Map<String, Text>> ci) {
		Map<String, Text> emotes = ci.getReturnValue();

		for (EmojiDefinition definition : HostedEmojis.getDefinitions()) {
			if (definition.shouldRegisterAsEmote(source)) {
				emotes.put(definition.getKey(), definition.getText());
			}
		}
	}
}
