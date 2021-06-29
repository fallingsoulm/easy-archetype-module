package io.github.fallingsoulm.easy.archetype.framework.jdbc;

import cn.hutool.core.lang.Assert;
import io.github.fallingsoulm.easy.archetype.framework.page.PageInfo;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class JdbcExecutorTest {

	@Test
	public void selectOne() {
		JdbcExecutor jdbcExecutor = getJdbcExecutor();
		SysUser user = jdbcExecutor.selectOne(new EntityWrapper<>(SysUser.builder().id(1L).build()), SysUser.class);
		System.out.println(user);
		System.out.println(jdbcExecutor.selectList(new EntityWrapper<>(SysUser.builder().id(1L).build()), SysUser.class));
	}

	@Test
	public void selectById() {
		JdbcExecutor jdbcExecutor = getJdbcExecutor();
		SysUser sysUser = jdbcExecutor.selectById(1, SysUser.class);
		System.out.println(sysUser);
	}

	@Test
	public void selectBatchIds() {

		JdbcExecutor jdbcExecutor = getJdbcExecutor();
		List<SysUser> sysUsers = jdbcExecutor.selectBatchIds(Arrays.asList(1L), SysUser.class);
		System.out.println(sysUsers);
	}

	@Test
	public void selectCount() {
		JdbcExecutor jdbcExecutor = getJdbcExecutor();
		Integer integer = jdbcExecutor.selectCount(new EntityWrapper<SysUser>(SysUser.class));
		System.out.println(integer);
	}

	@Test
	public void insert() {
		JdbcExecutor jdbcExecutor = getJdbcExecutor();
		SysUser user = SysUser.builder().nickName("大山").id(7L).createTime(new Date()).build();
		int insert = jdbcExecutor.insert(user);
		System.out.println(user);
		SysUser select = jdbcExecutor.selectById(user.getId(), SysUser.class);
		Assert.isTrue(select.getNickName().equals(user.getNickName()));
	}


	@Test
	public void updateById() {
		JdbcExecutor jdbcExecutor = getJdbcExecutor();
		Long id = 7L;
		SysUser user = jdbcExecutor.selectById(id, SysUser.class);
		jdbcExecutor.updateById(SysUser.builder().id(id).nickName(user.getNickName() + "--11111-").build());
		SysUser select = jdbcExecutor.selectById(id, SysUser.class);
		Assert.isFalse(user.getNickName().equals(select.getNickName()));
	}


	@Test
	public void update() {
		JdbcExecutor jdbcExecutor = getJdbcExecutor();
		jdbcExecutor.update(SysUser.builder().nickName("大山").build(), new EntityWrapper<SysUser>(SysUser.builder().id(1L).build()));
	}

	@Test
	public void deleteById() {
		Long id = 1L;
		JdbcExecutor jdbcExecutor = getJdbcExecutor();
		int i = jdbcExecutor.deleteById(id, SysUser.class);
		Assert.isNull(jdbcExecutor.selectById(id, SysUser.class));
	}

	@Test
	public void delete() {
		Long id = 2L;
		JdbcExecutor jdbcExecutor = getJdbcExecutor();
		int i = jdbcExecutor.delete(new EntityWrapper<SysUser>(SysUser.builder().id(id).build()));
		Assert.isNull(jdbcExecutor.selectById(id, SysUser.class));
	}


	@Test
	public void deleteBatchIds() {
		JdbcExecutor jdbcExecutor = getJdbcExecutor();
		int i = jdbcExecutor.deleteBatchIds(Arrays.asList(3, 4), SysUser.class);

	}

	@Test
	public void selectByPage() {

		JdbcExecutor jdbcExecutor = getJdbcExecutor();
		PageInfo<SysUser> pageInfo = jdbcExecutor.selectByPage(0, 10, new SysUser());
		System.out.println(pageInfo);
	}

	private JdbcExecutor getJdbcExecutor() {
		SimpleDataSource dataSource = new SimpleDataSource("com.mysql.cj.jdbc.Driver",
				"jdbc:mysql://localhost:3306/test?characterEncoding=utf8&serverTimezone=GMT%2B8&useSSL=false&allowMultiQueries=true",
				"root",
				"rootroot");
		JdbcExecutor jdbcExecutor = new JdbcExecutor();
		jdbcExecutor.setDataSource(dataSource);
		return jdbcExecutor;
	}
}