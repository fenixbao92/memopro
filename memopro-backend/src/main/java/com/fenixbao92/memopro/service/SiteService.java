package com.fenixbao92.memopro.service;

import com.fenixbao92.memopro.common.model.Site;
import com.fenixbao92.memopro.common.utils.VoConverter;
import com.fenixbao92.memopro.common.vo.SiteVo;
import com.fenixbao92.memopro.dao.SiteMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SiteService {

    @Resource
    SiteMapper siteMapper;

    @Resource
    UserService userService;

    public List<SiteVo> getList(String name, String tag, Long offset, Integer size) {
        Long userId = userService.getCurrentUserIdByAccount();
        List<Site> list = siteMapper.getList(userId, name, tag, offset, size);
        return list.stream().map(VoConverter::forSite).collect(Collectors.toList());
    }

    public Long getCount(String name, String tag) {
        Long userId = userService.getCurrentUserIdByAccount();
        return siteMapper.getCount(userId, name, tag);
    }

    public Boolean deleteByIds(String siteIds) {
        String[] ids = siteIds.split(",");
        return siteMapper.deleteByIds(ids) == ids.length;
    }

    public int updateSite(Site site) {
        site.setUpdateTime(new Date());
        return siteMapper.update(site);
    }


    public int add(Site site) {
        Long userId = userService.getCurrentUserIdByAccount();
        site.setUserId(userId);
        site.setUpdateTime(new Date());
        return siteMapper.add(site);
    }
}
