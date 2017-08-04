package com.voxelgameslib.voxelgameslib.game;

import com.voxelgameslib.voxelgameslib.components.team.Team;
import com.voxelgameslib.voxelgameslib.feature.Feature;
import com.voxelgameslib.voxelgameslib.lang.Translatable;
import com.voxelgameslib.voxelgameslib.phase.Phase;
import com.voxelgameslib.voxelgameslib.tick.Tickable;
import com.voxelgameslib.voxelgameslib.user.User;

import net.kyori.text.Component;

import java.time.Duration;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * A {@link Game} is the representation of an instance of a {@link GameMode}. Handles everything
 * that is related to that {@link GameMode}: starting, stopping, {@link Phase}s etc.
 */
public interface Game extends Tickable {

    /**
     * @param uuid the uuid for this game
     */
    void setUuid(@Nonnull UUID uuid);

    /**
     * @return a unique identifier for this game
     */
    UUID getUuid();

    /**
     * initialises this game and all phases, based on a saved game definition
     *
     * @param gameDefinition the definition for this game
     */
    void initGameFromDefinition(@Nonnull GameDefinition gameDefinition);

    /**
     * @return the game definition for this gamemode, generated by the phases and features this game
     * currently has
     */
    @Nonnull
    GameDefinition saveGameDefinition();

    /**
     * initialises this game and all phases, based on the stuff that was coded into the gamemode
     */
    void initGameFromModule();

    /**
     * Sends a message to every {@link User} that is related to
     * this game. This could be a participant in the game or a spectator.
     *
     * @param message the message to be send
     */
    void broadcastMessage(@Nonnull Component message);

    /**
     * Sends a message to everr User that is related to this game. This could be a participant in
     * the game or a spectator.
     *
     * @param key  the message to be send
     * @param args the arguments for the key
     */
    void broadcastMessage(@Nonnull Translatable key, @Nullable Object... args);

    /**
     * Ends the current {@link Phase} and starts the next one.
     */
    void endPhase();

    /**
     * Ends the game
     *
     * @param winnerTeam the team which won, might be null
     * @param winnerUser the user which won, might be null
     */
    void endGame(@Nullable Team winnerTeam, @Nullable User winnerUser);

    /**
     * @return the gamemode that is played in this game
     */
    @Nonnull
    GameMode getGameMode();

    /**
     * @return returns the {@link Phase} that is currently active
     */
    @Nonnull
    Phase getActivePhase();

    /**
     * Lets a user join this game
     *
     * @param user the user that wants to join this game
     */
    boolean join(@Nonnull User user);

    /**
     * lets a user spectate this game
     *
     * @param user the user which wants to spectate
     */
    boolean spectate(@Nonnull User user);

    /**
     * Lets a user leave this game
     *
     * @param user the user that wants to leave this game
     */
    void leave(@Nonnull User user);

    /**
     * Checks if that user is playing (not spectating!) this game
     *
     * @param uuid the uuid of the user to check
     * @return if the user is playing this game
     */
    boolean isPlaying(@Nonnull UUID uuid);

    /**
     * Checks if that user is spectating (not playing!) this game
     *
     * @param uuid the uuid of the user to check
     * @return if the user is spectating this game
     */
    boolean isSpectating(@Nonnull UUID uuid);

    boolean isParticipating(@Nonnull UUID user);

    /**
     * Creates a new feature class (using guice and stuff)
     *
     * @param featureClass the class of the feature that should be created
     * @param <T>          the feature
     * @param phase        the phase that the new feature should be attached to
     * @return the created feature instance
     */
    @Nonnull
    <T extends Feature> T createFeature(@Nonnull Class<T> featureClass, @Nonnull Phase phase);

    /**
     * Creates a new phase class (using guice and stuff)
     *
     * @param phaseClass the class of the phase that should be created
     * @param <T>        the phase
     * @return the created phase instance
     */
    @Nonnull
    <T extends Phase> T createPhase(@Nonnull Class<T> phaseClass);

    /**
     * @return the minimum amount of players for this game
     */
    int getMinPlayers();

    /**
     * @param minPlayers the minimum amount of players for this game
     */
    void setMinPlayers(int minPlayers);

    /**
     * @return the maximum amount of players for this game
     */
    int getMaxPlayers();

    /**
     * @param maxPlayers the maximum amount of players for this game
     */
    void setMaxPlayers(int maxPlayers);

    /**
     * @return the list of users that are currently playing this game
     */
    @Nonnull
    List<User> getPlayers();

    /**
     * @return the list of users that are currently spectating this game
     */
    @Nonnull
    List<User> getSpectators();

    /**
     * Saves a object with a key into the gamedata map
     *
     * @param data the data
     */
    void putGameData(@Nonnull GameData data);

    @Nonnull
    List<User> getAllUsers();

    /**
     * @param key the key to get the data for
     * @return the game data for that key, may be null
     */
    @Nonnull
    <T extends GameData> Optional<T> getGameData(@Nonnull Class<T> key);

    /**
     * Similar to endGame, but doesn't with stuff like elo or stats
     */
    void abortGame();

    /**
     * if running: get the duration between start and now.<br> else: get the duration this phase has
     * run
     *
     * @return the duration
     */
    Duration getDuration();

    /**
     * @return if this game is currently in the process of being aborted
     */
    boolean isAborting();
}
