package com.github.sakakiaruka.regularlymessage;

import org.bukkit.configuration.file.FileConfiguration;

import static com.github.sakakiaruka.regularlymessage.SettingsLoad.denied;

public class SettingsSave {
    public void main(){
        FileConfiguration config = RegularlyMessage.getInstance().getConfig();
        config.set("deny",denied);
    }
}
