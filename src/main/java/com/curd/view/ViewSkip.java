package com.curd.view;

import cn.hutool.core.util.StrUtil;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

/**
 * 页面跳转通用类
 *
 * @author Max
 * @date 2019-04-10 下午 9:22
 * @projectName curd
 */
@RestController
public class ViewSkip {

    @GetMapping("/add")
    public ModelAndView add(@RequestParam("page") String page) {
        if (!StrUtil.isBlank(page)) {
            return new ModelAndView("views/" + page + "/Add" + page + ".html");
        } else {
            return new ModelAndView("views/SystemInfo/AddSystemInfo.html");
        }
    }

    @GetMapping("/edit")
    public ModelAndView edit(@RequestParam("page") String page) {
        if (!StrUtil.isBlank(page)) {
            return new ModelAndView("views/" + page + "/Edit" + page + ".html");
        } else {
            return new ModelAndView("views/SystemInfo/EditSystemInfo.html");
        }
    }

    @GetMapping("/list")
    public ModelAndView list(@RequestParam("page") String page) {
        if (!StrUtil.isBlank(page)) {
            return new ModelAndView("views/" + page + "/List" + page + ".html");
        } else {
            return new ModelAndView("views/SystemInfo/ListSystemInfo.html");
        }
    }

    @GetMapping("/select")
    public ModelAndView select(@RequestParam("page") String page) {
        if (!StrUtil.isBlank(page)) {
            return new ModelAndView("views/" + page + "/Select" + page + ".html");
        } else {
            return new ModelAndView("views/SystemInfo/ListSystemInfo.html");
        }
    }

    @GetMapping("/view")
    public ModelAndView view(@RequestParam("page") String page) {
        if (!StrUtil.isBlank(page)) {
            return new ModelAndView("views/" + page + "/" + page + ".html");
        } else {
            return new ModelAndView("views/SystemInfo/SystemInfo.html");
        }
    }
}
