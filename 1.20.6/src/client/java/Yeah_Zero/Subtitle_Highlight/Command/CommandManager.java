package Yeah_Zero.Subtitle_Highlight.Command;

import Yeah_Zero.Subtitle_Highlight.Configure.Manager;
import Yeah_Zero.Subtitle_Highlight.Configure.Settings;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.FloatArgumentType;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.arguments.LongArgumentType;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.context.CommandContext;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandManager;
import net.fabricmc.fabric.api.client.command.v2.FabricClientCommandSource;
import net.minecraft.command.CommandRegistryAccess;
import net.minecraft.text.Text;

@Environment(EnvType.CLIENT)
public class CommandManager {
    public static void registerCommands(CommandDispatcher<FabricClientCommandSource> dispatcher, CommandRegistryAccess registryAccess) {
        dispatcher.register(ClientCommandManager.literal("subtitle_highlight")
            .then(ClientCommandManager.literal("reload")
                .executes(CommandManager::reloadConfig)
            )
            .then(ClientCommandManager.literal("save")
                .executes(CommandManager::saveConfig)
            )
            .then(ClientCommandManager.literal("maxDuration")
                .then(ClientCommandManager.argument("value", LongArgumentType.longArg(0))
                    .executes(CommandManager::setMaximumDuration)
                )
                .executes(CommandManager::getMaximumDuration)
            )
            .then(ClientCommandManager.literal("scale")
                .then(ClientCommandManager.argument("value", FloatArgumentType.floatArg(0.1f, 5.0f))
                    .executes(CommandManager::setScale)
                )
                .executes(CommandManager::getScale)
            )
            .then(ClientCommandManager.literal("bottomMargin")
                .then(ClientCommandManager.argument("value", FloatArgumentType.floatArg(0))
                    .executes(CommandManager::setBottomMargin)
                )
                .executes(CommandManager::getBottomMargin)
            )
            .then(ClientCommandManager.literal("sideMargin")
                .then(ClientCommandManager.argument("value", FloatArgumentType.floatArg(0))
                    .executes(CommandManager::setSideMargin)
                )
                .executes(CommandManager::getSideMargin)
            )
            .then(ClientCommandManager.literal("backgroundColor")
                .then(ClientCommandManager.argument("value", IntegerArgumentType.integer(0, 0xffffffff))
                    .executes(CommandManager::setBackgroundColor)
                )
                .executes(CommandManager::getBackgroundColor)
            )
            .then(ClientCommandManager.literal("startRatio")
                .then(ClientCommandManager.argument("value", FloatArgumentType.floatArg(0, 1))
                    .executes(CommandManager::setStartRatio)
                )
                .executes(CommandManager::getStartRatio)
            )
            .then(ClientCommandManager.literal("endRatio")
                .then(ClientCommandManager.argument("value", FloatArgumentType.floatArg(0, 1))
                    .executes(CommandManager::setEndRatio)
                )
                .executes(CommandManager::getEndRatio)
            )
            .then(ClientCommandManager.literal("ikunEasterEgg")
                .then(ClientCommandManager.argument("value", StringArgumentType.string())
                    .executes(CommandManager::setIkuneasterEgg)
                )
                .executes(CommandManager::getIkuneasterEgg)
            )
            .executes(CommandManager::showHelp)
        );
    }

    private static int reloadConfig(CommandContext<FabricClientCommandSource> context) {
        Manager.load();
        context.getSource().sendFeedback(Text.translatable("subtitle_highlight.command.reload.success"));
        return 1;
    }

    private static int saveConfig(CommandContext<FabricClientCommandSource> context) {
        Manager.save();
        context.getSource().sendFeedback(Text.translatable("subtitle_highlight.command.save.success"));
        return 1;
    }

    private static int setMaximumDuration(CommandContext<FabricClientCommandSource> context) {
        long value = LongArgumentType.getLong(context, "value");
        Manager.settings.maxDuration = value;
        context.getSource().sendFeedback(Text.translatable("subtitle_highlight.command.maxDuration.set", value));
        return 1;
    }

    private static int getMaximumDuration(CommandContext<FabricClientCommandSource> context) {
        context.getSource().sendFeedback(Text.translatable("subtitle_highlight.command.maxDuration.get", Manager.settings.maxDuration));
        return 1;
    }

    private static int setScale(CommandContext<FabricClientCommandSource> context) {
        float value = FloatArgumentType.getFloat(context, "value");
        Manager.settings.scale = value;
        context.getSource().sendFeedback(Text.translatable("subtitle_highlight.command.scale.set", value));
        return 1;
    }

    private static int getScale(CommandContext<FabricClientCommandSource> context) {
        context.getSource().sendFeedback(Text.translatable("subtitle_highlight.command.scale.get", Manager.settings.scale));
        return 1;
    }

    private static int setBottomMargin(CommandContext<FabricClientCommandSource> context) {
        float value = FloatArgumentType.getFloat(context, "value");
        Manager.settings.bottomMargin = value;
        context.getSource().sendFeedback(Text.translatable("subtitle_highlight.command.bottomMargin.set", value));
        return 1;
    }

    private static int getBottomMargin(CommandContext<FabricClientCommandSource> context) {
        context.getSource().sendFeedback(Text.translatable("subtitle_highlight.command.bottomMargin.get", Manager.settings.bottomMargin));
        return 1;
    }

    private static int setSideMargin(CommandContext<FabricClientCommandSource> context) {
        float value = FloatArgumentType.getFloat(context, "value");
        Manager.settings.sideMargin = value;
        context.getSource().sendFeedback(Text.translatable("subtitle_highlight.command.sideMargin.set", value));
        return 1;
    }

    private static int getSideMargin(CommandContext<FabricClientCommandSource> context) {
        context.getSource().sendFeedback(Text.translatable("subtitle_highlight.command.sideMargin.get", Manager.settings.sideMargin));
        return 1;
    }

    private static int setBackgroundColor(CommandContext<FabricClientCommandSource> context) {
        int value = IntegerArgumentType.getInteger(context, "value");
        Manager.settings.backgroundColor = value;
        context.getSource().sendFeedback(Text.translatable("subtitle_highlight.command.backgroundColor.set", Integer.toHexString(value)));
        return 1;
    }

    private static int getBackgroundColor(CommandContext<FabricClientCommandSource> context) {
        context.getSource().sendFeedback(Text.translatable("subtitle_highlight.command.backgroundColor.get", Integer.toHexString(Manager.settings.backgroundColor)));
        return 1;
    }

    private static int setStartRatio(CommandContext<FabricClientCommandSource> context) {
        float value = FloatArgumentType.getFloat(context, "value");
        Manager.settings.startRatio = value;
        context.getSource().sendFeedback(Text.translatable("subtitle_highlight.command.startRatio.set", value));
        return 1;
    }

    private static int getStartRatio(CommandContext<FabricClientCommandSource> context) {
        context.getSource().sendFeedback(Text.translatable("subtitle_highlight.command.startRatio.get", Manager.settings.startRatio));
        return 1;
    }

    private static int setEndRatio(CommandContext<FabricClientCommandSource> context) {
        float value = FloatArgumentType.getFloat(context, "value");
        Manager.settings.endRatio = value;
        context.getSource().sendFeedback(Text.translatable("subtitle_highlight.command.endRatio.set", value));
        return 1;
    }

    private static int getEndRatio(CommandContext<FabricClientCommandSource> context) {
        context.getSource().sendFeedback(Text.translatable("subtitle_highlight.command.endRatio.get", Manager.settings.endRatio));
        return 1;
    }

    private static int setIkuneasterEgg(CommandContext<FabricClientCommandSource> context) {
        String value = StringArgumentType.getString(context, "value");
        boolean boolValue = value.equalsIgnoreCase("true") || value.equalsIgnoreCase("on") || value.equalsIgnoreCase("yes");
        Manager.settings.ikunEasterEgg = boolValue;
        context.getSource().sendFeedback(Text.translatable("subtitle_highlight.command.ikunEasterEgg.set", boolValue ? Text.translatable("subtitle_highlight.command.enabled") : Text.translatable("subtitle_highlight.command.disabled")));
        return 1;
    }

    private static int getIkuneasterEgg(CommandContext<FabricClientCommandSource> context) {
        context.getSource().sendFeedback(Text.translatable("subtitle_highlight.command.ikunEasterEgg.get", Manager.settings.ikunEasterEgg ? Text.translatable("subtitle_highlight.command.enabled") : Text.translatable("subtitle_highlight.command.disabled")));
        return 1;
    }

    private static int showHelp(CommandContext<FabricClientCommandSource> context) {
        context.getSource().sendFeedback(Text.translatable("subtitle_highlight.command.help.title"));
        context.getSource().sendFeedback(Text.translatable("subtitle_highlight.command.help.reload"));
        context.getSource().sendFeedback(Text.translatable("subtitle_highlight.command.help.save"));
        context.getSource().sendFeedback(Text.translatable("subtitle_highlight.command.help.maxDuration"));
        context.getSource().sendFeedback(Text.translatable("subtitle_highlight.command.help.scale"));
        context.getSource().sendFeedback(Text.translatable("subtitle_highlight.command.help.bottomMargin"));
        context.getSource().sendFeedback(Text.translatable("subtitle_highlight.command.help.sideMargin"));
        context.getSource().sendFeedback(Text.translatable("subtitle_highlight.command.help.backgroundColor"));
        context.getSource().sendFeedback(Text.translatable("subtitle_highlight.command.help.startRatio"));
        context.getSource().sendFeedback(Text.translatable("subtitle_highlight.command.help.endRatio"));
        context.getSource().sendFeedback(Text.translatable("subtitle_highlight.command.help.ikunEasterEgg"));
        return 1;
    }
}