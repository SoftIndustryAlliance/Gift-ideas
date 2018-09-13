package com.soft_industry.findgift.domain.task

import com.soft_industry.findgift.domain.entities.GiftTarget
import com.soft_industry.findgift.domain.repository.DataRepository
import io.reactivex.Observable
import javax.inject.Inject

/**
 * Created by user on 3/26/18.
 */
class LoadTargets @Inject constructor(val dataRepository: DataRepository) {
    fun execute() = Observable.merge(loadEditors(),
            loadThemed(),
            loadForWomen(),
            loadForMen())




    private fun loadEditors() = dataRepository.loadEditors()
            .map(TargetData::Editors)

    private fun loadThemed() = dataRepository.loadThematic()
            .map(TargetData::Themed)

    private fun loadForWomen() = dataRepository.loadForWomen()
            .map(TargetData::ForWomen)


    private fun loadForMen() = dataRepository.loadForMen()
            .map(TargetData::ForMen)

}
sealed class TargetData {
    data class Editors(val data: List<GiftTarget>) : TargetData()
    data class Themed(val data: List<GiftTarget>) : TargetData()
    data class ForWomen(val data: List<GiftTarget>) : TargetData()
    data class ForMen(val data: List<GiftTarget>) : TargetData()
}