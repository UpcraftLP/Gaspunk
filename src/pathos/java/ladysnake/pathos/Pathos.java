package ladysnake.pathos;

import ladysnake.pathos.capability.CapabilitySickness;
import ladysnake.pathos.item.ModItems;
import ladysnake.pathos.network.PacketHandler;
import net.minecraft.launchwrapper.Launch;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

/*
  We might split this lib to a separate mod one day, which is why it's isolated from the main source set
 */
public class Pathos {
    public static final String MOD_ID = "gaspunk";
    public static final String PATHOS_ID = "pathos";

    public static void preInit(FMLPreInitializationEvent event) {
        // Because apparently EventBusSubscriber is broken in this source set
        if (isDevEnv()) {
            MinecraftForge.EVENT_BUS.register(ModItems.class);
            MinecraftForge.EVENT_BUS.register(CapabilitySickness.class);
        }
    }

    public static void init(FMLInitializationEvent event) {
        PacketHandler.initPackets();
    }

    public static boolean isDevEnv() {
        return (Boolean) Launch.blackboard.get("fml.deobfuscatedEnvironment");
    }
}
