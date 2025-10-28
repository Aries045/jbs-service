package com.ruoyi.system.mapper.work;

import java.util.List;
import com.ruoyi.system.domain.work.DmUser;

/**
 * dm员工管理Mapper接口
 *
 * @author wpy
 * @date 2025-10-23
 */
public interface DmUserMapper
{
    /**
     * 查询dm员工管理
     *
     * @param id dm员工管理主键
     * @return dm员工管理
     */
    public DmUser selectDmUserById(Long id);

    /**
     * 查询dm员工管理列表
     *
     * @param dmUser dm员工管理
     * @return dm员工管理集合
     */
    public List<DmUser> selectDmUserList(DmUser dmUser);

    /**
     * 新增dm员工管理
     *
     * @param dmUser dm员工管理
     * @return 结果
     */
    public int insertDmUser(DmUser dmUser);

    /**
     * 修改dm员工管理
     *
     * @param dmUser dm员工管理
     * @return 结果
     */
    public int updateDmUser(DmUser dmUser);

    /**
     * 删除dm员工管理
     *
     * @param id dm员工管理主键
     * @return 结果
     */
    public int deleteDmUserById(Long id);

    /**
     * 批量删除dm员工管理
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteDmUserByIds(Long[] ids);
}
