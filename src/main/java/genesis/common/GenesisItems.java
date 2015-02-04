package genesis.common;

import genesis.item.ItemFlintAndMarcasite;
import genesis.item.ItemGenesis;
import genesis.item.ItemGenesisFood;
import genesis.item.ItemGenesisMetadata;
import genesis.item.ItemGenesisSeedFood;
import genesis.item.ItemGenesisSeeds;
import genesis.metadata.EnumDung;
import genesis.metadata.EnumNodule;
import genesis.metadata.EnumPebble;
import genesis.util.Constants;
import net.minecraft.item.Item;
import net.minecraftforge.fml.common.registry.GameRegistry.ObjectHolder;

@ObjectHolder(Constants.MOD_ID)
public final class GenesisItems
{
	/* Materials */
	public static final ItemGenesis pebble = new ItemGenesisMetadata(EnumPebble.class).setUnlocalizedName("pebble");
	public static final ItemGenesis red_clay_ball = new ItemGenesis().setUnlocalizedName("redClay");
	public static final ItemGenesis octaedrite_shard = new ItemGenesis().setUnlocalizedName("octaedriteShard");
	public static final ItemGenesis red_clay_bowl = new ItemGenesis().setUnlocalizedName("bowlRedClay");
	public static final ItemGenesis ceramic_bowl = new ItemGenesis().setUnlocalizedName("bowlCeramic");
	public static final Item nodule = new ItemGenesisMetadata(EnumNodule.class).setUnlocalizedName("nodule");
	public static final ItemGenesis quartz = new ItemGenesis().setUnlocalizedName("quartz");
	public static final ItemGenesis zircon = new ItemGenesis().setUnlocalizedName("zircon");
	public static final ItemGenesis garnet = new ItemGenesis().setUnlocalizedName("garnet");
	public static final ItemGenesis manganese = new ItemGenesis().setUnlocalizedName("manganese");
	public static final ItemGenesis hematite = new ItemGenesis().setUnlocalizedName("hematite");
	public static final ItemGenesis malachite = new ItemGenesis().setUnlocalizedName("malachite");
	public static final ItemGenesis olivine = new ItemGenesis().setUnlocalizedName("olivine");
	public static final ItemGenesis dung = new ItemGenesisMetadata(EnumDung.class).setUnlocalizedName("dung");
	public static final ItemGenesis resin = new ItemGenesis().setUnlocalizedName("resin");
	public static final ItemGenesisSeeds calamites = new ItemGenesisSeeds().setUnlocalizedName("calamites");
	public static final ItemGenesis sphenophyllum_fiber = new ItemGenesis().setUnlocalizedName("sphenophyllumFiber");
	public static final ItemGenesis odontopteris_frond = new ItemGenesis().setUnlocalizedName("odontopterisFrond");
	public static final ItemGenesis prototaxites_flesh = new ItemGenesis().setUnlocalizedName("prototaxitesFlesh");
	public static final ItemGenesis tyrannosaurus_saliva = new ItemGenesis().setUnlocalizedName("tyrannosaurusSaliva");
	public static final ItemGenesis tyrannosaurus_tooth = new ItemGenesis().setUnlocalizedName("tyrannosaurusTooth");

	/* Food */
	public static final ItemGenesisFood aphthoroblattina = new ItemGenesisFood(1, 0.2F).setUnlocalizedName("aphthoroblattinaRaw");
	public static final ItemGenesisFood cooked_aphthoroblattina = new ItemGenesisFood(2, 0.8F).setUnlocalizedName("aphthoroblattinaCooked");
	public static final ItemGenesisFood climatius = new ItemGenesisFood(2, 0.4F).setUnlocalizedName("climatiusRaw");
	public static final ItemGenesisFood cooked_climatius = new ItemGenesisFood(5, 6.0F).setUnlocalizedName("climatiusCooked");
	public static final ItemGenesisFood eryops_leg = new ItemGenesisFood(2, 0.8F).setUnlocalizedName("eryopsLegRaw");
	public static final ItemGenesisFood cooked_eryops_leg = new ItemGenesisFood(5, 6.0F).setUnlocalizedName("eryopsLegCooked");
	public static final ItemGenesisFood tyrannosaurus = new ItemGenesisFood(4, 2.8F).setUnlocalizedName("tyrannosaurusRaw");
	public static final ItemGenesisFood cooked_tyrannosaurus = new ItemGenesisFood(16, 19.8F).setUnlocalizedName("tyrannosaurusCooked");
	public static final ItemGenesisSeedFood zingiberopsis_rhizome = new ItemGenesisSeedFood(2, 1.2F).setUnlocalizedName("zingiberopsisRhizome");
	public static final ItemGenesisSeedFood odontopteris_seeds = new ItemGenesisSeedFood(1, 0.8F).setUnlocalizedName("odontopterisSeeds");

	/* Misc */
	public static final ItemFlintAndMarcasite flint_and_marcasite = new ItemFlintAndMarcasite().setUnlocalizedName("flintAndMarcasite");

	public static void registerItems()
	{
		Genesis.proxy.registerItem(pebble, "pebble");
		Genesis.proxy.registerItem(red_clay_ball, "red_clay_ball");
		Genesis.proxy.registerItem(octaedrite_shard, "octaedrite_shard");
		Genesis.proxy.registerItem(red_clay_bowl, "red_clay_bowl");
		Genesis.proxy.registerItem(ceramic_bowl, "ceramic_bowl");
		Genesis.proxy.registerItem(quartz, "quartz");
		Genesis.proxy.registerItem(zircon, "zircon");
		Genesis.proxy.registerItem(garnet, "garnet");
		Genesis.proxy.registerItem(manganese, "manganese");
		Genesis.proxy.registerItem(hematite, "hematite");
		Genesis.proxy.registerItem(malachite, "malachite");
		Genesis.proxy.registerItem(olivine, "olivine");
		Genesis.proxy.registerItem(nodule, "nodule");
		Genesis.proxy.registerItem(dung, "dung");
		Genesis.proxy.registerItem(resin, "resin");
		Genesis.proxy.registerItem(calamites, "calamites");
		Genesis.proxy.registerItem(sphenophyllum_fiber, "sphenophyllum_fiber");
		Genesis.proxy.registerItem(odontopteris_frond, "odontopteris_frond");
		Genesis.proxy.registerItem(prototaxites_flesh, "prototaxites_flesh");
		Genesis.proxy.registerItem(tyrannosaurus_saliva, "tyrannosaurus_saliva");
		Genesis.proxy.registerItem(tyrannosaurus_tooth, "tyrannosaurus_tooth");
		Genesis.proxy.registerItem(aphthoroblattina, "aphthoroblattina");
		Genesis.proxy.registerItem(cooked_aphthoroblattina, "cooked_aphthoroblattina");
		Genesis.proxy.registerItem(climatius, "climatius");
		Genesis.proxy.registerItem(cooked_climatius, "cooked_climatius");
		Genesis.proxy.registerItem(eryops_leg, "eryops_leg");
		Genesis.proxy.registerItem(cooked_eryops_leg, "cooked_eryops_leg");
		Genesis.proxy.registerItem(tyrannosaurus, "tyrannosaurus");
		Genesis.proxy.registerItem(cooked_tyrannosaurus, "cooked_tyrannosaurus");
		Genesis.proxy.registerItem(zingiberopsis_rhizome, "zingiberopsis_rhizome");
		Genesis.proxy.registerItem(flint_and_marcasite, "flint_and_marcasite");
		Genesis.proxy.registerItem(odontopteris_seeds, "odontopteris_seeds");
	}
}
