package io.github.vladimirmi.localradio.data.service;

import android.support.v4.media.MediaMetadataCompat;

import io.github.vladimirmi.localradio.utils.StringUtils;

/**
 * Created by Vladimir Mikhalev 07.04.2018.
 */

public class Metadata {

    private static final String unsupported = "unsupported";

    public final String artist;
    public final String title;
    public final boolean isSupported;

    private Metadata(String artist, String title) {
        this.artist = artist;
        this.title = title;
        isSupported = !artist.equals(unsupported) && !title.equals(unsupported);
    }

    public static Metadata UNSUPPORTED = new Metadata(unsupported, unsupported);

    public static Metadata create(String meta) {
        String artistTitle = new StringUtils.Builder(meta).substringAfter("StreamTitle=", unsupported)
                .substringBefore(";")
                .trim(' ', '\'')
                .toString();

        String[] strings;
        if (artistTitle.contains(" - ")) {
            strings = artistTitle.split(" - ", 2);
        } else {
            strings = new String[]{"", artistTitle};
        }

        String artist = strings[0];
        String title = strings[1];

        if (title.isEmpty()) return UNSUPPORTED;
        if (title.endsWith("]")) title = StringUtils.substringBeforeLast(title, "[", title);

        return new Metadata(artist.trim(), title.trim());
    }

    @SuppressWarnings("ConstantConditions")
    public static Metadata create(MediaMetadataCompat meta) {
        return new Metadata(
                meta.getDescription().getSubtitle().toString(),
                meta.getDescription().getTitle().toString()
        );
    }

    public MediaMetadataCompat toMediaMetadata() {
        return new MediaMetadataCompat.Builder()
                .putString(MediaMetadataCompat.METADATA_KEY_ARTIST, artist)
                .putString(MediaMetadataCompat.METADATA_KEY_TITLE, title)
                .build();
    }

    public String toLogString() {
        if (!isSupported) {
            return "Metadata(Unsupported)";
        } else {
            return String.format("Metadata(artist='%s', title='%s')", artist, title);
        }
    }

    @Override
    public String toString() {
        return String.format("%s - %s", artist, title);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Metadata metadata = (Metadata) o;

        return artist.equals(metadata.artist) && title.equals(metadata.title);
    }

    @Override
    public int hashCode() {
        int result = artist.hashCode();
        result = 31 * result + title.hashCode();
        return result;
    }
}