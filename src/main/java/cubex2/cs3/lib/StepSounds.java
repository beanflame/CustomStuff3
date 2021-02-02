package cubex2.cs3.lib;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import net.minecraft.block.Block;

public class StepSounds
{
    private static BiMap<String, Block.SoundType> stepSoundMap = HashBiMap.create();

    public static Block.SoundType getStepSound(String name)
    {
        if (name == null)
            return null;

        Block.SoundType stepSound = null;

        if (stepSoundMap.containsKey(name))
        {
            stepSound = stepSoundMap.get(name);
        }

        return stepSound;
    }

    public static String getStepSoundName(Block.SoundType stepSound)
    {
        if (stepSound == null)
            return null;

        String name = null;

        if (stepSoundMap.inverse().containsKey(stepSound))
        {
            name = stepSoundMap.inverse().get(stepSound);
        }

        return name;
    }

    public static Block.SoundType[] getAllSounds()
    {
        return stepSoundMap.values().toArray(new Block.SoundType[stepSoundMap.values().size()]);
    }

    static
    {
        stepSoundMap.put("anvil", Block.soundTypeAnvil);
        stepSoundMap.put("cloth", Block.soundTypeCloth);
        stepSoundMap.put("glass", Block.soundTypeGlass);
        stepSoundMap.put("grass", Block.soundTypeGrass);
        stepSoundMap.put("gravel", Block.soundTypeGravel);
        stepSoundMap.put("ladder", Block.soundTypeLadder);
        stepSoundMap.put("metal", Block.soundTypeMetal);
        stepSoundMap.put("sand", Block.soundTypeSand);
        stepSoundMap.put("stone", Block.soundTypeStone);
        stepSoundMap.put("wood", Block.soundTypeWood);
    }
}
