package genesis.item;

import java.util.List;

import genesis.combo.*;
import genesis.combo.ItemsCeramicBowls.*;
import genesis.combo.VariantsOfTypesCombo.ObjectType;
import genesis.combo.variant.EnumDish;
import genesis.combo.variant.IFood;
import genesis.combo.variant.MultiMetadataList.MultiMetadata;
import genesis.common.GenesisItems;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemDish extends ItemGenesisEdible<MultiMetadata>
{
	public ItemDish(VariantsOfTypesCombo<MultiMetadata> owner, ObjectType<Block, ? extends ItemDish> type, List<MultiMetadata> variants, Class<MultiMetadata> variantClass)
	{
		super(owner, type, variants, variantClass);
		
		setHasSubtypes(true);
	}
	
	@Override
	protected IFood getFoodType(ItemStack stack)
	{
		return (EnumDish) owner.getVariant(stack).getOriginal();
	}
	
	@Override
	public ItemStack onItemUseFinish(ItemStack stack, World world, EntityPlayer player)
	{
		ItemStack out = super.onItemUseFinish(stack, world, player);
		
		ItemStack bowl = GenesisItems.bowls.getStack(EnumCeramicBowls.BOWL);
		
		if (stack.stackSize == 0)
		{
			for (int i = 0; i < player.inventory.getSizeInventory(); i++)
			{
				if (player.inventory.getStackInSlot(i) == stack)
				{
					return bowl;
				}
			}
		}
		else if (!player.inventory.addItemStackToInventory(bowl))
		{
			player.dropItem(bowl, false, true);
		}
		
		return out;
	}
}
