package com.ichings.mq.rocket.web.controller;

import com.ichings.mq.rocket.biz.sender.Tag001Sender;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author changebooks
 */
@Api(value = "Topic001Controller", tags = "Topic001")
@RequestMapping("Topic001")
@Validated
@RestController
public class Topic001Controller {

    private static final Logger LOGGER = LoggerFactory.getLogger(Topic001Controller.class);

    @Autowired
    private Tag001Sender sender;

    @ApiOperation(value = "Tag001")
    @GetMapping(value = "/sendTag001")
    public void sendTag001() {
        sender.send("hello world");
    }

}
