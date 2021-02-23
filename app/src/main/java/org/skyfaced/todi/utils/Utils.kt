package org.skyfaced.todi.utils

/*
object : RecyclerView.ItemDecoration() {
    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        val currentPosition = parent.getChildLayoutPosition(view)
        val maxItems = adapter.itemCount

        when (orientation) {
            ORIENTATION_PORTRAIT -> outRect.apply {
                top =
                    if (currentPosition == 0 || currentPosition == 1) cardMargin else cardMargin / 2
                bottom =
                    if (currentPosition - 1 == maxItems || currentPosition == maxItems) cardMargin else cardMargin / 2

                if (currentPosition % SPAN_COUNT_PORTRAIT == 0) {
                    left = cardMargin
                    right = cardMargin / 2
                } else {
                    left = cardMargin / 2
                    right = cardMargin
                }
            }
            ORIENTATION_LANDSCAPE -> outRect.apply {
                top =
                    if (currentPosition == 0 || currentPosition == 1 || currentPosition == 2) cardMargin else cardMargin / 2
                bottom =
                    if (currentPosition - 2 == maxItems || currentPosition - 1 == maxItems || currentPosition == maxItems) cardMargin else cardMargin / 2

                if (currentPosition % SPAN_COUNT_LANDSCAPE == 0) {
                    left = cardMargin
                    right = cardMargin / 2
                } else {
                    left = cardMargin / 2
                    right = cardMargin
                }
            }
        }
    }
}
*/