package com.rgalka88.notethecode.feature.drawer.usecase


import com.rgalka88.notethecode.core.UseCase
import com.rgalka88.notethecode.core.UseCase.None
import com.rgalka88.notethecode.data.hashtag.HashTagModel
import com.rgalka88.notethecode.data.hashtag.HashTagRepository
import io.reactivex.Observable
import javax.inject.Inject

class LoadHashTags @Inject constructor(
    private val hashTagRepository: HashTagRepository
) : UseCase<Observable<List<HashTagModel>>, None>() {

    override fun buildUseCase(params: None): Observable<List<HashTagModel>> =
        hashTagRepository.getAll()
}