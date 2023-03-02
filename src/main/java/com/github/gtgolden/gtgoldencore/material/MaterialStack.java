package com.github.gtgolden.gtgoldencore.material;

public record MaterialStack(GTMaterial material, long amount) {

    public MaterialStack copy(long amount) {
        return new MaterialStack(material, amount);
    }

    public MaterialStack copy() {
        return new MaterialStack(material, amount);
    }
}
