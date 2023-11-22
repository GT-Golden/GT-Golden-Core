package io.github.gtgolden.gtgoldencore.machines.api.slot;

import net.minecraft.inventory.InventoryBase;



public class GTSlotBuilder {
    private final GTSlotFactory type;
    private String label = null;
    private int x = 0;
    private int y = 0;
    private InventoryBase inventory = null;
    private int invSlot = -1;
    public GTSlotBuilder(GTSlotFactory type) {
        this.type = type;
    }
    public GTSlotBuilder() {
        this.type = GTSlot::new;
    }

    public GTSlot build() {
        return type.createSlot(label, inventory, invSlot, x, y);
    }

    public GTSlotBuilder withLabel(String label) {
        this.label = label;
        return this;
    }

    public GTSlotBuilder withInventory(InventoryBase inventory, int invSlot) {
        this.inventory = inventory;
        this.invSlot = invSlot;
        return this;
    }

    public GTSlotBuilder withCoordinates(int x, int y) {
        this.x = x;
        this.y = y;
        return this;
    }
}
