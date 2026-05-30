package com.qituo.shur.Data;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.qituo.shur.Util.ColorCode;
import net.fabricmc.fabric.api.resource.SimpleSynchronousResourceReloadListener;
import net.minecraft.resource.ResourceManager;
import net.minecraft.util.Identifier;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class SubtitleTypeLoader implements SimpleSynchronousResourceReloadListener {
    public static final Identifier ID = Identifier.of("shur", "subtitle_types");
    private static final Gson GSON = new Gson();
    private static Map<String, Map<String, ColorCode>> subtitleTypes = new HashMap<>();
    
    // 颜色查询缓存，使用并发 HashMap 实现 O(1) 查找
    private static final ConcurrentHashMap<String, ColorCode> colorCache = new ConcurrentHashMap<>();
    private static final String CACHE_KEY_SEPARATOR = ":";

    @Override
    public Identifier getFabricId() {
        return ID;
    }

    @Override
    public void reload(ResourceManager manager) {
        try {
            // 加载默认数据文件
            InputStream inputStream = manager.getResource(Identifier.of("shur", "subtitle_types.json")).get().getInputStream();
            JsonObject jsonObject = GSON.fromJson(new InputStreamReader(inputStream), JsonObject.class);
            JsonObject subtitleTypesJson = jsonObject.getAsJsonObject("subtitle_types");

            // 解析数据
            subtitleTypes.clear();
            loadSubtitleTypes(subtitleTypesJson, "", subtitleTypes);
            // 清除缓存
            invalidateCache();
        } catch (Exception e) {
            e.printStackTrace();
            // 如果加载失败，使用默认配置
            loadDefaultSubtitleTypes();
        }
    }

    private void loadSubtitleTypes(JsonObject jsonObject, String path, Map<String, Map<String, ColorCode>> result) {
        for (String key : jsonObject.keySet()) {
            String currentPath = path.isEmpty() ? key : path + "." + key;
            if (jsonObject.get(key).isJsonObject()) {
                JsonObject nestedObject = jsonObject.getAsJsonObject(key);
                if (nestedObject.has("color")) {
                    // 找到颜色配置
                    String colorName = nestedObject.get("color").getAsString();
                    ColorCode colorCode = ColorCode.fromName(colorName);
                    if (colorCode != null) {
                        String parentPath = path.isEmpty() ? "" : path;
                        if (!result.containsKey(parentPath)) {
                            result.put(parentPath, new HashMap<>());
                        }
                        result.get(parentPath).put(key, colorCode);
                    }
                } else {
                    // 递归处理嵌套对象
                    loadSubtitleTypes(nestedObject, currentPath, result);
                }
            }
        }
    }

    private void loadDefaultSubtitleTypes() {
        subtitleTypes.clear();

        // 环境
        Map<String, ColorCode> ambientMap = new HashMap<>();
        ambientMap.put("ambient", ColorCode.DARK_BLUE);
        subtitleTypes.put("", ambientMap);

        // 方块
        Map<String, ColorCode> blockMap = new HashMap<>();
        blockMap.put("generic", ColorCode.GRAY);
        blockMap.put("interact", ColorCode.GREEN);
        blockMap.put("working", ColorCode.YELLOW);
        blockMap.put("dangerous", ColorCode.RED);
        blockMap.put("crop", ColorCode.GREEN);
        blockMap.put("other", ColorCode.GRAY);
        subtitleTypes.put("block", blockMap);

        // 魔咒
        Map<String, ColorCode> enchantMap = new HashMap<>();
        enchantMap.put("enchant", ColorCode.LIGHT_PURPLE);
        subtitleTypes.put("", enchantMap);

        // 实体
        Map<String, ColorCode> entityMobPlayerMap = new HashMap<>();
        entityMobPlayerMap.put("attack", ColorCode.RED);
        entityMobPlayerMap.put("hurt", ColorCode.RED);
        entityMobPlayerMap.put("other", ColorCode.WHITE);
        subtitleTypes.put("entity.mob.player", entityMobPlayerMap);

        Map<String, ColorCode> entityMobMap = new HashMap<>();
        entityMobMap.put("passive", ColorCode.GREEN);
        entityMobMap.put("neutral", ColorCode.YELLOW);
        entityMobMap.put("hostile", ColorCode.RED);
        entityMobMap.put("boss", ColorCode.DARK_PURPLE);
        subtitleTypes.put("entity.mob", entityMobMap);

        Map<String, ColorCode> entityMap = new HashMap<>();
        entityMap.put("vehicle", ColorCode.GRAY);
        entityMap.put("projectile", ColorCode.AQUA);
        entityMap.put("explosive", ColorCode.RED);
        entityMap.put("decoration", ColorCode.GRAY);
        entityMap.put("other", ColorCode.GRAY);
        subtitleTypes.put("entity", entityMap);

        // 物品
        Map<String, ColorCode> itemMap = new HashMap<>();
        itemMap.put("weapon", ColorCode.RED);
        itemMap.put("armor", ColorCode.GOLD);
        itemMap.put("tool", ColorCode.AQUA);
        itemMap.put("other", ColorCode.GRAY);
        subtitleTypes.put("item", itemMap);

        // 其他
        Map<String, ColorCode> otherMap = new HashMap<>();
        otherMap.put("other", ColorCode.GRAY);
        subtitleTypes.put("", otherMap);

        // 清除缓存
        invalidateCache();
    }

    public static ColorCode getColor(String path, String key) {
        // 构建缓存键
        String cacheKey = buildCacheKey(path, key);

        // 先从缓存查找
        ColorCode cached = colorCache.get(cacheKey);
        if (cached != null) {
            return cached;
        }

        // 缓存未命中，从实际数据中查找
        Map<String, ColorCode> map = subtitleTypes.get(path);
        ColorCode result = (map != null) ? map.get(key) : null;

        // 缓存结果（即使是 null 也缓存，避免重复查询）
        colorCache.put(cacheKey, result);

        return result;
    }

    private static String buildCacheKey(String path, String key) {
        return path + CACHE_KEY_SEPARATOR + key;
    }

    public static void invalidateCache() {
        colorCache.clear();
    }

    public static void register() {
        // 注册数据加载器
    }
}
