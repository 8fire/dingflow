package com.agee.system.service;

import com.agee.system.domain.SysOperLog;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * @author qimingjin
 * @date 2022-09-15 13:53
 * @Description:
 */
public interface SysOperLogService extends IService<SysOperLog> {

    /**
     * 保存操作日志
     * @param operLogDTO 操作日志对象
     */
    void recordOper(final SysOperLog operLogDTO);
    /**
     * 新增操作日志
     *
     * @param operLog 操作日志对象
     */
    void insertOperlog(SysOperLog operLog);

    /**
     * 查询系统操作日志集合
     *
     * @param operLog 操作日志对象
     * @return 操作日志集合
     */
    List<SysOperLog> selectOperLogList(SysOperLog operLog);

    /**
     * 批量删除系统操作日志
     *
     * @param operIds 需要删除的操作日志ID
     * @return 结果
     */
    int deleteOperLogByIds(Long[] operIds);

    /**
     * 查询操作日志详细
     *
     * @param operId 操作ID
     * @return 操作日志对象
     */
    SysOperLog selectOperLogById(Long operId);

    /**
     * 清空操作日志
     */
    void cleanOperLog();
}
