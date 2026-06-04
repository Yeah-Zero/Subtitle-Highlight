# 数据包使用指南

本指南将帮助你创建和使用数据包来自定义 Subtitle Highlight 模组的字幕类型和颜色。

## 什么是数据包？

数据包（Datapack）是 Minecraft 的一种扩展机制，允许玩家和服务器管理员通过修改数据文件来自定义游戏内容。本模组支持通过数据包来自定义字幕的颜色配置，而无需修改模组代码。

## 数据包目录结构

一个基本的数据包目录结构如下：

```
your_datapack/
├── pack.mcmeta
└── data/
    └── subtitle_highlight/
        └── subtitle_types.json
```

## 创建数据包

### 1. 创建 pack.mcmeta 文件

在数据包根目录下创建 `pack.mcmeta` 文件：

```json
{
  "pack": {
    "pack_format": 48,
    "description": "我的自定义字幕颜色数据包"
  }
}
```

### 2. 创建字幕类型配置文件

在 `data/subtitle_highlight/` 目录下创建 `subtitle_types.json` 文件：

```json
{
  "subtitle_types": {
    "ambient": {
      "color": "dark_blue"
    },
    "block": {
      "generic": {
        "color": "gray"
      },
      "interact": {
        "color": "green"
      },
      "working": {
        "color": "yellow"
      },
      "dangerous": {
        "color": "red"
      },
      "crop": {
        "color": "green"
      },
      "other": {
        "color": "gray"
      }
    },
    "enchant": {
      "color": "light_purple"
    },
    "entity": {
      "mob": {
        "player": {
          "attack": {
            "color": "red"
          },
          "hurt": {
            "color": "red"
          },
          "other": {
            "color": "white"
          }
        },
        "passive": {
          "color": "green"
        },
        "neutral": {
          "color": "yellow"
        },
        "hostile": {
          "color": "red"
        },
        "boss": {
          "color": "dark_purple"
        }
      },
      "vehicle": {
        "color": "gray"
      },
      "projectile": {
        "color": "aqua"
      },
      "explosive": {
        "color": "red"
      },
      "decoration": {
        "color": "gray"
      },
      "other": {
        "color": "gray"
      }
    },
    "item": {
      "weapon": {
        "color": "red"
      },
      "armor": {
        "color": "gold"
      },
      "tool": {
        "color": "aqua"
      },
      "other": {
        "color": "gray"
      }
    },
    "other": {
      "color": "gray"
    }
  }
}
```

## 可用颜色

以下颜色可以用于配置字幕颜色：

- `black` - 黑色
- `dark_blue` - 深蓝色
- `dark_green` - 深绿色
- `dark_aqua` - 深青色
- `dark_red` - 深红色
- `dark_purple` - 深紫色
- `gold` - 金色
- `gray` - 灰色
- `dark_gray` - 深灰色
- `blue` - 蓝色
- `green` - 绿色
- `aqua` - 青色
- `red` - 红色
- `light_purple` - 浅紫色
- `yellow` - 黄色
- `white` - 白色

## 安装数据包

### 单人游戏

1. 打开 Minecraft 游戏
2. 选择或创建一个世界
3. 点击"编辑"按钮
4. 选择"打开世界文件夹"
5. 将数据包文件夹放入 `datapacks` 文件夹中
6. 重新进入世界，数据包将自动加载

### 多人游戏（服务器）

1. 将数据包文件夹放入服务器的 `world/datapacks` 文件夹中
2. 重启服务器
3. 数据包将自动加载

## 示例：自定义敌对生物字幕颜色

如果你想将所有敌对生物的字幕颜色改为亮红色，可以创建以下配置：

```json
{
  "subtitle_types": {
    "entity": {
      "mob": {
        "hostile": {
          "color": "red"
        }
      }
    }
  }
}
```

## 示例：自定义武器字幕颜色

如果你想将武器相关的字幕颜色改为金色，可以创建以下配置：

```json
{
  "subtitle_types": {
    "item": {
      "weapon": {
        "color": "gold"
      }
    }
  }
}
```

## 常见问题

### Q: 数据包会覆盖模组的默认配置吗？

A: 是的，数据包中的配置会覆盖模组的默认配置。如果数据包中缺少某些配置，模组会使用默认值。

### Q: 我可以只修改部分字幕颜色吗？

A: 可以，你只需要在数据包中包含你想要修改的配置即可，其他配置将使用模组的默认值。

### Q: 如何禁用数据包？

A: 在游戏中打开数据包菜单，选择你的数据包，然后点击禁用按钮。

### Q: 数据包会影响性能吗？

A: 数据包的影响非常小，因为它们只在游戏启动时加载一次。

### Q: 我可以分享我的数据包吗？

A: 可以，你可以将数据包文件夹压缩为 ZIP 文件，然后分享给其他玩家。

## 进阶使用

### 创建主题数据包

你可以创建多个主题数据包，每个数据包定义不同的颜色方案：

- `dark_theme.zip` - 深色主题
- `light_theme.zip` - 浅色主题
- `colorful_theme.zip` - 彩色主题

然后根据需要启用不同的主题。

### 与其他模组集成

如果你是模组开发者，可以通过本模组的 API 来集成自定义字幕处理：

```java
import Yeah_Zero.Subtitle_Highlight.api.SubtitleAPI;

// 注册自定义字幕处理器
SubtitleAPI.registerProcessor((text, settings) -> {
    // 自定义处理逻辑
    return text;
});
```

## 技术支持

如果你在使用数据包时遇到问题，请检查：

1. JSON 格式是否正确
2. 颜色名称是否拼写正确
3. 数据包目录结构是否正确
4. 数据包是否正确安装

更多技术支持和讨论，请访问模组的 GitHub 仓库。
