package com.github.sakakiaruka.regularlymessage;

import org.bukkit.plugin.java.JavaPlugin;

public final class RegularlyMessage extends JavaPlugin {
    private static RegularlyMessage instance;

    @Override
    public void onEnable() {
        // Plugin startup logic
        saveDefaultConfig();
        this.instance = this;
        new SettingsLoad().main();
        new MessageSend().main();
        getCommand("regularly").setExecutor(new Manage());

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        new DenyListSave().main();
    }

    public static RegularlyMessage getInstance(){
        return instance;
    }
}
