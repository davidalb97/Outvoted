package io.github.how_bout_no.outvoted;

import io.github.how_bout_no.outvoted.init.ModFeatures;
import io.github.how_bout_no.outvoted.init.ModFireBlock;
import io.github.how_bout_no.outvoted.init.ModPOITypes;
import io.github.how_bout_no.outvoted.init.ModSigns;
import io.github.how_bout_no.outvoted.item.ModSpawnEggItem;
import net.fabricmc.api.ModInitializer;

public class OutvotedFabric implements ModInitializer {
    @Override
    public void onInitialize() {
        Outvoted.init();
        ModSpawnEggItem.initSpawnEggs();
        ModFeatures.Configured.registerConfiguredFeatures();
        ModFireBlock.init();
        ModSigns.init();
        ModPOITypes.POINT_OF_INTEREST_TYPES.register();
    }
}