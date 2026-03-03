package CipherDataHandling.RotSwitSched;

import CipherDataHandling.Core.SaveManager;

public class RotSwitSchedRepository {
    private static final String PATH = "src/CipherData/RotorSwitchSchedules/rotorSwitchSchedule.json";

    public RotSwitSched loadRotorSwitchSchedule() {
        RotSwitSched rotorSwitchSchedule = SaveManager.load(PATH, RotSwitSched.class);
        if (rotorSwitchSchedule == null) {
            throw new RuntimeException("No rotor switch schedule found at path: " + PATH);
        }
        return rotorSwitchSchedule;
    }
}
