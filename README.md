# RegularlyMessage  
can send messages and commands
# about config.yml
## deny section
The list of players UUID that was set deny to broadcast messages.  
Recommend to edit from Minecraft while a server working.  
But, when a server not working, you can edit this file in safety.  
## message section
This section is the list of broadcast messages.  
You must write "broadcast message" and "interval" in one line.  

ex) `Hello world!(60)`  
This setting means to send the string "Hello world!" to players in 60 seconds interval.

## command section
This section is the list of commands that are executed by the console.  
You must not write commands that starts with "/".  

This section rule is same with the message section.  
(Must write "command" and "interval".)

# about commands
# /regularly
This plugins command is only "/regularly".  
The command has some arguments.
## add
When you execute the command with this argument, you will not get broadcast messages.  
## remove  
When you execute the command with this argument, you will be able to see broadcast messages.  
(means "remove the players UUID from the deny list.")


---
These commands are only for administrators.
## reload
When you execute the command with this argument, this plugin re-collect data from "config.yml" and restarts soon.
## set
This argument needs some more info.  
`/regularly set [targetPlayerName] [add | remove]`  
When you execute the command with this argument and some more info, you can set the target players broadcast messages setting.
