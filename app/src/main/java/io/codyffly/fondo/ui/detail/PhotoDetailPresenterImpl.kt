package io.codyffly.fondo.ui.detail

class PhotoDetailPresenterImpl(val view: PhotoDetailViewContract): PhotoDetailPresenterContract {
    val interactor: PhotoDetailInteractorContract = PhotoDetailInteractorImpl(this)

    override fun getDownloadLink(id: String) {
        interactor.getDownloadLink(id)
    }

    override fun onSuccess(image: String) {
        view.onSuccess(image)
    }

    override fun onError(error: String) {
        view.onError(error)
    }

}
