package genesis.common;

import genesis.entity.extendedproperties.*;
import genesis.portal.GenesisPortal;
import genesis.stats.GenesisAchievements;
import genesis.util.Constants;
import genesis.world.*;

import net.minecraft.entity.*;
import net.minecraft.entity.player.*;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.play.server.*;
import net.minecraft.potion.PotionEffect;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.management.PlayerList;
import net.minecraft.world.DimensionType;
import net.minecraft.world.Teleporter;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraftforge.common.DimensionManager;
import net.minecraftforge.fml.common.FMLCommonHandler;

public class GenesisDimensions
{
	public static final NBTEntityProperty STORED_PLAYERS = new NBTEntityProperty("dimensionPlayers", new NBTTagCompound(), true);
	public static final String GENESIS_PLAYER_DATA = "genesis";
	public static final String OTHER_PLAYER_DATA = "other";
	
	public static final DimensionType GENESIS_TYPE = DimensionType.register(Constants.MOD_ID, "_genesis",
			GenesisConfig.genesisDimId, WorldProviderGenesis.class, false);
	
	public static void register()
	{
		DimensionManager.registerDimension(GenesisConfig.genesisDimId, GENESIS_TYPE);
		
		GenesisEntityData.registerProperty(EntityPlayerMP.class, STORED_PLAYERS);
	}
	
	public static boolean isGenesis(World world)
	{
		return world.provider.getDimension() == GenesisConfig.genesisDimId;
	}
	
	public static TeleporterGenesis getTeleporter(WorldServer world)
	{
		for (Teleporter teleporter : world.customTeleporters)
		{
			if (teleporter instanceof TeleporterGenesis)
			{
				return (TeleporterGenesis) teleporter;
			}
		}
		
		return new TeleporterGenesis(world);
	}
	
	public static boolean teleportToDimension(Entity entity, GenesisPortal portal, int id, boolean force)
	{
		if (!entity.worldObj.isRemote)
		{
			double motionX = entity.motionX;
			double motionY = entity.motionY;
			double motionZ = entity.motionZ;
			
			EntityPlayerMP player = null;
			
			if (entity instanceof EntityPlayerMP)
			{
				player = (EntityPlayerMP) entity;
			}
			
			MinecraftServer server = FMLCommonHandler.instance().getMinecraftServerInstance();
			PlayerList manager = server.getPlayerList();
			
			WorldServer newWorld = server.worldServerForDimension(id);
			
			TeleporterGenesis teleporter = getTeleporter(newWorld);
			teleporter.setOriginatingPortal(portal);
			
			boolean teleported = false;
			
			if (player != null)
			{
				NBTTagCompound dimensionPlayers = null;
				NBTTagCompound restoreData = null;
				
				if (!force && !player.capabilities.isCreativeMode)
				{
					String storingName = null;
					String restoreName = null;
					
					// Set the names that will be loaded from and saved to in the extended entity property.
					if (id == GenesisConfig.genesisDimId)
					{
						storingName = OTHER_PLAYER_DATA;
						restoreName = GENESIS_PLAYER_DATA;
					}
					else
					{
						storingName = GENESIS_PLAYER_DATA;
						restoreName = OTHER_PLAYER_DATA;
					}
					
					// Get the stored players from both sides.
					dimensionPlayers = GenesisEntityData.getValue(player, STORED_PLAYERS);
					
					// Get the player to restore.
					restoreData = dimensionPlayers.getCompoundTag(restoreName);	
					dimensionPlayers.removeTag(restoreName);	// Remove the stored player so that no duplication occurs.
					
					// Write the current player.
					NBTTagCompound storingData = new NBTTagCompound();
					player.writeToNBT(storingData);	// Write the current player to the compound.
					storingData.getCompoundTag(GenesisEntityData.COMPOUND_KEY).removeTag(STORED_PLAYERS.getName());
					
					// Save the current player to the data.
					dimensionPlayers.setTag(storingName, storingData);
				}
				
				// Transfer the original player.
				manager.transferPlayerToDimension(player, id, teleporter);
				
				if (dimensionPlayers != null)
				{
					// Save player position.
					double x = player.posX;
					double y = player.posY;
					double z = player.posZ;
					float yaw = player.rotationYaw;
					float pitch = player.rotationPitch;
					
					// Create a new player to reset all their stats and inventory.
					EntityPlayerMP newPlayer = manager.recreatePlayerEntity(player, id, false);
					newPlayer.playerNetServerHandler.playerEntity = newPlayer;	// recreate doesn't set this.
					
					
					if (restoreData != null)
					{	// Restore the player's inventory from data saved when the player traveled from the dimension previously.
						newPlayer.readFromNBT(restoreData);
						newPlayer.dimension = id;
						newPlayer.capabilities.isFlying = player.capabilities.isFlying;	// Will be sent to client by setGameType.
						newPlayer.interactionManager.setGameType(player.interactionManager.getGameType());
					}
					
					newPlayer.inventory.currentItem = player.inventory.currentItem;	// Keep the current selected hotbar item.
					manager.syncPlayerInventory(newPlayer);	// Send the player's inventory, stats and current item.
					
					// Restore other relevant stuff.
					newPlayer.fallDistance = player.fallDistance;
					
					// Save the old player's data for restoration later.
					GenesisEntityData.setValue(newPlayer, STORED_PLAYERS, dimensionPlayers);
					
					// Restore the player to the position of the portal.
					newPlayer.playerNetServerHandler.setPlayerLocation(x, y, z, yaw, pitch);
					newPlayer.playerNetServerHandler.sendPacket(new SPacketEntityVelocity(player));
					
					// Send the player's current potion effects.
					for (PotionEffect effect : newPlayer.getActivePotionEffects())
						newPlayer.playerNetServerHandler.sendPacket(new SPacketEntityEffect(newPlayer.getEntityId(), effect));
					
					entity = player = newPlayer;
				}
				
				teleported = true;
			}
			else
			{	// Is broken, and we probably don't want vanilla entities entering the dimension anyway.
				// TODO: Should maybe get this working for Genesis -> Overworld, and maybe a server option?
				//manager.transferEntityToWorld(entity, entity.dimension, oldWorld, newWorld, teleporter);
			}
			
			//GenesisSounds.playMovingEntitySound(new ResourceLocation(Constants.ASSETS_PREFIX + "portal.enter"), false,
			//		entity, 0.9F + oldWorld.rand.nextFloat() * 0.2F, 0.8F + oldWorld.rand.nextFloat() * 0.4F);
			
			if (teleported)
			{
				entity.timeUntilPortal = GenesisPortal.COOLDOWN;
			}
			
			entity.motionX = motionX;
			entity.motionY = motionY;
			entity.motionZ = motionZ;
			
			if (player != null)
			{
				if (id == GenesisConfig.genesisDimId)
					player.addStat(GenesisAchievements.enterGenesis, 1);
			}
			
			return teleported;
		}
		
		return true;
	}
}
