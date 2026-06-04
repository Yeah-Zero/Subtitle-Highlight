# Subtitle Highlight API 教程

本教程将介绍如何使用 Subtitle Highlight 模组提供的 API 来自定义和扩展字幕处理功能。

## 基本概念

Subtitle Highlight API 提供了一个灵活的接口，允许其他模组注册自定义的字幕处理器，从而实现对字幕的自定义处理。

## API 结构

### 核心类和接口

1. **SubtitleAPI**：静态工具类，提供字幕处理的核心功能
2. **SubtitleAPI.SubtitleProcessor**：接口，定义了字幕处理器的标准方法

## 依赖配置

要使用 Subtitle Highlight API，你需要在你的模组的 `build.gradle` 文件中添加依赖：

```gradle
dependencies {
    modImplementation "Yeah_Zero.Subtitle_Highlight:Subtitle-Highlight:1.0.3-rc.2-1.21.11"
}
```

## 基本使用

### 创建字幕处理器

要创建一个自定义的字幕处理器，你需要实现 `SubtitleAPI.SubtitleProcessor` 接口：

```java
import Yeah_Zero.Subtitle_Highlight.Configure.Settings;
import Yeah_Zero.Subtitle_Highlight.api.SubtitleAPI;
import net.minecraft.text.Text;

public class MySubtitleProcessor implements SubtitleAPI.SubtitleProcessor {
    @Override
    public Text process(Text originalText, Settings settings) {
        // 自定义处理逻辑
        // 例如：添加前缀、修改颜色、过滤内容等
        return processedText;
    }
}
```

### 注册处理器

在你的模组初始化时，注册你的处理器：

```java
import Yeah_Zero.Subtitle_Highlight.api.SubtitleAPI;
import net.fabricmc.api.ModInitializer;

public class MyModInitializer implements ModInitializer {
    @Override
    public void onInitialize() {
        // 注册字幕处理器
        SubtitleAPI.registerProcessor(new MySubtitleProcessor());
    }
}
```

## 示例：自定义颜色处理器

```java
import Yeah_Zero.Subtitle_Highlight.Configure.Settings;
import Yeah_Zero.Subtitle_Highlight.api.SubtitleAPI;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.text.TextColor;
import net.minecraft.util.Formatting;

public class CustomColorProcessor implements SubtitleAPI.SubtitleProcessor {
    @Override
    public Text process(Text originalText, Settings settings) {
        // 创建可变文本
        MutableText mutableText = originalText.copy();
        
        // 添加自定义颜色
        mutableText.formatted(Formatting.BOLD);
        mutableText.setStyle(mutableText.getStyle().withColor(TextColor.parse("#FF69B4")));
        
        return mutableText;
    }
}
```

## 示例：字幕过滤处理器

```java
import Yeah_Zero.Subtitle_Highlight.Configure.Settings;
import Yeah_Zero.Subtitle_Highlight.api.SubtitleAPI;
import net.minecraft.text.Text;

public class SubtitleFilterProcessor implements SubtitleAPI.SubtitleProcessor {
    @Override
    public Text process(Text originalText, Settings settings) {
        String text = originalText.getString();
        
        // 过滤掉不需要的字幕
        if (text.contains("讨厌的声音")) {
            return Text.empty(); // 返回空文本
        }
        
        return originalText;
    }
}
```

## 示例：信息增强处理器

```java
import Yeah_Zero.Subtitle_Highlight.Configure.Settings;
import Yeah_Zero.Subtitle_Highlight.api.SubtitleAPI;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

public class InfoEnhancerProcessor implements SubtitleAPI.SubtitleProcessor {
    @Override
    public Text process(Text originalText, Settings settings) {
        MutableText mutableText = originalText.copy();
        
        // 添加额外信息
        mutableText.append(Text.literal(" [增强版]").formatted(Formatting.GRAY));
        
        return mutableText;
    }
}
```

## 处理器执行顺序

处理器按照注册顺序依次执行，后注册的处理器会接收到前一个处理器处理后的结果。

## 最佳实践

1. **保持处理器简单**：每个处理器只负责一个功能，便于维护和调试
2. **处理空值**：在处理文本时，确保处理空文本的情况
3. **性能考虑**：避免在处理器中执行耗时操作，影响游戏性能
4. **兼容性**：确保你的处理器与其他处理器兼容，避免冲突

## 故障排除

如果你的处理器不生效，请检查：
1. 是否正确实现了 `SubtitleProcessor` 接口
2. 是否正确注册了处理器
3. 处理器的逻辑是否正确
4. 是否有其他处理器覆盖了你的处理结果

## 注意事项

1. **API 稳定性**：API 可能会在未来版本中发生变化，请关注模组更新
2. **性能影响**：过多或复杂的处理器可能会影响游戏性能
3. **兼容性**：不同版本的 Subtitle Highlight 模组可能有不同的 API

通过使用 Subtitle Highlight API，你可以轻松地扩展和定制字幕系统的功能，为玩家提供更加个性化的游戏体验。
