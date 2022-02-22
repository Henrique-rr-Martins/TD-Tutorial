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
    private boolean waveStartTimer, waveTickTimerOver;
    private int waveTickLimit = 60 * 5;
    private int waveTick = 0;

    public WaveManager(Playing playing) {
        this.playing = playing;
        this.createWaves();
    }

    public void update() {
        if (this.enemySpawnTick < this.enemySpawnTickLimit)
            this.enemySpawnTick++;

        if(this.waveStartTimer){
            this.waveTick++;
            if(this.waveTick >= this.waveTickLimit){
                this.waveTickTimerOver = true;
            }
        }
    }

    public void increaseWaveIndex(){
        this.waveIndex++;
        this.waveTick = 0;
        this.waveTickTimerOver = false;
        this.waveStartTimer = false;
    }
    
    public void startWaveTimer() {
        this.waveStartTimer = true;
    }

    public int getNextEnemy(){
        this.enemySpawnTick = 0;
        return this.waves.get(waveIndex).getEnemyList().get(enemyIndex++);
    }

    private void createWaves() {
        this.waves.add(new Wave(new ArrayList<Integer>(Arrays.asList(ORC, ORC, ORC, ORC, ORC, ORC, ORC, ORC, ORC, BAT))));
        this.waves.add(new Wave(new ArrayList<Integer>(Arrays.asList(KNIGHT, ORC, ORC, ORC, ORC, ORC, ORC, ORC, ORC, BAT))));
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
    public boolean isThereMoreWaves() { return this.waveIndex + 1 < this.waves.size(); }
    public boolean isWaveTimerOver() { return this.waveTickTimerOver; }
    public void resetEnemyIndex() { this.enemyIndex = 0; }
    public int getWaveIndex(){ return this.waveIndex; }
    public float getTimeLeft(){
        float ticksLeft = this.waveTickLimit - waveTick;
        return (float) ticksLeft / 60;
    }

    public boolean isWaveTimerStarted() {
        return this.waveStartTimer;
    }
}
