package io.github.fallingsoulm.easy.archetype.framework.pipeline;

import lombok.Data;

/**
 * 订单上下文
 *
 * @author luyanan
 * @since 2021/7/17
 **/
@Data
public class OrderContext implements TransHandlerContext {
	/**
	 * 订单号
	 *
	 * @author luyanan
	 * @since 2021/7/17
	 */
	private String orderNo;

	/**
	 * 订单数量, 当订单数量大于5的时候, 发送优惠卷
	 *
	 * @author luyanan
	 * @since 2021/7/17
	 */
	private int num;

	/**
	 * 邮寄地址
	 *
	 * @author luyanan
	 * @since 2021/7/17
	 */
	private String address;
}
