package managers;

import events.Wave;
import scenes.Playing;

import java.util.ArrayList;
import java.util.Arrays;

import static util.ConstantsUtil.Enemies.*;

public class WaveManager {

    private Playing playing;
    private ArrayList<Wave> waves = new ArrayList<>();
    private int enemySpawnTickLimit = 60 * 1;
    private int enemySpawnTick = enemySpawnTickLimit;
    private int enemyIndex, waveIndex;

    public WaveManager(Playing playing) {
        this.playing = playing;
        this.createWaves();
    }

    public void update() {
        if (this.enemySpawnTick < this.enemySpawnTickLimit)
            this.enemySpawnTick++;
    }

    public int getNextEnemy(){
        this.enemySpawnTick = 0;
        return this.waves.get(waveIndex).getEnemyList().get(enemyIndex++);
    }

    private void createWaves() {
        this.waves.add(new Wave(new ArrayList<Integer>(Arrays.asList(ORC, ORC, ORC, ORC, ORC, ORC, ORC, ORC, ORC, BAT))));
    }

    public ArrayList<Wave> getWaves() {
        return this.waves;
    }

    public boolean isTimeForNewEnemy() {
        return this.enemySpawnTick >= this.enemySpawnTickLimit;
    }

    public boolean isThereMoreEnemiesInWave(){
        return this.enemyIndex < this.waves.get(waveIndex).getEnemyList().size();
    }
}
