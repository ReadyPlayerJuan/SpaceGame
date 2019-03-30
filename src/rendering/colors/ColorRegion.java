package rendering.colors;

public enum ColorRegion {
    /*GAME_BACKGROUND         (0, ColorSet.GAME_BACKGROUND),
    GAME_BACKGROUND_STARS   (1, ColorSet.GAME_BACKGROUND_STARS),
    GAME_BACKGROUND_PLANET  (2, ColorSet.GAME_BACKGROUND_PLANET),
    GAME_BACKGROUND_SUN     (3, ColorSet.GAME_BACKGROUND_SUN),

    GAME_PROJECTILE         (0, ColorSet.GAME_PROJECTILE),
    GAME_PLAYER_SHIP        (1, ColorSet.GAME_TEST_SHIP_1),
    GAME_ENEMY_SHIP         (2, ColorSet.GAME_TEST_SHIP_2),
    GAME_WEAPON             (3, ColorSet.GAME_WEAPON),
    GAME_                   (4, ColorSet.GAME_);*/
    GAME_BACKGROUND         (0, ColorSet.GAME_BACKGROUND),
    GAME_PLAYER_SHIP        (1, ColorSet.GAME_TEST_SHIP_1),
    GAME_ENEMY_SHIP         (2, ColorSet.GAME_TEST_SHIP_2),
    GAME_PROJECTILE         (3, ColorSet.GAME_PROJECTILE),
    GAME_                   (4, ColorSet.GAME_);

    //HUD_

    public int i;
    public ColorSet defaultData;
    ColorRegion(int i, ColorSet defaultData) {
        this.i = i;
        this.defaultData = defaultData;
    }
}
