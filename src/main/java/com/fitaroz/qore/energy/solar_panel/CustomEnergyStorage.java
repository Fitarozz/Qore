package com.fitaroz.qore.energy.solar_panel;

import net.neoforged.neoforge.energy.IEnergyStorage;

public class CustomEnergyStorage implements IEnergyStorage {
    private int energy;
    private final int capacity;
    private final int maxReceive;
    private final int maxExtract;

    public CustomEnergyStorage(int capacity, int maxReceive, int maxExtract) {
        this.capacity = capacity;
        this.maxReceive = maxReceive;
        this.maxExtract = maxExtract;
        this.energy = 0;
    }

    @Override
    public int receiveEnergy(int maxReceive, boolean simulate) {
        int energyReceived = Math.min(capacity - energy, Math.min(this.maxReceive, maxReceive));
        if (!simulate) {
            energy += energyReceived;
        }
        return energyReceived;
    }

    @Override
    public int extractEnergy(int maxExtract, boolean simulate) {
        int energyExtracted = Math.min(energy, Math.min(this.maxExtract, maxExtract));
        if (!simulate) {
            energy -= energyExtracted;
        }
        return energyExtracted;
    }

    @Override
    public int getEnergyStored() {
        return energy;
    }

    @Override
    public int getMaxEnergyStored() {
        return capacity;
    }

    @Override
    public boolean canExtract() {
        return maxExtract > 0;
    }

    @Override
    public boolean canReceive() {
        return maxReceive > 0;
    }
}
