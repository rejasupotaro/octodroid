package com.example.octodroid.views.components

import android.content.Context
import android.content.res.TypedArray
import android.graphics.Canvas
import android.graphics.Rect
import android.graphics.drawable.Drawable
import android.support.v7.widget.RecyclerView
import android.view.View

class DividerItemDecoration(context: Context) : RecyclerView.ItemDecoration() {

    private val divider: Drawable

    init {
        val a = context.obtainStyledAttributes(ATTRS)
        divider = a.getDrawable(0)
        a.recycle()
    }

    override fun onDraw(c: Canvas?, parent: RecyclerView?) {
        drawVertical(c, parent)
    }

    fun drawVertical(c: Canvas, parent: RecyclerView) {
        val left = parent.getPaddingLeft()
        val right = parent.getWidth() - parent.getPaddingRight()

        val childCount = parent.getChildCount()
        for (i in 0..childCount - 1) {
            val child = parent.getChildAt(i)
            val params = child.getLayoutParams() as RecyclerView.LayoutParams
            val top = child.getBottom() + params.bottomMargin
            val bottom = top + divider.getIntrinsicHeight()
            divider.setBounds(left, top, right, bottom)
            divider.draw(c)
        }
    }

    override fun getItemOffsets(outRect: Rect, itemPosition: Int, parent: RecyclerView?) {
        outRect.set(0, 0, 0, divider.getIntrinsicHeight())
    }

    companion object {

        private val ATTRS = intArrayOf(android.R.attr.listDivider)
    }
}

