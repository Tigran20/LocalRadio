package io.github.vladimirmi.localradio.data.service.player;

import android.support.v4.media.session.MediaSessionCompat;

/**
 * Created by Vladimir Mikhalev 07.04.2018.
 */

public class SessionCallback extends MediaSessionCompat.Callback {

    private final Interface callback;

    @SuppressWarnings("WeakerAccess")
    public SessionCallback(Interface callback) {
        super();
        this.callback = callback;
    }

    @Override
    public void onPlay() {
        callback.onPlayCommand();
    }

    @Override
    public void onPause() {
        callback.onPauseCommand(Playback.STOP_DELAY);
    }

    @Override
    public void onStop() {
        callback.onStopCommand();
    }

    @Override
    public void onSkipToPrevious() {
        callback.onSkipToPreviousCommand();
    }

    @Override
    public void onSkipToNext() {
        callback.onSkipToNextCommand();
    }

    interface Interface {

        void onPlayCommand();

        void onPauseCommand(long stopDelay);

        void onStopCommand();

        void onSkipToPreviousCommand();

        void onSkipToNextCommand();
    }
}
