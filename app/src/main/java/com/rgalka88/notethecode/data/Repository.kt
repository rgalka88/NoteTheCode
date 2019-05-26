package com.rgalka88.notethecode.data

import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit

abstract class Repository<Entity : Any>(
    private val remoteDataSource: DataSource<Entity>,
    private val localDataSource: DataSource<Entity>
) : DataSource<Entity> {

    override fun getAll(): Observable<List<Entity>> {
        return Observable.concatArrayEager(
            localDataSource.getAll().subscribeOn(Schedulers.io()),
            Observable.defer {
//                if (isNetworkAvailable())
                    remoteDataSource.getAll().delay(5, TimeUnit.SECONDS).subscribeOn(Schedulers.io())
                        .flatMap { items ->
                            localDataSource.removeAll().andThen(localDataSource.saveAll(items))
                        }
//                else Observable.empty()
            }.subscribeOn(Schedulers.io())
        )
    }

    override fun saveAll(list: List<Entity>): Observable<List<Entity>> {
        return Observable.defer {
//            if (isNetworkAvailable())
            // save items to remoteDataSource first
                remoteDataSource.saveAll(list).subscribeOn(Schedulers.io())
                    // save items to localDataSource
                    .flatMap { localDataSource.saveAll(list) }
//            else
//                Observable.error(IllegalAccessError("You are offline"))
        }
    }

    override fun removeAll(list: List<Entity>): Completable {
        return Completable.defer {
//            if (isNetworkAvailable())
            // remove items from remoteDataSource first
                remoteDataSource.removeAll(list).subscribeOn(Schedulers.io())
                    // remove items from localDataSource
                    .andThen(localDataSource.removeAll(list))
//            else
//                Completable.error(IllegalAccessError("You are offline"))
        }
    }

    override fun removeAll(): Completable {
        return Completable.defer {
//            if (isNetworkAvailable())
            // remove items from remoteDataSource first
                remoteDataSource.removeAll().subscribeOn(Schedulers.io())
                    // remove items from localDataSource
                    .andThen(localDataSource.removeAll())
//            else
//                Completable.error(IllegalAccessError("You are offline"))
        }
    }

    override fun getAll(query: DataSource.Query<Entity>): Observable<List<Entity>> {
//        return Observable.concatArrayEager(
            // get items from localDataSource first
//            localDataSource.getAll(query).subscribeOn(Schedulers.io()),
            // get items from remoteDataSource if Network is Available
            return Observable.defer {
//                if (isNetworkAvailable())
                // get new items from remoteDataSource
                    remoteDataSource.getAll(query).subscribeOn(Schedulers.io())
//                                .flatMap { applyPaging(it, query) }
//                        .flatMap { l ->
                            // get old items from localDataSource
//                            localDataSource.getAll(query)
                                // remove old items from localDataSource
//                                .flatMapCompletable { old -> localDataSource.removeAll(old) }
                                // save new items from remoteDataSource to localDataSource
//                                .andThen(localDataSource.saveAll(l))
//                        }
//                else
                // or return empty
//                    Observable.empty()
            }.subscribeOn(Schedulers.io())
//        )
    }
}