package org.syracus.harmonizer.theory;

import org.syracus.harominzer.common.tuples.Tuple3;

import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Key {

    private static final String[][] NOTE_NAMES = new String[][] {
        { "C", "C#", "D", "D#", "E", "F", "F#", "G", "G#", "A", "A#", "B" },
        { "C", "Db", "D", "Eb", "E", "F", "Gb", "G", "Ab", "A", "Bb", "B" }
    };

    private final int semitones;
    private final Optional<Integer> octave;
    private final Optional<Signature> signature;

    private Key(int semitones, Optional<Signature> signature, Optional<Integer> octave) {
        this.semitones = semitones;
        this.signature = signature;
        this.octave = octave;
    }

    public Key(Key key) {
        this.semitones = key.semitones;
        this.signature = key.signature;
        this.octave = key.octave;
    }

    public Key(String key) {
        final Tuple3<Integer, Optional<Signature>, Optional<Integer>> parsedKey = parseKeyString(key);
        this.semitones = parsedKey.getT1();
        this.signature = parsedKey.getT2();
        this.octave = parsedKey.getT3();
    }

    public int getSemitones() {
        return this.semitones;
    }

    public Optional<Signature> getSignature() {
        return this.signature;
    }

    public Optional<Integer> getOctave() {
        return this.octave;
    }

    private static final Pattern NOTE_PATTERN = Pattern.compile("([A-G]{1})([#b]?)(\\d{0,2})");
    private static final int DEFAULT_OCTAVE = 4;

    private static Tuple3<Integer, Optional<Signature>, Optional<Integer>> parseKeyString(String keyString) {
        Matcher matcher = NOTE_PATTERN.matcher(keyString);
        if (false == matcher.matches())
            throw new IllegalArgumentException(String.format("Invalid note string '%s'.", keyString));
        String keyName = matcher.group(1);
        Optional<Signature> signature = parseSignature(matcher.group(2));
        Optional<Integer> octave = parseOctave(matcher.group(3));
        return new Tuple3<>(findSemitoneForKeyName(keyName, signature), signature, octave);
    }

    private static final int findSemitoneForKeyName(String keyName, Optional<Signature> signature) {
        String note = String.format("%s%s", keyName, toString(signature));

        if (signature.isPresent()) {
            for (int i = 0; i < NOTE_NAMES[signature.get().ordinal()].length; ++i) {
                if (NOTE_NAMES[signature.get().ordinal()][i].equals(note))
                    return i;
            }
        } else {
            for (int j = 0; j < NOTE_NAMES.length; ++j) {
                for (int i = 0; i < NOTE_NAMES[j].length; ++i) {
                    if (NOTE_NAMES[j][i].equals(note))
                        return i;
                }
            }
        }
        throw new IllegalArgumentException(String.format("Invalid key name '%s'", keyName));
    }

    private static Optional<Signature> parseSignature(String signatureString) {
        if (null == signatureString || signatureString.isEmpty())
            return Optional.empty();
        if ("#".equals(signatureString))
            return Optional.of(Signature.Sharp);
        if ("b".equals(signatureString))
            return Optional.of(Signature.Flat);
        throw new IllegalArgumentException(String.format("Invalid signature string '%s'.", signatureString));
    }

    private static String toString(Optional<Signature> signature) {
        return signature.map(s -> {
            switch (s) {
                case Sharp:
                    return "#";
                case Flat:
                    return "b";
                default:
                    throw new IllegalArgumentException(String.format("Invalid signature '%s'.", signature.get()));
            }
        }).orElse("");
    }

    private static Optional<Integer> parseOctave(String octaveString) {
        if (null == octaveString || octaveString.isEmpty())
            return Optional.empty();
        try {
            int octaveInt = Integer.parseInt(octaveString);
            return Optional.of(octaveInt);
        } catch(NumberFormatException e) {
            throw new IllegalArgumentException(String.format("Invalid octave '%s'.", octaveString));
        }
    }

    public static class Builder {

        private int semitones;
        private Optional<Signature> signature;
        private Optional<Integer> octave;

        public Builder key(String note) {
            final Tuple3<Integer, Optional<Signature>, Optional<Integer>> parsedNote = parseKeyString(note);
            this.semitones = parsedNote.getT1();
            this.signature = parsedNote.getT2();
            this.octave = parsedNote.getT3();
            return this;
        }

        public Builder signature(Signature signature) {
            this.signature = Optional.of(signature);
            return this;
        }

        public Builder octave(int octave) {
            this.octave = Optional.of(octave);
            return this;
        }

        public Key build() {
            return new Key(this.semitones, this.signature, this.octave);
        }
    }
}
