package com.crazedrunner.modularminer.common.util.helpers;


public class BaseMachineLogic {

    private int speed = 0;
    private int tick = 0;

    private boolean energized = true;
    private boolean redstoneSignal = false;
    private boolean ignoreRedstoneFlag = true;

    public BaseMachineLogic(int baseSpeed){
        this.speed = baseSpeed;
        this.tick = 0;
    }

    public boolean isRunning(){
        if(this.energized) {
            if (!this.ignoreRedstoneFlag){
                return this.redstoneSignal;
            }
            return true;
        }
        return false;
    }

    public boolean tick(){
        if (this.tick == this.speed){
            this.tick = 0;
           return this.isRunning();
        }
        return false;
    }

    public void togglePower(){
        this.energized = !this.energized;
    }

    public void toggleRedstoneSignal(){
        this.redstoneSignal = ! this.redstoneSignal;
    }

    public void toggleIgnoreRedstoneSignal(){
        this.ignoreRedstoneFlag = ! this.ignoreRedstoneFlag;
    }

    public boolean isEnergized(){
        return this.energized;
    }

    public boolean hasIgnoreRedstoneflag(){
        return this.ignoreRedstoneFlag;
    }

    public boolean hasRedstoneSignal(){
        return this.redstoneSignal;
    }
}
