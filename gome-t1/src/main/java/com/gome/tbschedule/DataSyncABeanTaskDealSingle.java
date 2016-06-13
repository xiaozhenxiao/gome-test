//package com.gome.tbschedule;
//
//import java.util.ArrayList;
//import java.util.Comparator;
//import java.util.List;
//import com.taobao.pamirs.schedule.IScheduleTaskDealSingle;
//import com.taobao.pamirs.schedule.TaskItemDefine;
//
//
//public class DataSyncABeanTaskDealSingle implements IScheduleTaskDealSingle<OrderInfo> {
//	private static int lock = 1;
//    public List<OrderInfo> selectTasks(String taskParameter, String ownSign,
//            int taskItemNum, List<TaskItemDefine> queryCondition,
//            int eachFetchDataNum) throws Exception {
//
//        List<OrderInfo> result = new ArrayList<OrderInfo>();
//        if (queryCondition.size() == 0) {
//            return result;
//        }
//
//        StringBuffer condition = new StringBuffer();
//        for (int i = 0; i < queryCondition.size(); i++) {
//            if (i > 0) {
//                condition.append(",");
//            }
//            condition.append(queryCondition.get(i).getTaskItemId());
//        }
//        System.out.println(Thread.currentThread().getName()+" param:" + taskParameter);
//        /* 场景A：将tbOrder表中的数据分8个任务项，每次取200条数据， 同步到tbOrder_copy表中。 */
//        String sql = "select * from tbOrder " + "where "
//                + " BillNumber not in (select BillNumber from tbOrder_copy) "
//                + " and RIGHT(BuildDate,1) in (" + condition + ") " + "limit "
//                + eachFetchDataNum;
//
//        System.out.println("开始执行SQL：" + sql);
//        for (int i = 0; i < 2; i++) {
//        	if(++lock > 4) {
//				break;
//			}
//        	OrderInfo order = new OrderInfo();
//        	order.BillNumber = "BillNumber"+i+Thread.currentThread().getName();
//        	order.BuildDate = "BuildDate"+(i+i);
//        	order.Customer = "Customer"+i;
//        	order.GoodsName = "GoodsName"+i;
//        	order.Amount = 300.33f;
//        	order.SaleMoney = 120.56f;
//        	result.add(order);
//        	System.out.println("lock: "+lock);
//        	
//		}
//
//
//        return result;
//    }
//
//    public Comparator<OrderInfo> getComparator() {
//
//        return null;
//    }
//
//    public boolean execute(OrderInfo task, String ownSign) throws Exception {
//        String sql = "insert into tbOrder_copy values('" + task.BillNumber
//                + "','" + task.BuildDate + "','" + task.Customer + "','"
//                + task.GoodsName + "'," + task.Amount + "," + task.SaleMoney
//                + ")";
//
//        MySQLHelper.executeQuery(sql);
//
//        System.out.println("execute：" + sql + ":" +ownSign);
//
//        return true;
//    }
//}