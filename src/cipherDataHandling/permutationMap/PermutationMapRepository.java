package cipherDataHandling.permutationMap;

import cipherDataHandling.Core.SaveManager;

public class PermutationMapRepository {

    private static final String PATH = "src/CipherData/permutationMap/permutationMap.json";

    public PermutationMap loadPermutationMap() {
        PermutationMap permutationMap = SaveManager.load(PATH, PermutationMap.class);
        if (permutationMap == null) {
            throw new RuntimeException("No PermutationMap found at path: " + PATH);
        }
        return permutationMap;
    }

    public void savePermutationMap(PermutationMap permutationMap) {
        SaveManager.save(PATH, permutationMap);
    }

}
