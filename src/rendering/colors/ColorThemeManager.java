package rendering.colors;

public class ColorThemeManager {
    private static final int MAX_COLORS = 5;

    private static final ColorTheme[] themes = {ColorTheme.GAME};
    private static float[][][] colorThemes;
    //first dimension: each theme - one for the game background, one foreground, one for the menu, etc.
    //second dimension: reach region in the theme
    //third dimension: color data for that region

    public static void init() {
        //create color data array
        colorThemes = new float[themes.length][][];
        for(int i = 0; i < themes.length; i++) {
            ColorRegion[] regionTypes = themes[i].regions;
            int numRegionTypes = regionTypes.length;

            colorThemes[i] = new float[numRegionTypes][];

            for(int j = 0; j < numRegionTypes; j++) {
                ColorRegion regionType = regionTypes[j];

                colorThemes[i][j] = new float[MAX_COLORS * 3];
                System.arraycopy(regionType.defaultData.colorData, 0, colorThemes[i][j], 0, regionType.defaultData.colorData.length);
            }
        }
    }

    public static float[][] getColorTheme(ColorTheme theme) {
        return colorThemes[theme.index];
    }

    public static void cleanUp() {
        //layeredColorShader.cleanUp();
    }
}
