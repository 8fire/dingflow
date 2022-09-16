package com.agee.common.utils;

import cn.hutool.core.util.StrUtil;
import cn.hutool.db.sql.SqlUtil;
import com.agee.common.enums.ResponseCodeEnum;
import com.agee.common.exception.ServiceException;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * @author qimingjin
 * @date 2022-09-16 10:56
 * @Description:
 */
@Slf4j
public class SqlUtils extends SqlUtil {

    /**
     * 定义常用的 sql关键字
     */
    public static String SQL_REGEX = "select |insert |delete |update |drop |count |exec |chr |mid |master |truncate |char |and |declare ";

    /**
     * 仅支持字母、数字、下划线、空格、逗号、小数点（支持多个字段排序）
     */
    public static String SQL_PATTERN = "[a-zA-Z0-9_\\ \\,\\.]+";

    /**
     * 检查字符，防止注入绕过
     */
    public static String escapeOrderBySql(String value) {
        if (StrUtil.isNotEmpty(value) && !isValidOrderBySql(value)) {
            log.error("参数不符合规范，不能进行查询");
            throw new ServiceException(ResponseCodeEnum.SERVER_FAIL);
        }
        return value;
    }

    /**
     * 验证 order by 语法是否符合规范
     */
    public static boolean isValidOrderBySql(String value) {
        return value.matches(SQL_PATTERN);
    }

    /**
     * SQL关键字检查
     */
    public static void filterKeyword(String value) {
        if (StrUtil.isEmpty(value)) {
            return;
        }
        List<String> sqlKeywords = StrUtil.split(SQL_REGEX, "\\|");
        for (String sqlKeyword : sqlKeywords) {
            if (StrUtil.indexOfIgnoreCase(value, sqlKeyword) > -1) {
                log.error("参数存在SQL注入风险");
                throw new ServiceException(ResponseCodeEnum.SERVER_FAIL);
            }
        }
    }
}
