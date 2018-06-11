package com.websystique.springmvc.controller;

import com.websystique.springmvc.model.DummyTigerUserData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.websystique.springmvc.service.TigerService;

@RestController
public class TigerApiRestController {

    @Autowired
    TigerService userService;  //Service which will do all data retrieval/manipulation work


    //-------------------Retrieve Single DummyTigerUserData--------------------------------------------------------

    @RequestMapping(value = "/user/{id}", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<DummyTigerUserData> getUser(@PathVariable("id") long id) {
        System.out.println("Fetching DummyTigerUserData with id " + id);
        DummyTigerUserData dummyTigerUserData = userService.findById(id);
        if (dummyTigerUserData == null) {
            System.out.println("DummyTigerUserData with id " + id + " not found");
            return new ResponseEntity<DummyTigerUserData>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<DummyTigerUserData>(dummyTigerUserData, HttpStatus.OK);
    }

}