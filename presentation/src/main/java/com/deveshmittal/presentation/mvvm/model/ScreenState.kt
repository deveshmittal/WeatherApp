package com.deveshmittal.presentation.mvvm.model

enum class ScreenStateEnum { PROGRESS, LOADED, ERROR_STATE }


sealed class ScreenState(val state: ScreenStateEnum) {
    override fun equals(other: Any?): Boolean {
        if (other is ScreenState)
            return true
        return super.equals(other)
    }

    override fun hashCode(): Int {
        return state.hashCode()
    }
}

class Progress : ScreenState(ScreenStateEnum.PROGRESS) {
    override fun equals(other: Any?): Boolean {
        if (other is Progress)
            return true
        return super.equals(other)
    }
}

class Loaded : ScreenState(ScreenStateEnum.LOADED) {
    override fun equals(other: Any?): Boolean {
        if (other is Progress)
            return true
        return super.equals(other)
    }
}

data class ErrorState(val throwable: Throwable,
                      val message: CharSequence? = null,
                      val retry: () -> Unit) : ScreenState(ScreenStateEnum.ERROR_STATE) {
    override fun equals(other: Any?): Boolean =
            if (other is ErrorState)
                throwable::class.equals(other.throwable::class)
            else false;

    override fun hashCode(): Int {
        var result = throwable.hashCode()
        result = 31 * result + (message?.hashCode() ?: 0)
        result = 31 * result + retry.hashCode()
        return result
    }
}

