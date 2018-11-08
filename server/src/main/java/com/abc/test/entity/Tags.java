package com.abc.test.entity;

import java.util.Date;
import com.baomidou.mybatisplus.activerecord.Model;
import java.io.Serializable;



import com.baomidou.mybatisplus.annotations.Version;

import io.swagger.models.auth.In;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 
 * </p>
 *
 * @author cjie
 * @since 2018-11-08
 */
@Data
@Accessors(chain = true)
public class Tags extends Model<Tags> {

    private static final long serialVersionUID = 1L;

    private Integer id;

    private String name;

    private Date created;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
