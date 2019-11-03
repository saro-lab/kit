package me.saro.kit;

/**
 *
 * @param <T>
 */
public abstract class ClassSerializer<T> {

    public T deserialize(String data) {
        deserialize(new ByteData(data, charset()));
        return (T)this;
    }

    public T deserialize(byte[] data) {
        deserialize(new ByteData(data, charset()));
        return (T)this;
    }

    public byte[] serialize() {
        ByteData data = new ByteData(limit(), charset());
        serialize(data);
        return data.toBytes();
    }

    protected String charset() {
        return "UTF-8";
    }

    protected abstract void deserialize(ByteData data);

    protected abstract void serialize(ByteData data);

    protected abstract int limit();
}
