package com.qiantang.partybuilding.widget.verticaltablayout;


import com.qiantang.partybuilding.widget.verticaltablayout.widget.QTabView;

public interface TabAdapter {

    int getCount();

    int getBadge(int position);

    QTabView.TabIcon getIcon(int position);

    QTabView.TabTitle getTitle(int position);

    int getBackground(int position);
}
