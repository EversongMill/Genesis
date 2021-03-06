package genesis.combo;

import genesis.util.Constants;
import genesis.util.MiscUtils;
import genesis.util.Constants.Unlocalized;

import com.google.common.collect.ImmutableList;

import net.minecraft.block.Block;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.ItemStack;
import genesis.combo.variant.EnumDish;
import genesis.combo.variant.GenesisDye;
import genesis.combo.variant.IMetadata;
import genesis.combo.variant.MultiMetadataList;
import genesis.combo.variant.MultiMetadataList.MultiMetadata;
import genesis.item.*;

public class ItemsCeramicBowls extends VariantsOfTypesCombo<MultiMetadata>
{
	public enum EnumCeramicBowls implements IMetadata<EnumCeramicBowls>
	{
		BOWL(""),
		WATER_BOWL("water");
		
		protected String name;
		protected String unlocalizedName;
		
		EnumCeramicBowls(String name, String unlocalizedName)
		{
			this.name = name;
			this.unlocalizedName = unlocalizedName;
		}
		
		EnumCeramicBowls(String name)
		{
			this(name, name);
		}
		
		@Override
		public String getName()
		{
			return name;
		}
		
		@Override
		public String getUnlocalizedName()
		{
			return unlocalizedName;
		}
	}
	
	public static final MultiMetadataList ALL_VARIANTS =
			new MultiMetadataList(
					MiscUtils.iterable(EnumCeramicBowls.values()),
					GenesisDye.valueList(),
					MiscUtils.iterable(EnumDish.values()));
	
	public static final ObjectType<MultiMetadata, Block, ItemCeramicBowl> MAIN =
			ObjectType.createItem(MultiMetadata.class, "ceramic_bowl", Unlocalized.Section.MATERIAL + "ceramicBowl", ItemCeramicBowl.class)
					.setVariantFilter(MultiMetadataList.filter(EnumCeramicBowls.class));
	
	public static final ObjectType<MultiMetadata, Block, ItemDyeBowl> DYE =
			ObjectType.createItem(MultiMetadata.class, "dye", Unlocalized.Section.MATERIAL + "dye", ItemDyeBowl.class)
					.setVariantFilter(MultiMetadataList.filter(GenesisDye.class));
	
	public static final ObjectType<MultiMetadata, Block, ItemDish> DISH =
			ObjectType.createItem(MultiMetadata.class, "dish", Unlocalized.Section.FOOD + "dish", ItemDish.class)
					.setVariantFilter(MultiMetadataList.filter(EnumDish.class))
					.setResourceName("");
	
	public ItemsCeramicBowls()
	{
		super("ceramic_bowls", ImmutableList.of(MAIN, DYE, DISH),
				MultiMetadata.class, ALL_VARIANTS);
		
		setNames(Constants.MOD_ID, Unlocalized.PREFIX);
	}
	
	// Variants
	public MultiMetadata getVariant(EnumCeramicBowls variant)
	{
		return ALL_VARIANTS.getVariant(variant);
	}
	
	public MultiMetadata getVariant(GenesisDye variant)
	{
		return ALL_VARIANTS.getVariant(variant);
	}
	
	public MultiMetadata getVariant(EnumDyeColor variant)
	{
		return ALL_VARIANTS.getVariant(GenesisDye.get(variant));
	}
	
	public MultiMetadata getVariant(EnumDish variant)
	{
		return ALL_VARIANTS.getVariant(variant);
	}
	
	// getStack
	public ItemStack getStack(EnumCeramicBowls variant, int size)
	{
		return super.getStack(MAIN, getVariant(variant), size);
	}
	
	public ItemStack getStack(EnumCeramicBowls variant)
	{
		return getStack(variant, 1);
	}
	
	public ItemStack getStack(GenesisDye variant, int size)
	{
		return super.getStack(DYE, getVariant(variant), size);
	}
	
	public ItemStack getStack(GenesisDye variant)
	{
		return getStack(variant, 1);
	}
	
	public ItemStack getStack(EnumDyeColor color, int size)
	{
		return getStack(GenesisDye.get(color), size);
	}
	
	public ItemStack getStack(EnumDyeColor color)
	{
		return getStack(color, 1);
	}
	
	public ItemStack getStack(EnumDish variant, int size)
	{
		return getStack(DISH, getVariant(variant), size);
	}
	
	public ItemStack getStack(EnumDish variant)
	{
		return getStack(variant, 1);
	}
	
	// isStackOf
	public boolean isStackOf(ItemStack stack, EnumCeramicBowls variant)
	{
		return super.isStackOf(stack, getVariant(variant), MAIN);
	}
	
	public boolean isStackOf(ItemStack stack, GenesisDye variant)
	{
		return super.isStackOf(stack, getVariant(variant), DYE);
	}
	
	public boolean isStackOf(ItemStack stack, EnumDyeColor variant)
	{
		return super.isStackOf(stack, getVariant(variant), DYE);
	}
	
	public boolean isStackOf(ItemStack stack, EnumDish variant)
	{
		return super.isStackOf(stack, getVariant(variant), DISH);
	}
}
