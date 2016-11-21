package genesis.world.iworldgenerators;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import genesis.block.tileentity.TileEntityStorageBox;
import genesis.common.GenesisBiomes;
import genesis.common.GenesisBlocks;
import genesis.common.GenesisLoot;
import genesis.world.iworldgenerators.WorldGenStructureHelper.StructureType;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;

public class WorldGenHutNBT extends WorldGenStructureBase
{
	protected int rarity = 10;
	
	@Override
	public List<Biome> getAllowedBiomes()
	{
		List<Biome> biomes = new ArrayList<Biome>();
		
		biomes.add(GenesisBiomes.rainforest);
		biomes.add(GenesisBiomes.rainforestHills);
		biomes.add(GenesisBiomes.rainforestIslands);
		biomes.add(GenesisBiomes.rainforestM);
		
		return biomes;
	}
	
	@Override
	public List<Block> getSurfaceBlocks()
	{
		List<Block> blocks = new ArrayList<Block>();
		
		blocks.add(Blocks.DIRT);
		blocks.add(GenesisBlocks.MOSS);
		
		return blocks;
	}
	
	@Override
	public GenerationType getGenerationType()
	{
		return GenerationType.FINDGROUND;
	}
	
	@Override
	protected boolean doGenerate(World world, Random rand, BlockPos pos)
	{
		boolean generated = false;
		BlockPos curPos = pos;
		
		StructureType hut = StructureType.HUT1;
		
		if (!world.isAirBlock(curPos))
			curPos = curPos.up();
		
		/*
		generated = this.checkSurface(
				world, 
				curPos.add(MathHelper.abs_max(hut.getBounds().xCoord, hut.getBounds().zCoord), 0, MathHelper.abs_max(hut.getBounds().xCoord, hut.getBounds().zCoord)), 
				(int)(MathHelper.abs_max(hut.getBounds().xCoord, hut.getBounds().zCoord) * 0.45D), 
				(int)(hut.getBounds().yCoord * 0.7D));
		*/
		
		EnumFacing offset = hut.getOffse();
		
		generated = WorldGenStructureHelper.spawnStructure(
				world, 
				((offset == null)? curPos : curPos.offset(offset)), 
				hut,
				hut.getMirror()[rand.nextInt(hut.getMirror().length)], 
				hut.getRotation()[rand.nextInt(hut.getRotation().length)]);
		
		if (generated)
		{
			BlockPos storagePos = this.findBlockInArea(world, curPos, 8, 5, GenesisBlocks.STORAGE_BOX.getDefaultState(), true);
			
			if (storagePos!= null)
			{
				TileEntity te = world.getTileEntity(storagePos);
				
				if (te instanceof TileEntityStorageBox)
				{
					TileEntityStorageBox storageBox = (TileEntityStorageBox)te;
					storageBox.setLootTable(GenesisLoot.STORAGE_BOX_HUT, rand.nextLong());
				}
			}
		}
		
		return generated;
	}
}
