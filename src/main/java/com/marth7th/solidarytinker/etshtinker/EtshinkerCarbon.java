package com.marth7th.solidarytinker.etshtinker;

import static com.c2h6s.etshtinker.Mapping.ionizerFluidMap.*;
import static com.c2h6s.etshtinker.init.etshtinkerParticleType.*;
import static com.marth7th.solidarytinker.register.solidarytinkerFluid.*;

public class EtshinkerCarbon {
    public static void extendMap() {
        fluidParts.put(molten_dwarf.get(), plasmaexplosioncyan.get());
        fluidDmg.put(molten_dwarf.get(), 18F);
        fluidSpecial.put(molten_dwarf.get(), "burn");

        fluidParts.put(super_dt.get(), plasmaexplosionpurple.get());
        fluidDmg.put(super_dt.get(), 12F);
        fluidSpecial.put(super_dt.get(), "explosion");

        fluidParts.put(molten_starfall.get(), plasmaexplosionpurple.get());
        fluidDmg.put(molten_starfall.get(), 14.5F);
        fluidSpecial.put(molten_starfall.get(), "magic_damage");

        fluidParts.put(molten_icefantasy.get(), plasmaexplosionpurple.get());
        fluidDmg.put(molten_icefantasy.get(), 10F);
        fluidSpecial.put(molten_icefantasy.get(), "magic_damage");

        fluidParts.put(molten_damascus_steel.get(), plasmaexplosionlime.get());
        fluidDmg.put(molten_damascus_steel.get(), 3f);
        fluidSpecial.put(molten_damascus_steel.get(), "explosion");

        fluidParts.put(molten_experience_steel.get(), plasmaexplosiongreen.get());
        fluidDmg.put(molten_experience_steel.get(), 5f);
        fluidSpecial.put(molten_experience_steel.get(), "burn");
    }
}
