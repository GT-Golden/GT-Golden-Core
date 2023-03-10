package com.github.gtgolden.gtgoldencore.material.property.properties;

import com.github.gtgolden.gtgoldencore.material.property.MaterialProperties;
import com.github.gtgolden.gtgoldencore.material.property.MaterialProperty;
import net.minecraft.item.tool.ToolMaterial;
import org.jetbrains.annotations.NotNull;

public class ToolProperty implements MaterialProperty<ToolProperty> {
    public int miningLevel;
    public int durability;
    public float miningSpeed;
    public int attackDamage;
    public ToolMaterial toolMaterial;
    public ToolProperty() {
        this.miningLevel = 1;
        this.durability = 128;
        this.miningSpeed = 2f;
        this.attackDamage = 2;
    }
    public ToolProperty(int miningLevel, int durability, float miningSpeed, int attackDamage) {
        set(miningLevel, durability, miningSpeed, attackDamage);
    }
    public MaterialProperty<ToolProperty> set(@NotNull ToolMaterial toolMaterial) {
        return set(toolMaterial.getMiningLevel(), toolMaterial.getDurability(), toolMaterial.getMiningSpeed(), toolMaterial.getAttackDamage());
    }
    public MaterialProperty<ToolProperty> set(int miningLevel, int durability, float miningSpeed, int attackDamage) {
        this.miningLevel = miningLevel;
        this.durability = durability;
        this.miningSpeed = miningSpeed;
        this.attackDamage = attackDamage;
        return this;
    }
    @Override
    public void verifyProperty(@NotNull MaterialProperties properties) {
        properties.ensureStates("ingot", "dust");
    }
}
