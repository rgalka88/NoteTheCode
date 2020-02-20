package com.rgalka88.notethecode.data.hashtag

import com.rgalka88.notethecode.data.Repository
import javax.inject.Inject

class HashTagRepository @Inject constructor(
    localHashTagDataSource: LocalHashTagDataSource
) : Repository<HashTagModel>(localHashTagDataSource)