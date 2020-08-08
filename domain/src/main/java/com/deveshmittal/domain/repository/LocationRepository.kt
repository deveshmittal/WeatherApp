package com.deveshmittal.domain.repository

import com.deveshmittal.domain.model.Location
import io.reactivex.Single

interface LocationRepository {
    fun provideLocation(): Single<Location>

}