package ryan.coords;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;

import java.util.Locale;

public class Coords implements ModInitializer {
    private final MinecraftClient mc = MinecraftClient.getInstance();

    @Override
    public void onInitialize() {
        HudRenderCallback.EVENT.register(((drawContext, tickDelta) -> {
            if (mc.player != null && !mc.getDebugHud().shouldShowDebugHud()) {
                Vec3d pos = mc.player.getPos();
                String coords = String.format(Locale.ROOT, "%.3f / %.5f / %.3f", pos.x, pos.y, pos.z);
                
                float yaw = MathHelper.wrapDegrees(mc.player.getYaw());
                float pitch = MathHelper.wrapDegrees(mc.player.getPitch());
                String facing = String.format(Locale.ROOT, "%.3f / %.3f", yaw, pitch);
                
                Direction direction = Direction.fromRotation(yaw);
                String strDir = String.valueOf(direction.getName().charAt(0));
                String sign = direction.getDirection() == Direction.AxisDirection.NEGATIVE ? " -" : " +";
                strDir += sign + direction.getAxis().getName().charAt(0);
                strDir = strDir.toUpperCase(Locale.ROOT);

                // y param is calculated by `2 + fontHeight * row` with fontHeight being 9
                drawContext.drawText(mc.textRenderer, coords, 2, 2, -1, true);
                drawContext.drawText(mc.textRenderer, facing, 2, 11, -1, true);
                drawContext.drawText(mc.textRenderer, strDir, 2, 20, -1, true);
            }
        }));
    }
}