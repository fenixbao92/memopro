package com.fenixbao92.memopro.service;

import com.fenixbao92.memopro.common.constants.BusinessExceptionEnum;
import com.fenixbao92.memopro.common.exceptions.BusinessException;
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
    private ContactMapper contactMapper;

    @Resource
    private UserService userService;

    public List<ContactVo> getList(String name, String tag, Long offset, Integer size) {
        Long userId = userService.getCurrentUserId();
        List<Contact> list = contactMapper.getList(userId, name, tag, offset, size);
        return list.stream().map(VoConverter::forContact).collect(Collectors.toList());
    }

    public Long getCount(String name, String tag) {
        Long userId = userService.getCurrentUserId();
        return contactMapper.getCount(userId, name, tag);
    }

    public void deleteByIds(String contactIds) {
        String[] ids = contactIds.split(",");
        if(contactMapper.deleteByIds(ids) != ids.length){
            throw new BusinessException(BusinessExceptionEnum.DATABASE_ERROR);
        }
    }

    public void updateContact(Contact contact) {
        contact.setUpdateTime(new Date());
        if(contactMapper.update(contact)!=1){
            throw new BusinessException(BusinessExceptionEnum.DATABASE_ERROR);
        }
    }

    public void add(Contact contact) {
        Long userId = userService.getCurrentUserId();
        contact.setUserId(userId);
        contact.setUpdateTime(new Date());
        if(contactMapper.add(contact)<1){
            throw new BusinessException(BusinessExceptionEnum.DATABASE_ERROR);
        }
    }
}
