package io.github.fallingsoulm.easy.archetype.job.utils;

import org.quartz.CronExpression;

import java.text.ParseException;
import java.util.Date;

/**
 * cron表达式工具类
 *
 * @author luyanan
 * @since 2021/3/18
 **/
public class CronUtils {


	/**
	 * 当前表达式是否有效
	 *
	 * @param cronExpression cron表达式
	 * @return boolean 是否有效
	 * @since 2021/3/18
	 */
	public static boolean isValid(String cronExpression) {
		return CronExpression.isValidExpression(cronExpression);
	}

	/**
	 * 返回表达式无效的时候的错误描述
	 *
	 * @param cronExpression cron 表达式
	 * @return java.lang.String 错误描述
	 * @since 2021/3/18
	 */
	public static String getInvalidMessage(String cronExpression) {

		try {
			new CronExpression(cronExpression);
			return null;
		} catch (ParseException e) {
			return e.getMessage();
		}
	}

	/**
	 * 根据给定的cron表达式返回下一个执行时间
	 *
	 * @param cronExpression cron 表达式
	 * @return java.util.Date
	 * @since 2021/3/18
	 */
	public static Date getNextExecution(String cronExpression) {

		try {
			CronExpression cron = new CronExpression(cronExpression);
			return cron.getNextValidTimeAfter(new Date(System.currentTimeMillis()));
		} catch (ParseException e) {
			throw new IllegalArgumentException(e.getMessage());
		}
	}


}
