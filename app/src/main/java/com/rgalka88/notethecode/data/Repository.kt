package com.rgalka88.notethecode.data

import io.reactivex.Completable
import io.reactivex.Observable

abstract class Repository<Entity : Any>(
    private val localDataSource: DataSource<Entity>
) : DataSource<Entity> {

    override fun getAll(): Observable<List<Entity>> = localDataSource.getAll()

    override fun saveAll(list: List<Entity>): Completable = localDataSource.saveAll(list)

    override fun save(item: Entity): Completable = localDataSource.save(item)

    override fun removeAll(list: List<Entity>): Completable = localDataSource.removeAll(list)

    override fun removeAll(): Completable = localDataSource.removeAll()

    override fun getAll(query: DataSource.Query<Entity>): Observable<List<Entity>> =
        localDataSource.getAll(query)
}