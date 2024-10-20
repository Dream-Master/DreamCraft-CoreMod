package com.dreammaster.command;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiConfirmOpenLink;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiYesNoCallback;
import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayerMP;

public class WikiCommand implements ICommand {

    private List aliases;

    public WikiCommand() {
        aliases = new ArrayList();
        aliases.add("wiki");
    }

    @Override
    public int compareTo(Object arg0) {
        return 0;
    }

    @Override
    public String getCommandName() {
        return "wiki";
    }

    @Override
    public String getCommandUsage(ICommandSender p_71518_1_) {
        return "/wiki [<topic>|hand|block]";
    }

    @Override
    public List getCommandAliases() {
        return aliases;
    }

    // Main page, if no arguments are provided.
    private static final URI URIMainPage;

    static {
        URI uri = null;
        try {
            uri = new URI("https://gtnh.miraheze.org/wiki/Main_Page");
        } catch (URISyntaxException ignored) {}
        URIMainPage = uri;
    }

    // Substring "@@@" to be replaced by search keyword(s).
    private static final String URISearchPattern = "https://gtnh.miraheze.org/wiki/Special:Search?search=@@@&go=Search";

    @Override
    public void processCommand(ICommandSender pCmdSender, String[] pArgs) {
        if (!(pCmdSender instanceof EntityPlayerMP)) return;

        if (pArgs.length == 0) {
            openURIRespectingSettings(URIMainPage);
        } else {
            URI uri;
            try {
                uri = new URI(URISearchPattern.replace("@@@", String.join("%20", pArgs)));
            } catch (URISyntaxException ignore) {
                return;
            }
            openURIRespectingSettings(uri);
        }
    }

    private void openURIRespectingSettings(URI uri) {
        if (uri == null) return;
        if (Minecraft.getMinecraft().gameSettings.chatLinksPrompt) {
            // Schedule task for the next tick. Otherwise, processing the command closes the window instantly.
            Minecraft.getMinecraft().func_152344_a(() -> openURIConfirmationScreen(uri));
        } else {
            openURI(uri);
        }
    }

    private void openURIConfirmationScreen(URI uri) {
        GuiScreen oldScreen = Minecraft.getMinecraft().currentScreen;
        Minecraft.getMinecraft().displayGuiScreen(new GuiConfirmOpenLink(new GuiYesNoCallback() {

            @Override
            public void confirmClicked(boolean result, int id) {
                if (result) openURI(uri);
                Minecraft.getMinecraft().displayGuiScreen(oldScreen);
            }
        }, uri.toString(), 0, false));
    }

    private void openURI(URI uri) {
        try {
            Class<?> oclass = Class.forName("java.awt.Desktop");
            Object object = oclass.getMethod("getDesktop").invoke(null);
            oclass.getMethod("browse", URI.class).invoke(object, uri);
        } catch (Throwable ignore) {}
    }

    @Override
    public boolean canCommandSenderUseCommand(ICommandSender pCommandSender) {
        return true;
    }

    @Override
    public List addTabCompletionOptions(ICommandSender p_71516_1_, String[] p_71516_2_) {
        return null;
    }

    @Override
    public boolean isUsernameIndex(String[] p_82358_1_, int p_82358_2_) {
        return false;
    }
}
