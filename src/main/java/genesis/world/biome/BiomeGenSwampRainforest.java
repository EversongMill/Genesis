package genesis.world.biome;

import genesis.common.GenesisBlocks;
import genesis.metadata.EnumTree;
import genesis.world.biome.decorate.BiomeDecoratorGenesis;
import genesis.world.biome.decorate.WorldGenGrowingPlant;
import genesis.world.biome.decorate.WorldGenOdontopteris;
import genesis.world.biome.decorate.WorldGenUnderWaterPatch;
import genesis.world.biome.decorate.WorldGenZygopteris;
import genesis.world.gen.feature.WorldGenRottenLog;
import genesis.world.gen.feature.WorldGenTreeCordaites;
import genesis.world.gen.feature.WorldGenTreeLepidodendron;
import genesis.world.gen.feature.WorldGenTreePsaronius;
import genesis.world.gen.feature.WorldGenTreeSigillaria;

import java.util.Random;

import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.chunk.ChunkPrimer;
import net.minecraft.world.gen.feature.WorldGenerator;

public class BiomeGenSwampRainforest extends BiomeGenBaseGenesis
{
	public int totalTreesPerChunk = 1100;
	
	public BiomeGenSwampRainforest(int id)
	{
		super(id);
		this.biomeName = "Swamp Rainforest";
		this.rainfall = 1.0F;
		this.temperature = 0.95F;
		this.minHeight = 0.05F;
		this.maxHeight = 0.1F;
		this.theBiomeDecorator.treesPerChunk = 0;
		this.theBiomeDecorator.grassPerChunk = 6;
		
		((BiomeDecoratorGenesis) this.theBiomeDecorator).generateDefaultTrees = false;
		
		this.waterColorMultiplier = 0x725113;
		setColor(522674);
		
		((BiomeDecoratorGenesis) this.theBiomeDecorator).decorations.add(new WorldGenOdontopteris().setCountPerChunk(60));
		((BiomeDecoratorGenesis) this.theBiomeDecorator).decorations.add(new WorldGenGrowingPlant(GenesisBlocks.sphenophyllum).setPatchSize(10).setCountPerChunk(650));
		((BiomeDecoratorGenesis) this.theBiomeDecorator).decorations.add(new WorldGenGrowingPlant(GenesisBlocks.calamites).setPatchSize(10).setCountPerChunk(650));
		((BiomeDecoratorGenesis) this.theBiomeDecorator).decorations.add(new WorldGenUnderWaterPatch(GenesisBlocks.peat.getDefaultState()).setCountPerChunk(900));
		
		((BiomeDecoratorGenesis) this.theBiomeDecorator).trees.add(new WorldGenTreeLepidodendron(10, 14, true).setTreeCountPerChunk(MathHelper.ceiling_float_int((float)totalTreesPerChunk * 0.3F)));
		((BiomeDecoratorGenesis) this.theBiomeDecorator).trees.add(new WorldGenTreeSigillaria(8, 11, true).setTreeCountPerChunk(MathHelper.ceiling_float_int((float)totalTreesPerChunk * 0.25F)));
		((BiomeDecoratorGenesis) this.theBiomeDecorator).trees.add(new WorldGenTreeCordaites(10, 14, true).setTreeCountPerChunk(MathHelper.ceiling_float_int((float)totalTreesPerChunk * 0.35F)));
		((BiomeDecoratorGenesis) this.theBiomeDecorator).trees.add(new WorldGenTreePsaronius(5, 6, true).setTreeCountPerChunk(MathHelper.ceiling_float_int((float)totalTreesPerChunk * 0.1F)));
		
		((BiomeDecoratorGenesis) this.theBiomeDecorator).trees.add(new WorldGenRottenLog(3, 6, EnumTree.LEPIDODENDRON, true).setTreeCountPerChunk(120));
		((BiomeDecoratorGenesis) this.theBiomeDecorator).trees.add(new WorldGenRottenLog(3, 6, EnumTree.SIGILLARIA, true).setTreeCountPerChunk(120));
		((BiomeDecoratorGenesis) this.theBiomeDecorator).trees.add(new WorldGenRottenLog(3, 6, EnumTree.CORDAITES, true).setTreeCountPerChunk(120));
	}
	
	@Override
	public WorldGenerator getRandomWorldGenForGrass(Random rand)
	{
		return new WorldGenZygopteris();
	}
	
	@Override
	public void genTerrainBlocks(World world, Random rand, ChunkPrimer p_180622_3_, int p_180622_4_, int p_180622_5_, double p_180622_6_)
	{
		double d1 = field_180281_af.func_151601_a((double)p_180622_4_ * 0.25D, (double)p_180622_5_ * 0.25D);
		
		if (d1 > 0.0D)
		{
			int k = p_180622_4_ & 15;
			int l = p_180622_5_ & 15;
			
			for (int i1 = 255; i1 >= 0; --i1)
			{
				if (p_180622_3_.getBlockState(l, i1, k).getBlock().getMaterial() != Material.air)
				{
					if (i1 == 62 && p_180622_3_.getBlockState(l, i1, k).getBlock() != Blocks.water)
					{
						p_180622_3_.setBlockState(l, i1, k, Blocks.water.getDefaultState());
					}
					
					break;
				}
			}
		}
		
		this.generateBiomeTerrain(world, rand, p_180622_3_, p_180622_4_, p_180622_5_, p_180622_6_);
    }
}