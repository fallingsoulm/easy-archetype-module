package io.github.fallingsoulm.easy.archetype.framework.sensitive;

/**
 * 默认的脱敏策略
 *
 * @author luyanan
 * @since 2021/2/8
 **/
public class DefaultSensitiveStrategy implements SensitiveStrategy {

	@Override
	public String conver(SensitiveTypeEnum type, String value) {
		String newValue = null;
		switch (type) {
			case CHINESE_NAME:
				newValue = DesensitizedUtils.chineseName(value);
				break;
			case ID_CARD:
				newValue = DesensitizedUtils.idCardNum(value);
				break;
			case FIXED_PHONE:
				newValue = DesensitizedUtils.fixedPhone(value);
				break;
			case MOBILE_PHONE:
				newValue = DesensitizedUtils.mobilePhone(value);
				break;
			case ADDRESS:
				newValue = DesensitizedUtils.address(value);
				break;
			case EMAIL:
				newValue = DesensitizedUtils.email(value);
				break;
			case BANK_CARD:
				newValue = DesensitizedUtils.bankCard(value);
				break;
			case PASSWORD:
				newValue = DesensitizedUtils.password(value);
				break;
			case KEY:
				newValue = DesensitizedUtils.key(value);
				break;
			case CUSTOMER:
				newValue = DesensitizedUtils.desValue(value, value.length() / 3, value.length() / 3, "*");
				break;
			default:
				throw new IllegalArgumentException("Unknow sensitive type enum " + type);

		}
		return newValue;
	}

}
