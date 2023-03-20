package com.github.sakakiaruka.regularlymessage;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.UUID;

import static com.github.sakakiaruka.regularlymessage.SettingsLoad.*;

public class Manage implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command,String label,String[] args){
        /*
        * args
        * add | remove | reload (admin only)
         */

        if(args.length < 1)return false;
        if(args[0].equalsIgnoreCase("reload")){
            if(sender instanceof Player && !sender.isOp())return false;
            messages.clear();
            commands.clear();
            denied.clear();
            interval = 0l;
            new SettingsLoad().main();
            return true;
        }

        if(!(sender instanceof Player))return false;
        UUID uuid = ((Player) sender).getUniqueId();
        if(args[0].equalsIgnoreCase("add")){
            if(denied.contains(uuid))denied.remove(uuid);
            sender.sendMessage("  => You have became possible to catch broadcast messages.");
        }else if(args[0].equalsIgnoreCase("remove")){
            if(!denied.contains(uuid))denied.add(uuid);
            sender.sendMessage("  => The setting \"Catch broadcast messages\" is disabled.");
        }

        return true;
    }
}
