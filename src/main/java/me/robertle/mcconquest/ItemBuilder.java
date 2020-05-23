package me.robertle.mcconquest;

import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.material.Wool;

import java.lang.reflect.Method;
import java.util.*;

public class ItemBuilder {
    private static final Map<String, Class<?>> classCache = new HashMap();
    private static final Map<String, Method> methodCache = new HashMap();
    private ItemStack itemStack;

    public ItemBuilder(Material var1) {
        this.itemStack = new ItemStack(var1);
    }

    public ItemBuilder(Material var1, int var2) {
        this.itemStack = new ItemStack(var1, var2);
    }

    public ItemBuilder(Material var1, int var2, short var3) {
        this.itemStack = new ItemStack(var1, var2, var3);
    }

    public ItemBuilder(Material var1, int var2, short var3, String var4) {
        this.itemStack = new ItemStack(var1, var2, var3);
        this.displayName(var4);
    }

    public ItemBuilder(ItemStack var1) {
        this.itemStack = var1;
    }

    public static ItemBuilder clone(ItemStack var0) {
        return of(var0.clone());
    }

    public static ItemBuilder of(ItemStack var0) {
        Optional var1 = Optional.ofNullable(var0);
        return var1.isPresent() ? new ItemBuilder((ItemStack) var1.get()) : null;
    }

    public ItemBuilder type(Material var1) {
        this.itemStack.setType(var1);
        return this;
    }

    public ItemBuilder amount(int var1) {
        this.itemStack.setAmount(var1);
        return this;
    }

    public ItemBuilder durability(short var1) {
        this.itemStack.setDurability(var1);
        return this;
    }

    public ItemBuilder displayName(String var1) {
        ItemMeta var2 = this.itemStack.getItemMeta();
        var2.setDisplayName(Replacer.replace(var1));
        this.itemStack.setItemMeta(var2);
        return this;
    }

    public ItemBuilder lore(String... var1) {
        this.lore(Arrays.asList(var1));
        return this;
    }

    public ItemBuilder lore(List<String> var1) {
        ItemMeta var2 = this.itemStack.getItemMeta();
        var2.setLore(Replacer.replace(var1));
        this.itemStack.setItemMeta(var2);
        return this;
    }

    public ItemBuilder append(String... var1) {
        return this.append(Arrays.asList(var1));
    }

    public ItemBuilder append(List<String> var1) {
        ItemMeta var2 = this.itemStack.getItemMeta();
        Optional var3 = Optional.ofNullable(var2.getLore());
        List var4 = (List) var3.orElse(new ArrayList());
        var4.addAll(var1);
        this.lore(var4);
        return this;
    }

    public ItemBuilder enchants(Map<Enchantment, Integer> var1) {
        ItemMeta var2 = this.itemStack.getItemMeta();
        Iterator var3 = var1.entrySet().iterator();

        while (var3.hasNext()) {
            Map.Entry var4 = (Map.Entry) var3.next();
            var2.addEnchant((Enchantment) var4.getKey(), (Integer) var4.getValue(), true);
        }

        this.itemStack.setItemMeta(var2);
        return this;
    }

    public ItemBuilder unsafeEnchants(Map<Enchantment, Integer> var1) {
        Iterator var2 = var1.entrySet().iterator();

        while (var2.hasNext()) {
            Map.Entry var3 = (Map.Entry) var2.next();
            this.itemStack.addUnsafeEnchantment((Enchantment) var3.getKey(), (Integer) var3.getValue());
        }

        return this;
    }

    public ItemBuilder enchant(Enchantment var1, int var2) {
        ItemMeta var3 = this.itemStack.getItemMeta();
        var3.addEnchant(var1, var2, false);
        this.itemStack.setItemMeta(var3);
        return this;
    }

    public ItemBuilder unsafeEnchant(Enchantment var1, int var2) {
        this.itemStack.addUnsafeEnchantment(var1, var2);
        return this;
    }

    public ItemBuilder removeEnchants() {
        ItemMeta var1 = this.itemStack.getItemMeta();
        Map var2 = var1.getEnchants();
        var2.keySet().forEach((var1x) -> {
            Integer var10000 = (Integer) var2.remove(var1x);
        });
        this.itemStack.setItemMeta(var1);
        return this;
    }

    public ItemBuilder flags(ItemFlag... var1) {
        ItemMeta var2 = this.itemStack.getItemMeta();
        var2.addItemFlags(var1);
        this.itemStack.setItemMeta(var2);
        return this;
    }

    public ItemBuilder flag(ItemFlag var1) {
        this.flags(var1);
        return this;
    }

    public ItemBuilder unbreakable(boolean var1) {
        ItemMeta var2 = this.itemStack.getItemMeta();
        var2.setUnbreakable(var1);
        this.itemStack.setItemMeta(var2);
        return this;
    }

    public ItemBuilder hideAll(boolean var1) {
        return this.flag(ItemFlag.HIDE_ENCHANTS).flag(ItemFlag.HIDE_ATTRIBUTES).flag(ItemFlag.HIDE_UNBREAKABLE).flag(ItemFlag.HIDE_POTION_EFFECTS).flag(ItemFlag.HIDE_DESTROYS);
    }

    public ItemBuilder woolColor(DyeColor var1) {
        if (this.itemStack != null && this.itemStack.getType() == Material.WHITE_WOOL) {
            Wool var2 = new Wool(var1);
            this.itemStack.setDurability(var2.toItemStack().getDurability());
        }

        return this;
    }

    public ItemBuilder skull(SkullMeta var1) {
        if (this.itemStack != null && this.itemStack.getType() == Material.PLAYER_HEAD) {
            this.itemStack.setItemMeta(var1);
        }

        return this;
    }

    public ItemBuilder potion(PotionMeta var1) {
        if (this.itemStack != null && (this.itemStack.getType() == Material.POTION || this.itemStack.getType() == Material.SPLASH_POTION)) {
            this.itemStack.setItemMeta(var1);
        }

        return this;
    }

    public ItemBuilder setGlowing(boolean var1) {
        if (var1) {
            this.unsafeEnchant(this.itemStack.getType() != Material.BOW ? Enchantment.ARROW_INFINITE : Enchantment.LUCK, 10);
            this.flag(ItemFlag.HIDE_ENCHANTS);
        }

        return this;
    }

    public ItemBuilder setCustomModelData(Integer var1) {
        try {
            ItemMeta var2 = this.itemStack.getItemMeta();
            Method var3 = (Method) methodCache.get("setCustomModelData");
            var3.invoke(var2, var1);
            this.itemStack.setItemMeta(var2);
        } catch (Exception var4) {
            var4.printStackTrace();
        }

        return this;
    }

    public ItemStack asItemStack() {
        return this.itemStack;
    }

    static {
        classCache.put("ItemMeta", ItemMeta.class);
        try {
            methodCache.put("setCustomModelData", ((Class) classCache.get("ItemMeta")).getMethod("setCustomModelData", Integer.class));
        } catch (NoSuchMethodException var1) {
            var1.printStackTrace();

        }
    }

}
