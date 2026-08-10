package mel.Polokalap.ffa.Utils;

import java.time.Duration;

import static mel.Polokalap.ffa.Main.getInstance;

public class States {

    public enum BlockState {
        NONE,
        PLACE,
        BREAK,
        BOTH;

        public BlockState next() {
            BlockState[] values = values();
            return values[(this.ordinal() + 1) % values.length];
        }

    }

    public enum DecayTime {
        FIVE_SECONDS(Duration.ofSeconds(5)),
        TEN_SECONDS(Duration.ofSeconds(10)),
        THIRTY_SECONDS(Duration.ofSeconds(30)),
        ONE_MINUTE(Duration.ofMinutes(1)),
        THREE_MINUTES(Duration.ofMinutes(3)),
        NEVER(null);

        private final Duration duration;

        DecayTime(Duration duration) {
            this.duration = duration;
        }

        public boolean isNever() {
            return duration == null;
        }

        public long toTicks() {
            return duration.toMillis() / 50;
        }

        public DecayTime next() {
            DecayTime[] values = values();
            return values[(this.ordinal() + 1) % values.length];
        }

    }

    public enum RegenerationTime {
        THIRTY_MINUTES(Duration.ofMinutes(30)),
        ONE_HOUR(Duration.ofHours(1)),
        SIX_HOURS(Duration.ofHours(6)),
        ONE_DAY(Duration.ofMinutes(1)),
        NEVER(null);

        private final Duration duration;

        RegenerationTime(Duration duration) {
            this.duration = duration;
        }

        public boolean isNever() {
            return duration == null;
        }

        public long toTicks() {
            return duration.toMillis() / 50;
        }

        public RegenerationTime next() {
            RegenerationTime[] values = values();
            return values[(this.ordinal() + 1) % values.length];
        }

    }

    public enum ExplosionState {
        NONE,
        DAMAGE,
        BREAK,
        BOTH;

        public ExplosionState next() {
            ExplosionState[] values = values();
            return values[(this.ordinal() + 1) % values.length];
        }

    }

    public static String getString(Object obj) {

        if (obj instanceof BlockState state) {

            String key = switch (state) {
                case NONE -> "none";
                case PLACE -> "place";
                case BREAK -> "break";
                case BOTH -> "both";
            };

            return getInstance().getConfig().getString("states.block-state." + key);

        }

        if (obj instanceof DecayTime time) {

            String key = switch (time) {
                case FIVE_SECONDS -> "five-seconds";
                case TEN_SECONDS -> "ten-seconds";
                case THIRTY_SECONDS -> "thirty-seconds";
                case ONE_MINUTE -> "one-minute";
                case THREE_MINUTES -> "three-minutes";
                case NEVER -> "never";
            };

            return getInstance().getConfig().getString("states.decay-time." + key);

        }

        if (obj instanceof RegenerationTime time) {

            String key = switch (time) {
                case THIRTY_MINUTES -> "thirty-minutes";
                case ONE_HOUR -> "one-hour";
                case SIX_HOURS -> "six-hours";
                case ONE_DAY -> "one-day";
                case NEVER -> "never";
            };

            return getInstance().getConfig().getString("states.regeneration-time." + key);

        }

        if (obj instanceof ExplosionState state) {

            String key = switch (state) {
                case NONE -> "none";
                case DAMAGE -> "damage";
                case BREAK -> "break";
                case BOTH -> "both";
            };

            return getInstance().getConfig().getString("states.explosion-state." + key);

        }

        if (obj instanceof Boolean state) {

            String key = Boolean.toString(state);

            return getInstance().getConfig().getString("states.boolean." + key);

        }

        return "?";

    }

}
