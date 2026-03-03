package CipherDataHandling.RotSwitSched;

public class RotSwitSched {
    private int[] deflector;
    private int[][] rotorSwitchSchedule;

    public RotSwitSched(int[] deflector, int[][] rotorSwitchSchedule) {
        this.deflector = deflector;
        this.rotorSwitchSchedule = rotorSwitchSchedule;
    }

    public int[] getDeflector() {
        return deflector;
    }

    public int[][] getRotorSwitchSchedule() {
        return rotorSwitchSchedule;
    }
}
