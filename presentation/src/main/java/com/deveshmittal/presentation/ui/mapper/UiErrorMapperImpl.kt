package com.deveshmittal.presentation.ui.mapper

import android.content.res.Resources
import com.deveshmittal.domain.exception.LocationPermisionDenied
import com.deveshmittal.domain.ui.UIErrorMapper
import com.deveshmittal.presentation.R
import javax.inject.Inject

class UiErrorMapperImpl @Inject constructor(val resoruces: Resources) : UIErrorMapper {
    override fun mapErrorToText(t: Throwable): CharSequence =
            when (t) {
                is LocationPermisionDenied -> resoruces.getString(R.string.something_went_wrong)
                else -> resoruces.getString(R.string.something_went_wrong)
            }


}