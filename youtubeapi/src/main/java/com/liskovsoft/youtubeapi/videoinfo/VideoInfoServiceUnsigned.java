package com.liskovsoft.youtubeapi.videoinfo;

import com.liskovsoft.sharedutils.mylogger.Log;
import com.liskovsoft.youtubeapi.common.helpers.RetrofitHelper;
import com.liskovsoft.youtubeapi.common.locale.LocaleManager;
import com.liskovsoft.youtubeapi.videoinfo.models.VideoInfo;
import retrofit2.Call;

public class VideoInfoServiceUnsigned extends VideoInfoServiceBase {
    private static final String TAG = VideoInfoServiceUnsigned.class.getSimpleName();
    private static VideoInfoServiceUnsigned sInstance;
    private final VideoInfoManagerUnsignedV2 mVideoInfoManagerUnsigned;
    private final LocaleManager mLocaleManager;

    private VideoInfoServiceUnsigned() {
        mVideoInfoManagerUnsigned = RetrofitHelper.withQueryString(VideoInfoManagerUnsignedV2.class);
        mLocaleManager = LocaleManager.instance();
    }

    public static VideoInfoServiceUnsigned instance() {
        if (sInstance == null) {
            sInstance = new VideoInfoServiceUnsigned();
        }

        return sInstance;
    }

    public VideoInfo getVideoInfo(String videoId) {
        VideoInfo result = getVideoInfoHls(videoId);

        if (result != null && result.isAgeRestricted()) {
            Log.e(TAG, "Seems that video age restricted. Retrying with different query method...");
            result = getVideoInfoRestricted(videoId);
        }

        if (result != null) {
            decipherFormats(result.getAdaptiveFormats());
            decipherFormats(result.getRegularFormats());
        } else {
            Log.e(TAG, "Can't get video info for videoId " + videoId);
        }

        return result;
    }
    
    private VideoInfo getVideoInfoHls(String videoId) {
        Call<VideoInfo> wrapper = mVideoInfoManagerUnsigned.getVideoInfoHls(videoId, mLocaleManager.getLanguage());

        return RetrofitHelper.get(wrapper);
    }

    private VideoInfo getVideoInfoRestricted(String videoId) {
        Call<VideoInfo> wrapper = mVideoInfoManagerUnsigned.getVideoInfoRestricted(videoId, mLocaleManager.getLanguage());

        return RetrofitHelper.get(wrapper);
    }
}
