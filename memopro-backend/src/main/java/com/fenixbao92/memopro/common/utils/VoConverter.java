package com.fenixbao92.memopro.common.utils;

import com.fenixbao92.memopro.common.model.Contact;
import com.fenixbao92.memopro.common.model.Site;
import com.fenixbao92.memopro.common.model.Todo;
import com.fenixbao92.memopro.common.vo.ContactVo;
import com.fenixbao92.memopro.common.vo.SiteVo;
import com.fenixbao92.memopro.common.vo.TodoVo;

public class VoConverter {

    public static ContactVo forContact(Contact contact){
        ContactVo contactVo = new ContactVo();
        BeanMapper.copy(contact,contactVo);
        return contactVo;
    }

    public static SiteVo forSite(Site site){
        SiteVo siteVo = new SiteVo();
        BeanMapper.copy(site,siteVo);
        return siteVo;
    }

    public static TodoVo forTodo(Todo todo){
        TodoVo todoVo = new TodoVo();
        BeanMapper.copy(todo,todoVo);
        return todoVo;
    }
}
