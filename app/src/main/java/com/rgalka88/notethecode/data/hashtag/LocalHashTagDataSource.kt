package com.rgalka88.notethecode.data.hashtag

import com.rgalka88.notethecode.core.utils.addOrReplaceIfExist
import com.rgalka88.notethecode.core.utils.takeOnlyFirst
import com.rgalka88.notethecode.data.DataSource
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.subjects.BehaviorSubject
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LocalHashTagDataSource @Inject constructor(
) : DataSource<HashTagModel> {

    private val hashTagList = listOf(
        HashTagModel("HashTag1"),

        HashTagModel("HashTagA", "HashTag1"),
        HashTagModel("HashTagB", "HashTag1"),
        HashTagModel("HashTagC", "HashTag1"),
        HashTagModel("HashTagD", "HashTag1"),
        HashTagModel("HashTagE", "HashTag1"),

        HashTagModel("HashTagA1", "HashTagA"),
        HashTagModel("HashTagA2", "HashTagA"),
        HashTagModel("HashTagA3", "HashTagA"),
        HashTagModel("HashTagA4", "HashTagA"),

        HashTagModel("HashTagA11", "HashTagA1"),
        HashTagModel("HashTagA12", "HashTagA1"),
        HashTagModel("HashTagA13", "HashTagA1"),
        HashTagModel("HashTagA14", "HashTagA1"),

        HashTagModel("HashTagA111", "HashTagA11"),
        HashTagModel("HashTagA112", "HashTagA11"),
        HashTagModel("HashTagA113", "HashTagA11"),
        HashTagModel("HashTagA114", "HashTagA11"),

        HashTagModel("HashTagA1111", "HashTagA111"),
        HashTagModel("HashTagA1112", "HashTagA111"),
        HashTagModel("HashTagA1113", "HashTagA111"),
        HashTagModel("HashTagA1114", "HashTagA111")
    )

    private val hashTagSubject = BehaviorSubject.createDefault<List<HashTagModel>>(hashTagList)

    override fun getAll(): Observable<List<HashTagModel>> = hashTagSubject

    override fun getAll(query: DataSource.Query<HashTagModel>): Observable<List<HashTagModel>> =
        hashTagSubject.flatMap { hashTagList ->
            Observable.fromCallable {
                var outputList = hashTagList
                outputList =
                    if (query.has("title")) outputList.filter { it.title == query.get("title") } else outputList
                outputList =
                    if (query.has("parent")) outputList.filter { it.parent == query.get("parent") } else outputList
                outputList
            }
        }


    override fun saveAll(list: List<HashTagModel>): Completable = hashTagSubject
        .map { storedHashTagList ->
            storedHashTagList.map { hashTag ->
                list.firstOrNull { it.title == hashTag.title }?.let { it } ?: hashTag
            }
        }
        .flatMapCompletable {
            Completable.fromAction { hashTagSubject.onNext(it) }
        }

    override fun save(item: HashTagModel): Completable = hashTagSubject
        .takeOnlyFirst()
        .map { hashTagList -> hashTagList.addOrReplaceIfExist(item) { it.title == item.title } }
        .flatMapCompletable { hashTagList ->
            Completable.fromCallable {
                hashTagSubject.onNext(hashTagList)
            }
        }

    override fun removeAll(list: List<HashTagModel>): Completable {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun removeAll(): Completable {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}