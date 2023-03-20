package com.github.sakakiaruka.regularlymessage;

import org.bukkit.configuration.file.FileConfiguration;

import java.util.*;

public class SettingsLoad {
    private FileConfiguration config;

    public static Map<Long,List<String>> messages;
    public static Map<Long,List<String>> commands;
    public static List<UUID> denied;
    public static long interval;


    public void main(){
        config = RegularlyMessage.getInstance().getConfig();
        messages = new HashMap<>();
        commands = new HashMap<>();
        denied = new ArrayList<>();

        setMessages();
        setCommands();

        //debug
        System.out.println(messages);
        System.out.println(commands);

        setInterval();
        setDenied();
    }

    private boolean check(String path){
        if(!config.contains(path))return false;
        if(config.getStringList(path).isEmpty())return false;
        return true;
    }

    private void setMessages(){
//        if(!check("message"))return;

        //debug
        System.out.println("messages : "+config.getStringList("message"));

        for(String s:config.getStringList("message")){
            long interval = Long.valueOf(s);
            for(String t:config.getStringList("message."+s)){
                if(!commands.containsKey(interval))commands.put(interval,new ArrayList<>());
                commands.get(interval).add(t);
            }
        }
    }

    private void setCommands(){
//        if(!check("command"))return;
        for(String s:config.getStringList("command")){
            long interval = Long.valueOf(s);
            for(String t:config.getStringList("command."+s)){
                if(!commands.containsKey(interval))commands.put(interval,new ArrayList<>());
                commands.get(interval).add(t);
            }
        }
    }

    private void setDenied(){
//        if(!check("deny"))return;
        List<String> list = config.getStringList("deny");
        list.forEach(s->denied.add(UUID.fromString(s)));
    }

    private void setInterval(){
        List<Long> list = new ArrayList<>();
        messages.keySet().forEach(s->list.add(s));
        commands.keySet().forEach(s->list.add(s));
        Collections.sort(list);
        Collections.reverse(list);

        interval = list.get(0);
        for(int i=1;i<list.size();i++){
            interval = gcd(interval,list.get(i));
        }
    }

    private long gcd(long x,long y){
        if(y == 0)return x;
        return gcd(y,x%y);
    }


}
