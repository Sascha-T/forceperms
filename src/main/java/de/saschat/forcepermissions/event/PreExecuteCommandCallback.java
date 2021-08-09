package de.saschat.forcepermissions.event;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.ActionResult;

public interface PreExecuteCommandCallback {
    Event<PreExecuteCommandCallback> EVENT = EventFactory.createArrayBacked(PreExecuteCommandCallback.class,
        (listeners) -> (player, cmd) -> {
            for (PreExecuteCommandCallback listener : listeners) {
                ActionResult result = listener.preExecuteCommand(player, cmd);

                if (result != ActionResult.PASS) {
                    return result;
                }
            }

            return ActionResult.PASS;
        });

    ActionResult preExecuteCommand(ServerPlayerEntity player, String rawCommand);
}
