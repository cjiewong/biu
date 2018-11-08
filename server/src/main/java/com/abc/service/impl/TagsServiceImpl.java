package com.abc.service.impl;

import com.abc.annotation.DataSource;
import com.abc.dao.TagsMapper;
import com.abc.entity.Tags;
import com.abc.enums.DataSourceEnum;
import com.abc.service.ITagsService;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

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
    @Override
//    @DataSource(DataSourceEnum.DB_BIANLA)
    public List<Tags> selectList(Wrapper<Tags> wrapper) {
        return super.selectList(wrapper);
    }
}
