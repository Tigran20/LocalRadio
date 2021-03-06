package io.github.vladimirmi.localradio.presentation.playercontrol;

import android.graphics.PorterDuff;
import android.support.constraint.ConstraintLayout;
import android.support.transition.ChangeBounds;
import android.support.transition.TransitionManager;
import android.support.v4.view.animation.FastOutSlowInInterpolator;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import butterknife.BindView;
import io.github.vladimirmi.localradio.R;
import io.github.vladimirmi.localradio.data.entity.Station;
import io.github.vladimirmi.localradio.di.Scopes;
import io.github.vladimirmi.localradio.presentation.core.BaseFragment;
import io.github.vladimirmi.localradio.utils.NonSwipeableViewPager;
import io.github.vladimirmi.localradio.utils.UiUtils;
import io.github.vladimirmi.playerbutton.PlayerButton;

/**
 * Created by Vladimir Mikhalev 08.04.2018.
 */

public class PlayerControlFragment extends BaseFragment<PlayerControlPresenter> implements PlayerControlView {

    @BindView(R.id.root) ConstraintLayout root;
    @BindView(R.id.iconIv) ImageView iconIv;
    @BindView(R.id.previousBt) Button previousBt;
    @BindView(R.id.playPauseBt) PlayerButton playPauseBt;
    @BindView(R.id.loadingPb) ProgressBar loadingPb;
    @BindView(R.id.nextBt) Button nextBt;
    @BindView(R.id.favoriteBt) Button favoriteBt;
    @BindView(R.id.metadataTv) TextView metadataTv;
    @BindView(R.id.titleTv) TextView titleTv;
    @BindView(R.id.bandTv) TextView bandTv;
    @BindView(R.id.sloganTv) TextView sloganTv;
    @BindView(R.id.descriptionTv) TextView descriptionTv;
    @BindView(R.id.genreTv) TextView genreTv;
    @BindView(R.id.locationTv) TextView locationTv;
    @BindView(R.id.websiteTv) TextView websiteTv;
    @BindView(R.id.emailTv) TextView emailTv;
    @BindView(R.id.phoneTv) TextView phoneTv;


    @Override
    protected int getLayout() {
        return R.layout.fragment_player_controls;
    }

    @Override
    protected PlayerControlPresenter providePresenter() {
        return Scopes.getAppScope().getInstance(PlayerControlPresenter.class);
    }

    @Override
    protected void setupView(View view) {
        metadataTv.setSelected(true);
        playPauseBt.setOnClickListener(v -> presenter.playPause());
        previousBt.setOnClickListener(v -> presenter.skipToPrevious());
        nextBt.setOnClickListener(v -> presenter.skipToNext());
        favoriteBt.setOnClickListener(v -> presenter.switchFavorite());

        playPauseBt.setManualMode(true);
        loadingPb.getIndeterminateDrawable().mutate().setColorFilter(getResources()
                .getColor(R.color.grey_50), PorterDuff.Mode.SRC_IN);
    }

    @Override
    public void setStation(Station station) {
        animateStationInfoLayout();

        UiUtils.loadImageInto(iconIv, station);

        setTextOrHideIfEmpty(titleTv, station.getName());
        setTextOrHideIfEmpty(bandTv, station.getBandString());
        setTextOrHideIfEmpty(sloganTv, station.getSlogan());
        setTextOrHideIfEmpty(descriptionTv, station.getDescription());
        setTextOrHideIfEmpty(genreTv, station.getGenre());
        setTextOrHideIfEmpty(websiteTv, station.getWebsiteUrl());
        setTextOrHideIfEmpty(emailTv, station.getEmail());
        setTextOrHideIfEmpty(phoneTv, station.getPhone());
        setTextOrHideIfEmpty(locationTv, station.getLocationString());

    }

    @Override
    public void setFavorite(boolean isFavorite) {
        favoriteBt.setBackgroundResource(isFavorite ? R.drawable.ic_star : R.drawable.ic_star_empty);
    }

    @Override
    public void setMetadata(String string) {
        metadataTv.setText(string);
    }

    @Override
    public void showLoading() {
        playPauseBt.setPlaying(true);
        loadingPb.setVisibility(View.VISIBLE);
    }

    @Override
    public void showPlaying() {
        playPauseBt.setPlaying(true);
        loadingPb.setVisibility(View.GONE);
    }

    @Override
    public void showStopped() {
        playPauseBt.setPlaying(false);
        loadingPb.setVisibility(View.GONE);
    }

    private void animateStationInfoLayout() {
        ChangeBounds transition = new ChangeBounds();
        transition.setDuration(NonSwipeableViewPager.ANIMATION_DURATION);
        transition.addTarget(root);
        transition.setInterpolator(new FastOutSlowInInterpolator());
        TransitionManager.beginDelayedTransition(root, transition);
    }

    private void setTextOrHideIfEmpty(TextView tv, String text) {
        if (text == null || text.isEmpty()) {
            tv.setVisibility(View.GONE);
        } else {
            tv.setVisibility(View.VISIBLE);
            tv.setText(text);
        }
    }
}
