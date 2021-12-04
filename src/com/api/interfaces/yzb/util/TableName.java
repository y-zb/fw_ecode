package com.api.interfaces.yzb.util;

import com.wbi.util.Util;
import weaver.conn.RecordSet;
import weaver.general.BaseBean;

/**
 * @Author: yzb
 * @CreateTime: 2021/12/4 10:51
 * @Version: 1.0
 * @Description: 获取表的字段
 */
public class TableName {
    /**
     * 获取下拉框值
     * @param strId 流程id
     * @param strField 字段名称 例如bh
     * @param tableType 主表字段还是明细表字段
     * @param strValue 获取下拉框的值
     * @return
     */

    public static String getDropDownBoxValue(String strId,String strField,String tableType,String strValue){
        BaseBean baseBean = new BaseBean();
        RecordSet reOne = new RecordSet();
        String strSql = "select b.id as id from workflow_base a left join workflow_billfield b on a.formid  = b.billid where a.id = " + strId+ " and b.fieldname = " + strField + " and viewtype = " + tableType;
        baseBean.writeLog("--getDropDownBoxValue--strSql1"+strSql);
        reOne.executeQuery(strSql);
        if(reOne.next()){
            String id = Util.null2String(reOne.getString("id"));
            strSql = "select SELECTNAME as name from workflow_selectitem where fieldid = "+id+" and selectvalue = "+strValue;
            baseBean.writeLog("--getDropDownBoxValue--strSql2"+strSql);
            reOne.executeQuery(strSql);
            if(reOne.next()){
                String name = Util.null2String(reOne.getString("name"));
                baseBean.writeLog("---getDropDownBoxValue--下拉框"+name);
                return name;
            }
        }
        return "";
    }

    /**
     * 根据requestid获取主表名称
     *
     * @param rs
     * @param requestid
     * @return
     */
    public String findWorkflowFormtoRequestid(RecordSet rs, String requestid) {
        String tablename = "";
        rs.execute("select t2.tablename from workflow_requestbase t,workflow_base t1,workflow_bill t2 where t" +
                ".workflowid=t1.id and t2.id=t1.formid and t.requestid='" + requestid + "'");
        if (rs.next()) {
            tablename = Util.null2String(rs.getString("tablename"));
        } else {
            tablename = "-1";
        }
        return tablename;
    }
}
