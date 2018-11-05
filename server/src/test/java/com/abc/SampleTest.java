package com.abc;

import com.abc.dao.TestUserMapper;
import com.abc.entity.TestUser;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SampleTest {
    @Autowired
    private TestUserMapper testUserMapper;

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

}
