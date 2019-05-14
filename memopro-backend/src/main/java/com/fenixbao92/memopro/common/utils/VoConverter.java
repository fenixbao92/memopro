package com.fenixbao92.memopro.common.utils;

import com.fenixbao92.memopro.common.model.Contact;
import com.fenixbao92.memopro.common.model.Site;
import com.fenixbao92.memopro.common.model.Todo;
import com.fenixbao92.memopro.common.vo.ContactVo;
import com.fenixbao92.memopro.common.vo.SiteVo;
import com.fenixbao92.memopro.common.vo.TodoVo;

import java.util.Date;

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
        Date latestStartTime = todo.getLatestStartTime();
        Date now = new Date();
        String status = todo.getStatus();
        if (status.equals("未开始")) {
            todoVo.setCostTimeStr("-");
        } else if (status.equals("进行中")) {
            Long time = todo.getCostMillis()+(now.getTime()-latestStartTime.getTime());
            todoVo.setCostTimeStr(getCostTimeStr(time));
        } else if (status.equals("暂停中")) {
            Long time = todo.getCostMillis();
            todoVo.setCostTimeStr(getCostTimeStr(time));
        } else if (status.equals("已完成")) {
            Long time = todo.getCostMillis();
            todoVo.setCostTimeStr(getCostTimeStr(time));
        }
        Long planTimeHours = todo.getPlanMillis()/1000/3600;
        Long planTimeMinutes = (todo.getPlanMillis()/1000%3600)/60;
        todoVo.setPlanTimeHour(planTimeHours.intValue());
        todoVo.setPlanTimeMinute(planTimeMinutes.intValue());
        return todoVo;
    }

    private static String getCostTimeStr(Long time) {
        Long seconds = time / 1000;
        Long hour = seconds / 3600;
        seconds = seconds % 3600;
        Long minutes = seconds / 60;
        seconds = seconds % 60;
        return hour + "h" + minutes + "m" + seconds + "s";
    }

}
