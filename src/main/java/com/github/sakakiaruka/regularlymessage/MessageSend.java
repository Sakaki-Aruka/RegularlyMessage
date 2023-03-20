package com.github.sakakiaruka.regularlymessage;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import static com.github.sakakiaruka.regularlymessage.SettingsLoad.*;

public class MessageSend {
    private long counter = 0;
    public void main(){
        BukkitRunnable main = new BukkitRunnable() {
            @Override
            public void run() {
                List<UUID> uuids = getSendPlayers();
                long time = counter * interval;
                List<String> command = getList(time,commands);
                List<String> message = getList(time, messages);

                command.forEach(s-> Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(),s));
                uuids.forEach(s->{
                    Player p = Bukkit.getPlayer(s);
                    message.forEach(t->p.sendMessage(t));
                });

                addCount();
            }
        };

        main.runTaskTimer(RegularlyMessage.getInstance(),interval * 20,1l);

    }

    private void addCount(){
        counter++;
    }

    private List<UUID> getSendPlayers(){
        List<Player> temp = (List<Player>) Bukkit.getOnlinePlayers();
        List<UUID> list = new ArrayList<>();
        temp.forEach(s->list.add(s.getUniqueId()));
        list.removeAll(denied);
        return list;
    }

    private List<String> getList(long time, Map<Long,List<String>> map){
        List<String> list = new ArrayList<>();
        for(long l:map.keySet()){
            if(l < time)continue;
            if(time % l ==0)list.addAll(map.get(l));
        }
        return list;
    }
}
