package concurrent.api.basics.utils;

public interface Derializer {
    <T> T derializer(byte[] var1, Class<T> var2);
}
