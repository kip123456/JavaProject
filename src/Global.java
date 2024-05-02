public class Global {
    public static final int TICK_PER_SEC = 50;
    public static final int CHANNEL_WIDTH = 4;
    public static final int CHANNEL_HEIGHT = 150;
    public static final int CHANNEL_COUNT = 4;
    public static final int WINDOW_WIDTH = 800;
    public static final int CHANNEL_PADDING=100;
    public static final int GENERATE_PADDING=25;
    public static enum MovingState {
        UP,
        DOWN,
        LEFT,
        RIGHT,
        STOP
    }
    public static final int TICKS_PER_PLAYER_MOVE = 4;
}
