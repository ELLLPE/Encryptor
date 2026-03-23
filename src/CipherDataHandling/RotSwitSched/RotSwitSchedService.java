package CipherDataHandling.RotSwitSched;

public class RotSwitSchedService {
    private final RotSwitSchedRepository repository;
    private RotSwitSched rotorSwitchSchedule;

    public RotSwitSchedService(RotSwitSchedRepository repository) {
        this.repository = repository;
        this.rotorSwitchSchedule = repository.loadRotorSwitchSchedule();
    }

    public int[][] getRotorSwitchSchedule() {
        return rotorSwitchSchedule.getRotorSwitchSchedule(); // If it works, it works
    }

    public int[] getDeflector() {
        return rotorSwitchSchedule.getDeflector();
    }

}
