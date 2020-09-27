package com.liskovsoft.youtubeapi.videoinfo;

import com.liskovsoft.youtubeapi.common.helpers.RetrofitHelper;
import com.liskovsoft.youtubeapi.common.helpers.TestHelpers;
import com.liskovsoft.youtubeapi.videoinfo.models.CaptionTrack;
import com.liskovsoft.youtubeapi.videoinfo.models.VideoInfoResult;
import com.liskovsoft.youtubeapi.videoinfo.models.formats.AdaptiveVideoFormat;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.shadows.ShadowLog;
import retrofit2.Call;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.List;

@RunWith(RobolectricTestRunner.class)
public class VideoInfoManagerUnsignedTest {
    private VideoInfoManagerUnsigned mService;

    @Before
    public void setUp() {
        // fix issue: No password supplied for PKCS#12 KeyStore
        // https://github.com/robolectric/robolectric/issues/5115
        System.setProperty("javax.net.ssl.trustStoreType", "JKS");

        ShadowLog.stream = System.out; // catch Log class output

        RetrofitHelper.sForceEnableProfiler = true;

        mService = RetrofitHelper.withQueryString(VideoInfoManagerUnsigned.class);
    }

    @Test
    public void testThatVideoInfoContainsAllRequiredFields() throws IOException {
        testThatVideoInfoContainsAllRequiredFields(TestHelpers.VIDEO_ID_SIMPLE);
    }

    @Test
    public void testThatGeminiManContainsAllRequiredFields() throws IOException {
        testThatVideoInfoContainsAllRequiredFields(TestHelpers.VIDEO_ID_GEMINI_MAN);
    }

    @Test
    public void testThatLiveVideoContainsSpecificFields()  throws IOException {
        testThatLiveVideoContainsSpecificFields(TestHelpers.VIDEO_ID_LIVE);
    }

    @Test
    public void testThatSubtitleContainsAllRequiredFields() throws IOException {
        testThatSubtitleContainsAllRequiredFields(TestHelpers.VIDEO_ID_SIMPLE);
    }

    private void testThatLiveVideoContainsSpecificFields(String videoId)  throws IOException {
        Call<VideoInfoResult> wrapper = mService.getVideoInfo(videoId);
        VideoInfoResult result = wrapper.execute().body();

        assertNotNull("Result not null", result);
        assertNotNull("Contains dash url", result.getDashManifestUrl());
        assertNotNull("Contains hls url", result.getHlsManifestUrl());
    }

    private void testThatSubtitleContainsAllRequiredFields(String videoId) throws IOException {
        Call<VideoInfoResult> wrapper = mService.getVideoInfo(videoId);
        VideoInfoResult result = wrapper.execute().body();

        assertNotNull("Result not null", result);
        List<CaptionTrack> captionTracks = result.getCaptionTracks();
        assertNotNull("Contains captions", captionTracks);
        CaptionTrack track = captionTracks.get(0);

        assertNotNull("Contains name", track.getName());
        assertNotNull("Contains base url", track.getBaseUrl());
        assertNotNull("Contains vss id", track.getVssId());
        assertNotNull("Contains lang", track.getLanguageCode());
        assertNotNull("Contains mime type", track.getMimeType());
        assertNotNull("Contains codecs", track.getCodecs());

        assertTrue("Subtitle url exists", TestHelpers.urlExists(track.getBaseUrl()));
    }

    private void testThatVideoInfoContainsAllRequiredFields(String videoId) throws IOException {
        Call<VideoInfoResult> wrapper = mService.getVideoInfo(videoId);
        VideoInfoResult result = wrapper.execute().body();

        assertNotNull("Result not null", result);
        assertTrue("Video available externally", result.isVideoAvailable());
        List<AdaptiveVideoFormat> formats = result.getAdaptiveFormats();
        assertTrue("Formats not empty", formats.size() > 0);
        assertNotNull("Contains range", formats.get(0).getIndexRange());
        assertTrue("Contains fps", formats.get(0).getFps() != 0);
        assertTrue("Contains bitrate", formats.get(0).getBitrate() != 0);
        assertNotNull("Contains tracking url", result.getVideoStatsWatchTimeUrl());
        assertNotNull("Contains captions", result.getCaptionTracks());
        assertNotNull("Contains video details", result.getVideoDetails());
        assertNotNull("Contains event id", result.getEventId());
        assertNotNull("Contains vm tracking param", result.getVisitorMonitoringData());
    }
}