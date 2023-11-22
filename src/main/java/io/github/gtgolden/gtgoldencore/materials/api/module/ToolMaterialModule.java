package io.github.gtgolden.gtgoldencore.materials.api.module;

import io.github.gtgolden.gtgoldencore.GTGoldenCore;
import net.minecraft.item.tool.ToolMaterial;
import net.modificationstation.stationapi.api.item.tool.ToolMaterialFactory;
import net.modificationstation.stationapi.api.util.Identifier;

public class ToolMaterialModule implements Module {
    public ToolMaterial material;

    public ToolMaterialModule(ToolMaterial material) {
        this.material = material;
    }

    // TODO Fix this once Stapi has fixed it..
    public ToolMaterialModule(String materialName, int miningLevel, int durability, float miningSpeed, int attackDamage) {
        this.material = switch (miningLevel) {
            case 0 ->
                    ToolMaterialFactory.create(GTGoldenCore.NAMESPACE.id(materialName).toString(), miningLevel, durability, miningSpeed, attackDamage).inheritsFrom(ToolMaterial.field_1688).requiredBlockTag(Identifier.of("needs_wood_tool"));
            case 1 ->
                    ToolMaterialFactory.create(GTGoldenCore.NAMESPACE.id(materialName).toString(), miningLevel, durability, miningSpeed, attackDamage).inheritsFrom(ToolMaterial.field_1689).requiredBlockTag(Identifier.of("needs_stone_tool"));
            case 2 ->
                    ToolMaterialFactory.create(GTGoldenCore.NAMESPACE.id(materialName).toString(), miningLevel, durability, miningSpeed, attackDamage).inheritsFrom(ToolMaterial.field_1690).requiredBlockTag(Identifier.of("needs_iron_tool"));
            case 3 ->
                    ToolMaterialFactory.create(GTGoldenCore.NAMESPACE.id(materialName).toString(), miningLevel, durability, miningSpeed, attackDamage).inheritsFrom(ToolMaterial.field_1691).requiredBlockTag(Identifier.of("needs_diamond_tool"));
            default ->
                    ToolMaterialFactory.create(GTGoldenCore.NAMESPACE.id(materialName).toString(), miningLevel, durability, miningSpeed, attackDamage);
        };
    }

    @Override
    public Class<? extends Module> getModuleType() {
        return ToolMaterialModule.class;
    }
}
