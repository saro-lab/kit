package me.saro.kit;

import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;

/**
 * byte data
 * @author		PARK Yong Seo
 * @since		1.0.0
 */
public class ByteData {

    final private String charset;
    final private ByteBuffer byteBuffer;

    /**
     *
     * @param data
     * @param charset
     */
    public ByteData(String data, String charset) {
        try {
            byteBuffer = ByteBuffer.wrap(data.getBytes(charset));
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("this system does not support encoding for the `"+charset+"`");
        }
        this.charset = charset;
    }

    /**
     *
     * @param data
     * @param charset
     */
    public ByteData(byte[] data, String charset) {
        this.byteBuffer = ByteBuffer.wrap(data);
        this.charset = charset;
    }

    /**
     *
     * @param byteSize
     * @param charset
     */
    public ByteData(int byteSize, String charset) {
        this.byteBuffer = ByteBuffer.allocate(byteSize);
        this.charset = charset;
    }

    /**
     *
     * @return
     */
    public int limit() {
        return this.byteBuffer.limit();
    }

    /**
     *
     * @return
     */
    public int position() {
        return this.byteBuffer.position();
    }

    /**
     *
     * @return
     */
    public byte readByte() {
        return this.byteBuffer.get();
    }

    /**
     *
     * @return
     */
    public short readShort() {
        return byteBuffer.getShort();
    }

    /**
     *
     * @return
     */
    public int readInt() {
        return byteBuffer.getInt();
    }

    /**
     *
     * @return
     */
    public long readLong() {
        return byteBuffer.getLong();
    }

    /**
     *
     * @return
     */
    public float readFloat() {
        return byteBuffer.getFloat();
    }

    /**
     *
     * @return
     */
    public double readDouble() {
        return byteBuffer.getDouble();
    }

    /**
     *
     * @param size
     * @return
     */
    public byte[] readBytes(int size) {
        byte[] bytes = new byte[size];
        this.byteBuffer.get(bytes);
        return bytes;
    }

    /**
     *
     * @param size
     * @return
     */
    public String readString(int size) {
        try {
            return new String(readBytes(size), charset);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("this system does not support encoding for the `"+charset+"`");
        }
    }

    /**
     *
     * @param size
     * @param spaceCharacter
     * @return
     */
    public String readString(int size, char spaceCharacter) {
        return Texts.rtrim(readString(size), spaceCharacter);
    }

    /**
     *
     * @param size
     * @param spaceCharacter
     * @return
     */
    public String readStringRightAlign(int size, char spaceCharacter) {
        return Texts.ltrim(readString(size), spaceCharacter);
    }

    /**
     *
     * @param size
     * @return
     */
    public byte readTextByte(int size) {
        return Byte.parseByte(readString(size).replaceAll("[^0-9^\\.]+", ""));
    }

    /**
     *
     * @param size
     * @return
     */
    public short readTextShort(int size) {
        return Short.parseShort(readString(size).replaceAll("[^0-9^\\.]+", ""));
    }

    /**
     *
     * @param size
     * @return
     */
    public int readTextInt(int size) {
        return Integer.parseInt(readString(size).replaceAll("[^0-9^\\.]+", ""));
    }

    /**
     *
     * @param size
     * @return
     */
    public long readTextLong(int size) {
        return Long.parseLong(readString(size).replaceAll("[^0-9^\\.]+", ""));
    }

    /**
     *
     * @param size
     * @return
     */
    public float readTextFloat(int size) {
        return Float.parseFloat(readString(size).replaceAll("[^0-9^\\.]+", ""));
    }

    /**
     *
     * @param size
     * @return
     */
    public double readTextDouble(int size) {
        return Double.parseDouble(readString(size).replaceAll("[^0-9^\\.]+", ""));
    }

    /**
     *
     * @param b
     * @return
     */
    public ByteData writeByte(byte b) {
        byteBuffer.put(b);
        return this;
    }

    /**
     *
     * @param value
     * @return
     */
    public ByteData writeShort(short value) {
        byteBuffer.putShort(value);
        return this;
    }

    /**
     *
     * @param value
     * @return
     */
    public ByteData writeInt(int value) {
        byteBuffer.putInt(value);
        return this;
    }

    /**
     *
     * @param value
     * @return
     */
    public ByteData writeLong(long value) {
        byteBuffer.putLong(value);
        return this;
    }

    /**
     *
     * @param value
     * @return
     */
    public ByteData writeFloat(float value) {
        byteBuffer.putFloat(value);
        return this;
    }

    /**
     *
     * @param value
     * @return
     */
    public ByteData writeDouble(double value) {
        byteBuffer.putDouble(value);
        return this;
    }

    /**
     *
     * @param bytes
     * @return
     */
    public ByteData writeBytes(byte[] bytes) {
        byteBuffer.put(bytes);
        return this;
    }

    /**
     *
     * @param bytes
     * @param offset
     * @param length
     * @return
     */
    public ByteData writeBytes(byte[] bytes, int offset, int length) {
        byteBuffer.put(bytes, offset, length);
        return this;
    }

    /**
     *
     * @param repeatSize
     * @param b
     * @return
     */
    public ByteData writeRepeatByte(int repeatSize, byte b) {
        for (int i = 0 ; i < repeatSize ; i++) {
            byteBuffer.put(b);
        }
        return this;
    }

    /**
     * does not move position
     * @param size
     * @param b
     * @return
     */
    public ByteData fillByte(int offset, int size, byte b) {
        var pos = byteBuffer.position();
        byteBuffer.position(offset);
        for (int i = offset ; i < size ; i++) {
            byteBuffer.put(b);
        }
        byteBuffer.position(pos);
        return this;
    }

    /**
     *
     * @param text
     * @return
     */
    public ByteData writeString(String text) {
        try {
            if (text != null) {
                byteBuffer.put(text.getBytes(charset));
            }
            return this;
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("this system does not support encoding for the `"+charset+"`");
        }
    }

    public ByteData writeString(String text, int size) {
        return writeString(text, size, (byte)' ');
    }

    /**
     *
     * @param text
     * @param size
     * @param fillByte
     * @return
     */
    public ByteData writeString(String text, int size, byte fillByte) {
        try {
            if (text == null) {
                text = "";
            }
            byte[] bytes = text.getBytes(charset);
            if (bytes.length > size) {
                throw new ArrayIndexOutOfBoundsException("`" + text + "` is over " + size + " bytes");
            }
            byteBuffer.put(bytes);
            if (bytes.length < size) {
                writeRepeatByte(size - bytes.length, fillByte);
            }
            return this;
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("this system does not support encoding for the `"+charset+"`");
        }
    }

    /**
     *
     * @param text
     * @param size
     * @param fillByte
     * @return
     */
    public ByteData writeStringAlignRight(String text, int size, byte fillByte) {
        try {
            if (text == null) {
                text = "";
            }
            byte[] bytes = text.getBytes(charset);
            if (bytes.length > size) {
                throw new ArrayIndexOutOfBoundsException("`" + text + "` is over " + size + " bytes");
            }
            if (bytes.length < size) {
                writeRepeatByte(size - bytes.length, fillByte);
            }
            byteBuffer.put(bytes);
            return this;
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("this system does not support encoding for the `"+charset+"`");
        }
    }

    /**
     *
     * @param value
     * @param size
     * @param fillByte
     * @return
     */
    public ByteData writeTextByte(byte value, int size, byte fillByte) {
        writeString(Byte.toString(value), size, fillByte);
        return this;
    }

    /**
     *
     * @param value
     * @param size
     * @param fillByte
     * @return
     */
    public ByteData writeTextShort(short value, int size, byte fillByte) {
        writeString(Short.toString(value), size, fillByte);
        return this;
    }

    /**
     *
     * @param value
     * @param size
     * @param fillByte
     * @return
     */
    public ByteData writeTextInt(int value, int size, byte fillByte) {
        writeString(Integer.toString(value), size, fillByte);
        return this;
    }

    /**
     *
     * @param value
     * @param size
     * @param fillByte
     * @return
     */
    public ByteData writeTextLong(long value, int size, byte fillByte) {
        writeString(Long.toString(value), size, fillByte);
        return this;
    }

    /**
     *
     * @param value
     * @param size
     * @param fillByte
     * @return
     */
    public ByteData writeTextByteAlignRight(byte value, int size, byte fillByte) {
        writeStringAlignRight(Byte.toString(value), size, fillByte);
        return this;
    }

    /**
     *
     * @param value
     * @param size
     * @param fillByte
     * @return
     */
    public ByteData writeTextShortAlignRight(short value, int size, byte fillByte) {
        writeStringAlignRight(Short.toString(value), size, fillByte);
        return this;
    }

    /**
     *
     * @param value
     * @param size
     * @param fillByte
     * @return
     */
    public ByteData writeTextIntAlignRight(int value, int size, byte fillByte) {
        writeStringAlignRight(Integer.toString(value), size, fillByte);
        return this;
    }

    /**
     *
     * @param value
     * @param size
     * @param fillByte
     * @return
     */
    public ByteData writeTextLongAlignRight(long value, int size, byte fillByte) {
        writeStringAlignRight(Long.toString(value), size, fillByte);
        return this;
    }

    /**
     *
     * @return
     */
    public byte[] toBytes() {
        byte[] data = new byte[byteBuffer.position()];
        byteBuffer.compact();
        byteBuffer.get(data);
        return data;
    }
}
