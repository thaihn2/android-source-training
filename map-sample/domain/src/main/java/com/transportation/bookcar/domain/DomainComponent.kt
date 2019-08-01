package com.transportation.bookcar.domain

import dagger.Component
import com.transportation.bookcar.common.UtilComponent
import com.transportation.bookcar.data.DataComponent
import com.transportation.bookcar.domain.interactor.MapInteractor
import com.transportation.bookcar.domain.interactor.MovieInteractor
import com.transportation.bookcar.domain.interactor.SearchLocationInteractor
import com.transportation.bookcar.domain.interactor.TvInteractor
import com.transportation.bookcar.domain.interactor.UserInteractor

/**
 * Created on 2/7/2018.
 */
@Component(
        modules = [DomainModule::class],
        dependencies = [DataComponent::class, UtilComponent::class]
)
interface DomainComponent {
    fun userInteractor(): UserInteractor
    fun tvInteractor(): TvInteractor
    fun movieInteractor(): MovieInteractor
    fun searchLocationInteractor(): SearchLocationInteractor
    fun mapInteractor(): MapInteractor
}
