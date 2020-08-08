package com.deveshmittal.domain.ui

interface UIErrorMapper {
    fun mapErrorToText(t: Throwable): CharSequence
}