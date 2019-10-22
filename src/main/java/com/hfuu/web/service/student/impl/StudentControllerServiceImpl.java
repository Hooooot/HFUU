package com.hfuu.web.service.student.impl;

import com.hfuu.web.dao.CourseDao;
import com.hfuu.web.dao.base.BaseDao;
import com.hfuu.web.entity.CourseEntity;
import com.hfuu.web.entity.SubmitEntity;
import com.hfuu.web.entity.TaskEntity;
import com.hfuu.web.service.base.BaseServiceImpl;
import com.hfuu.web.service.student.StudentControllerService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

/**
 * @Description: Student的Controller层service实现
 * @Author: Starry the Night
 * @Date:  2019/10/18 20:13
 * @return
 */
@Service("studentControllerService")
public class StudentControllerServiceImpl extends BaseServiceImpl implements StudentControllerService {

    @Resource
    private CourseDao courseDao;


    @Override
    public BaseDao getBaseDao() {
        return courseDao;
    }


    @Override
    public Map<String, List<CourseEntity>> getCourseByStuNum(String stuNum) {
        //from Department as d inner join fetch d.employees e where e.name='Tom';
        List<CourseEntity> list=courseDao.findByHql("from CourseEntity c inner join fetch c.classEntity.stusFromClass s where s.stuNum=?",stuNum);
        return this.groupByCozName(list);
    }

    @Override
    public List<CourseEntity> getCourse(String stuNum, String cozName) {


        return this.getCourseByStuNum(stuNum).get(cozName);
    }

    @Override
    public List<Map> getTaskFromCourse(String stuNum, String cozName) {
        List<CourseEntity> list=getCourse(stuNum,cozName);
        List<Map> data = new ArrayList<Map>();
        for (CourseEntity c : list) {
            Set<TaskEntity> taskList = c.getTasksFromCoz();
            for (TaskEntity t : taskList) {
                Map<String, Object> shiYan=t.toMap();
                shiYan.remove("submitSet");

                Set<SubmitEntity> submitEntities = t.getSubmitsFromTask();
                Iterator<SubmitEntity> iterator = submitEntities.iterator();
                //删除非此用户的提交信息
                while (iterator.hasNext()) {
                    SubmitEntity submit = iterator.next();
                    if (!submit.getStuEntity().getStuNum().equals(stuNum)) {
                        iterator.remove();
                    }
                }
                if (!submitEntities.isEmpty()) {
                    shiYan.put("score",submitEntities.iterator().next().getScore());
                    shiYan.put("subTime",submitEntities.iterator().next().getSubTime());
                    shiYan.put("subState",submitEntities.iterator().next().getSubState());
                    shiYan.put("subFile",submitEntities.iterator().next().getSubFile());
                }else {
                    shiYan.put("score",0);
                    shiYan.put("subTime","0000-00-00 00:00:00.0");
                    shiYan.put("subState","待提交");
                    shiYan.put("subFile",null);
                }

                data.add(shiYan);
            }
        }
        return data;
    }

    @Override
    public Map groupByCozName(List<CourseEntity> list) {
        if (list == null){
            return null;
        }
        Map<String, List<CourseEntity>> map = new HashMap<>(5);
        for(CourseEntity c : list){
            if(map.containsKey(c.getCozName())){
                List<CourseEntity> li = map.get(c.getCozName());
                li.add(c);
                map.put(c.getCozName(), li);
            }else{
                List<CourseEntity> li = new ArrayList<>();
                li.add(c);
                map.put(c.getCozName(), li);
            }
        }
        return map;
    }
}
