package CipherDataHandling.Seed;

import CipherDataHandling.Core.SaveManager;

public class SeedRepository {

    private static final String PATH = "src/CipherData/SavedSeeds/seed.json";

    public Seed loadSeed() {
        Seed seed = SaveManager.load(PATH, Seed.class);
        if (seed == null) {
            throw new RuntimeException("No seed found at path: " + PATH);
        }
        return seed;
    }

    public void save(Seed seed) {
        SaveManager.save(PATH, seed);
    }
}
