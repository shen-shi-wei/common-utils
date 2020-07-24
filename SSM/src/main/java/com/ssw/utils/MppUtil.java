package com.ssw.utils;

import com.jacob.activeX.ActiveXComponent;
import com.jacob.com.Dispatch;
import com.jacob.com.Variant;
import com.ssw.entity.SchProjectTask;
import com.ssw.entity.TaskBean;
import net.sf.mpxj.*;
import net.sf.mpxj.mpp.MPPReader;
import net.sf.mpxj.mpx.MPXReader;
import net.sf.mpxj.mspdi.MSPDIReader;

import java.io.*;
import java.util.ArrayList;
import java.util.List;


/**
 * @Author ssw
 * @Describe  解析 project
 * @Date 2020/04/01/9:56
 * @Params
 * @Return
 */
public class MppUtil {

    /**
     * 顶级的父类Id
     **/
    private static final int TOP_PARENTID = 0;

    /**
     * 顶级的层次
     **/
    private static final int TOP_LEVEL = 1;

    /**
     * 导出生成mpp文件存放的路径
     **/
    private static final String FILE_PATH = "D:/tempFileSavePath.mpp";


    /**
     * 读取项目文件
     *
     * @param fileName 文件名 绝对路径
     * @return
     * @throws FileNotFoundException
     */
    private static ProjectFile readProject(String fileName)
            throws FileNotFoundException {
        InputStream is = new BufferedInputStream(new FileInputStream(fileName));
        is.mark(0);// 下面需要重复使用输入流，所以重新包装并设置重置标记

        ProjectFile mpx = null;
        try {
            mpx = new MPXReader().read(is);
        } catch (Exception ex) {
            try {
                is.reset();// 重置
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        if (mpx == null) {
            try {
                mpx = new MPPReader().read(is);
            } catch (Exception ex) {
                try {
                    is.reset();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        if (mpx == null) {
            try {
                mpx = new MSPDIReader().read(is);
            } catch (Exception ex) {
            }
        }
        return mpx;
    }


    /**
     * 项目文件--获取任务列表
     *
     * @throws FileNotFoundException
     * @Author xuyangyang
     * @Describe
     * @Date 2017/3/24
     * @Params fileName 读取.mpp文件路径
     * @Return
     */
    public static List<SchProjectTask> getTaskList(String fileName) throws FileNotFoundException {
        ProjectFile file = readProject(fileName);
        List<Task> tasks = file.getChildTasks();
        List<SchProjectTask> schProjectTaskList = new ArrayList<SchProjectTask>();
        if (!tasks.isEmpty()) {
            Task msTask = tasks.get(TOP_PARENTID);
            schProjectTaskList = listHierarchy(msTask, TOP_PARENTID);
        }
        return schProjectTaskList;
    }

    /**
     * 获取任务的子任务
     *
     * @param parentId 父任务Id
     * @Author xuyangyang
     * @Describe
     * @Date 2017/3/24
     * @Params task 任务
     * @Return
     */

    private static List<SchProjectTask> listHierarchy(Task msTask, int parentId) {
        List<Task> childTasks = msTask.getChildTasks();
        List<SchProjectTask> schProjectTaskList = new ArrayList<SchProjectTask>();
        SchProjectTask schProjectTask = null;
        if (!childTasks.isEmpty()) {
            for (Task task : childTasks) {
                schProjectTaskList.add(getTaskBean(schProjectTask, task, parentId));
                schProjectTaskList.addAll(listHierarchy(task, task.getID()));
            }
        }
        return schProjectTaskList;
    }


    /**
     * 获取任务的具体字段
     *
     * @Author xuyangyang
     * @Describe
     * @Date 2017/3/24
     * @Params schProjectTask 任务Bean
     * @Params task 任务
     * @Params parentId 父类Id
     * @Return schProjectTask bean;
     */
    private static SchProjectTask getTaskBean(SchProjectTask schProjectTask, Task task, Integer parentId) {

        schProjectTask = new SchProjectTask();
        schProjectTask.setRecordId(task.getID().toString());
        schProjectTask.setUniqueId(task.getUniqueID().toString());
        schProjectTask.setParentId(parentId.toString());
        schProjectTask.setName(task.getName());
        schProjectTask.setDuration(task.getDuration().getDuration());
        schProjectTask.setDurationTimeUnit(task.getDuration().getUnits().getName());
        schProjectTask.setStartTime(task.getStart());
        schProjectTask.setFinishTime(task.getFinish());
        schProjectTask.setPercentageComplete(task.getPercentageComplete());
        schProjectTask.setLevel(task.getOutlineLevel());
        List<String> preLists = new ArrayList<String>();

        List<Relation> predecessors = task.getPredecessors();
        if (predecessors != null && predecessors.isEmpty() == false) {
            for (Relation relation : predecessors) {
                Task tragetTask = relation.getTargetTask();
                Integer targetTaskRecordId = tragetTask.getID();
                Integer targetTaskUniqueId = tragetTask.getUniqueID();
                String m_type = relation.getType().toString();//SS///FS
                String m_lag = relation.getLag().toString();//10.0d//0.0d//-35.0d
                String predecessor = targetTaskRecordId + ":"+targetTaskUniqueId+":"+ m_type + ":" + m_lag;
                preLists.add(predecessor);}
        }
        schProjectTask.setPredecessors(listToString(preLists));
        return schProjectTask;
    }

    /**
     * List 转为 String
     * @Author xuyangyang
     * @Describe
     * @Date 2017/3/24
     * @Params List 对象集合
     * @Return String
     */
    public static String listToString(List list) {
        StringBuilder sb = new StringBuilder();
        if (list != null && list.size() > 0) {
            for (int i = 0; i < list.size(); i++) {
                if (i < list.size() - 1) {
                    sb.append(list.get(i) + ",");
                } else {
                    sb.append(list.get(i));
                }
            }
        }
        return sb.toString();
    }




    /**
     * 创建mpp文件
     *
     * @param taskBeanList 任务列表
     * @throws Exception
     */

    public static void createMppFile(List<TaskBean> taskBeanList) throws Exception {
        File file = new File(FILE_PATH);
        if (file.exists()) {
            file.delete();
        }
        if (taskBeanList != null && taskBeanList.size() > 0) {
            ActiveXComponent app = null;
            try {
                app = new ActiveXComponent("MSProject.Application");
                app.setProperty("Visible", new Variant(false));
                Dispatch projects = app.getProperty("Projects").toDispatch();
                Dispatch project = Dispatch.call(projects, "Add").toDispatch();//添加一个项目
                Dispatch tasks = Dispatch.get(project, "Tasks").toDispatch();//生成一个task集合
                //生成Task
                TaskBean topTaskBean = getTopTaskBean(taskBeanList);
                createTreeTable(tasks, topTaskBean, TOP_LEVEL, taskBeanList);
                //另存为
                Dispatch.invoke(project, "SaveAs", Dispatch.Method, new Object[]{FILE_PATH, new Variant(0)}, new int[1]);
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("aaaaaaaaaa");
            } finally {
                if (app != null)
                    app.invoke("Quit", new Variant[]{});
            }
        }
    }

    /**
     * 创建树形结构
     *
     * @param tasks        任务集合
     * @param taskBean     任务Bean
     * @param level        层次
     * @param taskBeanList 任务列表
     */
    private static void createTreeTable(Dispatch tasks, TaskBean taskBean, int level, List<TaskBean> taskBeanList) {
        Dispatch task = Dispatch.call(tasks, "Add").toDispatch();
        setTaskValue(task, taskBean, level);
        List<TaskBean> sonTaskBeanList = getSonTaskBean(taskBeanList, taskBean);
        if (!sonTaskBeanList.isEmpty()) {
            for (TaskBean sonTaskBean : sonTaskBeanList) {
                createTreeTable(tasks, sonTaskBean, level + 1, taskBeanList);
            }
        }
    }

    /**
     * 获取所有的子任务
     *
     * @param taskBeanList   任务列表
     * @param parentTaskBean 父级任务Bean
     * @return
     */
    private static List<TaskBean> getSonTaskBean(List<TaskBean> taskBeanList, TaskBean parentTaskBean) {
        List<TaskBean> sonTaskBeanList = new ArrayList<TaskBean>();
        for (TaskBean taskBean : taskBeanList) {
            if ((taskBean.getParentId()+"").equals(parentTaskBean.getId())) {
                sonTaskBeanList.add(taskBean);
            }
        }
        return sonTaskBeanList;
    }

    /**
     * 获取顶级任务
     *
     * @param taskBeanList 任务列表
     * @return
     */
    private static TaskBean getTopTaskBean(List<TaskBean> taskBeanList) {
        for (TaskBean taskBean : taskBeanList) {
            if ((TOP_PARENTID+"").equals(taskBean.getParentId()))
                return taskBean;
        }
        return null;
    }

    /**
     * 给任务设置属性
     *
     * @param task     任务指针
     * @param taskBean 任务Bean
     * @param level    层次
     */
    private static void setTaskValue(Dispatch task, TaskBean taskBean, int level) {
        Dispatch.put(task, "Name", taskBean.getName());
//      Dispatch.put(task, "Duration",Duration.getInstance(5, TimeUnit.DAYS));
        Dispatch.put(task, "Start", taskBean.getStartTime());
        Dispatch.put(task, "Finish", taskBean.getFinishTime());
//      Dispatch.put(task, "Duration",taskBean.getDuration());
//      Dispatch.put(task, "PercentageComplete",NumberUtility.getDouble(taskBean.getPercentageComplete()));
        Dispatch.put(task, "OutlineLevel", level);
        Dispatch.put(task, "ResourceNames", taskBean.getResource());
    }

    /**
     * 获取任务中的资源
     *
     * @param task 任务
     * @return
     */
    private static String listTaskRes(Task task) {
        StringBuffer buf = new StringBuffer();
        List<ResourceAssignment> assignments = task.getResourceAssignments();//获取任务资源列表
        if (assignments != null && !assignments.isEmpty()) {
            ResourceAssignment assignment = (ResourceAssignment) assignments.get(0);//只获取第一个资源
            Resource resource = assignment.getResource();
            if (resource != null)
                buf.append(resource.getName());
        }
        return buf.toString();
    }

    public static void main(String[] args) throws Exception {
//        Project project=MppUtil.getTaskList("D:/环境科学楼含机电.mpp");
//        List<TaskBean>taskBeanList=project.getTaskBeanList();
//        System.out.println(taskBeanList.size());
//        for(TaskBean task:taskBeanList){
//        System.out.println(task);
//        }
//        createMppFile(taskBeanList);


        List<SchProjectTask> taskBeanList = MppUtil.getTaskList("D:/环境科学楼含机电.mpp");
        System.out.println(taskBeanList.size());
        for (SchProjectTask task : taskBeanList) {
            System.out.println(task);
        }

        List<TaskBean> list = new ArrayList<>();
        for (SchProjectTask task : taskBeanList) {
            System.out.println(task);
            TaskBean taskBean = new TaskBean();
            taskBean.setId(task.getId());
            taskBean.setDuration(task.getDuration());
            taskBean.setDurationTimeUnit(task.getDurationTimeUnit());
            taskBean.setFinishTime(task.getFinishTime());
            taskBean.setLevel(task.getLevel());
            taskBean.setName(task.getName());
            taskBean.setParentId(task.getParentId());
            taskBean.setPercentageComplete(task.getPercentageComplete());
            taskBean.setPredecessors(task.getPredecessors());
            taskBean.setProId(task.getProId());
            taskBean.setRecordId(task.getRecordId());
            taskBean.setResource("");
            taskBean.setStartTime(task.getStartTime());
            taskBean.setUniqueId(task.getUniqueId());

            list.add(taskBean);
        }

        createMppFile(list);
    }
}

