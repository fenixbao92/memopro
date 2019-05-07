package com.fenixbao92.memopro.controller;
import com.alibaba.fastjson.JSON;
import com.fenixbao92.memopro.common.model.Site;
import com.fenixbao92.memopro.common.model.Todo;
import com.fenixbao92.memopro.common.utils.BeanMapper;
import com.fenixbao92.memopro.common.vo.SiteVo;
import com.fenixbao92.memopro.common.vo.Result;
import com.fenixbao92.memopro.service.SiteService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/site")
public class SiteController {

    @Resource
    SiteService siteService;

    @RequestMapping(value = "/page/{offset}/{size}")
    public Map<String, Object> getPage(@RequestParam(required = false) String name,
                                       @RequestParam(required = false) String tag,
                                       @PathVariable Long offset,
                                       @PathVariable Integer size) {
        List<SiteVo> siteVoList = siteService.getList(name, tag, offset, size);
        Long count = siteService.getCount(name, tag);
        Map<String, Object> map = new HashMap<>();
        map.put("sites", siteVoList);
        map.put("count", count);
        return map;
    }

    @RequestMapping(value = "/add",method = RequestMethod.POST)
    public Result add(Site site) {
        if (siteService.add(site) == 1) {
            return Result.ok("添加成功!");
        }
        return Result.error("添加失败!");
    }

    @RequestMapping("/delete")
    public Result delete(@RequestParam String siteIds) {
        if(siteService.deleteByIds(siteIds)){
            return Result.ok("删除成功!");
        }
        return Result.error("删除失败!");
    }

    @RequestMapping("/update")
    public Result update(Site site) {
        if (siteService.updateSite(site) == 1) {
            return Result.ok("更新成功!");
        }
        return Result.error("更新失败!");
    }


    public static void main(String[] args){
        Todo todo = new Todo();
        Todo todo1 = new Todo();
        todo.setName("dddd");
        System.out.println(JSON.toJSON(todo));
        System.out.println(JSON.toJSON(todo1));
        BeanMapper.copy(todo,todo1);
        System.out.println(JSON.toJSON(todo1));
    }
}

