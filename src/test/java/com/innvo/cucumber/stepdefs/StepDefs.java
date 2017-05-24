package com.innvo.cucumber.stepdefs;

import com.innvo.ApprefactoryApp;

import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.ResultActions;

import org.springframework.boot.test.context.SpringBootTest;

@WebAppConfiguration
@SpringBootTest
@ContextConfiguration(classes = ApprefactoryApp.class)
public abstract class StepDefs {

    protected ResultActions actions;

}
