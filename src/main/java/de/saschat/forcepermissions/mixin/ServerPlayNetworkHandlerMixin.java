package de.saschat.forcepermissions.mixin;

import de.saschat.forcepermissions.event.PreExecuteCommandCallback;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.ActionResult;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ServerPlayNetworkHandler.class)
public class ServerPlayNetworkHandlerMixin {

    @Shadow
    public ServerPlayerEntity player;

    @Inject(at=@At("HEAD"), method="executeCommand", cancellable = true)
    private void executeCommand(String input, CallbackInfo callbackInfo) {
        ActionResult res = PreExecuteCommandCallback.EVENT.invoker().preExecuteCommand(player, input);

        if(res == ActionResult.FAIL)
            callbackInfo.cancel();
    }
}
