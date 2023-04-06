package com.github.sakakiaruka.regularlymessage;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static com.github.sakakiaruka.regularlymessage.MessageSend.taskID;
import static com.github.sakakiaruka.regularlymessage.SettingsLoad.*;

public class Manage implements CommandExecutor, TabCompleter {
    @Override
    public boolean onCommand(CommandSender sender, Command command,String label,String[] args){
        /*
        * args
        * add | remove | reload (admin only)
         */

        if(args.length < 1)return false;
        if(args[0].equalsIgnoreCase("reload")){
            if(sender instanceof Player && !sender.isOp())return false;
            RegularlyMessage.getInstance().reloadConfig();
            messages.clear();
            commands.clear();
            new DenyListSave().main();
            interval = 0l;
            Bukkit.getScheduler().cancelTask(taskID);
            new SettingsLoad().main();
            new MessageSend().main();
            return true;
        }

        if(!(sender instanceof Player))return false;
        UUID uuid = ((Player) sender).getUniqueId();
        String name = ((Player)sender).getName();
        if(args[0].equalsIgnoreCase("allow")){
            if(denied.contains(uuid))denied.remove(uuid);
            sender.sendMessage("  => The setting \"Catch broadcast messages\" is enabled.");
            Bukkit.getLogger().info(String.format("The player \"%s\"(UUID : %s) was removed from the deny list.",name,uuid));
        }else if(args[0].equalsIgnoreCase("deny")){
            if(!denied.contains(uuid))denied.add(uuid);
            sender.sendMessage("  => The setting \"Catch broadcast messages\" is disabled.");
            Bukkit.getLogger().info(String.format("The player \"%s\"(UUID : %s) was added to the deny list.",name,uuid));
        }else if(args[0].equalsIgnoreCase("help")){
            StringBuilder builder = new StringBuilder();
            builder.append("=== regularly command usage ===\n\n");
            builder.append("/regularly allow -> ALLOW to receive broadcast messages..\n\n");
            builder.append("/regularly deny -> DENY to receive broadcast messages.\n\n");
            builder.append("=== regularly command usage ===\n");
            sender.sendMessage(builder.toString());
        }else if(args[0].equalsIgnoreCase("set")){
            if(args.length != 3)return false;
            // /regularly set [playerName] [add | remove]
            if(!sender.isOp())return false;
            UUID target;
            Player player;
            try{
                player = Bukkit.getPlayer(args[1]);
                if(!((List<Player>)Bukkit.getOnlinePlayers()).contains(player))return false;
                target = player.getUniqueId();
            }catch (Exception e){
                sender.sendMessage("[Regularly Message] The player not found.");
                return false;
            }


            if(args[2].equalsIgnoreCase("allow")){
                if(!denied.contains(target))denied.add(target);
                String notice = String.format(" => The player \"%s\" (UUID : %s) was added to the deny list.",player.getName(),target);
                sender.sendMessage(notice);
                Bukkit.getLogger().info(notice);
            }else if(args[2].equalsIgnoreCase("deny")){
                if(denied.contains(target))denied.remove(target);
                String notice = String.format(" => The player \"%s\" (UUID : %s) was removed from the deny list.",player,target);
                sender.sendMessage(notice);
                Bukkit.getLogger().info(notice);
            }return false;
        }

        //debug
        Bukkit.getLogger().info(String.format("deny list : %s",denied));

        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
        List<String> list = Arrays.asList("allow","deny","help");
        List<String> result = new ArrayList<>();

        if(args.length != 1)return null;
        for(String s : list){
            if(s.contains(args[0]))result.add(s);
        }
        return result;
    }
}
