package com.rgalka88.notethecode.data.draweritem

import android.content.Context
import com.rgalka88.notethecode.R
import com.rgalka88.notethecode.core.utils.addOrReplaceIfExist
import com.rgalka88.notethecode.core.utils.takeOnlyFirst
import com.rgalka88.notethecode.data.DataSource
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.subjects.BehaviorSubject
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LocalDrawerItemDataSource @Inject constructor(
    context: Context
) : DataSource<DrawerItemModel> {

    private val hashTagList = listOf(
        DrawerItemModel(
            title = context.getString(R.string.notes),
            iconResId = R.drawable.ic_note
        ),
        DrawerItemModel(
            title = context.getString(R.string.untagged),
            iconResId = R.drawable.ic_note,
            parent = context.getString(R.string.notes)
        ),
        DrawerItemModel(
            title = context.getString(R.string.todo),
            iconResId = R.drawable.ic_note,
            parent = context.getString(R.string.notes)
        ),
        DrawerItemModel(
            title = context.getString(R.string.today),
            iconResId = R.drawable.ic_note,
            parent = context.getString(R.string.notes)
        ),
        DrawerItemModel(
            title = context.getString(R.string.locked),
            iconResId = R.drawable.ic_note,
            parent = context.getString(R.string.notes)
        ),
        DrawerItemModel(
            title = context.getString(R.string.trash),
            iconResId = R.drawable.ic_trash
        ),
        DrawerItemModel("HashTag1"),
        DrawerItemModel("HashTag2"),

        DrawerItemModel("HashTagA", "HashTag1"),
        DrawerItemModel("HashTagB", "HashTag1"),
        DrawerItemModel("HashTagC", "HashTag1"),
        DrawerItemModel("HashTagD", "HashTag1"),
        DrawerItemModel("HashTagE", "HashTag1"),

        DrawerItemModel("HashTagA1", "HashTagA"),
        DrawerItemModel("HashTagA2", "HashTagA"),
        DrawerItemModel("HashTagA3", "HashTagA"),
        DrawerItemModel("HashTagA4", "HashTagA"),

        DrawerItemModel("HashTagA11", "HashTagA1"),
        DrawerItemModel("HashTagA12", "HashTagA1"),
        DrawerItemModel("HashTagA13", "HashTagA1"),
        DrawerItemModel("HashTagA14", "HashTagA1"),

        DrawerItemModel("HashTagA111", "HashTagA11"),
        DrawerItemModel("HashTagA112", "HashTagA11"),
        DrawerItemModel("HashTagA113", "HashTagA11"),
        DrawerItemModel("HashTagA114", "HashTagA11"),

        DrawerItemModel("HashTagA1111", "HashTagA111"),
        DrawerItemModel("HashTagA1112", "HashTagA111"),
        DrawerItemModel("HashTagA1113", "HashTagA111"),
        DrawerItemModel("HashTagA1114", "HashTagA111")
    )

    private val hashTagSubject = BehaviorSubject.createDefault<List<DrawerItemModel>>(hashTagList)

    override fun getAll(): Observable<List<DrawerItemModel>> = hashTagSubject

    override fun getAll(query: DataSource.Query<DrawerItemModel>): Observable<List<DrawerItemModel>> =
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

    override fun saveAll(list: List<DrawerItemModel>): Completable = hashTagSubject
        .map { storedHashTagList ->
            storedHashTagList.map { hashTag ->
                list.firstOrNull { it.title == hashTag.title }?.let { it } ?: hashTag
            }
        }
        .flatMapCompletable {
            Completable.fromAction { hashTagSubject.onNext(it) }
        }

    override fun save(item: DrawerItemModel): Completable = hashTagSubject
        .takeOnlyFirst()
        .map { hashTagList -> hashTagList.addOrReplaceIfExist(item) { it.title == item.title } }
        .flatMapCompletable { hashTagList ->
            Completable.fromCallable { hashTagSubject.onNext(hashTagList) }
        }

    override fun removeAll(list: List<DrawerItemModel>): Completable {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun removeAll(): Completable {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}