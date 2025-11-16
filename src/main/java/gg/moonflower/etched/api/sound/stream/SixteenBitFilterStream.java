package gg.moonflower.etched.api.sound.stream;

import net.minecraft.client.sounds.AudioStream;

import javax.sound.sampled.AudioFormat;
import java.io.IOException;
import java.nio.ByteBuffer;

/**
 * Reduces the bit depth of an audio stream to create a retro, "lo-fi" sound effect.
 * This is used to emulate the sound of older video game consoles, such as the SNES.
 */
public class SixteenBitFilterStream implements AudioStream {

    private final AudioStream source;
    private final int bits;
    private final int bytes;

    public SixteenBitFilterStream(AudioStream source, int bits) {
        this.source = source;
        this.bits = bits;
        this.bytes = this.bits / 8;
    }

    @Override
    public AudioFormat getFormat() {
        return this.source.getFormat();
    }

    @Override
    public ByteBuffer read(int amount) throws IOException {
        ByteBuffer buffer = this.source.read(amount);
        if (this.bytes > 0) {
            int shift = 16 - this.bits;
            for (int i = 0; i < buffer.limit() / 2; i++) {
                short sample = buffer.getShort(i * 2);
                sample = (short) ((sample >> shift) << shift);
                buffer.putShort(i * 2, sample);
            }
        }
        return buffer;
    }

    @Override
    public void close() throws IOException {
        this.source.close();
    }
}
