package me.zonal.passwordtenshi.commands;

import me.zonal.passwordtenshi.PasswordChecker;
import me.zonal.passwordtenshi.PasswordTenshi;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;


public class CommandRegister implements CommandExecutor {

    private final PasswordTenshi pt;

    public CommandRegister(PasswordTenshi pt) {
        this.pt = pt;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("§bPPTenshi says§r: fuck off console user");
            return false;
        }

        Player player = (Player) sender;

        final String password = args[0];

        if(pt.getPasswordHash(player.getUniqueId()) != null) {
            sender.sendMessage("§bPPTenshi says§r: you already have a password");
            return false;
        }

        try {
            String hash = PasswordChecker.getSaltedHash(password);
            pt.setPasswordHash(player.getUniqueId(), hash);
            pt.setAuthorized(player.getUniqueId(), true);

            // TODO: make this not such a quick fix
            if (player.getGameMode() == GameMode.SPECTATOR) {
                player.setGameMode(GameMode.SURVIVAL);
            }

            sender.sendMessage("§bPPTenshi says§r: i got your password now <3");
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }

        sender.sendMessage("§bPPTenshi says§r: fuxk fuck fuck fuck");
        return true;
    }
}
