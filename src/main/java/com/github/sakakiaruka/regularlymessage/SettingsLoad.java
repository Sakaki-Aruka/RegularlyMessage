package com.github.sakakiaruka.regularlymessage;

import org.bukkit.configuration.file.FileConfiguration;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

        if(config.contains("command"))setCommands();
        if(config.contains("message"))setMessages();
        if(config.contains("command") || config.contains("message"))setInterval();
        if(config.contains("deny"))setDenied();
    }

    private void setMessages(){
        for(String s:config.getStringList("message")){
            Pattern p = Pattern.compile("(.*)\\((\\d+)\\)");
            Matcher m = p.matcher(s);
            if(!m.find())continue;
            long interval;
            String message;
            try{
                interval = Long.valueOf(m.group(2));
                message = m.group(1);
            }catch (Exception e){
                System.out.println("[CustomCrafter] A invalid setting found from the config file.");
                continue;
            }
            if(!messages.containsKey(interval))messages.put(interval,new ArrayList<>());
            messages.get(interval).add(message);

            // SHOW INFO
            System.out.println(String.format("Message set. | message : %s | interval : %ds",message,interval));
        }
    }

    private void setCommands(){
        for(String s:config.getStringList("command")){
            Pattern p = Pattern.compile("(.*)\\((\\d+)\\)");
            Matcher m = p.matcher(s);
            if(!m.find())continue;
            long interval;
            String command;
            try{
                interval = Long.valueOf(m.group(2));
                command = m.group(1);
            }catch (Exception e){
                System.out.println("[CustomCrafter] A invalid setting found from the config file.");
                continue;
            }

            if(!commands.containsKey(interval))commands.put(interval,new ArrayList<>());
            commands.get(interval).add(command);

            // SHOW INFO
            System.out.println(String.format("Command set. | command : %s | interval : %ds",command,interval));
        }
    }

    private void setDenied(){
        List<String> list = config.getStringList("deny");
        if(list.isEmpty())return;
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

        //debug
        System.out.println(String.format("interval : %d",interval));
    }

    private long gcd(long x,long y){
        if(y == 0)return x;
        return gcd(y,x%y);
    }


}
