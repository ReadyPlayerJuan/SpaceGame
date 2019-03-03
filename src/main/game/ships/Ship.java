package main.game.ships;

import main.game.enums.Team;
import main.views.GameView;

import java.util.ArrayList;

public abstract class Ship {
    protected GameView parentView;

    protected int columnPosition, shipWidth;
    protected Team team;

    protected ShipSection[] sections;
    protected int focusedSection;

    protected ArrayList<ShipAction> globalActionInfo = new ArrayList<>();

    public void update(double delta) {
        for(ShipSection s: sections) {
            s.update(delta);
        }
    }

    public void setTeam(Team team) {
        this.team = team;
    }
}
