package genesis.world;

import genesis.common.GenesisDimensions;
import genesis.util.Constants;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraft.world.WorldSavedData;
import net.minecraft.world.storage.MapStorage;
import net.minecraftforge.common.MinecraftForge;

public class GenesisWorldData extends WorldSavedData
{
	public static void register()
	{
		MinecraftForge.EVENT_BUS.register(new EventHandler());
	}
	
	private static class EventHandler
	{
		
	}
	
	public static GenesisWorldData get(World world)
	{
		if (!GenesisDimensions.isGenesis(world))
			return null;
		
		MapStorage storage = world.getPerWorldStorage();
		GenesisWorldData data = (GenesisWorldData) storage.getOrLoadData(GenesisWorldData.class, Constants.MOD_ID);
		
		if (data == null)
		{
			data = new GenesisWorldData(Constants.MOD_ID);
			storage.setData(Constants.MOD_ID, data);
		}
		
		return data;
	}
	
	protected long time = 0;
	
	public GenesisWorldData(String name)
	{
		super(name);
	}
	
	public long getTime()
	{
		return time;
	}
	
	public void setTime(long time)
	{
		this.time = time;
		markDirty();
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbt)
	{
		time = nbt.getLong("time");
	}
	
	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound nbt)
	{
		nbt.setLong("time", time);
		return nbt;
	}
}
