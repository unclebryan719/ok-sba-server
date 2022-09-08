package com.noriental.oksbaserver.arthas.app.web;

/**
 * @author unclebryan
 * @version 1.0
 * @project ok-sba-server
 * @description
 * @date 2022/9/8 16:21:50
 */

import com.noriental.oksbaserver.arthas.AgentInfo;
import com.noriental.oksbaserver.arthas.TunnelServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.Set;

/**
 * 获取所有注册到 Arthas 的客户端 <br>
 *
 * @date: 2022年8月24日11:30:30 <br>
 * @author: yzg <br>
 * @since: 1.0 <br>
 * @version: 1.0 <br>
 */
@RequestMapping("/api/arthas")
@RestController
public class ArthasController {

    @Autowired
    private TunnelServer tunnelServer;

    @RequestMapping(value = "/clients", method = RequestMethod.GET)
    public Set<String> getClients() {
        Map<String, AgentInfo> agentInfoMap = tunnelServer.getAgentInfoMap();
        return agentInfoMap.keySet();
    }


}