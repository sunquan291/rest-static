package com.zte.sdn.oscp.statics.control;

import com.zte.sdn.oscp.statics.config.RestStaticsProperties;
import com.zte.sdn.oscp.statics.filter.RestStaticRecordSenior;
import com.zte.sdn.oscp.statics.filter.StaticRecordInfo;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * @Author 10184538
 * @Date 2022/8/31 12:01
 **/
@RequestMapping("/rest-statics")
@ResponseBody
public class RestStaticInfosControl {
    private RestStaticsProperties restStaticsProperties;

    public RestStaticInfosControl(RestStaticsProperties restStaticsProperties) {
        this.restStaticsProperties = restStaticsProperties;
    }


    @GetMapping("list")
    public List<StaticRecordInfo> showRecordInfos() {
        List<StaticRecordInfo> recordInfos = RestStaticRecordSenior.getRecordInfos();
        return recordInfos;
    }

    @GetMapping("list-by/{index}")
    public StaticRecordInfo showRecordInfos(@PathVariable("index") long index) {
        return RestStaticRecordSenior.getRecordInfo(index);
    }

    @GetMapping("list-error")
    public List<StaticRecordInfo> showErrorRecordInfos() {
        return RestStaticRecordSenior.getErrorRecordInfo();
    }

    @GetMapping("reset")
    public void rest() {
        RestStaticRecordSenior.rest();
    }
}