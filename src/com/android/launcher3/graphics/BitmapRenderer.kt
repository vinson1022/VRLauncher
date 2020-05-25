/*
 * Copyright (C) 2017 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.android.launcher3.graphics

import android.annotation.TargetApi
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Picture
import android.os.Build
import com.android.launcher3.Utilities
import com.android.launcher3.util.aboveApi28

object BitmapRenderer {
    @JvmField
    val USE_HARDWARE_BITMAP = Utilities.ATLEAST_P

    fun createSoftwareBitmap(width: Int, height: Int, renderer: Renderer): Bitmap {
        val result = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
        renderer.draw(Canvas(result))
        return result
    }

    @JvmStatic
    @TargetApi(Build.VERSION_CODES.P)
    fun createHardwareBitmap(width: Int, height: Int, renderer: Renderer): Bitmap {
        aboveApi28 {
            val picture = Picture()
            renderer.draw(picture.beginRecording(width, height))
            picture.endRecording()
            return Bitmap.createBitmap(picture)
        }
        return createSoftwareBitmap(width, height, renderer)
    }

    /**
     * Interface representing a bitmap draw operation.
     */
    interface Renderer {
        fun draw(out: Canvas)
    }
}