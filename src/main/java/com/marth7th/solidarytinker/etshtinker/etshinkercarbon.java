package com.marth7th.solidarytinker.etshtinker;

import static com.c2h6s.etshtinker.Mapping.ionizerFluidMap.*;
import static com.c2h6s.etshtinker.init.etshtinkerParticleType.plasmaexplosioncyan;
import static com.c2h6s.etshtinker.init.etshtinkerParticleType.plasmaexplosionpurple;
import static com.marth7th.solidarytinker.register.solidarytinkerFluid.*;

public class etshinkercarbon {
    public static void extendMap () {
        fluidParts.put(molten_dwarf.get(), plasmaexplosioncyan.get());
        fluidDmg.put(molten_dwarf.get(), 18F);
        fluidSpecial.put(molten_dwarf.get(), "burn");

        fluidParts.put(super_dt.get(), plasmaexplosionpurple.get());
        fluidDmg.put(super_dt.get(), 12F);
        fluidSpecial.put(super_dt.get(), "explosion");

        fluidParts.put(molten_starfall.get(), plasmaexplosionpurple.get());
        fluidDmg.put(molten_starfall.get(), 145F);
        fluidSpecial.put(molten_starfall.get(), "magic_damage");

        fluidParts.put(molten_starfall.get(), plasmaexplosionpurple.get());
        fluidDmg.put(molten_starfall.get(), 145F);
        fluidSpecial.put(molten_starfall.get(), "magic_damage");
    }
}
