package io.github.thebusybiscuit.slimefun4.api.recipes;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.function.BiConsumer;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;

import org.bukkit.ChatColor;
import org.bukkit.Keyed;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import io.github.bakedlibs.dough.items.CustomItemStack;
import io.github.bakedlibs.dough.recipes.MinecraftRecipe;
import io.github.thebusybiscuit.slimefun4.api.MinecraftVersion;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.core.multiblocks.MultiBlockMachine;
import io.github.thebusybiscuit.slimefun4.implementation.Slimefun;
import io.github.thebusybiscuit.slimefun4.implementation.SlimefunItems;
import io.github.thebusybiscuit.slimefun4.implementation.items.altar.AltarRecipe;
import io.github.thebusybiscuit.slimefun4.implementation.items.altar.AncientAltar;

public class RecipeType implements Keyed {

    public static final RecipeType MULTIBLOCK = new RecipeType(new NamespacedKey(Slimefun.instance(), "multiblock"), new CustomItemStack(Material.BRICKS, "&bMultiBlock", "", "&a&oBuild it in the World"));
    public static final RecipeType ARMOR_FORGE = new RecipeType(new NamespacedKey(Slimefun.instance(), "armor_forge"), SlimefunItems.ARMOR_FORGE, "", "&a&oCraft it in an Armor Forge");
    public static final RecipeType GRIND_STONE = new RecipeType(new NamespacedKey(Slimefun.instance(), "grind_stone"), SlimefunItems.GRIND_STONE, RecipeShape.SUBSET, "", "&a&oGrind it using the Grind Stone");
    public static final RecipeType SMELTERY = new RecipeType(new NamespacedKey(Slimefun.instance(), "smeltery"), SlimefunItems.SMELTERY, RecipeShape.SUBSET, "", "&a&oSmelt it using a Smeltery");
    public static final RecipeType ORE_CRUSHER = new RecipeType(new NamespacedKey(Slimefun.instance(), "ore_crusher"), SlimefunItems.ORE_CRUSHER, RecipeShape.SUBSET, "", "&a&oCrush it using the Ore Crusher");
    public static final RecipeType GOLD_PAN = new RecipeType(new NamespacedKey(Slimefun.instance(), "gold_pan"), SlimefunItems.GOLD_PAN, "", "&a&oUse a Gold Pan on Gravel to obtain this Item");
    public static final RecipeType COMPRESSOR = new RecipeType(new NamespacedKey(Slimefun.instance(), "compressor"), SlimefunItems.COMPRESSOR, RecipeShape.SUBSET, "", "&a&oCompress it using the Compressor");
    public static final RecipeType PRESSURE_CHAMBER = new RecipeType(new NamespacedKey(Slimefun.instance(), "pressure_chamber"), SlimefunItems.PRESSURE_CHAMBER, RecipeShape.SUBSET, "", "&a&oCompress it using the Pressure Chamber");
    public static final RecipeType MAGIC_WORKBENCH = new RecipeType(new NamespacedKey(Slimefun.instance(), "magic_workbench"), SlimefunItems.MAGIC_WORKBENCH, "", "&a&oCraft it in a Magic Workbench");
    public static final RecipeType ORE_WASHER = new RecipeType(new NamespacedKey(Slimefun.instance(), "ore_washer"), SlimefunItems.ORE_WASHER, RecipeShape.SUBSET, "", "&a&oWash it in an Ore Washer");
    public static final RecipeType ENHANCED_CRAFTING_TABLE = new RecipeType(new NamespacedKey(Slimefun.instance(), "enhanced_crafting_table"), SlimefunItems.ENHANCED_CRAFTING_TABLE, "", "&a&oA regular Crafting Table cannot", "&a&ohold this massive Amount of Power...");
    public static final RecipeType JUICER = new RecipeType(new NamespacedKey(Slimefun.instance(), "juicer"), SlimefunItems.JUICER, RecipeShape.SUBSET, "", "&a&oUsed for Juice Creation");

    public static final RecipeType ANCIENT_ALTAR = new RecipeType(new NamespacedKey(Slimefun.instance(), "ancient_altar"), SlimefunItems.ANCIENT_ALTAR, (recipe, output) -> {
        AltarRecipe altarRecipe = new AltarRecipe(Arrays.asList(recipe), output);
        AncientAltar altar = ((AncientAltar) SlimefunItems.ANCIENT_ALTAR.getItem());
        altar.getRecipes().add(altarRecipe);
    });

    public static final RecipeType MOB_DROP = new RecipeType(new NamespacedKey(Slimefun.instance(), "mob_drop"), new CustomItemStack(Material.IRON_SWORD, "&bMob Drop"), RecipeType::registerMobDrop, "", "&rKill the specified Mob to obtain this Item");
    public static final RecipeType BARTER_DROP = new RecipeType(new NamespacedKey(Slimefun.instance(), "barter_drop"), new CustomItemStack(Material.GOLD_INGOT, "&bBarter Drop"), RecipeType::registerBarterDrop, "&aBarter with piglins for a chance", "&ato obtain this item");

    public static final RecipeType HEATED_PRESSURE_CHAMBER = new RecipeType(new NamespacedKey(Slimefun.instance(), "heated_pressure_chamber"), SlimefunItems.HEATED_PRESSURE_CHAMBER);
    public static final RecipeType FOOD_FABRICATOR = new RecipeType(new NamespacedKey(Slimefun.instance(), "food_fabricator"), SlimefunItems.FOOD_FABRICATOR);
    public static final RecipeType FOOD_COMPOSTER = new RecipeType(new NamespacedKey(Slimefun.instance(), "food_composter"), SlimefunItems.FOOD_COMPOSTER);
    public static final RecipeType FREEZER = new RecipeType(new NamespacedKey(Slimefun.instance(), "freezer"), SlimefunItems.FREEZER);
    public static final RecipeType REFINERY = new RecipeType(new NamespacedKey(Slimefun.instance(), "refinery"), SlimefunItems.REFINERY);

    public static final RecipeType GEO_MINER = new RecipeType(new NamespacedKey(Slimefun.instance(), "geo_miner"), SlimefunItems.GEO_MINER);
    public static final RecipeType NUCLEAR_REACTOR = new RecipeType(new NamespacedKey(Slimefun.instance(), "nuclear_reactor"), SlimefunItems.NUCLEAR_REACTOR);

    public static final RecipeType NULL = new RecipeType();

    private final NamespacedKey key;
    private final ItemStack item;
    private final RecipeShape defaultShape;

    public RecipeType(NamespacedKey key, ItemStack item, RecipeShape defaultShape) {
        this.key = key;
        this.item = item;
        this.defaultShape = defaultShape;
    }

    public RecipeType(NamespacedKey key, ItemStack item) {
        this(key, item, RecipeShape.TRANSLATED);
    }

    private RecipeType() {
        this(new NamespacedKey(Slimefun.instance(), "null"), null);
    }

    @Deprecated
    public RecipeType(ItemStack item, String id, RecipeShape defaultShape) {
        this.item = item;
        this.defaultShape = defaultShape;

        if (id.length() > 0) {
            this.key = new NamespacedKey(Slimefun.instance(), id.toLowerCase(Locale.ROOT));
        } else {
            this.key = new NamespacedKey(Slimefun.instance(), "unknown");
        }
    }

    @Deprecated
    public RecipeType(ItemStack item, String id) {
        this(item, id, RecipeShape.TRANSLATED);
    }

    public RecipeType(NamespacedKey key, SlimefunItemStack slimefunItem, RecipeShape shape, String... lore) {
        this(key, new CustomItemStack(slimefunItem, null, lore), shape);
    }

    public RecipeType(NamespacedKey key, SlimefunItemStack slimefunItem, String... lore) {
        this(key, new CustomItemStack(slimefunItem, null, lore));
    }

    @Deprecated
    public RecipeType(NamespacedKey key, ItemStack item, BiConsumer<ItemStack[], ItemStack> callback, String... lore) {
        this(key, new CustomItemStack(item, null, lore));
    }

    public RecipeType(MinecraftRecipe<?> recipe) {
        this(
            NamespacedKey.minecraft(recipe.getRecipeClass().getSimpleName().toLowerCase(Locale.ROOT).replace("recipe", "")),
            new ItemStack(recipe.getMachine())
        );
    }

    /**
     * This method is deprecated, use Recipe#registerRecipes instead
     */
    @Deprecated
    public void register(ItemStack[] recipe, ItemStack result) {
        Recipe.registerRecipes(this, new RecipeBuilder()
            .inputs(recipe)
            .outputs(result)
            .build());
    }

    public @Nullable ItemStack toItem() {
        return this.item;
    }

    public @Nonnull ItemStack getItem(Player p) {
        return Slimefun.getLocalization().getRecipeTypeItem(p, this);
    }

    /**
     * This method is deprecated! RecipeType is no longer bound to a single machine
     * @return {@code null}
     */
    @Deprecated
    public SlimefunItem getMachine() {
        return null;
    }

    @Override
    public final @Nonnull NamespacedKey getKey() {
        return key;
    }

    public @Nonnull RecipeShape getDefaultShape() {
        return defaultShape;
    }

    @Override
    public final boolean equals(Object obj) {
        if (obj instanceof RecipeType recipeType) {
            return recipeType.getKey().equals(this.getKey());
        } else {
            return false;
        }
    }

    @Override
    public final int hashCode() {
        return getKey().hashCode();
    }

    @ParametersAreNonnullByDefault
    private static void registerBarterDrop(ItemStack[] recipe, ItemStack output) {
        if (Slimefun.getMinecraftVersion().isAtLeast(MinecraftVersion.MINECRAFT_1_16)) {
            Slimefun.getRegistry().getBarteringDrops().add(output);
        }
    }

    @ParametersAreNonnullByDefault
    private static void registerMobDrop(ItemStack[] recipe, ItemStack output) {
        String mob = ChatColor.stripColor(recipe[4].getItemMeta().getDisplayName()).toUpperCase(Locale.ROOT).replace(' ', '_');
        EntityType entity = EntityType.valueOf(mob);
        Set<ItemStack> dropping = Slimefun.getRegistry().getMobDrops().getOrDefault(entity, new HashSet<>());
        dropping.add(output);
        Slimefun.getRegistry().getMobDrops().put(entity, dropping);
    }

    /**
     * This method is deprecated. Use {@code Recipe#getRecipes(recipeType)} instead
     * @return An empty list
     */
    @Deprecated
    public static List<ItemStack> getRecipeInputs(MultiBlockMachine machine) {
        return new ArrayList<>();
    }


    /**
     * This method is deprecated. Use {@code Recipe#getRecipes(recipeType)} instead
     * @return An empty list
     */
    @Deprecated
    public static List<ItemStack[]> getRecipeInputList(MultiBlockMachine machine) {
        return new ArrayList<>();
    }

    /**
     * This method is deprecated. Use {@code Recipe#searchRecipes(type, inputs)} instead
     * @return An itemstack of air
     */
    @Deprecated
    public static ItemStack getRecipeOutput(MultiBlockMachine machine, ItemStack input) {
        return new ItemStack(Material.AIR);
    }


    /**
     * This method is deprecated. Use {@code Recipe#searchRecipes(type, inputs)} instead
     * @return An itemstack of air
     */
    @Deprecated
    public static ItemStack getRecipeOutputList(MultiBlockMachine machine, ItemStack[] input) {
        return new ItemStack(Material.AIR);
    }
}