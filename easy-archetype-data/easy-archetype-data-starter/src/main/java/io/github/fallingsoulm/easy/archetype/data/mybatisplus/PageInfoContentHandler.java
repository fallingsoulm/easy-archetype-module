package io.github.fallingsoulm.easy.archetype.data.mybatisplus;

import java.util.List;

/**
 * @author luyanan
 * @since 2020/9/8
 * <p>分页内容处理</p>
 **/
public interface PageInfoContentHandler<T> {

    /**
     * <p>分页内容处理</p>
     *
     * @param contentList 分页内容列表
     * @author luyanan
     * @since 2020/1/15
     */
    void handler(List<T> contentList);


}
