package com.example.photofilters.ui.custom

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.ScaleGestureDetector
import android.view.View
import androidx.core.view.GestureDetectorCompat
import com.example.photofilters.R
import java.lang.Float.min


class CustomView @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attributeSet, defStyleAttr), GestureDetector.OnGestureListener, ScaleGestureDetector.OnScaleGestureListener {
    private val viewRectF = RectF()
    private val imageRectF = RectF()
    private val backgroundBitmapRectF = RectF()

    private val imageMatrix = Matrix()
    private val backgroundMatrix = Matrix()

    private var actualBitmap = BitmapFactory.decodeResource(context.resources, R.drawable.no_backgorund)
    private var segmentedBitmap =
        BitmapFactory.decodeResource(context.resources, R.drawable.no_backgorund)
    private val backgroundBitmap =
        BitmapFactory.decodeResource(context.resources, R.drawable.background)

    private val imagePaint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val maskPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        xfermode = PorterDuffXfermode(PorterDuff.Mode.SRC_IN)
    }

    private var mGesture = GestureDetectorCompat(context, this)
    private var mGestureScale = ScaleGestureDetector(context, this)

    private var mScaleFactor = 1.0f

    private val imageClipRectF = RectF()

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        viewRectF.set(0f,0f,w.toFloat(),h.toFloat())
        updateImageMatrix()
        updateBackgroundImageMatrix()
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        canvas?.clipRect(imageClipRectF)
        canvas?.drawBitmap(backgroundBitmap,backgroundMatrix,imagePaint)
        canvas?.saveLayer(viewRectF, imagePaint)
        canvas?.scale(mScaleFactor, mScaleFactor);
        canvas?.drawBitmap(actualBitmap, imageMatrix, imagePaint)
        canvas?.drawBitmap(segmentedBitmap, imageMatrix, maskPaint)
        canvas?.restore()
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        var res: Boolean = mGestureScale.onTouchEvent(event)
        if (!mGestureScale.isInProgress) {
            res = mGesture.onTouchEvent(event)
        }
        return res
    }

    private fun updateImageMatrix() {
        actualBitmap.runIfSafe {
            imageRectF.set(0f, 0f, it.width.toFloat(), it.height.toFloat())
            val widthScale = viewRectF.width() / imageRectF.width()
            val heightScale = viewRectF.height() / imageRectF.height()
            val scaleFactor = min(widthScale, heightScale)
            val translateX = viewRectF.width() / 3f
            val translateY = (viewRectF.height() - imageRectF.height() * scaleFactor) / 2f
            imageMatrix.setScale(scaleFactor, scaleFactor)
            imageMatrix.postTranslate(translateX, translateY)
            invalidate()
        }
    }

    private fun updateBackgroundImageMatrix() {
        backgroundBitmap.runIfSafe {
            backgroundBitmapRectF.set(0f, 0f, it.width.toFloat(), it.height.toFloat())
            val widthScale = viewRectF.width() / backgroundBitmapRectF.width()
            val heightScale = viewRectF.height() / backgroundBitmapRectF.height()
            val scaleFactor = min(widthScale, heightScale)
            val translateX = (viewRectF.width() - backgroundBitmapRectF.width() * scaleFactor) / 2f
            val translateY =
                (viewRectF.height() - backgroundBitmapRectF.height() * scaleFactor) / 2f
            backgroundMatrix.setScale(scaleFactor, scaleFactor)
            backgroundMatrix.postTranslate(translateX, translateY)
            backgroundMatrix.mapRect(imageClipRectF,backgroundBitmapRectF)
            invalidate()
        }
    }

    private fun Bitmap?.runIfSafe(function: (Bitmap) -> Unit) {
        this ?: return

        if (isRecycled.not()) {
            function(this)
        }
    }

    fun drawEffect(overlayId: Int, bitmap: Bitmap) {
        segmentedBitmap = if (overlayId == 0) {
            BitmapFactory.decodeResource(context.resources, R.drawable.no_backgorund)
        } else {
            bitmap
        }
        actualBitmap = segmentedBitmap
        invalidate()
    }

    override fun onDown(event: MotionEvent): Boolean {
        return true
    }

    override fun onFling(
        event1: MotionEvent,
        event2: MotionEvent,
        velocityX: Float,
        velocityY: Float
    ): Boolean {
        return true
    }

    override fun onLongPress(event: MotionEvent) {
    }

    override fun onScroll(
        event1: MotionEvent,
        event2: MotionEvent,
        distanceX: Float,
        distanceY: Float
    ): Boolean {
        imageMatrix.postTranslate(-distanceX, -distanceY)
        invalidate()
        return true
    }

    override fun onShowPress(event: MotionEvent) {
    }

    override fun onSingleTapUp(event: MotionEvent): Boolean {
        return true
    }

    override fun onScale(detector: ScaleGestureDetector?): Boolean {
        mScaleFactor *= detector!!.scaleFactor
        mScaleFactor = 0.1f.coerceAtLeast(mScaleFactor.coerceAtMost(5.0f));
        invalidate()
        return true
    }

    override fun onScaleBegin(detector: ScaleGestureDetector?): Boolean {
        return true
    }

    override fun onScaleEnd(detector: ScaleGestureDetector?) {

    }

}