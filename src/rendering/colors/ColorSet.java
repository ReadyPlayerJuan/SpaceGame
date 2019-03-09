package rendering.colors;

public enum ColorSet {
    /*GAME_BACKGROUND         (new float[] {0.0f, 0.0f, 0.0f}),
    GAME_BACKGROUND_STARS   (new float[] {0.0f, 0.0f, 0.0f}),
    GAME_BACKGROUND_PLANET  (new float[] {0.0f, 0.0f, 0.0f}),
    GAME_BACKGROUND_SUN     (new float[] {0.0f, 0.0f, 0.0f}),

    GAME_TEST_SHIP_1        (new float[] {1.0f, 0.0f, 0.0f,    1.0f, 0.5f, 0.0f,    1.0f, 1.0f, 0.0f}),
    GAME_TEST_SHIP_2        (new float[] {0.0f, 1.0f, 0.0f,    0.0f, 0.5f, 0.0f,    0.0f, 0.2f, 0.0f}),
    GAME_WEAPON             (new float[] {0.0f, 0.0f, 0.0f}),
    GAME_PROJECTILE         (new float[] {0.0f, 0.0f, 0.0f}),
    GAME_                   (new float[] {0.0f, 0.0f, 0.0f});*/
    GAME_BACKGROUND         (new float[] {0.0f, 0.0f, 0.0f,    1.0f, 1.0f, 1.0f}),
    GAME_TEST_SHIP_1        (new float[] {1.0f, 0.0f, 0.0f,    1.0f, 0.5f, 0.0f,    1.0f, 1.0f, 0.0f}),
    GAME_TEST_SHIP_2        (new float[] {0.0f, 1.0f, 0.0f,    0.0f, 0.5f, 0.0f,    0.0f, 0.2f, 0.0f}),
    GAME_WEAPON             (new float[] {0.0f, 0.0f, 0.0f}),
    GAME_PROJECTILE         (new float[] {0.0f, 0.0f, 0.0f}),
    GAME_                   (new float[] {0.0f, 0.0f, 0.0f});

    public float[] colorData;
    ColorSet(float[] colorData) {
        this.colorData = colorData;
    }
}
