package com.alibaba.csp.sentinel.dashboard.rule.nacos.apidefinition;

import com.alibaba.csp.sentinel.dashboard.datasource.entity.gateway.ApiDefinitionEntity;
import com.alibaba.csp.sentinel.dashboard.rule.DynamicRuleProvider;
import com.alibaba.csp.sentinel.dashboard.rule.nacos.NacosConfigProperties;
import com.alibaba.csp.sentinel.dashboard.rule.nacos.NacosConfigUtil;
import com.alibaba.csp.sentinel.datasource.Converter;
import com.alibaba.csp.sentinel.util.StringUtil;
import com.alibaba.nacos.api.config.ConfigService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ApiDefinitionRuleNacosProvider implements DynamicRuleProvider<List<ApiDefinitionEntity>> {

    public static final Logger log = LoggerFactory.getLogger(ApiDefinitionRuleNacosProvider.class);

    @Autowired
    private ConfigService configService;
    @Autowired
    private Converter<String, List<ApiDefinitionEntity>> converter;
    @Autowired
    private NacosConfigProperties properties;

    @Override
    public List<ApiDefinitionEntity> getRules(String appName) throws Exception {
        String rules = configService.getConfig(appName + NacosConfigUtil.API_DEFINITION_DATA_ID_POSTFIX,
                NacosConfigUtil.GROUP_ID, properties.getObtainConfigTimeout());
        log.info("sentinel dashboard obtain api definition rules from nacos config:{}", rules);

        if (StringUtil.isEmpty(rules)) {
            return new ArrayList<>();
        }
        return converter.convert(rules);
    }
}
