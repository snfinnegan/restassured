package org.restassured;
import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import stepdefinitions.CommonStepDefinition;

import java.io.IOException;

public class RunServiceTest {

    @Test
    public void callService() throws IOException {
        CommonStepDefinition stepDefinition = new CommonStepDefinition();
        stepDefinition.iAmUsingUser("sfinneganauto")
                .iSendATweetWithATimestamp()
                .iReadTheLatestText()
                .theTimestampIsFromToday();
    }
}
