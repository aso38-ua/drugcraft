package com.disco190.drugcraft.util;

import net.minecraft.util.StringRepresentable;

public enum DrugType implements StringRepresentable {
    METH("meth"),
    DMT("dmt");

    private final String name;

    DrugType(String name) {
        this.name = name;
    }

    @Override
    public String getSerializedName() {
        return name;
    }
}
