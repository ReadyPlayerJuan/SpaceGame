package rendering.colors;

public enum ColorTheme {
    /*GAME_BACKGROUND (0, new ColorRegion[] {
            ColorRegion.GAME_BACKGROUND, ColorRegion.GAME_BACKGROUND_STARS, ColorRegion.GAME_BACKGROUND_PLANET,
            ColorRegion.GAME_BACKGROUND_SUN}),
    GAME_FOREGROUND (1, new ColorRegion[] {
            ColorRegion.GAME_PLAYER_SHIP, ColorRegion.GAME_ENEMY_SHIP, ColorRegion.GAME_WEAPON,
            ColorRegion.GAME_PROJECTILE, ColorRegion.GAME_});*/
    GAME (0, new ColorRegion[] {
            ColorRegion.GAME_BACKGROUND, ColorRegion.GAME_PLAYER_SHIP, ColorRegion.GAME_ENEMY_SHIP,
            ColorRegion.GAME_WEAPON, ColorRegion.GAME_});

    public int index;
    public ColorRegion[] regions;
    ColorTheme(int index, ColorRegion[] regions) {
        this.index = index;
        this.regions = regions;
    }
}
