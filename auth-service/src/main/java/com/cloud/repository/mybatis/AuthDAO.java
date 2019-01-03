package com.cloud.repository.mybatis;

import com.cloud.model.dto.KeyValuePair;

import java.util.List;

public interface AuthDAO {
    List<KeyValuePair> findUrlRole();
}
