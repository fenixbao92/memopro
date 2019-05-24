package com.fenixbao92.memopro.controller;
import com.fenixbao92.memopro.common.model.Site;
import com.fenixbao92.memopro.common.vo.SiteVo;
import com.fenixbao92.memopro.service.SiteService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/site")
public class SiteController {

    @Resource
    private SiteService siteService;

    @RequestMapping(value = "/page/{offset}/{size}")
    public Map<String, Object> getPage(@RequestParam(required = false) String name,
                                       @RequestParam(required = false) String tag,
                                       @PathVariable Long offset,
                                       @PathVariable Integer size) {
        List<SiteVo> siteVoList = siteService.getList(name, tag, offset, size);
        Long count = siteService.getCount(name, tag);
        Map<String, Object> map = new HashMap<>();
        map.put("sites", siteVoList);
        map.put("count", count);
        return map;
    }

    @RequestMapping(value = "/add",method = RequestMethod.POST)
    public void add(Site site) {
        siteService.add(site);
    }

    @RequestMapping("/delete")
    public void delete(@RequestParam String siteIds) {
        siteService.deleteByIds(siteIds);
    }

    @RequestMapping("/update")
    public void update(Site site) {
        siteService.updateSite(site);
    }

}

