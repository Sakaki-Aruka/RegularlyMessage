package com.github.sakakiaruka.regularlymessage;

import org.bukkit.configuration.file.FileConfiguration;

import java.util.ArrayList;
import java.util.List;

import static com.github.sakakiaruka.regularlymessage.SettingsLoad.denied;

public class DenyListSave {
    public void main(){
        FileConfiguration config = RegularlyMessage.getInstance().getConfig();
        List<String> list = new ArrayList<>();
        if(!denied.isEmpty())denied.forEach(s->list.add(s.toString()));
        config.set("deny",list);
        RegularlyMessage.getInstance().saveConfig();
    }
}
