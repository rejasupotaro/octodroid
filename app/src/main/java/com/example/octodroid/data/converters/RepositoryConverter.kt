package com.example.octodroid.data.converters

import com.rejasupotaro.octodroid.models.Repository
import com.rejasupotaro.octodroid.models.Resource
import com.yatatsu.autobundle.Converter

class RepositoryConverter : Converter<Repository, String> {
    override fun convert(repository: Repository): String {
        return repository.toJson()
    }

    override fun original(serializedRepository: String): Repository {
        return Resource.fromJson(serializedRepository, Repository::class.java)
    }
}
