package genesis.metadata;

import genesis.block.BlockGenesisLogs;
import genesis.block.BlockWattleFence;
import genesis.item.ItemWoodBillet;

import java.util.ArrayList;

import net.minecraft.block.Block;
import net.minecraft.block.BlockFence;
import net.minecraft.client.renderer.block.statemap.IStateMapper;
import net.minecraft.client.renderer.block.statemap.StateMap;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class TreeBlocks extends BlocksAndItemsWithVariantsOfTypes
{
	public static final ObjectType<BlockGenesisLogs> LOG = new ObjectType<BlockGenesisLogs>("log", BlockGenesisLogs.class, null);
	public static final ObjectType<ItemWoodBillet> BILLET = new ObjectType<ItemWoodBillet>("billet", null, ItemWoodBillet.class, EnumTree.NO_BILLET);
	public static final ObjectType<BlockWattleFence> WATTLE_FENCE = new ObjectType<BlockWattleFence>("wattle_fence", BlockWattleFence.class, null, EnumTree.NO_BILLET){
		@Override
		public IStateMapper getStateMapper(BlockWattleFence fenceBlock)
		{
			return new StateMap.Builder()
					.setProperty(fenceBlock.variantProp)
					.addPropertiesToIgnore(BlockFence.NORTH, BlockFence.EAST, BlockFence.SOUTH, BlockFence.WEST)
					.setBuilderSuffix("_" + WATTLE_FENCE.getName())
					.build();
		}
	};
	
	public TreeBlocks()
	{
		super(new ObjectType[] {LOG, BILLET, WATTLE_FENCE}, EnumTree.values());
		
		for (IMetadata variant : getValidVariants(BILLET))
		{
			ItemStack logStack = getStack(LOG, variant, 1);
			ItemStack billetStack = getStack(BILLET, variant, 4);
			
			GameRegistry.addShapelessRecipe(billetStack, logStack);
		}
	}
}
