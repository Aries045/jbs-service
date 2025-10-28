package com.ruoyi.system.service.work.impl;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.system.mapper.work.DmUserMapper;
import com.ruoyi.system.domain.work.DmUser;
import com.ruoyi.system.service.work.IDmUserService;

/**
 * dm员工管理Service业务层处理
 *
 * @author wpy
 * @date 2025-10-23
 */
@Service
public class DmUserServiceImpl implements IDmUserService {
    @Autowired
    private DmUserMapper dmUserMapper;

    /**
     * 查询dm员工管理
     *
     * @param id dm员工管理主键
     * @return dm员工管理
     */
    @Override
    public DmUser selectDmUserById(Long id) {
        return dmUserMapper.selectDmUserById(id);
    }

    /**
     * 查询dm员工管理列表
     *
     * @param dmUser dm员工管理
     * @return dm员工管理
     */
    @Override
    public List<DmUser> selectDmUserList(DmUser dmUser) {
        return dmUserMapper.selectDmUserList(dmUser);
    }

    /**
     * 新增dm员工管理
     *
     * @param dmUser dm员工管理
     * @return 结果
     */
    @Override
    public int insertDmUser(DmUser dmUser) {
        return dmUserMapper.insertDmUser(dmUser);
    }

    /**
     * 修改dm员工管理
     *
     * @param dmUser dm员工管理
     * @return 结果
     */
    @Override
    public int updateDmUser(DmUser dmUser) {
        return dmUserMapper.updateDmUser(dmUser);
    }

    /**
     * 批量删除dm员工管理
     *
     * @param ids 需要删除的dm员工管理主键
     * @return 结果
     */
    @Override
    public int deleteDmUserByIds(Long[] ids) {
        return dmUserMapper.deleteDmUserByIds(ids);
    }

    /**
     * 删除dm员工管理信息
     *
     * @param id dm员工管理主键
     * @return 结果
     */
    @Override
    public int deleteDmUserById(Long id) {
        return dmUserMapper.deleteDmUserById(id);
    }

    @Override
    public int isResign(DmUser dmUser) {
        Date date = new Date();
        dmUser.setIsResign(dmUser.getIsResign());
        if(dmUser.getIsResign() == 0) {
            dmUser.setResignDate(date);
        }else{
            dmUser.setResignDate(null);
        }
        return dmUserMapper.updateDmUser(dmUser);
    }
}
