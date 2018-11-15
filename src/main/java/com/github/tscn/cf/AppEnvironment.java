package com.github.tscn.cf;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.text.MessageFormat;

@Component
public class AppEnvironment {

    @Value("${vcap.application.application_id}")
    private String guid;

    @Value("${vcap.application.name}")
    private String name;

    @Value("${vcap.application.space_id}")
    private String space_guid;

    @Value("${vcap.application.space_name}")
    private String space;

    @Value("${CF_INSTANCE_INDEX}")
    private int index;

    @Value("${MEMORY_LIMIT}")
    private String memory;

    @Value("${CF_INSTANCE_INTERNAL_IP}")
    private String ip;

    @Value("${PORT}")
    private int port;

    @Value("${CF_INSTANCE_IP}")
    private String hostIp;

    @Value("${CF_INSTANCE_PORT}")
    private int hostPort;

    @Value("${hands-on.org.guid:}")
    private String orgGuid;

    @Value("${hands-on.apps.url:}")
    private String appsManagerUrl;

    @Autowired
    private HttpServletRequest request;

    public String getName() {
        return name;
    }

    public String getSpace() {
        return space;
    }

    public int getIndex() {
        return index;
    }

    public String getMemory() {
        return memory;
    }

    public String getIp() {
        return ip;
    }

    public int getPort() {
        return port;
    }

    public String getHostIp() {
        return hostIp;
    }

    public int getHostPort() {
        return hostPort;
    }

    public String getDomain() {
        if (request != null) {
            String host = request.getHeader("Host");
            if (host != null) {
                return host.substring(host.indexOf(".") + 1);
            }
        }
        return "";
    }

    public String getHostname() {
        if (request != null) {
            String host = request.getHeader("Host");
            if (host != null) {
                return host.substring(0, host.indexOf("."));
            }
        }
        return "";
    }

    public String getAppsManHref() {
        if (StringUtils.isEmpty(appsManagerUrl) || StringUtils.isEmpty(orgGuid)) {
            return "";
        }
        return MessageFormat.format("https://{0}/organizations/{1}/spaces/{2}/applications/{3}", appsManagerUrl, orgGuid, space_guid, guid);
    }
}
