package com.example;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.emc.documentum.IntegrationLayer;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = IntegrationLayer.class)
@WebAppConfiguration
@IntegrationTest("server.port:8888")   // 4
public class IntegrationLayerApplicationTests {

	@Test
	public void contextLoads() {
	}

}