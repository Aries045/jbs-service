package com.ruoyi.web.controller.work;

import java.util.List;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.system.domain.work.DmUser;
import com.ruoyi.system.service.work.IDmUserService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * dm员工管理Controller
 *
 * @author wpy
 * @date 2025-10-23
 */
@RestController
@RequestMapping("/work/user")
public class DmUserController extends BaseController {
    @Autowired
    private IDmUserService dmUserService;

    /**
     * 查询dm员工管理列表
     */
    @PreAuthorize("@ss.hasPermi('work:user:list')")
    @GetMapping("/list")
    public TableDataInfo list(DmUser dmUser) {
        startPage();
        List<DmUser> list = dmUserService.selectDmUserList(dmUser);
        return getDataTable(list);
    }

    /**
     * 导出dm员工管理列表
     */
    @PreAuthorize("@ss.hasPermi('work:user:export')")
    @Log(title = "dm员工管理", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, DmUser dmUser) {
        List<DmUser> list = dmUserService.selectDmUserList(dmUser);
        ExcelUtil<DmUser> util = new ExcelUtil<DmUser>(DmUser.class);
        util.exportExcel(response, list, "dm员工管理数据");
    }

    /**
     * 获取dm员工管理详细信息
     */
    @PreAuthorize("@ss.hasPermi('work:user:query')")
    @GetMapping(value = "/get/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return success(dmUserService.selectDmUserById(id));
    }

    /**
     * 新增dm员工管理
     */
    @PreAuthorize("@ss.hasPermi('work:user:add')")
    @Log(title = "dm员工管理", businessType = BusinessType.INSERT)
    @PostMapping("/save")
    public AjaxResult add(@Validated @RequestBody DmUser dmUser) {
        return toAjax(dmUserService.insertDmUser(dmUser));
    }

    /**
     * 修改dm员工管理
     */
    @PreAuthorize("@ss.hasPermi('work:user:edit')")
    @Log(title = "dm员工管理", businessType = BusinessType.UPDATE)
    @PutMapping("/update")
    public AjaxResult edit(@Validated @RequestBody DmUser dmUser) {
        return toAjax(dmUserService.updateDmUser(dmUser));
    }

    /**
     * 删除dm员工管理
     */
    @PreAuthorize("@ss.hasPermi('work:user:remove')")
    @Log(title = "dm员工管理", businessType = BusinessType.DELETE)
    @DeleteMapping("/delete/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(dmUserService.deleteDmUserByIds(ids));
    }

//    @PreAuthorize("@ss.hasPermi('work:user:remove')")
    @Log(title = "dm员工管理", businessType = BusinessType.UPDATE)
    @PostMapping("/isResign")
    public AjaxResult isResign(@RequestBody DmUser dmUser) {
        return toAjax(dmUserService.isResign(dmUser));
    }
}
