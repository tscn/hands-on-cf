package com.github.tscn.cf;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

@Component
public class AppEnvironment {

    @Value("${vcap.application.name}")
    private String name;

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
}
