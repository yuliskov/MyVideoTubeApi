package com.liskovsoft.youtubeapi.videoinfo.models;

import com.liskovsoft.youtubeapi.common.converters.jsonpath.JsonPath;
import com.liskovsoft.youtubeapi.videoinfo.models.formats.AdaptiveFormat;
import com.liskovsoft.youtubeapi.videoinfo.models.formats.RegularFormat;

import java.util.List;

public class VideoInfoResult {
    @JsonPath("$.streamingData.formats[*]")
    private List<RegularFormat> mRegularFormats;

    @JsonPath("$.streamingData.adaptiveFormats[*]")
    private List<AdaptiveFormat> mAdaptiveFormats;

    @JsonPath("$.streamingData.hlsManifestUrl")
    private String mHlsManifestUrl;

    @JsonPath("$.playbackTracking.videostatsWatchtimeUrl.baseUrl")
    private String mVideostatsWatchtimeUrl;

    @JsonPath("$.captions.playerCaptionsTracklistRenderer.captionTracks[*]")
    private List<CaptionTrack> mCaptionTracks;

    @JsonPath("$.streamingData.dashManifestUrl")
    private String mDashManifestUrl;

    @JsonPath("$.videoDetails")
    private VideoDetails mVideoDetails;

    @JsonPath("$.storyboards.playerStoryboardSpecRenderer.spec")
    private String mPlayerStoryboardSpec;

    public List<AdaptiveFormat> getAdaptiveFormats() {
        return mAdaptiveFormats;
    }

    public String getHlsManifestUrl() {
        return mHlsManifestUrl;
    }

    public String getVideostatsWatchtimeUrl() {
        return mVideostatsWatchtimeUrl;
    }

    public List<CaptionTrack> getCaptionTracks() {
        return mCaptionTracks;
    }

    public String getDashManifestUrl() {
        return mDashManifestUrl;
    }

    public String getPlayerStoryboardSpec() {
        return mPlayerStoryboardSpec;
    }

    public List<RegularFormat> getRegularFormats() {
        return mRegularFormats;
    }

    public VideoDetails getVideoDetails() {
        return mVideoDetails;
    }
}