package me.minidigger.voxelgameslib.signs;

import net.kyori.text.Component;

import me.minidigger.voxelgameslib.user.User;

import org.bukkit.Location;

/**
 * A simple sign placeholder that only replaces the key
 */
public interface SimpleSignPlaceHolder extends SignPlaceHolder {

    /**
     * Applies this placeholder to the lines, will only replace the key
     *
     * @param user     the user that will receive the updated sign
     * @param location the location of the sign
     * @param rawLines the plain text content of the lines
     * @param lines    the parsed lines
     * @param key      the key of this placeholder
     * @return the replacement for the key
     */
    Component apply(User user, Location location, String[] rawLines, Component[] lines, String key);
}
