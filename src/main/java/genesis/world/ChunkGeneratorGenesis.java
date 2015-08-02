package genesis.world;

import static net.minecraftforge.event.terraingen.PopulateChunkEvent.Populate.EventType.ANIMALS;
import static net.minecraftforge.event.terraingen.PopulateChunkEvent.Populate.EventType.DUNGEON;
import static net.minecraftforge.event.terraingen.PopulateChunkEvent.Populate.EventType.ICE;
import static net.minecraftforge.event.terraingen.PopulateChunkEvent.Populate.EventType.LAKE;
import static net.minecraftforge.event.terraingen.PopulateChunkEvent.Populate.EventType.LAVA;
import genesis.common.GenesisBlocks;
import genesis.world.gen.MapGenCavesGenesis;
import genesis.world.gen.MapGenRavineGenesis;
import genesis.world.gen.feature.WorldGenGenesisLakes;

import java.util.Random;

import net.minecraft.block.BlockFalling;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraft.world.ChunkCoordIntPair;
import net.minecraft.world.SpawnerAnimals;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.ChunkPrimer;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.ChunkProviderGenerate;
import net.minecraft.world.gen.ChunkProviderSettings;
import net.minecraft.world.gen.MapGenBase;
import net.minecraft.world.gen.NoiseGeneratorPerlin;
import net.minecraft.world.gen.feature.WorldGenDungeons;
import net.minecraft.world.gen.structure.MapGenMineshaft;
import net.minecraft.world.gen.structure.MapGenScatteredFeature;
import net.minecraft.world.gen.structure.MapGenStronghold;
import net.minecraft.world.gen.structure.MapGenVillage;
import net.minecraft.world.gen.structure.StructureOceanMonument;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.terraingen.ChunkProviderEvent;
import net.minecraftforge.event.terraingen.PopulateChunkEvent;
import net.minecraftforge.event.terraingen.TerrainGen;
import net.minecraftforge.fml.common.eventhandler.Event.Result;

public class ChunkGeneratorGenesis extends ChunkProviderGenerate
{
	private MapGenBase caveGenerator;
	private MapGenBase ravineGenerator;
	
	public ChunkGeneratorGenesis(World world, long seed, boolean mapFeaturesEnabled, String generatorOptions)
	{
		super(world, seed, mapFeaturesEnabled, generatorOptions);
		caveGenerator = new MapGenCavesGenesis();
        ravineGenerator = new MapGenRavineGenesis();
	}
	
	@Override
	public void populate(IChunkProvider chunkProvider, int chunkX, int chunkZ)
	{
		BlockFalling.fallInstantly = true;
		int blockX = chunkX * 16;
		int blockZ = chunkZ * 16;
		BlockPos pos = new BlockPos(blockX, 0, blockZ);
		BiomeGenBase biome = worldObj.getBiomeGenForCoords(pos.add(16, 0, 16));
		rand.setSeed(worldObj.getSeed());
		long i1 = rand.nextLong() / 2L * 2L + 1L;
		long j1 = rand.nextLong() / 2L * 2L + 1L;
		rand.setSeed((long) chunkX * i1 + (long) chunkZ * j1 ^ worldObj.getSeed());
		boolean flag = false;
		ChunkCoordIntPair coords = new ChunkCoordIntPair(chunkX, chunkZ);
		
		MinecraftForge.EVENT_BUS.post(new PopulateChunkEvent.Pre(chunkProvider, worldObj, rand, chunkX, chunkZ, flag));
		
        if (settings.useMineShafts && mapFeaturesEnabled)
        {
            mineshaftGenerator.func_175794_a(worldObj, rand, coords);
        }

        if (settings.useVillages && mapFeaturesEnabled)
        {
            flag = villageGenerator.func_175794_a(worldObj, rand, coords);
        }

        if (settings.useStrongholds && mapFeaturesEnabled)
        {
            strongholdGenerator.func_175794_a(worldObj, rand, coords);
        }

        if (settings.useTemples && mapFeaturesEnabled)
        {
            scatteredFeatureGenerator.func_175794_a(worldObj, rand, coords);
        }

        if (settings.useMonuments && mapFeaturesEnabled)
        {
            oceanMonumentGenerator.func_175794_a(worldObj, rand, coords);
        }

        if (biome != BiomeGenBase.desert && biome != BiomeGenBase.desertHills && settings.useWaterLakes && !flag && rand.nextInt(settings.waterLakeChance) == 0
            && TerrainGen.populate(chunkProvider, worldObj, rand, chunkX, chunkZ, flag, LAKE))
        {
            int x = rand.nextInt(16) + 8;
            int y = rand.nextInt(256);
            int z = rand.nextInt(16) + 8;
            (new WorldGenGenesisLakes(Blocks.water)).generate(worldObj, rand, pos.add(x, y, z));
        }

        if (TerrainGen.populate(chunkProvider, worldObj, rand, chunkX, chunkZ, flag, LAVA) && !flag && rand.nextInt(settings.lavaLakeChance / 10) == 0 && settings.useLavaLakes)
        {
        	int x = rand.nextInt(16) + 8;
        	int y = rand.nextInt(rand.nextInt(248) + 8);
        	int z = rand.nextInt(16) + 8;

            if (y < 63 || rand.nextInt(settings.lavaLakeChance / 8) == 0)
            {
                (new WorldGenGenesisLakes(GenesisBlocks.komatiitic_lava)).generate(worldObj, rand, pos.add(x, y, z));
            }
        }

        if (settings.useDungeons)
        {
            boolean doGen = TerrainGen.populate(chunkProvider, worldObj, rand, chunkX, chunkZ, flag, DUNGEON);
            for (int x = 0; doGen && x < settings.dungeonChance; ++x)
            {
            	int y = rand.nextInt(16) + 8;
            	int z = rand.nextInt(256);
                int j2 = rand.nextInt(16) + 8;
                (new WorldGenDungeons()).generate(worldObj, rand, pos.add(y, z, j2));
            }
        }

        biome.decorate(worldObj, rand, new BlockPos(blockX, 0, blockZ));
        if (TerrainGen.populate(chunkProvider, worldObj, rand, chunkX, chunkZ, flag, ANIMALS))
        {
        	SpawnerAnimals.performWorldGenSpawning(worldObj, biome, blockX + 8, blockZ + 8, 16, 16, rand);
        }
        
        pos = pos.add(8, 0, 8);

        boolean doGen = TerrainGen.populate(chunkProvider, worldObj, rand, chunkX, chunkZ, flag, ICE);
        
        for (int x = 0; doGen && x < 16; ++x)
        {
            for (int y = 0; y < 16; ++y)
            {
                BlockPos surface = worldObj.getPrecipitationHeight(pos.add(x, 0, y));
                BlockPos water = surface.down();

                if (worldObj.canBlockFreezeNoWater(water))
                {
                    worldObj.setBlockState(water, Blocks.ice.getDefaultState(), 2);
                }

                if (worldObj.canSnowAt(surface, true))
                {
                    worldObj.setBlockState(surface, Blocks.snow_layer.getDefaultState(), 2);
                }
            }
        }

        MinecraftForge.EVENT_BUS.post(new PopulateChunkEvent.Post(chunkProvider, worldObj, rand, chunkX, chunkZ, flag));

        BlockFalling.fallInstantly = false;
    }
	
	@Override
    public void setBlocksInChunk(int x, int y, ChunkPrimer primer)
    {
        biomesForGeneration = worldObj.getWorldChunkManager().getBiomesForGeneration(biomesForGeneration, x * 4 - 2, y * 4 - 2, 10, 10);
        func_147423_a(x * 4, 0, y * 4);

        for (int k = 0; k < 4; ++k)
        {
            int l = k * 5;
            int i1 = (k + 1) * 5;

            for (int j1 = 0; j1 < 4; ++j1)
            {
                int k1 = (l + j1) * 33;
                int l1 = (l + j1 + 1) * 33;
                int i2 = (i1 + j1) * 33;
                int j2 = (i1 + j1 + 1) * 33;

                for (int k2 = 0; k2 < 32; ++k2)
                {
                    double d0 = 0.125D;
                    double d1 = field_147434_q[k1 + k2];
                    double d2 = field_147434_q[l1 + k2];
                    double d3 = field_147434_q[i2 + k2];
                    double d4 = field_147434_q[j2 + k2];
                    double d5 = (field_147434_q[k1 + k2 + 1] - d1) * d0;
                    double d6 = (field_147434_q[l1 + k2 + 1] - d2) * d0;
                    double d7 = (field_147434_q[i2 + k2 + 1] - d3) * d0;
                    double d8 = (field_147434_q[j2 + k2 + 1] - d4) * d0;

                    for (int l2 = 0; l2 < 8; ++l2)
                    {
                        double d9 = 0.25D;
                        double d10 = d1;
                        double d11 = d2;
                        double d12 = (d3 - d1) * d9;
                        double d13 = (d4 - d2) * d9;

                        for (int i3 = 0; i3 < 4; ++i3)
                        {
                            double d14 = 0.25D;
                            double d16 = (d11 - d10) * d14;
                            double d15 = d10 - d16;

                            for (int j3 = 0; j3 < 4; ++j3)
                            {
                                if ((d15 += d16) > 0.0D)
                                {
                                    primer.setBlockState(k * 4 + i3, k2 * 8 + l2, j1 * 4 + j3, GenesisBlocks.granite.getDefaultState());
                                }
                                else if (k2 * 8 + l2 < settings.seaLevel)
                                {
                                    primer.setBlockState(k * 4 + i3, k2 * 8 + l2, j1 * 4 + j3, field_177476_s.getDefaultState());
                                }
                            }

                            d10 += d12;
                            d11 += d13;
                        }

                        d1 += d5;
                        d2 += d6;
                        d3 += d7;
                        d4 += d8;
                    }
                }
            }
        }
    }
}
