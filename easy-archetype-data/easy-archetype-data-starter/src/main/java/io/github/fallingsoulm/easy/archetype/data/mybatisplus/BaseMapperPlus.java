package io.github.fallingsoulm.easy.archetype.data.mybatisplus;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import org.apache.ibatis.annotations.Param;

/**
 * 自定义BaseMapper
 *
 * @author luyanan
 * @since 2021/1/20
 **/
public interface BaseMapperPlus<T> extends BaseMapper<T> {

	/**
	 * 根据实体条件逻辑删除并填充字段
	 * @param params 删除条件
	 * @return int
	 * @since 2021/1/21
	 */
	int deleteByIdWithFill(T params);

	/**
	 * 批量逻辑删除并填充字段
	 * @param param
	 * @param wrapper
	 * @return int
	 * @since 2021/1/21
	 */
	int batchDeleteWithFill(@Param(Constants.ENTITY) T param, @Param(Constants.WRAPPER) Wrapper<T> wrapper);

}
