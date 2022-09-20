package com.agee.quartz.controller;

import com.agee.common.core.controller.BaseController;
import com.agee.common.core.domain.R;
import com.agee.common.core.page.TableDataInfo;
import com.agee.common.enums.BusinessType;
import com.agee.quartz.domain.SysJobLog;
import com.agee.quartz.service.ISysJobLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 调度日志操作处理
 *
 * @author ruoyi
 */
@RestController
@RequestMapping("/monitor/jobLog")
public class SysJobLogController extends BaseController {
    @Autowired
    private ISysJobLogService jobLogService;

    /**
     * 查询定时任务调度日志列表
     */
//    @PreAuthorize("@ss.hasPermi('monitor:job:list')")
    @GetMapping("/list")
    public R<TableDataInfo<SysJobLog>> list(SysJobLog sysJobLog) {
        startPage();
        List<SysJobLog> list = jobLogService.selectJobLogList(sysJobLog);
        return getDataTable(list);
    }

    /**
     * 导出定时任务调度日志列表
     */
//    @PreAuthorize("@ss.hasPermi('monitor:job:export')")
//    @Log(title = "任务调度日志", businessType = BusinessType.EXPORT)
//    @PostMapping("/export")
//    public void export(HttpServletResponse response, SysJobLog sysJobLog) {
//        List<SysJobLog> list = jobLogService.selectJobLogList(sysJobLog);
//        ExcelUtil<SysJobLog> util = new ExcelUtil<SysJobLog>(SysJobLog.class);
//        util.exportExcel(response, list, "调度日志");
//    }

    /**
     * 根据调度编号获取详细信息
     */
//    @PreAuthorize("@ss.hasPermi('monitor:job:query')")
    @GetMapping(value = "/{jobLogId}")
    public R getInfo(@PathVariable Long jobLogId) {
        return R.ok(jobLogService.selectJobLogById(jobLogId));
    }


    /**
     * 删除定时任务调度日志
     */
//    @PreAuthorize("@ss.hasPermi('monitor:job:remove')")
//    @Log(title = "定时任务调度日志", businessType = BusinessType.DELETE)
    @DeleteMapping("/{jobLogIds}")
    public R remove(@PathVariable Long[] jobLogIds) {
        jobLogService.deleteJobLogByIds(jobLogIds);
        return R.ok();
    }

    /**
     * 清空定时任务调度日志
     */
//    @PreAuthorize("@ss.hasPermi('monitor:job:remove')")
//    @Log(title = "调度日志", businessType = BusinessType.CLEAN)
    @DeleteMapping("/clean")
    public R clean() {
        jobLogService.cleanJobLog();
        return R.ok();
    }
}
