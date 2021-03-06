package com.voxelgameslib.voxelgameslib.api.feature.features;

import com.google.gson.annotations.Expose;

import java.util.Collections;
import java.util.List;
import javax.annotation.Nonnull;

import com.voxelgameslib.voxelgameslib.api.feature.AbstractFeature;
import com.voxelgameslib.voxelgameslib.api.feature.Feature;

import org.bukkit.World;

public class TimeFeature extends AbstractFeature {

    @Expose
    private boolean shouldChange = false;
    @Expose
    private int time = 6000;

    @Override
    public void enable() {
        World world = getPhase().getFeature(MapFeature.class).getWorld();
        world.setGameRuleValue("doDaylightCycle", shouldChange + "");
        world.setTime(time);
    }

    @Nonnull
    @Override
    public List<Class<? extends Feature>> getDependencies() {
        return Collections.singletonList(MapFeature.class);
    }
}
