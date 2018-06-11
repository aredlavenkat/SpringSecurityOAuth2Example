package com.websystique.springmvc.service;

import com.websystique.springmvc.model.DummyTigerUserData;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

@Service("userService")
public class TigerServiceImpl implements TigerService {

    private static final AtomicLong counter = new AtomicLong();

    private static List<DummyTigerUserData> dummyTigerUserData;

    static {
        dummyTigerUserData = populateDummyUsers();
    }

    public List<DummyTigerUserData> findAllUsers() {
        return dummyTigerUserData;
    }

    public DummyTigerUserData findById(long id) {
        for (DummyTigerUserData dummyTigerUserData : TigerServiceImpl.dummyTigerUserData) {
            if (dummyTigerUserData.getId() == id) {
                return dummyTigerUserData;
            }
        }
        return null;
    }


    private static List<DummyTigerUserData> populateDummyUsers() {
        List<DummyTigerUserData> dummyTigerUserData = new ArrayList<DummyTigerUserData>();
        dummyTigerUserData.add(new DummyTigerUserData(counter.incrementAndGet(), "Sam", 30, 70000));
        dummyTigerUserData.add(new DummyTigerUserData(counter.incrementAndGet(), "Tom", 40, 50000));
        dummyTigerUserData.add(new DummyTigerUserData(counter.incrementAndGet(), "Jerome", 45, 30000));
        dummyTigerUserData.add(new DummyTigerUserData(counter.incrementAndGet(), "Silvia", 50, 40000));
        return dummyTigerUserData;
    }

}
