package cipherDataHandling.permutationMap;

public class PermutationMapService {
    private final PermutationMapRepository repository;
    private final PermutationMap permutationMap;

    public PermutationMapService(PermutationMapRepository repository) {
        this.repository = repository;
        this.permutationMap = repository.loadPermutationMap();
    }

    public int[][] getPermutationMap() {
        return permutationMap.getPermutationMap();
    }

    public void setPermutationMap(int[][] permutationMap) {
        this.permutationMap.setPermutationMap(permutationMap);
        repository.savePermutationMap(this.permutationMap);
    }
}
