package com.fenixbao92.memopro.controller;

import com.fenixbao92.memopro.common.model.Contact;
import com.fenixbao92.memopro.common.vo.ContactVo;
import com.fenixbao92.memopro.service.ContactService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    private Logger logger = LoggerFactory.getLogger(ContactController.class);


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
        logger.info(map.toString());
        return map;
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public void add(Contact contact) {
        contactService.add(contact);
    }

    @RequestMapping("/delete")
    public void delete(@RequestParam String contactIds) {
        contactService.deleteByIds(contactIds);
    }

    @RequestMapping("/update")
    public void update(Contact contact) {
        contactService.updateContact(contact);
    }

}

