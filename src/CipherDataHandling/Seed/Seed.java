package CipherDataHandling.Seed;

public class Seed {
    private String seedName; // Name of the seed, e.g., "MySeed"
    private String seed;
    private int seedLength;
    private int[] seedSpeeds;
    private int[] seedRotorPositions;
    private int[] seedRotorSchedules;

    public Seed(String seedName, String seed, int seedLength, int[] seedSpeeds, int[] seedRotorPositions,
            int[] seedRotorSchedules) {
        this.seedName = seedName;
        this.seed = seed;
        this.seedLength = seedLength;
        this.seedSpeeds = seedSpeeds;
        this.seedRotorPositions = seedRotorPositions;
        this.seedRotorSchedules = seedRotorSchedules;
    }

    public String getSeedName() {
        return seedName;
    }

    public String getSeed() {
        return seed;
    }

    public int getSeedLength() {
        return seedLength;
    }

    public int[] getSeedSpeeds() {
        return seedSpeeds;
    }

    public int[] getSeedRotorPositions() {
        return seedRotorPositions;
    }

    public int[] getSeedRotorSchedules() {
        return seedRotorSchedules;
    }

    public void setSeedName(String seedName) {
        this.seedName = seedName;
    }

    public void setSeed(String seed) {
        this.seed = seed;
    }

    public void setSeedLength(int seedLength) {
        this.seedLength = seedLength;
    }

    public void setSeedSpeeds(int[] seedSpeeds) {
        this.seedSpeeds = seedSpeeds;
    }

    public void setSeedRotorPositions(int[] seedRotorPositions) {
        this.seedRotorPositions = seedRotorPositions;
    }

    public void setSeedRotorSchedules(int[] seedRotorSchedules) {
        this.seedRotorSchedules = seedRotorSchedules;
    }
}
