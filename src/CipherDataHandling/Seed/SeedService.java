package CipherDataHandling.Seed;

public class SeedService {

    private final SeedRepository repository;
    private final Seed seed;

    public SeedService(SeedRepository repository) {
        this.repository = repository;
        this.seed = repository.loadSeed();
    }

    public void updateSeed(String seedName, String seedValue, int seedLength, int[] seedSpeeds,
            int[] seedRotorPositions, int[] seedRotorSchedules) {

        seed.setSeedName(seedName);
        seed.setSeed(seedValue);
        seed.setSeedLength(seedLength);
        seed.setSeedSpeeds(seedSpeeds);
        seed.setSeedRotorPositions(seedRotorPositions);
        seed.setSeedRotorSchedules(seedRotorSchedules);
        repository.save(seed);
    }

    public int[][] getCurrentSeed() {
        return new int[][] { seed.getSeedSpeeds(), seed.getSeedRotorPositions(), seed.getSeedRotorSchedules() };
    }

}
