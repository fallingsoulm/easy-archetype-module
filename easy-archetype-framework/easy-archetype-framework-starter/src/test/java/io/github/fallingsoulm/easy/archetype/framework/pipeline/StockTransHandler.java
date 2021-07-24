package io.github.fallingsoulm.easy.archetype.framework.pipeline;

/**
 * 库存的处理类
 *
 * @author luyanan
 * @since 2021/7/17
 **/
public class StockTransHandler implements TransHandler {
	@Override
	public boolean handler(TransHandlerContext context) {
		System.out.println("扣减库存");
		return true;
	}
}
