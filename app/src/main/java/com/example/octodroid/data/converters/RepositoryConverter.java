package com.example.octodroid.data.converters;

import com.rejasupotaro.octodroid.models.Repository;
import com.rejasupotaro.octodroid.models.Resource;
import com.yatatsu.autobundle.Converter;

public class RepositoryConverter implements Converter<Repository, String> {
    @Override
    public String convert(Repository repository) {
        return repository.toJson();
    }

    @Override
    public Repository original(String serializedRepository) {
        return Resource.fromJson(serializedRepository, Repository.class);
    }
}
