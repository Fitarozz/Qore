package com.fitaroz.qore.blocks.solar_panel;

import com.fitaroz.qore.Config;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.component.DataComponentMap;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.common.util.Lazy;
import net.neoforged.neoforge.energy.EnergyStorage;
import net.neoforged.neoforge.energy.IEnergyStorage;

import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
public class SolarPanelBlockEntity extends BlockEntity {

    private EnergyStorage energy;
    private Lazy<IEnergyStorage> energyHandler;

    private int energyGen;
    private int maxEnergyTransfer;
    private int maxEnergyStored;

    private int currentEnergyTransfer;
    private int currentEnergyGen;

    public SolarPanelBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
        setEnergyValues();

        currentEnergyGen = 0;
        currentEnergyTransfer = 0;
    }

    private void setupEnergy() {
        this.energy = createEnergyStorage();
        this.energyHandler = Lazy.of(() -> new SolarEnergy(energy) {
            @Override
            public int receiveEnergy(int i, boolean b) {
                return 0;
            }

            @Override
            public int extractEnergy(int i, boolean b) {
                return 0;
            }

            @Override
            public boolean canExtract() {
                return false;
            }

            @Override
            public boolean canReceive() {
                return false;
            }
        });
    }

    private void setEnergyValues() {
        this.energyGen = 10;
        this.maxEnergyTransfer = 40;
        this.maxEnergyStored = 100;
        setupEnergy();
    }
    public static boolean shouldAlwayGenerate;
    public void tickServer() {
        if (level == null) return;

        boolean powered = level.canSeeSky(worldPosition) && level.isDay() || shouldAlwayGenerate;

        if (powered) {
            currentEnergyGen = generateEnergy();
        }

        if (powered != getBlockState().getValue(BlockStateProperties.POWERED)) {
            level.setBlockAndUpdate(worldPosition, getBlockState().setValue(BlockStateProperties.POWERED, powered));
        }

        currentEnergyTransfer = sendEnergy();
    }

    private int generateEnergy() {
        if (energy.getEnergyStored() < energy.getMaxEnergyStored()) {
            int generating = energy.receiveEnergy(energyGen, false);
            setChanged();
            return generating;
        }
        return 0;
    }

    private int sendEnergy() {
        if (level == null || energy.getEnergyStored() <= 0) return 0;
        IEnergyStorage energyBelow = level.getCapability(Capabilities.EnergyStorage.BLOCK, worldPosition.below(), Direction.UP);
        if (energyBelow != null && energyBelow.canReceive()) {
            int received = energyBelow.receiveEnergy(Math.min(energy.getEnergyStored(), maxEnergyTransfer), false);
            energy.extractEnergy(received, false);
            setChanged();
            return received;
        }
        return 0;
    }

    // Getters
    public int getStoredEnergy() { return energy.getEnergyStored(); }
    public int getMaxEnergy() { return maxEnergyStored; }
    public int getMaxEnergyTransfer() { return maxEnergyTransfer; }
    public int getCurrentEnergyTransfer() { return currentEnergyTransfer; }
    public int getMaxEnergyGen() { return energyGen; }
    public int getCurrentEnergyGen() { return currentEnergyGen; }

    @Override
    protected void saveAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.saveAdditional(tag, registries);
        tag.put("Energy", energy.serializeNBT(registries));
        tag.putInt("Gen", energyGen);
        tag.putInt("Transfer", maxEnergyTransfer);
        tag.putInt("Capacity", maxEnergyStored);
    }

    @Override
    protected void loadAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.loadAdditional(tag, registries);
        if (tag.contains("Gen") && tag.contains("Transfer") && tag.contains("Capacity")) {
            energyGen = tag.getInt("Gen");
            maxEnergyTransfer = tag.getInt("Transfer");
            maxEnergyStored = tag.getInt("Capacity");
            setupEnergy();
        }
        if (tag.contains("Energy")) {
            energy.deserializeNBT(registries, tag.get("Energy"));
        }
    }

    @Nonnull
    private EnergyStorage createEnergyStorage() {
        return new EnergyStorage(maxEnergyStored, maxEnergyTransfer, maxEnergyTransfer);
    }

    public IEnergyStorage getEnergyHandler() {
        return energyHandler.get();
    }
}
