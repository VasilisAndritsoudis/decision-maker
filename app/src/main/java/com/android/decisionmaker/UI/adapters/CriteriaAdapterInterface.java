package com.android.decisionmaker.UI.adapters;

public interface CriteriaAdapterInterface {
    /**
     * An abstract method implemented in the CriteriaScore Class to change the value of
     * a Choice's value or a Criterion's weight
     * @param position is the item's position in the recycler view
     * @param seekBarValue is the seekBar's progress value
     * @param criterionName is the name of the criterion
     */
    void onSeekBarChange(int position, int seekBarValue, String criterionName);
}
