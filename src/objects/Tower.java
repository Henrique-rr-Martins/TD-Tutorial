package objects;

import static util.ConstantsUtil.Towers.*;

public class Tower {
    private int x, y, id, towerType, cooldownTick, dmg;
    private float range, cooldown;
    private int tier;

    public Tower(int x, int y, int id, int towerType){
        this.x = x;
        this.y = y;
        this.id = id;
        this.towerType = towerType;
        this.tier = 1;
        this.setDefaultDamage();
        this.setDefaultRange();
        this.setDefaultCooldown();
        this.cooldownTick = 0;
    }

    public void update(){
        this.cooldownTick++;
    }

    public void upgradeTower(){

        if(this.tier < 3) {
            this.tier++;

            switch (towerType) {
                case ARCHER:
                    dmg += 2;
                    range += 20;
                    cooldown -= 5;
                    break;
                case CANNON:
                    dmg += 5;
                    range += 20;
                    cooldown -= 15;
                    break;
                case WIZARD:
                    range += 20;
                    cooldown -= 10;
                    break;
            }
        }
    }

    public boolean isCooldownOver() { return this.cooldownTick >= this.cooldown; }

    public void resetCooldown() {
        this.cooldownTick = 0;
    }

    private void setDefaultCooldown() {
        this.cooldown = getDefaultCooldown(this.towerType);
    }

    private void setDefaultRange() {
        this.range = getDefaultRange(this.towerType);
    }

    private void setDefaultDamage() {
        this.dmg = getStartDamage(this.towerType);
    }

    // getters
    public int getX() { return x; }
    public void setX(int x) { this.x = x; }
    public int getY() { return y; }
    public void setY(int y) { this.y = y; }
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public int getTowerType() { return towerType; }
    public void setTowerType(int towerType) { this.towerType = towerType; }
    public int getDmg() { return dmg; }
    public float getRange() { return this.range; }
    public float getCooldown() { return this.cooldown; }
    public int getTier(){ return this.tier; }
}
