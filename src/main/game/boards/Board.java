package main.game.boards;

import main.game.collision.Hitbox;
import main.game.collision.projectiles.Projectile;
import main.game.ships.Ship;
import main.game.ships.ShipSection;
import main.views.GameView;
import rendering.Graphics;

import java.util.ArrayList;

public class Board {
    private final float LINE_WIDTH_PIXELS = 3.0f;

    private ArrayList<Projectile> projectiles = new ArrayList<>();
    private ArrayList<Hitbox> hitboxes = new ArrayList<>();

    private GameView parentView;
    private int numColumns;
    private float columnWidth = 1.0f;
    private float playableWidth, playableHeight;
    private float lineWidth;

    public Board(GameView parentVew, int numColumns, float height) {
        this.parentView = parentVew;
        this.numColumns = numColumns;

        this.playableWidth = columnWidth * numColumns;
        this.playableHeight = height;
    }

    public void update(double delta) {
        for(Projectile p: projectiles) {
            p.update(delta);
        }

        for(Hitbox h: hitboxes) {
            if(h.canCollide()) {
                for(Projectile p: projectiles) {
                    if(p.isColliding(h)) {
                        h.collide(p);
                        p.collide(h);
                    }
                }
            }
        }

        for(int i = 0; i < projectiles.size(); i++) {
            if(projectiles.get(i).shouldBeDestroyed()) {
                projectiles.remove(i);
                i--;
            }
        }
    }

    public void drawBackground(BoardCamera camera) {
        lineWidth = 2.0f * LINE_WIDTH_PIXELS / camera.getViewWidth();

        float screenUnitX = camera.getScreenUnitX();

        Graphics.drawQuad(
                -1, -1,
                1, -1,
                -1, 1,
                1, 1);

        for(int i = 0; i < numColumns+1; i++) {
            float colx = i - ((numColumns) / 2.0f)  - (camera.getCenterX() / columnWidth);
            Graphics.drawQuad(
                    colx * screenUnitX - lineWidth/2, -1,
                    colx * screenUnitX - lineWidth/2, 1,
                    colx * screenUnitX + lineWidth/2, -1,
                    colx * screenUnitX + lineWidth/2, 1);
        }
    }

    public void drawProjectiles(BoardCamera camera) {
        for(Projectile p: projectiles) {
            p.draw(camera);
        }
    }

    public void addShip(Ship s) {
        for(ShipSection section: s.getSections())
            hitboxes.add(section.getHitbox());
    }

    public void addProjectile(Projectile p) {
        projectiles.add(p);
    }

    public void clearHitboxes() {
        hitboxes.clear();
    }

    public void clearProjectiles() {
        projectiles.clear();
    }

    public int getNumColumns() {
        return numColumns;
    }

    /*public int getBoardWidth() {
        return boardColumns;
    }*/

    public float getPlayableWidth() {
        return playableWidth;
    }

    public float getPlayableHeight() {
        return playableHeight;
    }

    public float getColumnWidth() {
        return columnWidth;
    }
}
