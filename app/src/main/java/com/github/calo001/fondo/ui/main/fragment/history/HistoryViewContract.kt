package com.github.calo001.fondo.ui.main.fragment.history

import com.github.calo001.fondo.base.BasePhotoViewContract
import com.github.calo001.fondo.model.Photo

interface HistoryViewContract : BasePhotoViewContract {
    fun onLoadPhotosSuccess(list: List<Photo>)
}