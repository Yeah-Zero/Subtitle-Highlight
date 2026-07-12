package Yeah_Zero.Subtitle_Highlight.Keybind;

import Yeah_Zero.Subtitle_Highlight.Configure.Manager;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.text.Text;
import org.lwjgl.glfw.GLFW;

@Environment(EnvType.CLIENT)
public class KeybindManager {
    private static KeyBinding toggleSubtitles;
    private static KeyBinding increaseScale;
    private static KeyBinding decreaseScale;
    private static KeyBinding reloadConfig;
    private static KeyBinding saveConfig;
    private static KeyBinding toggleIkunEasterEgg;
    private static KeyBinding increaseOpacity;
    private static KeyBinding decreaseOpacity;

    public static void registerKeybinds() {
        String categoryName = "key.category.subtitle_highlight.keybinds";

        toggleSubtitles = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "key.subtitle_highlight.toggle_subtitles",
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_UNKNOWN,
                categoryName
        ));

        increaseScale = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "key.subtitle_highlight.increase_scale",
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_UNKNOWN,
                categoryName
        ));

        decreaseScale = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "key.subtitle_highlight.decrease_scale",
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_UNKNOWN,
                categoryName
        ));

        reloadConfig = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "key.subtitle_highlight.reload_config",
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_UNKNOWN,
                categoryName
        ));

        saveConfig = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "key.subtitle_highlight.save_config",
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_UNKNOWN,
                categoryName
        ));

        toggleIkunEasterEgg = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "key.subtitle_highlight.toggle_ikun",
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_UNKNOWN,
                categoryName
        ));

        increaseOpacity = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "key.subtitle_highlight.increase_opacity",
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_UNKNOWN,
                categoryName
        ));

        decreaseOpacity = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "key.subtitle_highlight.decrease_opacity",
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_UNKNOWN,
                categoryName
        ));

        ClientTickEvents.END_CLIENT_TICK.register(KeybindManager::handleKeybinds);
    }

    private static void handleKeybinds(MinecraftClient client) {
        if (client.player == null) return;

        while (toggleSubtitles.wasPressed()) {
            boolean newValue = !client.options.getShowSubtitles().getValue();
            client.options.getShowSubtitles().setValue(newValue);
            client.options.write();
            client.player.sendMessage(Text.translatable(newValue ? "subtitle_highlight.keybind.toggle_subtitles.on" : "subtitle_highlight.keybind.toggle_subtitles.off"), true);
        }

        while (increaseScale.wasPressed()) {
            Manager.settings.scale = Math.min(Manager.settings.scale + 0.1f, 3.0f);
            Manager.save();
            client.player.sendMessage(Text.translatable("subtitle_highlight.keybind.scale.changed", String.format("%.1f", Manager.settings.scale)), true);
        }

        while (decreaseScale.wasPressed()) {
            Manager.settings.scale = Math.max(Manager.settings.scale - 0.1f, 0.5f);
            Manager.save();
            client.player.sendMessage(Text.translatable("subtitle_highlight.keybind.scale.changed", String.format("%.1f", Manager.settings.scale)), true);
        }

        while (reloadConfig.wasPressed()) {
            Manager.load();
            client.player.sendMessage(Text.translatable("subtitle_highlight.keybind.reload.success"), true);
        }

        while (saveConfig.wasPressed()) {
            Manager.save();
            client.player.sendMessage(Text.translatable("subtitle_highlight.keybind.save.success"), true);
        }

        while (toggleIkunEasterEgg.wasPressed()) {
            Manager.settings.ikunEasterEgg = !Manager.settings.ikunEasterEgg;
            Manager.save();
            client.player.sendMessage(Text.translatable(Manager.settings.ikunEasterEgg ? "subtitle_highlight.keybind.ikun.on" : "subtitle_highlight.keybind.ikun.off"), true);
        }

        while (increaseOpacity.wasPressed()) {
            int alpha = (Manager.settings.backgroundColor >>> 24) & 0xFF;
            alpha = Math.min(alpha + 17, 255);
            Manager.settings.backgroundColor = (alpha << 24) | (Manager.settings.backgroundColor & 0x00FFFFFF);
            Manager.save();
            client.player.sendMessage(Text.translatable("subtitle_highlight.keybind.opacity.changed", String.format("%.0f%%", (alpha / 255.0) * 100)), true);
        }

        while (decreaseOpacity.wasPressed()) {
            int alpha = (Manager.settings.backgroundColor >>> 24) & 0xFF;
            alpha = Math.max(alpha - 17, 0);
            Manager.settings.backgroundColor = (alpha << 24) | (Manager.settings.backgroundColor & 0x00FFFFFF);
            Manager.save();
            client.player.sendMessage(Text.translatable("subtitle_highlight.keybind.opacity.changed", String.format("%.0f%%", (alpha / 255.0) * 100)), true);
        }
    }
}
