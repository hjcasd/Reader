package com.hjc.library_widget

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import java.lang.IllegalArgumentException
import kotlin.collections.ArrayList

/**
 * create by WangPing
 * on 2020/6/23
 * 宽度只能是个精确的宽度
 * 高度只能是自适应高度
 */
class FlowLayout(
    context: Context,
    attrs: AttributeSet?
) : ViewGroup(context, attrs, 0, 0) {

    //默认排列是从左到右排列
    private var layoutGravity = FlowLayoutGravity.LEFT

    //child垂直方向的间距
    private var childVerticalMargin = 0f

    //child水平方向的间距
    private var childHorizontalMargin = 0f

    /**
     * 流式布局最大展示行数。常用于收起、展开
     * eg:需求默认最多展示5行,超过5行的收起,点击某个按钮展开。
     * 那么实现就可以为初始maxLines = 5 然后展开按钮后设置maxLines = Int.MAX_VALUE
     * 点击收起后又设置maxLines = 5
     */
    var maxLines = 5
        set(value) {
            field = value
            requestLayout()
            invalidate()
        }

    private val allViewLines: MutableList<MutableList<View>> = ArrayList() //记录所有行
    private val perLineHeight: MutableList<Int> = ArrayList()  //记录所有行高
    private var perLineViews: MutableList<View> = ArrayList()  //临时记录每行的view

    init {
        val obtainStyledAttributes = context.obtainStyledAttributes(attrs, R.styleable.FlowLayout)
        obtainStyledAttributes.let {
            childVerticalMargin = it.getDimension(R.styleable.FlowLayout_childVerticalMargin, 0f)
            childHorizontalMargin =
                it.getDimension(R.styleable.FlowLayout_childHorizontalMargin, 0f)
            maxLines = it.getInt(R.styleable.FlowLayout_maxLines, 5)
            layoutGravity = if (it.getInt(R.styleable.FlowLayout_gravity, 1) == 0) {
                FlowLayoutGravity.LEFT
            } else {
                FlowLayoutGravity.RIGHT
            }
        }
        obtainStyledAttributes.recycle()
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        arrayListOf(allViewLines, perLineHeight, perLineViews).forEach {
            it.clear()
        }
        val flowLayoutWidth = MeasureSpec.getSize(widthMeasureSpec) //宽度已经在创建的时候就固定了
        var flowLayoutHeight = 0 //最终的布局高度,通过计算获得
        val lineCanUseAllWidth = flowLayoutWidth - paddingLeft - paddingRight //一行可以使用的宽度
        var currentLineUsedWidth = 0 //当前行已经使用了多宽,主要用于判断是否需要换行
        var currentLineHeight = 0 //当前行的高度


        for (i in 0 until childCount) {
            val child = getChildAt(i)
            if (child == null || child.visibility == View.GONE) {
                continue
            }
            //测量子view
            measureChild(child, widthMeasureSpec, heightMeasureSpec)
            val childContentWidth = child.measuredWidth
            val childLayoutParams = child.layoutParams as MarginLayoutParams
            //使用自定义的间距
            if (childVerticalMargin != 0f) {
                childLayoutParams.topMargin = childVerticalMargin.toInt()
            }
            if (childHorizontalMargin != 0f) {
                if (layoutGravity == FlowLayoutGravity.RIGHT) {
                    childLayoutParams.rightMargin = 0
                    childLayoutParams.leftMargin = childHorizontalMargin.toInt()
                } else if (layoutGravity == FlowLayoutGravity.LEFT) {
                    childLayoutParams.leftMargin = 0
                    childLayoutParams.rightMargin = childHorizontalMargin.toInt()
                }
            }
            val childHeight =
                child.measuredHeight + childLayoutParams.topMargin + childLayoutParams.bottomMargin
            val childMarginSize = childLayoutParams.leftMargin + childLayoutParams.rightMargin
            if (layoutGravity == FlowLayoutGravity.RIGHT && childLayoutParams.rightMargin != 0) {
                throw IllegalArgumentException("从右往左的排列方式,childView不该有右margin值,不然可能出现item被顶出屏幕的情况")
            } else if (layoutGravity == FlowLayoutGravity.LEFT && childLayoutParams.leftMargin != 0) {
                throw IllegalArgumentException("从左往右的排列方式,childView不该有左margin值,不然可能出现item被顶出屏幕的情况")
            }
            if (childContentWidth + currentLineUsedWidth > flowLayoutWidth) {
                //子view的内容剩余位置展示不下,需要换行
                if (childContentWidth > lineCanUseAllWidth) {
                    //这个子view需要独占一行
                    currentLineHeight = childHeight
                    perLineViews.add(child)
                }
                flowLayoutHeight += currentLineHeight
                //换行前,保存上一行数据
                perLineHeight.add(currentLineHeight)
                allViewLines.add(perLineViews)
                //换行,新行数据初始化
                currentLineUsedWidth = 0 //重新赋值行宽
                currentLineHeight = 0 //重新赋值行高
                perLineViews = ArrayList() //创建新行
                if (allViewLines.size >= maxLines) {
                    break
                }
                if (childContentWidth > lineCanUseAllWidth) {
                    //如果是独占一行的子View就满足换行条件并且数据已经保存,则不需要下面重复添加了
                    continue
                }
            }
            //光是子view的内容剩余位置能展示
            currentLineUsedWidth += if (childContentWidth + childMarginSize + currentLineUsedWidth > flowLayoutWidth) {
                //加上margin就展示不下了,处理下当前行宽
                //这里就是为了避免市面上大部分的流式布局都有的一个bug,本来剩余的位置内容是展示得下的,但加上margin展示不下,就直接换行了
                lineCanUseAllWidth
            } else {
                childContentWidth + childMarginSize
            }
            currentLineHeight = currentLineHeight.coerceAtLeast(childHeight)
            perLineViews.add(child)
        }
        //添加最后一行数据
        flowLayoutHeight += currentLineHeight //累加高度
        perLineHeight.add(currentLineHeight)
        allViewLines.add(perLineViews)
        //设置FlowLayout的最终宽高
        setMeasuredDimension(flowLayoutWidth, flowLayoutHeight + paddingTop + paddingBottom)
    }


    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        var perLineTop = paddingTop //初始行的top是padding
        //先确定每一行
        for (line in 0 until allViewLines.size) {
            //获取当前行和行高
            val currentLineViews = allViewLines[line]
            val currentLineHeight = perLineHeight[line]
            if (layoutGravity == FlowLayoutGravity.LEFT) {
                var left = paddingLeft
                //开始布局每一行
                for (column in currentLineViews.indices) {
                    val child = currentLineViews[column]
                    if (child.visibility == View.GONE) {
                        continue
                    }
                    val childLayoutParams = child.layoutParams as MarginLayoutParams
                    val childLeft = left + childLayoutParams.leftMargin
                    val childTop = perLineTop + childLayoutParams.topMargin
                    val childRight = childLeft + child.measuredWidth
                    val childBottom = childTop + child.measuredHeight
                    child.layout(childLeft, childTop, childRight, childBottom)
                    //更新下一个view添加到当前行的left
                    left += (child.measuredWidth + childLayoutParams.leftMargin
                            + childLayoutParams.rightMargin)
                }
            } else if (layoutGravity == FlowLayoutGravity.RIGHT) {
                var right = width - paddingRight
                //开始布局每一行
                for (column in currentLineViews.indices) {
                    val child = currentLineViews[column]
                    if (child.visibility == View.GONE) {
                        continue
                    }
                    val childLayoutParams = child.layoutParams as MarginLayoutParams
                    val childLeft = right - child.measuredWidth
                    val childTop = perLineTop + childLayoutParams.topMargin
                    val childRight = right
                    val childBottom = childTop + child.measuredHeight
                    child.layout(childLeft, childTop, childRight, childBottom)
                    //更新下一个view添加到当前行的left
                    right -= (child.measuredWidth + childLayoutParams.leftMargin
                            + childLayoutParams.rightMargin)
                }
            }
            //更新下一个view添加到下一行的top
            perLineTop += currentLineHeight
        }
    }

    override fun generateLayoutParams(attrs: AttributeSet?): LayoutParams =
        MarginLayoutParams(context, attrs)

    override fun generateDefaultLayoutParams(): LayoutParams = MarginLayoutParams(
        LayoutParams.WRAP_CONTENT,
        LayoutParams.WRAP_CONTENT
    )

    override fun generateLayoutParams(params: LayoutParams?): LayoutParams =
        MarginLayoutParams(params)

    /**
     * 获取行数
     */
    fun getRowCount(): Int {
        return allViewLines.size
    }

    enum class FlowLayoutGravity {
        LEFT, RIGHT
    }
}

