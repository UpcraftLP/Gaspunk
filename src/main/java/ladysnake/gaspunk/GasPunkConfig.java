package ladysnake.gaspunk;

import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.ConfigManager;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Config(modid = GasPunk.MOD_ID, name = GasPunk.MOD_ID + "/" + GasPunk.MOD_ID)
@Mod.EventBusSubscriber(modid = GasPunk.MOD_ID)
public class GasPunkConfig {

    @Config.Comment("Disables gas / smoke / vapor clouds checking for a clear path to entities and make them only check straight distance instead")
    public static boolean fastGas = false;

    @Config.Comment("Makes ash require smelting nether wart instead of rotten flesh")
    public static boolean alternativeAshRecipe = false;

    @Config.Comment({
            "The items which ID's are added here will be considered as gas masks when breathing gas",
            "You can specify entire armor sets by separating items with \"&\" in a single entry. Using \"*\" instead of an item id will match anything.",
            "Examples: \"minecraft:diamond_helmet\", \"minecraft:golden_helmet&*&minecraft:chainmail_leggings\""
    })
    public static String[] otherGasMasks = new String[0];

    public static Client client = new Client();

    public static class Client {
        @Config.Comment({"Enables the use of shaders to render the gas overlay", "won't do anything if renderGasOverlays is set to false"})
        public boolean useShaders = true;

        @Config.Comment({"Display a custom overlay when inside a gas cloud", "combine with useShaders for a dynamically generated overlay"})
        public boolean renderGasOverlays = false;

        @Config.Comment("The maximum amount of particles that should be displayed on the screen at the same time")
        public int maxParticles = 500;
    }

    @SubscribeEvent
    public static void onConfigChanged(ConfigChangedEvent.OnConfigChangedEvent event) {
        if (event.getModID().equals(GasPunk.MOD_ID)) {
            ConfigManager.sync(GasPunk.MOD_ID, Config.Type.INSTANCE);
        }
    }
}
