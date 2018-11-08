package com.abc.test.service.impl;

import com.abc.annotation.DataSource;
import com.abc.enums.DataSourceEnum;
import com.abc.test.entity.Tags;
import com.abc.test.mapper.TagsMapper;
import com.abc.test.service.ITagsService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author cjie
 * @since 2018-11-08
 */
@Service
@DataSource(DataSourceEnum.DB_BIANLA)
public class TagsServiceImpl extends ServiceImpl<TagsMapper, Tags> implements ITagsService {

}
