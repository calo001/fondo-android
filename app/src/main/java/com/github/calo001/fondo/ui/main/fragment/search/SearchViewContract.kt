package com.github.calo001.fondo.ui.main.fragment.search

import com.github.calo001.fondo.base.BasePhotoViewContract
import com.github.calo001.fondo.model.Result

interface SearchViewContract : BasePhotoViewContract {
    fun onLoadPhotosSuccess(result: Result)
}