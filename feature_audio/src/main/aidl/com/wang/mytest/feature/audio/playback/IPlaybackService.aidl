// IPlaybackService.aidl
package com.wang.mytest.feature.audio.playback;

import com.wang.mytest.feature.audio.playback.IPlaybackCallback;

interface IPlaybackService {

    void setDataSource(String file);

    void start();

    void pause();

    void stop();

    int getState();

    void addCallback(IPlaybackCallback callback);

    void removeCallback(IPlaybackCallback callback);
}
