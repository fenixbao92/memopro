package com.fenixbao92.memopro.service;

import com.fenixbao92.memopro.common.model.Contact;
import com.fenixbao92.memopro.common.utils.VoConverter;
import com.fenixbao92.memopro.common.vo.ContactVo;
import com.fenixbao92.memopro.dao.ContactMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ContactService {

    @Resource
    ContactMapper contactMapper;

    @Resource
    UserService userService;

    public List<ContactVo> getList(String name, String tag, Long offset, Integer size) {
        Long userId = userService.getCurrentUserId();
        List<Contact> list = contactMapper.getList(userId, name, tag, offset, size);
        return list.stream().map(VoConverter::forContact).collect(Collectors.toList());
    }

    public Long getCount(String name, String tag) {
        Long userId = userService.getCurrentUserId();
        return contactMapper.getCount(userId, name, tag);
    }

    public Boolean deleteByIds(String contactIds) {
        String[] ids = contactIds.split(",");
        return contactMapper.deleteByIds(ids) == ids.length;
    }

    public int updateContact(Contact contact) {
        contact.setUpdateTime(new Date());
        return contactMapper.update(contact);
    }


    public int add(Contact contact) {
        Long userId = userService.getCurrentUserId();
        contact.setUserId(userId);
        contact.setUpdateTime(new Date());
        return contactMapper.add(contact);
    }
}
