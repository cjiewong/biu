package com.abc;

import com.abc.dao.TestUserMapper;
import com.abc.entity.TestUser;
import com.abc.test.entity.Tags;
import com.abc.test.mapper.TagsMapper;
import com.abc.test.service.ITagsService;
import com.google.common.collect.Maps;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;
import java.util.List;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SampleTest {
    @Autowired
    private TestUserMapper testUserMapper;
    @Autowired
    private TagsMapper tagsMapper;

    @Test
    public void test() {
        System.out.println("test insert");
        for(int i=0;i<10;i++) {
            TestUser testUser = new TestUser();
            testUser.setAge(i+1);
            testUser.setCreated(new Date());
            testUser.setName("白痴"+i);
            testUserMapper.insert(testUser);
        }
    }

    @Test
    public void test2() {
        System.out.println("start test ===========================");
        List<TestUser> testUsers = testUserMapper.selectList(null);
        testUsers.forEach(System.out::println);
    }

    @Test
    public void testDelete() {
        System.out.println("delete ing ===========================");
        Map<String, Object> map = Maps.newHashMap();
        map.put("name", "白痴4");
        testUserMapper.deleteByMap(map);
    }

    @Test
    public void testMulti() {
        System.out.println("test multi");
        List<Tags> tags = tagsMapper.selectList(null);
        tags.forEach(System.out::println);
    }
}
