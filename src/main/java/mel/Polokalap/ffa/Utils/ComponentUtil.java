package mel.Polokalap.ffa.Utils;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextDecoration;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver;

import static mel.Polokalap.ffa.Main.getInstance;

public class ComponentUtil {

    public static Component getComponent(String path) {

        return MiniMessage.miniMessage().deserialize(
                getInstance().getConfig().getString(path, "?")
        ).decorationIfAbsent(TextDecoration.ITALIC, TextDecoration.State.FALSE);

    }

    public static Component getComponent(String path, TagResolver... resolvers) {

        String raw = getInstance().getConfig().getString(path, "?");
        return MiniMessage.miniMessage().deserialize(raw, resolvers);

    }

}
