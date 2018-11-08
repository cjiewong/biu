package com.abc.test.mapper;

import com.abc.annotation.DataSource;
import com.abc.enums.DataSourceEnum;
import com.abc.test.entity.Tags;
import com.baomidou.mybatisplus.mapper.BaseMapper;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author cjie
 * @since 2018-11-08
 */
@DataSource(DataSourceEnum.DB_BIANLA)
public interface TagsMapper extends BaseMapper<Tags> {

}
