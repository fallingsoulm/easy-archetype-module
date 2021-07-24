package io.github.fallingsoulm.easy.archetype.framework.pipeline;

import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

public class PipelineProcessorEngineTest {

	@Test
	public void test() {

		OrderContext orderContext = new OrderContext();
		orderContext.setOrderNo(UUID.randomUUID().toString());
		orderContext.setNum(6);
		PipelineProcessorEngine.build().create(orderContext)
				.and(new StockTransHandler())
				.or(new EmailNoticeTransHandler())
				.or(new CouponTransHandler())
				.start();


		try {
			Thread.sleep(10000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}


	}

}