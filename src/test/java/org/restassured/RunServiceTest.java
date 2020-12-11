package org.restassured;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;

public class RunServiceTest {
    @Test
    public void callService() throws IOException {
        Services services = new Services();
        services.getTimeLine();
        Assertions.assertTrue(true);
    }
}
