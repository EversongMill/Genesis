package genesis.world.biome;

import genesis.common.GenesisBlocks;
import genesis.world.biome.decorate.WorldGenArchaeomarasmius;
import genesis.world.biome.decorate.WorldGenGrowingPlant;
import genesis.world.biome.decorate.WorldGenGrowingPlant.GrowingPlantType;
import genesis.world.biome.decorate.WorldGenMossStages;
import genesis.world.biome.decorate.WorldGenPalaeoagaracites;
import genesis.world.biome.decorate.WorldGenPhlebopteris;
import genesis.world.biome.decorate.WorldGenRockBoulders;
import genesis.world.gen.feature.WorldGenTreeAraucarioxylon;

import java.util.Random;

import net.minecraft.world.World;
import net.minecraft.world.chunk.ChunkPrimer;
import net.minecraft.world.gen.feature.WorldGenerator;

public class BiomeGenAuxPlains extends BiomeGenBaseGenesis
{
	public BiomeGenAuxPlains(int id)
	{
		super(id);
		setBiomeName("Araucarioxylon Plains");
		setTemperatureRainfall(1.1F, 1.0F);
		
		theBiomeDecorator.grassPerChunk = 1;
		
		setSpawnablePlants(GenesisBlocks.programinis);
		
		addDecoration(new WorldGenArchaeomarasmius().setPatchSize(3).setCountPerChunk(5));
		addDecoration(new WorldGenPalaeoagaracites().setPatchSize(5).setCountPerChunk(10));
		addDecoration(new WorldGenGrowingPlant(GenesisBlocks.programinis).setPlantType(GrowingPlantType.NORMAL).setPatchSize(5).setCountPerChunk(7));
		addDecoration(new WorldGenRockBoulders().setCountPerChunk(5));
		addDecoration(new WorldGenMossStages().setCountPerChunk(30));
		
		addTree(new WorldGenTreeAraucarioxylon(25, 30, true).setTreeCountPerChunk(1));
	}
	
	@Override
	public WorldGenerator getRandomWorldGenForGrass(Random rand)
	{
		return new WorldGenPhlebopteris();
	}
	
	@Override
	public void generateBiomeTerrain(World world, Random rand, ChunkPrimer primer, int blockX, int blockZ, double d)
	{
		mossStages = new int[2];
		mossStages[0] = 1;
		mossStages[1] = 2;
		super.generateBiomeTerrain(world, rand, primer, blockX, blockZ, d);
	}
}