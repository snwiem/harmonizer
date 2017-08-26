package org.syracus.harmonizer.theory;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class KeyTest {

    private static final String[] NOTES_SHARP = new String[]{
            "C", "C#", "D", "D#", "E", "F", "F#", "G", "G#", "A", "A#", "B"
    };
    private static final String[] NOTES_FLAT = new String[]{
            "C", "Db", "D", "Eb", "E", "F", "Gb", "G", "Ab", "A", "Bb", "B"
    };

    @Test
    public void testGetSemitones() throws Exception {
        Key key = null;

        for (int i = 0; i < NOTES_SHARP.length; ++i) {
            key = new Key(NOTES_SHARP[i]);
            assertEquals(i, key.getSemitones());
        }

        for (int i = 0; i < NOTES_FLAT.length; ++i) {
            key = new Key(NOTES_FLAT[i]);
            assertEquals(i, key.getSemitones());
        }

    }

    @Test
    public void testGetSignature() throws Exception {
        Key key = null;

        for (int i = 0; i < NOTES_SHARP.length; ++i) {
            key = new Key(NOTES_SHARP[i]);
            switch(i) {
                case 1:
                case 3:
                case 6:
                case 8:
                case 10:
                    assertTrue(key.getSignature().isPresent());
                    assertEquals(Signature.Sharp, key.getSignature().get());
                    break;
                default:
                    assertFalse(key.getSignature().isPresent());
            }
        }

        for (int i = 0; i < NOTES_FLAT.length; ++i) {
            key = new Key(NOTES_FLAT[i]);
            switch(i) {
                case 1:
                case 3:
                case 6:
                case 8:
                case 10:
                    assertTrue(key.getSignature().isPresent());
                    assertEquals(Signature.Flat, key.getSignature().get());
                    break;
                default:
                    assertFalse(key.getSignature().isPresent());
            }
        }

    }

    @Test
    public void testGetOctave() throws Exception {
        Key key = null;

        for (int i = 0; i < NOTES_SHARP.length; ++i) {
            key = new Key(NOTES_SHARP[i]);
            assertFalse(key.getOctave().isPresent());
        }

        for (int i = 0; i < NOTES_FLAT.length; ++i) {
            key = new Key(NOTES_FLAT[i]);
            assertFalse(key.getOctave().isPresent());
        }


        int[] testOctaves = new int[]{ 0, 4, 7, 9 };
        for (int o = 0; o < testOctaves.length; ++o) {
            for (int i = 0; i < NOTES_SHARP.length; ++i) {
                key = new Key(NOTES_SHARP[i] + String.valueOf(o));
                assertTrue(key.getOctave().isPresent());
                assertEquals(o, key.getOctave().get().intValue());
            }

            for (int i = 0; i < NOTES_FLAT.length; ++i) {
                key = new Key(NOTES_FLAT[i] + String.valueOf(o));
                assertTrue(key.getOctave().isPresent());
                assertEquals(o, key.getOctave().get().intValue());
            }

        }

    }

}