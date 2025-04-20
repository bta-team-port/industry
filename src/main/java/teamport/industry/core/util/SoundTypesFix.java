package teamport.industry.core.util;

import com.b100.utils.StringUtils;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonParser;
import net.minecraft.core.sound.SoundTypes;
import teamport.industry.Industry;

import java.io.InputStream;

import static net.minecraft.core.sound.SoundTypes.register;

public class SoundTypesFix {
    private static final String format = "/assets/%s/sounds/sounds.json";

    private static JsonElement loadStreamAsElement(InputStream stream) throws JsonParseException {
        return JsonParser.parseString(StringUtils.readInputString(stream));
    }

    public static void loadSoundsJson(final String namespace) {
        final JsonObject o;
        try {
            o = loadStreamAsElement(SoundTypes.class.getResourceAsStream(String.format(format, namespace))).getAsJsonObject();
        } catch (final Exception e) {
            Industry.LOGGER.error("Failed to load sounds.json from '{}' as a json object!", namespace, e);
            return;
        }
        final boolean isMC = namespace.equals("minecraft");
        for (final String s : o.asMap().keySet()) {
            register(namespace + ":" + s);
            if (isMC) register(s);
        }
    }
}
