package CipherDataHandling.RotSwitSched;

public class RotSwitSchedService {
    private final RotSwitSchedRepository repository;
    private RotSwitSched rotorSwitchSchedule;

    public RotSwitSchedService(RotSwitSchedRepository repository) {
        this.repository = repository;
        this.rotorSwitchSchedule = repository.loadRotorSwitchSchedule();
    }

    public RotSwitSched getUser() {
        return rotorSwitchSchedule;
    }

    public int[][] getRotorSwitchSchedule() {
        return rotorSwitchSchedule.getRotorSwitchSchedule();
    }

    public int[] getDeflector() {
        return rotorSwitchSchedule.getDeflector();
    }

}
