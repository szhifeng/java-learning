package concurrent.api.basics.utils;


import com.caucho.hessian.io.Hessian2Input;

import java.io.ByteArrayInputStream;
import java.io.IOException;

public class Hessian2Derializer implements Derializer {
    public static final String NAME = "Hessian2Derializer";

    public Hessian2Derializer() {
    }

    public <T> T derializer(byte[] data, Class<T> clazz) {
        if (data != null && data.length != 0) {
            Hessian2Input hessian2Input = new Hessian2Input(new ByteArrayInputStream(data));

            Object var5;
            try {
                Object var4 = clazz.cast(hessian2Input.readObject(clazz));
                return (T) var4;
            } catch (Exception var15) {
                System.out.printf("deserialize %s error,%s", clazz, var15.getMessage());
                var5 = null;
            } finally {
                try {
                    hessian2Input.close();
                } catch (IOException var14) {
                }

            }

            return (T) var5;
        } else {
            return null;
        }
    }
}

