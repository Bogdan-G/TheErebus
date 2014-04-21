package erebus.world.biomes;
import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.entity.EntityLiving;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.biome.SpawnListEntry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import erebus.ModBiomes;
import erebus.ModBlocks;
import erebus.core.helper.TimeMeasurement;
import erebus.world.biomes.decorators.BiomeDecoratorErebus;
import erebus.world.loot.IWeightProvider;

// @formatter:off
public abstract class BiomeBaseErebus extends BiomeGenBase implements IWeightProvider{
	private final BiomeDecoratorErebus decorator;
	private short biomeWeight;
	private int grassColor,foliageColor;
	private short[] fogColorRGB = new short[]{ 255,255,255 };
	
	public BiomeBaseErebus(int biomeID, BiomeDecoratorErebus decorator){
		super(biomeID);
		this.decorator = decorator;
		
		if (getClass().getGenericSuperclass() == BiomeBaseErebus.class){
			ModBiomes.biomeList.add(this);
		}
		
		setDisableRain();
		
		spawnableMonsterList.clear();
		spawnableCreatureList.clear();
		spawnableWaterCreatureList.clear();
		spawnableCaveCreatureList.clear();
	}
	
	protected final BiomeBaseErebus setColors(int grassAndFoliage){
		setColors(grassAndFoliage,grassAndFoliage);
		return this;
	}
	
	protected final BiomeBaseErebus setColors(int grass, int foliage){
		setColor(grass);
		func_76733_a(grass);
		grassColor = grass;
		foliageColor = foliage;
		return this;
	}
	
	protected final BiomeBaseErebus setFog(int red, int green, int blue){
		this.fogColorRGB = new short[]{ (short)red, (short)green, (short)blue };
		return this;
	}
	
	protected final BiomeBaseErebus setWeight(int weight){
		this.biomeWeight = (short)weight;
		return this;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public final int getBiomeGrassColor(){
		return grassColor;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public final int getBiomeFoliageColor(){
		return foliageColor;
	}

	@SideOnly(Side.CLIENT)
	public final short[] getFogRGB(){
		return fogColorRGB;
	}
	
	@Override
	public final short getWeight(){
		return biomeWeight;
	}

	@Override
	public void decorate(World world, Random rand, int x, int z){
		String id = getClass().getSimpleName();
		TimeMeasurement.start(id);
		
		decorator.decorate(world,rand,x,z);
		
		TimeMeasurement.finish(id);
	}
	
	public byte placeCaveBlock(byte blockID, int x, int y, int z, Random rand){
		return blockID == (byte)ModBlocks.umberstone.blockID || blockID == topBlock || blockID == fillerBlock || blockID == Block.sandStone.blockID ? 0 : blockID;
	}
	
	/**
	 * Every time a biome is generated, this method is called to specify sub biome to generate inside the biome.
	 * @param randomValue value between 0 and 100 (both inclusive) generated by GenLayer
	 * @return sub biome to generate, or null
	 */
	public BiomeBaseErebus getRandomSubBiome(int randomValue){
		return null;
	}
	
	protected static class SpawnEntry extends SpawnListEntry{
		protected SpawnEntry(Class<? extends EntityLiving> mobClass, int weight, int minGroupCount, int maxGroupCount){
			super(mobClass,weight,minGroupCount,maxGroupCount);
		}
	}
}
// @formatter:on
