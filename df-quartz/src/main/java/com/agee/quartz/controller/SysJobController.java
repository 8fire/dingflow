package com.agee.quartz.controller;

import com.agee.common.constant.Constants;
import com.agee.common.core.controller.BaseController;
import com.agee.common.core.domain.R;
import com.agee.common.core.page.TableDataInfo;
import com.agee.common.exception.job.TaskException;
import com.agee.common.utils.StringUtils;
import com.agee.quartz.domain.SysJob;
import com.agee.quartz.service.ISysJobService;
import com.agee.quartz.util.CronUtils;
import com.agee.quartz.util.ScheduleUtils;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 调度任务信息操作处理
 *
 * @author ruoyi
 */
@RestController
@RequestMapping("/monitor/job")
public class SysJobController extends BaseController {
    @Autowired
    private ISysJobService jobService;

    /**
     * 查询定时任务列表
     */
//    @PreAuthorize("@ss.hasPermi('monitor:job:list')")
    @GetMapping("/list")
    public R<TableDataInfo<SysJob>> list(SysJob sysJob) {
        startPage();
        List<SysJob> list = jobService.selectJobList(sysJob);
        return getDataTable(list);
    }

    /**
     * 导出定时任务列表
     */
//    @PreAuthorize("@ss.hasPermi('monitor:job:export')")
//    @Log(title = "定时任务", businessType = BusinessType.EXPORT)
//    @PostMapping("/export")
//    public void export(HttpServletResponse response, SysJob sysJob) {
//        List<SysJob> list = jobService.selectJobList(sysJob);
//        ExcelUtil<SysJob> util = new ExcelUtil<SysJob>(SysJob.class);
//        util.exportExcel(response, list, "定时任务");
//    }

    /**
     * 获取定时任务详细信息
     */
//    @PreAuthorize("@ss.hasPermi('monitor:job:query')")
    @GetMapping(value = "/{jobId}")
    public R getInfo(@PathVariable("jobId") Long jobId) {
        return R.ok(jobService.selectJobById(jobId));
    }

    /**
     * 新增定时任务
     */
//    @PreAuthorize("@ss.hasPermi('monitor:job:add')")
//    @Log(title = "定时任务", businessType = BusinessType.INSERT)
    @PostMapping
    public R add(@RequestBody SysJob job) throws SchedulerException, TaskException {
        if (!CronUtils.isValid(job.getCronExpression())) {
            return R.fail("新增任务'" + job.getJobName() + "'失败，Cron表达式不正确");
        } else if (StringUtils.containsIgnoreCase(job.getInvokeTarget(), Constants.LOOKUP_RMI)) {
            return R.fail("新增任务'" + job.getJobName() + "'失败，目标字符串不允许'rmi'调用");
        } else if (StringUtils.containsAnyIgnoreCase(job.getInvokeTarget(), new String[]{Constants.LOOKUP_LDAP, Constants.LOOKUP_LDAPS})) {
            return R.fail("新增任务'" + job.getJobName() + "'失败，目标字符串不允许'ldap(s)'调用");
        } else if (StringUtils.containsAnyIgnoreCase(job.getInvokeTarget(), new String[]{Constants.HTTP, Constants.HTTPS})) {
            return R.fail("新增任务'" + job.getJobName() + "'失败，目标字符串不允许'http(s)'调用");
        } else if (StringUtils.containsAnyIgnoreCase(job.getInvokeTarget(), Constants.JOB_ERROR_STR)) {
            return R.fail("新增任务'" + job.getJobName() + "'失败，目标字符串存在违规");
        } else if (!ScheduleUtils.whiteList(job.getInvokeTarget())) {
            return R.fail("新增任务'" + job.getJobName() + "'失败，目标字符串不在白名单内");
        }
        job.setCreateBy(getUsername());
        jobService.insertJob(job);
        return R.ok();
    }

    /**
     * 修改定时任务
     */
//    @PreAuthorize("@ss.hasPermi('monitor:job:edit')")
//    @Log(title = "定时任务", businessType = BusinessType.UPDATE)
    @PutMapping
    public R edit(@RequestBody SysJob job) throws SchedulerException, TaskException {
        if (!CronUtils.isValid(job.getCronExpression())) {
            return R.fail("修改任务'" + job.getJobName() + "'失败，Cron表达式不正确");
        } else if (StringUtils.containsIgnoreCase(job.getInvokeTarget(), Constants.LOOKUP_RMI)) {
            return R.fail("修改任务'" + job.getJobName() + "'失败，目标字符串不允许'rmi'调用");
        } else if (StringUtils.containsAnyIgnoreCase(job.getInvokeTarget(), new String[]{Constants.LOOKUP_LDAP, Constants.LOOKUP_LDAPS})) {
            return R.fail("修改任务'" + job.getJobName() + "'失败，目标字符串不允许'ldap(s)'调用");
        } else if (StringUtils.containsAnyIgnoreCase(job.getInvokeTarget(), new String[]{Constants.HTTP, Constants.HTTPS})) {
            return R.fail("修改任务'" + job.getJobName() + "'失败，目标字符串不允许'http(s)'调用");
        } else if (StringUtils.containsAnyIgnoreCase(job.getInvokeTarget(), Constants.JOB_ERROR_STR)) {
            return R.fail("修改任务'" + job.getJobName() + "'失败，目标字符串存在违规");
        } else if (!ScheduleUtils.whiteList(job.getInvokeTarget())) {
            return R.fail("修改任务'" + job.getJobName() + "'失败，目标字符串不在白名单内");
        }
        job.setUpdateBy(getUsername());
        jobService.updateJob(job);
        return R.ok();
    }

    /**
     * 定时任务状态修改
     */
//    @PreAuthorize("@ss.hasPermi('monitor:job:changeStatus')")
//    @Log(title = "定时任务", businessType = BusinessType.UPDATE)
    @PutMapping("/changeStatus")
    public R changeStatus(@RequestBody SysJob job) throws SchedulerException {
        SysJob newJob = jobService.selectJobById(job.getJobId());
        newJob.setStatus(job.getStatus());
        jobService.changeStatus(newJob);
        return R.ok();
    }

    /**
     * 定时任务立即执行一次
     */
//    @PreAuthorize("@ss.hasPermi('monitor:job:changeStatus')")
//    @Log(title = "定时任务", businessType = BusinessType.UPDATE)
    @PutMapping("/run")
    public R run(@RequestBody SysJob job) throws SchedulerException {
        boolean result = jobService.run(job);
        return result ? R.ok() : R.fail("任务不存在或已过期！");
    }

    /**
     * 删除定时任务
     */
//    @PreAuthorize("@ss.hasPermi('monitor:job:remove')")
//    @Log(title = "定时任务", businessType = BusinessType.DELETE)
    @DeleteMapping("/{jobIds}")
    public R remove(@PathVariable Long[] jobIds) throws SchedulerException, TaskException {
        jobService.deleteJobByIds(jobIds);
        return R.ok();
    }
}
