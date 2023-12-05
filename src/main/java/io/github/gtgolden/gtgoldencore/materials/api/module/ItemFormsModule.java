package io.github.gtgolden.gtgoldencore.materials.api.module;

import io.github.gtgolden.gtgoldencore.GTGoldenCore;
import net.minecraft.block.BlockBase;
import net.minecraft.item.ItemBase;
import net.minecraft.item.ItemInstance;
import org.jetbrains.annotations.Nullable;
import uk.co.benjiweber.expressions.tuple.BiTuple;

import java.util.HashMap;

// TODO Decide whether this will be moved into Material itself
// TODO Mixin vanilla classes so we don't have to use raw objects.
public class ItemFormsModule implements Module {
    private final HashMap<String, ItemForm> forms = new HashMap<>();

    public ItemFormsModule(String formName, Object form) {
        forms.put(formName, convertToForm(form));
    }

    public ItemFormsModule(String formName, ItemBase baseItem, String material) {
        forms.put(formName, new NBTItemForm(baseItem, material));
    }

    @SafeVarargs
    public ItemFormsModule(BiTuple<String, Object>... forms) {
        for (BiTuple<String, Object> form : forms) {
            if (!this.forms.containsKey(form.one())) {
                var toAdd = convertToForm(form.two());
                if (toAdd == null) continue;

                this.forms.put(form.one(), toAdd);
            }
        }
    }

    private ItemForm convertToForm(Object object) {
        if (object instanceof ItemBase itemBase) {
            return new ItemForm(new ItemInstance(itemBase));
        } else if (object instanceof BlockBase blockBase) {
            return new ItemForm(new ItemInstance(blockBase));
        } else if (object instanceof ItemInstance itemInstance) {
            return new ItemForm(itemInstance);
        } else if (object instanceof NBTItemForm itemForm) {
            return itemForm;
        }
        GTGoldenCore.LOGGER.error("Invalid form registered");
        return null;
    }

    @Override
    public Class<? extends Module> getModuleType() {
        return ItemFormsModule.class;
    }

    @Override
    public Module combine(Module existingModule) {
        assert existingModule instanceof ItemFormsModule;
        ((ItemFormsModule) existingModule).forms.forEach(
                (key, itemInstance) -> {
                    if (!forms.containsKey(key)) {
                        forms.put(key, itemInstance);
                    }
                }
        );
        return this;
    }

    public @Nullable ItemInstance getForm(String form) {
        if (!forms.containsKey(form)) return null;
        return forms.get(form).getForm().copy();
    }
}
