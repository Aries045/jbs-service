<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="dm名字" prop="dmName">
        <el-input
          v-model="queryParams.dmName"
          placeholder="请输入dm名字"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>


      <el-form-item label="职称" prop="titleName">
        <el-select v-model="queryParams.titleName" placeholder="请选择职称" clearable>
          <el-option
            v-for="dict in dict.type.dm_title_level"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="入职日期">
        <el-date-picker
          v-model="daterangeEntryDate"
          style="width: 240px"
          value-format="yyyy-MM-dd"
          type="daterange"
          range-separator="-"
          start-placeholder="开始日期"
          end-placeholder="结束日期"
        ></el-date-picker>
      </el-form-item>

      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button
          type="primary"
          plain
          icon="el-icon-plus"
          size="mini"
          @click="handleAdd"
          v-hasPermi="['work:user:add']"
        >新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          plain
          icon="el-icon-edit"
          size="mini"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['work:user:edit']"
        >修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          plain
          icon="el-icon-delete"
          size="mini"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['work:user:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['work:user:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="userList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />

      <el-table-column label="dm名字" align="center" prop="dmName" />
      <el-table-column label="电话" align="center" prop="phone" />
<!--      <el-table-column label="家庭住址" align="center" prop="address" />-->
<!--      <el-table-column label="出生日期" align="center" prop="birthday" width="180">-->
<!--        <template slot-scope="scope">-->
<!--          <span>{{ parseTime(scope.row.birthday, '{y}-{m}-{d}') }}</span>-->
<!--        </template>-->
<!--      </el-table-column>-->
<!--      <el-table-column label="身份证号" align="center" prop="idNumber" />-->
      <el-table-column label="职称" align="center" prop="titleName">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.dm_title_level" :value="scope.row.titleName"/>
        </template>
      </el-table-column>
      <el-table-column label="底薪" align="center" prop="salary" />
      <el-table-column label="入职日期" align="center" prop="entryDate" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.entryDate, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="离职日期" align="center" prop="resignDate" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.resignDate, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>

      <el-table-column label="是否在职" align="center" class-name="small-padding fixed-width">
        <template #default="scope">
          <el-switch
            style="display: block"
            v-model="scope.row.isResigned"
            active-color="#13ce66"
          inactive-color="#ff4949"
          active-text="在职"
          inactive-text="离职"
          @change="isResign(scope.row)"
          ></el-switch>
        </template>
      </el-table-column>

      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['work:user:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['work:user:remove']"
          >删除</el-button>
        </template>
      </el-table-column>
    </el-table>

    <pagination
      v-show="total>0"
      :total="total"
      :page.sync="queryParams.pageNum"
      :limit.sync="queryParams.pageSize"
      @pagination="getList"
    />

    <!-- 添加或修改dm员工管理对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="dm名字" prop="dmName">
          <el-input v-model="form.dmName" placeholder="请输入dm名字" />
        </el-form-item>
        <el-form-item label="电话" prop="phone">
          <el-input v-model="form.phone" placeholder="请输入电话" />
        </el-form-item>
        <el-form-item label="家庭住址" prop="address">
          <el-input v-model="form.address" placeholder="请输入家庭住址" />
        </el-form-item>
        <el-form-item label="出生日期" prop="birthday">
          <el-date-picker clearable
            v-model="form.birthday"
            type="date"
            value-format="yyyy-MM-dd"
            placeholder="请选择出生日期">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="身份证号" prop="idNumber">
          <el-input v-model="form.idNumber" placeholder="请输入身份证号" />
        </el-form-item>
        <el-form-item label="职称" prop="titleName">
          <el-select v-model="form.titleName" placeholder="请选择职称" @change="handleTitleChange">
            <el-option
              v-for="dict in dict.type.dm_title_level"
              :key="dict.value"
              :label="dict.label"
              :value="dict.label"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="底薪" prop="salary">
          <el-input v-model="form.salary" placeholder="请输入底薪" />
        </el-form-item>
        <el-form-item label="入职日期" prop="entryDate">
          <el-date-picker clearable
            v-model="form.entryDate"
            type="date"
            value-format="yyyy-MM-dd"
            placeholder="请选择入职日期">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="离职日期" prop="resignDate">
          <el-date-picker clearable
            v-model="form.resignDate"
            type="date"
            value-format="yyyy-MM-dd"
            placeholder="请选择离职日期">
          </el-date-picker>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { listUser, getUser, delUser, addUser, updateUser, isResign } from "@/api/work/user";

export default {
  name: "User",
  dicts: ['dm_title_level'],
  data() {
    return {
      // 遮罩层
      loading: true,
      // 选中数组
      ids: [],
      // 非单个禁用
      single: true,
      // 非多个禁用
      multiple: true,
      // 显示搜索条件
      showSearch: true,
      // 总条数
      total: 0,
      // dm员工管理表格数据
      userList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 逻辑删除时间范围
      daterangeEntryDate: [],
      // 逻辑删除时间范围
      daterangeResignDate: [],
      value1: true,
      value2: true,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        dmName: null,
        phone: null,
        address: null,
        birthday: null,
        idNumber: null,
        titleName: null,
        titleCode: null,
        salary: null,
        entryDate: null,
        resignDate: null,
        isResign: null,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        dmName: [
          { required: true, message: "dm名字不能为空", trigger: "blur" }
        ],
        titleName: [
          { required: true, message: "职称不能为空", trigger: "change" }
        ],
        titleCode: [
          { required: true, message: "职称代码不能为空", trigger: "blur" }
        ],
        salary: [
          { required: true, message: "底薪不能为空", trigger: "blur" }
        ],
        entryDate: [
          { required: true, message: "入职日期不能为空", trigger: "blur" }
        ]
      }
    };
  },
  created() {
    this.getList();
  },
  methods: {
    /** 查询dm员工管理列表 */
    getList() {
      this.loading = true;
      this.queryParams.params = {};
      if (null != this.daterangeEntryDate && '' != this.daterangeEntryDate) {
        this.queryParams.params["beginEntryDate"] = this.daterangeEntryDate[0];
        this.queryParams.params["endEntryDate"] = this.daterangeEntryDate[1];
      }
      if (null != this.daterangeResignDate && '' != this.daterangeResignDate) {
        this.queryParams.params["beginResignDate"] = this.daterangeResignDate[0];
        this.queryParams.params["endResignDate"] = this.daterangeResignDate[1];
      }
      listUser(this.queryParams).then(response => {
        this.userList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    // 取消按钮
    cancel() {
      this.open = false;
      this.reset();
    },
    // 表单重置
    reset() {
      this.form = {
        id: null,
        dmName: null,
        phone: null,
        address: null,
        birthday: null,
        idNumber: null,
        titleName: null,
        titleCode: null,
        salary: null,
        entryDate: null,
        resignDate: null,
        isResign: null,
        delFlag: null
      };
      this.resetForm("form");
    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.queryParams.pageNum = 1;
      this.getList();
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.daterangeEntryDate = [];
      this.daterangeResignDate = [];
      this.resetForm("queryForm");
      this.handleQuery();
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.id)
      this.single = selection.length!==1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加dm员工管理";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids
      getUser(id).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改dm员工管理";
      });
    },
    /** 是否在职按钮 */
    isResign(row){
      // 1. 确认当前行的状态（是否在职）
      const isResignedNum = row.isResigned ? 1 : 0;// 从开关绑定的属性获取当前状态
      // 2. 构造后端需要的参数对象（而非单个id）
      const params = {
        id: row.id, // 必传：用户ID
        isResign: isResignedNum // 传递开关状态（0=离职，1=在职）
      };
      // 3. 调用是否在职接口（注意接口名建议改为 updateResignStatus 更清晰）
      isResign(params).then(response => {
        if (response.code === 200) {
          this.$message.success(response.msg ||'状态更新成功!');
          // 可刷新表格数据确保同步
          this.getList();
        } else {
          this.$message.error(response.msg || '状态更新失败!');
          // 回滚状态（避免前端显示与后端不一致）
          row.isResigned = !row.isResigned;
        }
      }).catch(err => {
        console.error('请求失败：', err);
        row.isResigned = !row.isResigned; // 异常时回滚
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != null) {
            updateUser(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addUser(this.form).then(response => {
              this.$modal.msgSuccess("新增成功");
              this.open = false;
              this.getList();
            });
          }
        }
      });
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const ids = row.id || this.ids;
      this.$modal.confirm('是否确认删除dm员工管理编号为"' + ids + '"的数据项？').then(function() {
        return delUser(ids);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('work/user/export', {
        ...this.queryParams
      }, `user_${new Date().getTime()}.xlsx`)
    },
    /**新增change事件 */
    handleTitleChange(selectedLabel) {
      // 根据选中的label，找到对应的value（职称代码）
      const targetDict = this.dict.type.dm_title_level.find(
        item => item.label === selectedLabel
      );
      if (targetDict) {
        this.form.titleCode = targetDict.value;
      } else {
        this.form.titleCode = ''; // 未匹配到时清空
      }
    }
  }

};
</script>
