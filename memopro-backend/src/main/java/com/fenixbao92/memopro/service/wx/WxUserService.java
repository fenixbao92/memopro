package com.fenixbao92.memopro.service.wx;

import com.fenixbao92.memopro.common.model.wx.WxUser;
import com.fenixbao92.memopro.dao.WxUserMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@Service
@Slf4j
public class WxUserService {

    @Resource
    WxUserMapper wxUserMapper;

    public List<WxUser> getList(Long wxUserId, Long userId, String openId,Long offset, Integer size) {
        List<WxUser> list = wxUserMapper.getList(wxUserId,userId, openId,offset, size);
        return list;
    }

    public Long getCount(Long wxUserId, Long userId,String openId) {
        return wxUserMapper.getCount(wxUserId, userId,openId);
    }

    public Boolean deleteByIds(String wxUserIds) {
        String[] ids = wxUserIds.split(",");
        return wxUserMapper.deleteByIds(ids) == ids.length;
    }

    public int updateWxUser(WxUser wxUser) {
        wxUser.setUpdateTime(new Date());
        return wxUserMapper.update(wxUser);
    }


    public int add(WxUser wxUser) {
        wxUser.setUpdateTime(new Date());
        return wxUserMapper.add(wxUser);
    }

    /////////
    public WxUser getByOpenId(String openId){
        List<WxUser> list = getList(null,null,openId,0L,1);
        if(list!=null&&list.size()==1){
            return list.get(0);
        }
        return null;
    }

    /////////
    public void confirmCodeAuth(String openId, String sessionkey) {
        WxUser wxUser = getByOpenId(openId);
        if(wxUser==null){
            wxUser = new WxUser();
            wxUser.setOpenId(openId);
            wxUser.setSessionKey(sessionkey);
            int res = add(wxUser);
            if(res<=0){
                log.error("confirmCodeAuth add err{}",openId);
            }
        }else {
            wxUser.setSessionKey(sessionkey);
            int res = updateWxUser(wxUser);
            if(res<=0){
                log.error("confirmCodeAuth update err{}",openId);
            }
        }
    }
}
