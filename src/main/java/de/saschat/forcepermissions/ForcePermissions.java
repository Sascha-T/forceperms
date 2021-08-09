package de.saschat.forcepermissions;

import de.saschat.forcepermissions.event.PreExecuteCommandCallback;
import net.fabricmc.api.DedicatedServerModInitializer;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.luckperms.api.LuckPerms;
import net.luckperms.api.LuckPermsProvider;
import net.luckperms.api.cacheddata.CachedPermissionData;
import net.luckperms.api.model.user.User;
import net.luckperms.api.model.user.UserManager;
import net.luckperms.api.util.Tristate;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.LiteralText;
import net.minecraft.util.ActionResult;

import javax.swing.*;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

public class ForcePermissions implements DedicatedServerModInitializer {
    private static LuckPerms API;

    @Override
    public void onInitializeServer() {
        ServerLifecycleEvents.SERVER_STARTED.register(this::init);
    }

    private void init(MinecraftServer minecraftServer) {
        API = LuckPermsProvider.get();
        PreExecuteCommandCallback.EVENT.register(this::commandHandler);
    }

    private ActionResult commandHandler(ServerPlayerEntity serverPlayerEntity, String s) {
        UserManager userManager = API.getUserManager();
        CompletableFuture<User> userFuture = userManager.loadUser(serverPlayerEntity.getUuid());
        User lpuser = userFuture.join();

        CachedPermissionData permissionData = lpuser.getCachedData().getPermissionData();

        ActionResult result = permissionData.checkPermission("forceperms.default").asBoolean() ? ActionResult.PASS : ActionResult.FAIL;

        String firstPart = s.split(" ")[0].substring(1);
        Tristate actualPermission = permissionData.checkPermission("forceperms.commands." + firstPart);

        if (actualPermission == Tristate.TRUE)
            result = ActionResult.PASS;
        if (actualPermission == Tristate.FALSE)
            result = ActionResult.FAIL;

        if (result != ActionResult.PASS)
            serverPlayerEntity.sendSystemMessage(new LiteralText("You do not have permission to run this command: " + firstPart), UUID.fromString("00000000-0000-0000-0000-000000000000"));
        return result;
    }
}
