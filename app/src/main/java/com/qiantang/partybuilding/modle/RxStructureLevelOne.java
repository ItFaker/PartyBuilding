package com.qiantang.partybuilding.modle;

import com.chad.library.adapter.base.entity.AbstractExpandableItem;
import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.qiantang.partybuilding.module.assistant.adapter.StructureAdapter;

/**
 * Created by zhaoyong bai on 2018/6/17.
 */
public class RxStructureLevelOne extends AbstractExpandableItem<RxStructureLevelTwo> implements MultiItemEntity {
    private String dept_name;
    private String dept_id;

    public String getDept_name() {
        return dept_name;
    }

    public void setDept_name(String dept_name) {
        this.dept_name = dept_name;
    }

    public String getDept_id() {
        return dept_id;
    }

    public void setDept_id(String dept_id) {
        this.dept_id = dept_id;
    }

    @Override
    public int getLevel() {
        return StructureAdapter.TYPE_LEVEL_0;
    }

    @Override
    public int getItemType() {
        return StructureAdapter.TYPE_LEVEL_0;
    }
}
