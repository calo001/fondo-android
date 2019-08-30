package com.github.calo001.fondo.ui.main.fragment.today

import com.github.calo001.fondo.base.BasePhotoViewContract
import com.github.calo001.fondo.model.Photo

interface TodayViewContract: BasePhotoViewContract {
    fun onLoadPhotosSuccess(list: List<Photo>)
}