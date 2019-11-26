package me.frostdev.frostyspawners.util;

import me.frostdev.frostyspawners.util.config.Config;
import org.bukkit.Location;
import org.bukkit.Sound;

public enum SoundHandler {
    UPGRADE_MAX_EXPLOSION("ENTITY_FIREWORK_TWINKLE", "ENTITY_FIREWORK_ROCKET_TWINKLE"),
    UPGRADE_MAX_RANDOM_1("ENTITY_FIREWORK_BLAST", "ENTITY_FIREWORK_ROCKET_BLAST"),
    UPGRADE_MAX_RANDOM_2("ENTITY_FIREWORK_BLAST_FAR", "ENTITY_FIREWORK_ROCKET_BLAST_FAR"),
    UPGRADE_MAX_RANDOM_3("ENTITY_FIREWORK_LARGE_BLAST", "ENTITY_FIREWORK_ROCKET_LARGE_BLAST"),
    UPGRADE_MAX_RANDOM_4("ENTITY_FIREWORK_LARGE_BLAST_FAR", "ENTITY_FIREWORK_ROCKET_LARGE_BLAST_FAR"),
    MENU_OPEN("BLOCK_ANVIL_LAND", "BLOCK_ANVIL_LAND"),
    OPTION_SELECT("BLOCK_NOTE_HAT", "BLOCK_NOTE_BLOCK_HAT"),
    OPTION_UPGRADE("ENTITY_PLAYER_LEVELUP", "ENTITY_PLAYER_LEVELUP"),
    OPTION_ENABLE("BLOCK_NOTE_HARP", "BLOCK_NOTE_BLOCK_HARP"),
    OPTION_DISABLE("BLOCK_NOTE_BASS", "BLOCK_NOTE_BLOCK_BASS"),
    OPTION_EXIT("BLOCK_NOTE_BASEDRUM", "BLOCK_NOTE_BLOCK_BASEDRUM"),
    OPTION_CHANGETYPE("BLOCK_ENCHANTMENT_TABLE_USE", "BLOCK_ENCHANTMENT_TABLE_USE");

    private String legacy;
    private String latest;

    private SoundHandler(String a, String b) {
        this.legacy = a;
        this.latest = b;
    }

    public void playSound(Location loc, float f1, float f2) {
        if (!this.name().contains("OPTION") || Config.menuSounds.get()) {
            if (Util.isLegacyVersion()) {
                loc.getWorld().playSound(loc, Sound.valueOf(this.legacy), f1, f2);
            } else {
                loc.getWorld().playSound(loc, Sound.valueOf(this.latest), f1, f2);
            }

        }
    }

    public Sound getSound() {
        return Util.isLegacyVersion() ? Sound.valueOf(this.legacy) : Sound.valueOf(this.latest);
    }
}
