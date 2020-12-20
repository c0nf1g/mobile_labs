package iot.mobile.presentation.callbacks;

import iot.mobile.presentation.uiData.NewsViewData;

public interface NewsListener {
    public void onNewsItemClick(NewsViewData newsViewData);
    public void onMyProfileClick();
}
