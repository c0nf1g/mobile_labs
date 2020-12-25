package iot.mobile.presentation.listeners;

import iot.mobile.presentation.uiData.NewsViewData;

public interface NewsListener {
    void onNewsItemClick(NewsViewData newsViewData);
    void onMyProfileClick();
}
