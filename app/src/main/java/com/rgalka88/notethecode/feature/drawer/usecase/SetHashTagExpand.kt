package com.rgalka88.notethecode.feature.drawer.usecase

import com.rgalka88.notethecode.core.UseCase
import com.rgalka88.notethecode.core.utils.takeOnlyFirst
import com.rgalka88.notethecode.data.hashtag.HashTagModel
import com.rgalka88.notethecode.data.hashtag.HashTagRepository
import io.reactivex.Completable
import javax.inject.Inject

class SetHashTagExpand @Inject constructor(
    private val hashTagRepository: HashTagRepository
) : UseCase<Completable, String>() {

    override fun buildUseCase(params: String): Completable = hashTagRepository
        .query()
        .where("title", params)
        .findFirst()
        .takeOnlyFirst()
        .switchMapCompletable { hashTag -> saveHashTag(hashTag) }

    private fun saveHashTag(hashTag: HashTagModel): Completable =
        hashTagRepository.save(
            hashTag.copy(
                expanded = hashTag.expanded.not(),
                animateArrow = true
            )
        )
}