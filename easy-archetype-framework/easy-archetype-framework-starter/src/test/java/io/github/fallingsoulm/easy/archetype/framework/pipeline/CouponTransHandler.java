package io.github.fallingsoulm.easy.archetype.framework.pipeline;

/**
 * 优惠价处理类
 *
 * @author luyanan
 * @since 2021/7/17
 **/
public class CouponTransHandler implements TransHandler {
	@Override
	public boolean support(TransHandlerContext context) {
		OrderContext orderContext = (OrderContext) context;
		return orderContext.getNum() > 5;
	}

	@Override
	public boolean handler(TransHandlerContext context) {
		System.out.println("发送优惠价");
		return true;
	}
}
