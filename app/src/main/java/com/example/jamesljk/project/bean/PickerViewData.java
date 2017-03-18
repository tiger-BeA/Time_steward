package com.example.jamesljk.project.bean;

/**
 * Created by jamesljk on 2016/12/9.
 */
import com.example.jamesljk.project.model.IPickerViewData;

/**
 * Created by Administrator on 2016/7/13.
 */
public class PickerViewData implements IPickerViewData {
    private String content;

    public PickerViewData(String content) {
        this.content = content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String getPickerViewText() {
        return content;
    }
}
