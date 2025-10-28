package com.ruoyi.system.domain.work;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.phone.Phone;
import com.ruoyi.common.core.domain.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;

/**
 * dm员工管理对象 dm_user
 *
 * @author wpy
 * @date 2025-10-23
 */

@EqualsAndHashCode(callSuper = true)
@Data
public class DmUser extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    private String id;

    /**
     * dm名字
     */
    @Excel(name = "dm名字")
    private String dmName;

    /**
     * 电话
     */
    @Excel(name = "电话")
    @NotNull(message = "手机号不能为空")
    @Phone(message = "请输入正确的手机号") // 使用自定义注解
    private String phone;

    /**
     * 家庭住址
     */
    @Excel(name = "家庭住址")
    private String address;

    /**
     * 出生日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "出生日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date birthday;

    /**
     * 身份证号
     */
    @Excel(name = "身份证号")
    private String idNumber;

    /**
     * 职称
     */
    @Excel(name = "职称")
    private String titleName;

    /**
     * 职称代码
     */
    @Excel(name = "职称代码")
    private String titleCode;

    /**
     * 底薪
     */
    @Excel(name = "底薪")
    private BigDecimal salary;

    /**
     * 入职日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "入职日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date entryDate;

    /**
     * 离职日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "离职日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date resignDate;

    /**
     * 是否在职（0否1是）
     */
    @Excel(name = "是否在职", readConverterExp = "0=否1是")
    private Integer isResign;

    /**
     * 逻辑删除（0否1是）
     */
    private Integer delFlag;

}
