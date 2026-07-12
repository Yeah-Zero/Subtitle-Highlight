package Yeah_Zero.Subtitle_Highlight;

import Yeah_Zero.Subtitle_Highlight.Command.CommandManager;
import Yeah_Zero.Subtitle_Highlight.Configure.Manager;
import Yeah_Zero.Subtitle_Highlight.Data.SubtitleTypeLoader;
import Yeah_Zero.Subtitle_Highlight.Keybind.KeybindManager;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandRegistrationCallback;
import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.minecraft.resource.ResourceType;

public class Initializer implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        Manager.load();
        
        ClientCommandRegistrationCallback.EVENT.register(CommandManager::registerCommands);
        
        ResourceManagerHelper.get(ResourceType.SERVER_DATA).registerReloadListener(new SubtitleTypeLoader());
        
        KeybindManager.registerKeybinds();
    }
}