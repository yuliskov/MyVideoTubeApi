package com.liskovsoft.youtubeapi.browse.ver2.models.grid;

import com.liskovsoft.youtubeapi.common.converters.jsonpath.JsonPath;

import java.util.List;

public class GridTabResult {
    @JsonPath("$.contents.tvBrowseRenderer.content.tvSecondaryNavRenderer.sections[0].tvSecondaryNavSectionRenderer.tabs[*].tabRenderer")
    private List<GridTab> mTabs;

    @JsonPath("$.responseContext.visitorData")
    private String mVisitorData;

    public List<GridTab> getTabs() {
        return mTabs;
    }

    public String getVisitorData() {
        return mVisitorData;
    }
}
