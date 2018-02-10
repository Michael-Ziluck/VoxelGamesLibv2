package com.voxelgameslib.voxelgameslib.map;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.logging.Level;
import javax.annotation.Nonnull;
import javax.inject.Inject;
import javax.inject.Singleton;

import com.voxelgameslib.voxelgameslib.feature.AbstractFeature;
import com.voxelgameslib.voxelgameslib.game.GameHandler;
import com.voxelgameslib.voxelgameslib.handler.Handler;
import com.voxelgameslib.voxelgameslib.timings.Timings;

import lombok.Getter;
import lombok.extern.java.Log;

import io.github.lukehutch.fastclasspathscanner.FastClasspathScanner;

/**
 * Created by Martin on 04.10.2016.
 */
@Log
@Singleton
public class MapHandler implements Handler {

    @Inject
    private GameHandler gameHandler;

    //TODO implement chests
    @Nonnull
    private HashMap<String, ChestMarker> chests = new HashMap<>();

    @Getter
    private List<MarkerDefinition> markerDefinitions = new ArrayList<>();

    @Override
    public void start() {
        Timings.time("ScanningFeatures", () ->
                new FastClasspathScanner().scan().getNamesOfSubclassesOf(AbstractFeature.class).stream().map(n -> {
                    try {
                        //noinspection unchecked
                        return (Class<AbstractFeature>) Class.forName(n);
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    }
                    return null;
                }).filter(Objects::nonNull).forEach(clazz -> {
                    try {
                        markerDefinitions.addAll(Arrays.asList(clazz.newInstance().getMarkers()));
                    } catch (InstantiationException | IllegalAccessException e) {
                        log.log(Level.WARNING, "Feature " + clazz.getName() + " is malformed!", e);
                    }
                }));
        log.info("Loaded " + markerDefinitions.size() + " MarkerDefinitions");
    }

    @Override
    public void stop() {

    }

    @Nonnull
    public MarkerDefinition createMarkerDefinition(@Nonnull String markerData) {
        Optional<MarkerDefinition> markerDefinition = markerDefinitions.stream().filter(def -> def.matches(markerData)).findFirst();
        MarkerDefinition def = markerDefinition.orElseGet(() -> new BasicMarkerDefinition(markerData.replace("vgl:", "")));
        def.parse(markerData);
        return def;
    }
}
