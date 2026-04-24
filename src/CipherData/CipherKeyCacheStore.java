package CipherData;

public class CipherKeyCacheStore {
    private static volatile CipherKeyCache result;

    private CipherKeyCacheStore() {
    }

    public static void set(CipherKeyCache r) {
        result = r;
    }

    public static CipherKeyCache get() {
        if (result == null) {
            throw new IllegalStateException("Result not initialized");
        }
        return result;
    }
}
