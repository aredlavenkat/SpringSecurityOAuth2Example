package com.websystique.springmvc.service;

import com.websystique.springmvc.model.DummyTigerUserData;


public interface TigerService {
    DummyTigerUserData findById(long id);
}
