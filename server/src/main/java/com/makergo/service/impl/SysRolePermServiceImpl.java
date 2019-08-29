package com.makergo.service.impl;

import com.makergo.dao.SysRolePermMapper;
import com.makergo.entity.SysRolePerm;
import com.makergo.service.SysRolePermService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class SysRolePermServiceImpl extends ServiceImpl<SysRolePermMapper, SysRolePerm> implements SysRolePermService {
}
