package com.hfuu.web.controller.teacher;


import com.hfuu.web.entity.ClassEntity;
import com.hfuu.web.entity.CourseEntity;
import com.hfuu.web.entity.TaskEntity;
import com.hfuu.web.entity.TeacherEntity;
import com.hfuu.web.others.ConstValues;
import com.hfuu.web.others.utils.TermUtils;
import com.hfuu.web.service.TaskService;
import com.hfuu.web.service.teacher.TeacherControllerService;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @Description : 教师页面首页控制器
 * @date :2019年10月10日 16点49分
 * @author :whh0987@foxmail.com
 * 最后修改时间：
 * 最后修改人：
 */
@Controller
@RequestMapping("")
@SessionAttributes(names = {"teacher"})
public class TeacherHomeController {
    static private Logger log = Logger.getLogger(TeacherHomeController.class);
    @Resource
    TeacherControllerService teacherControllerService;
    @Resource
    TaskService taskService;

    @RequestMapping(value = {"/teacher/home"}, method = RequestMethod.GET)
    public String toHome(Model model){
        if (model.asMap().get(ConstValues.TEACHER_LOGGED_IN_INSTANCE_NAME) != null){
            log.debug("跳转到:teacher/home.jsp");
            return "teacher/home";
        }
        return "redirect: ../logintPage";
    }

    @ModelAttribute
    public void consoleModel(String tcNum, Model model){
        if(tcNum == null){
            return;
        }
        List list = teacherControllerService.getCourseByTeacherNumAndTerm(tcNum, TermUtils.getCurrentTerm());
        //noinspection unchecked
        Map map = teacherControllerService.groupByCozName(list);
        model.addAttribute("course", map);
    }

    @RequestMapping(value = {"/teacher/console"}, method = RequestMethod.GET)
    public String toConsole() {
        log.debug("跳转到:teacher/console.jsp");
        return "teacher/console";
    }

    @RequestMapping(value = {"/teacher/already_homework"}, method = RequestMethod.GET)
    public String toAlreadyHomework(String term, Model model) {
        TeacherEntity tc = (TeacherEntity)(model.asMap().get(ConstValues.TEACHER_LOGGED_IN_INSTANCE_NAME));
        // TODO
        System.out.println("term:" + term);
        Map<ClassEntity, List<TaskEntity>> clazz = teacherControllerService.getClazzAndTaskByTcNumAndTerm(tc.getTcNum(), TermUtils.getCurrentTerm());
        model.addAttribute("clazzs", clazz);
        log.debug("跳转到:teacher/already_homework.jsp");
        return "teacher/already_homework";
    }

    @RequestMapping(value = {"/teacher/deploy_homework"}, method = RequestMethod.GET)
    public String toDeployHomework(@RequestParam(required = false) List<String> cozNum, @RequestParam(required = false) List<String> classNum, Model model) {
        TeacherEntity tc = (TeacherEntity)(model.asMap().get(ConstValues.TEACHER_LOGGED_IN_INSTANCE_NAME));
        List<CourseEntity> courseList = teacherControllerService.getCourseByTeacherNumAndTerm(tc.getTcNum(), TermUtils.getCurrentTerm());
        if(cozNum != null && classNum != null){
            model.addAttribute("selectedCoz", cozNum);
            model.addAttribute("selectedClass", classNum);
        }
        model.addAttribute("allCourseAndClass", teacherControllerService.duplicateRemoval(courseList));
        log.debug("跳转到:teacher/deploy_homework.jsp");
        return "teacher/deploy_homework";
    }

    @RequestMapping(value = {"/teacher/account"}, method = RequestMethod.GET)
    public String toAccount() {
        log.debug("跳转到:teacher/account.jsp");
        return "teacher/account";
    }
}
