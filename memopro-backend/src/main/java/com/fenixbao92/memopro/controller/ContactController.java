package com.fenixbao92.memopro.controller;

import com.fenixbao92.memopro.common.model.Contact;
import com.fenixbao92.memopro.common.vo.ContactVo;
import com.fenixbao92.memopro.common.vo.Result;
import com.fenixbao92.memopro.service.ContactService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/contact")
public class ContactController {

    @Resource
    ContactService contactService;

    @RequestMapping(value = "/page/{offset}/{size}")
    public Map<String, Object> getPage(@RequestParam(required = false) String name,
                                       @RequestParam(required = false) String tag,
                                       @PathVariable Long offset,
                                       @PathVariable Integer size) {
        List<ContactVo> contactVoList = contactService.getList(name, tag, offset, size);
        Long count = contactService.getCount(name, tag);
        Map<String, Object> map = new HashMap<>();
        map.put("contacts", contactVoList);
        map.put("count", count);
        System.out.println(map);
        return map;
    }

    @RequestMapping(value = "/add",method = RequestMethod.POST)
    public Result add(Contact contact) {
        System.out.println(contact);
        if (contactService.add(contact) == 1) {
            return Result.ok("添加成功!");
        }
        return Result.error("添加失败!");
    }

    @RequestMapping("/delete")
    public Result delete(@RequestParam String contactIds) {
        if(contactService.deleteByIds(contactIds)){
            return Result.ok("删除成功!");
        }
        return Result.error("删除失败!");
    }

    @RequestMapping("/update")
    public Result update(Contact contact) {
        if (contactService.updateContact(contact) == 1) {
            return Result.ok("更新成功!");
        }
        return Result.error("更新失败!");
    }

}

