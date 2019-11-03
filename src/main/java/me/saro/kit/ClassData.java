package me.saro.kit;

public abstract class ClassData<T> {

    public T bindClass(String data) {
        return toClass(new ByteData(data, charset()));
    }

    public T bindClass(byte[] data) {
        return toClass(new ByteData(data, charset()));
    }

    public byte[] toBytes() {
        ByteData data = new ByteData(limit(), charset());
        bindByteData(data);
        return data.toBytes();
    }

    protected abstract T toClass(ByteData data);

    protected abstract void bindByteData(ByteData data);

    protected String charset() {
        return "UTF-8";
    }

    protected abstract int limit();
}
