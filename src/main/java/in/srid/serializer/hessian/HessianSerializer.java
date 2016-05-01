package in.srid.serializer.hessian;

import com.caucho.hessian.io.HessianInput;
import com.caucho.hessian.io.HessianOutput;
import in.srid.serializer.Deserializer;
import in.srid.serializer.Serializer;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class HessianSerializer implements Serializer, Deserializer {

    @Override
    public <T> byte[] serialize(final T source) {
        try {
            ByteArrayOutputStream bytes = new ByteArrayOutputStream();
            HessianOutput out = new HessianOutput(bytes);
            out.writeObject(source);
            out.flush();
            return bytes.toByteArray();
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }

    @Override
    public <T> T deserialize(byte[] bytes, Class<T> clazz) {
        HessianInput in = new HessianInput(new ByteArrayInputStream(bytes));
        try {
            return clazz.cast(in.readObject());
        } catch (IOException e) {
            throw new IllegalStateException(e);
        } finally {
            in.close();
        }
    }

}