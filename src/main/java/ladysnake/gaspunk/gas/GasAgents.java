package ladysnake.gaspunk.gas;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import ladysnake.gaspunk.GasPunk;
import ladysnake.gaspunk.api.IGasAgent;
import ladysnake.gaspunk.gas.agent.DamageAgent;
import ladysnake.gaspunk.gas.agent.GasAgent;
import ladysnake.gaspunk.gas.agent.LingeringAgent;
import ladysnake.gaspunk.gas.agent.PotionAgent;
import ladysnake.pathos.api.ISickness;
import net.minecraft.potion.Potion;
import net.minecraft.util.ResourceLocation;

import java.util.function.Supplier;

public class GasAgents {

    // not a registry to avoid adding too much network load, and because gases are already synchronized
    public static final BiMap<ResourceLocation, IGasAgent> AGENT_MAP = HashBiMap.create();

    public static IGasAgent createDamageAgent(String name, int maxDamage) {
        IGasAgent ret = name(new DamageAgent(maxDamage), name);
        AGENT_MAP.put(new ResourceLocation(GasPunk.MOD_ID, name), ret);
        return ret;
    }

    public static IGasAgent createPotionAgent(String name, int potionDuration, int potionAmplifier, Potion potion) {
        IGasAgent ret = name(new PotionAgent(potion, potionDuration, potionAmplifier), "potion");
        AGENT_MAP.put(new ResourceLocation(GasPunk.MOD_ID, name), ret);
        return ret;
    }

    public static IGasAgent createSicknessAgent(String name, boolean toxic, boolean ignoreBreath, ISickness sickness) {
        return createSicknessAgent(name, () -> new LingeringAgent(toxic, ignoreBreath, sickness), sickness);
    }

    public static IGasAgent createSicknessAgent(String name, Supplier<LingeringAgent> agentSupplier, ISickness sickness) {
        LingeringAgent agent = name(agentSupplier.get(), name);
        ResourceLocation id = new ResourceLocation(GasPunk.MOD_ID, name);
        AGENT_MAP.put(id, agent);
        return agent;
    }

    public static <T extends GasAgent> T name(T agent, String name) {
        agent.setUnlocalizedName("agent." + GasPunk.MOD_ID + "." + name);
        return agent;
    }

    public static ResourceLocation getId(IGasAgent agent) {
        return AGENT_MAP.inverse().get(agent);
    }

    public static IGasAgent getAgent(ResourceLocation id) {
        return AGENT_MAP.get(id);
    }
}
