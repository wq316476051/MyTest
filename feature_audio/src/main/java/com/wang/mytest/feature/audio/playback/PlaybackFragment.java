package com.wang.mytest.feature.audio.playback;

import android.content.Context;
import android.os.Bundle;
import android.os.Parcelable;
import android.os.RemoteException;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.wang.mytest.feature.audio.R;
import com.wang.mytest.feature.audio.bean.Recording;
import com.wang.mytest.feature.audio.databinding.PlaybackBinding;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

public class PlaybackFragment extends Fragment {

    private static final String TAG = "PlaybackFragment";

    private static final String KEY = "recording";
    private Recording mRecording;
    private PlaybackBinding mBinding;
    private PlaybackManager mPlaybackManager;

    public static Fragment newInstance(Recording recording) {
        Bundle args = new Bundle();
        args.putParcelable(KEY, recording);

        PlaybackFragment fragment = new PlaybackFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mPlaybackManager = PlaybackManager.create(context);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle arguments = getArguments();
        Log.d(TAG, "onCreate: arguments = " + arguments);
        if (arguments == null) {
            getFragmentManager().popBackStack();
            return;
        }
        Parcelable parcelable = arguments.getParcelable(KEY);
        Log.d(TAG, "onCreate: parcelable = " + parcelable);
        if (parcelable instanceof Recording) {
            mRecording = (Recording) parcelable;
            getActivity().setTitle(mRecording.getFilename());
        } else {
            getFragmentManager().popBackStack();
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_playback, container, false);
        return mBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mPlaybackManager.getConnectState().observe(getViewLifecycleOwner(), isConnected -> {

        });

        mPlaybackManager.addCallback(new IPlaybackCallback.Stub() {
            @Override
            public void onStateChanged(int state) throws RemoteException {
                onUpdateView(state);
            }
        });

        mBinding.ibPlayPause.setOnClickListener(button -> {
            Log.d(TAG, "onViewCreated: ");
            mPlaybackManager.setDataSource(mRecording.getFilepath());
            if (!mPlaybackManager.isPlaying()) {
                mPlaybackManager.start();
            } else {
                mPlaybackManager.pause();
            }
        });

        mBinding.ibSkipPrevious.setOnClickListener(button -> {

        });

        mBinding.ibSkipNext.setOnClickListener(button -> {

        });
    }

    private void onUpdateView(int state) {
        switch (state) {
            case PlaybackManager.STATE_IDLE:
                mBinding.ibPlayPause.setEnabled(false);
                mBinding.ibPlayPause.setImageResource(android.R.drawable.ic_media_play);
                break;
            case PlaybackManager.STATE_PREPARED:
                mBinding.ibPlayPause.setEnabled(true);
                mBinding.ibPlayPause.setImageResource(android.R.drawable.ic_media_play);
                break;
            case PlaybackManager.STATE_PLAYING:
                mBinding.ibPlayPause.setEnabled(true);
                mBinding.ibPlayPause.setImageResource(android.R.drawable.ic_media_pause);
                break;
            case PlaybackManager.STATE_PAUSED:
                mBinding.ibPlayPause.setEnabled(true);
                mBinding.ibPlayPause.setImageResource(android.R.drawable.ic_media_play);
                break;
            default:
                break;
        }
    }
}
