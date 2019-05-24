package com.fenixbao92.memopro.service;

import com.fenixbao92.memopro.common.constants.BusinessExceptionEnum;
import com.fenixbao92.memopro.common.exceptions.BusinessException;
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
        Long userId = userService.getCurrentUserId();
        List<Site> list = siteMapper.getList(userId, name, tag, offset, size);
        return list.stream().map(VoConverter::forSite).collect(Collectors.toList());
    }

    public Long getCount(String name, String tag) {
        Long userId = userService.getCurrentUserId();
        return siteMapper.getCount(userId, name, tag);
    }

    public void deleteByIds(String siteIds) {
        String[] ids = siteIds.split(",");
        if(siteMapper.deleteByIds(ids) != ids.length){
            throw new BusinessException(BusinessExceptionEnum.DATABASE_ERROR);
        }
    }

    public void updateSite(Site site) {
        site.setUpdateTime(new Date());
        if(siteMapper.update(site)!=1){
            throw new BusinessException(BusinessExceptionEnum.DATABASE_ERROR);
        }
    }

    public void add(Site site) {
        Long userId = userService.getCurrentUserId();
        site.setUserId(userId);
        site.setUpdateTime(new Date());
        if(siteMapper.add(site)<1){
            throw new BusinessException(BusinessExceptionEnum.DATABASE_ERROR);
        }
    }
}
