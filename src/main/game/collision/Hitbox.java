package main.game.collision;

import main.game.collision.projectiles.Projectile;
import main.game.enums.Team;

public class Hitbox {
    private HitboxController owner;
    private Team team;

    //private int colX;
    private double worldX, worldY;
    private double width, height;

    private boolean canCollide = true;

    public Hitbox(HitboxController owner, Team team, double worldX, double worldY, double width, double height) {
        this.owner = owner;
        this.team = team;
        this.worldX = worldX;
        this.worldY = worldY;
        this.width = width;
        this.height = height;
    }

    public void collide(Projectile p) {
        owner.collide(p);
    }

    public void setPosition(double x, double y) {
        this.worldX = x;
        this.worldY = y;
    }

    /*public int getColX() {
        return colX;
    }*/

    public void setOwner(HitboxController owner) {
        this.owner = owner;
    }

    public double getWorldX() {
        return worldX;
    }

    public double getWorldY() {
        return worldY;
    }

    public double getWidth() {
        return width;
    }

    public double getHeight() {
        return height;
    }

    public boolean canCollide() {
        return canCollide;
    }

    public Team getTeam() {
        return team;
    }

    public void setCanCollide(boolean canCollide) {
        this.canCollide = canCollide;
    }
}
