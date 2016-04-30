package genesis.world.biome.decorate;

import java.util.Random;

import genesis.block.BlockGrowingPlant;
import genesis.util.WorldBlockMatcher;
import genesis.util.WorldUtils;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class WorldGenGrowingPlant extends WorldGenDecorationBase
{
	private BlockGrowingPlant plant;
	private boolean nextToWater = false;
	private int waterRadius = 4;
	private int waterHeight = 2;
	
	public WorldGenGrowingPlant(BlockGrowingPlant plant)
	{
		super(WorldBlockMatcher.STANDARD_AIR, WorldBlockMatcher.TRUE);
		
		this.plant = plant;
	}
	
	public WorldGenGrowingPlant setWaterProximity(int radius, int height)
	{
		this.waterRadius = radius;
		this.waterHeight = height;
		return this;
	}
	
	public WorldGenGrowingPlant setNextToWater(boolean nextToWater)
	{
		this.nextToWater = nextToWater;
		return this;
	}
	
	@Override
	public boolean place(World world, Random random, BlockPos pos)
	{
		if (nextToWater && !WorldUtils.waterInRange(world, pos.down(), waterRadius, waterRadius, waterHeight))
			return false;
		
		return plant.placeRandomAgePlant(world, pos, random, shouldNotify() ? 3 : 2);
	}
}
