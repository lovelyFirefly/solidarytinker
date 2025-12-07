package com.marth7th.solidarytinker.tools.tinkeritem;

import com.marth7th.solidarytinker.util.method.SearchTools;
import net.minecraft.world.level.material.Fluid;

public enum SoulgeFuel {
    LiquidMagic(search("manaliquidizer:mana_fluid"),0.8f),
    ChaiYou(search("immersivepetroleum:diesel"),1.1f),
    LinBing(search("ad_astra:cryo_fuel"),1.3f),
    QiYou(search("immersivepetroleum:gasoline"),2.0f),
    YiQue(search("kubejs:ethyne"),3.0f),
    DaoChuan(search("mekanismgenerators:fusion_fuel"),2.5f);
    private final Fluid fluid;
    private final float scale;

    SoulgeFuel(Fluid fluid,float scale) {
        this.fluid = fluid;
        this.scale=scale;
    }
    private static Fluid search(String s){
        return SearchTools.findFluid(s);
    }
    public Fluid getFluid(){
        return fluid;
    }
    public float getScale(){
        return scale;
    }
}
