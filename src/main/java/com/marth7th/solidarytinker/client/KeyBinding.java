package com.marth7th.solidarytinker.client;

import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.KeyMapping;
import net.minecraftforge.client.settings.KeyConflictContext;
import org.lwjgl.glfw.GLFW;

public class KeyBinding {
    public static final String KEY_CATEGORY_SOLIDARYTINKER = "key.category.solidarytinker";
    public static final String KEY_ADJUST_DIGGING_SPEED = "key.solidarytinker.digging_speed";
    public static final String KEY_SOULGE_CONFIG = "key.solidarytinker.soulge_config";
    public static final KeyMapping DIGGING_SPEED_KEY = new KeyMapping(KEY_ADJUST_DIGGING_SPEED, KeyConflictContext.IN_GAME, InputConstants.Type.KEYSYM, GLFW.GLFW_KEY_Y, KEY_CATEGORY_SOLIDARYTINKER);
    public static final KeyMapping SOULGE_CONFIG = new KeyMapping(KEY_SOULGE_CONFIG, KeyConflictContext.IN_GAME, InputConstants.Type.KEYSYM, GLFW.GLFW_KEY_C, KEY_CATEGORY_SOLIDARYTINKER);
}
