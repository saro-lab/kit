package me.saro.kit;

import java.io.UnsupportedEncodingException;

/**
 *
 * @param <T>
 */
public abstract class ClassSerializer<T> {

    public T read(String data) {
        read(new ByteData(data, charsetByteData()));
        return (T)this;
    }

    public T read(byte[] data) {
        read(new ByteData(data, charsetByteData()));
        return (T)this;
    }

    public byte[] toBytes() {
        ByteData data = new ByteData(limitSizeByteData(), charsetByteData());
        write(data);
        return data.toBytes();
    }

    public String toSerializeString() {
        try {
            return new String(toBytes(), charsetByteData());
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("this system does not support `"+charsetByteData()+"` encoding");
        }
    }

    protected String charsetByteData() {
        return "UTF-8";
    }

    /**
     * read byteData
     * @param data
     */
    protected abstract void read(ByteData data);

    /**
     * write byteData
     * @param data
     */
    protected abstract void write(ByteData data);

    /**
     * limit size byte data
     * @return
     */
    protected abstract int limitSizeByteData();
}
