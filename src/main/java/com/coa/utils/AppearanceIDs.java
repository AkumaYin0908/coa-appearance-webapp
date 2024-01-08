package com.coa.utils;

import java.util.ArrayList;
import java.util.List;

public class AppearanceIDs {

   private List<Long> appearanceIDs= new ArrayList<>();


    public List<Long> getAppearanceIDs() {
        return appearanceIDs;
    }

    public void setAppearanceIDs(List<Long> appearanceIDs) {
        this.appearanceIDs = appearanceIDs;
    }

    public void setAppearanceIDs(Long[] appearanceIDs){
        this.appearanceIDs=List.of(appearanceIDs);
    }
}
