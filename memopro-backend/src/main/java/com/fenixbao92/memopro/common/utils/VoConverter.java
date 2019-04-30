package com.fenixbao92.memopro.common.utils;

import com.fenixbao92.memopro.common.model.Contact;
import com.fenixbao92.memopro.common.vo.ContactVo;

public class VoConverter {

    public static ContactVo forContact(Contact contact){
        ContactVo contactVo = new ContactVo();
        contactVo.setContactId(contact.getContactId());
        contactVo.setUserId(contact.getUserId());
        contactVo.setName(contact.getName());
        contactVo.setBirthday(contact.getBirthday());
        contactVo.setPhone(contact.getPhone());
        contactVo.setEmail(contact.getEmail());
        contactVo.setTag(contact.getTag());
        contactVo.setRemark(contact.getRemark());
        contactVo.setUpdateTime(contact.getUpdateTime());
        return contactVo;
    }
}
