package com.github.tscn.cf;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnCloudPlatform;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;
import org.springframework.boot.autoconfigure.data.redis.RedisRepositoriesAutoConfiguration;
import org.springframework.cloud.Cloud;
import org.springframework.cloud.app.ApplicationInstanceInfo;
import org.springframework.cloud.service.ServiceInfo;
import org.springframework.cloud.service.common.RedisServiceInfo;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.naming.ldap.Control;
import java.time.Instant;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Properties;
import java.util.concurrent.ThreadLocalRandom;

@org.springframework.stereotype.Controller
public class Controller {

    private static final Logger LOG = LoggerFactory.getLogger(Controller.class);

    private long healthChecked;
    private boolean healthy = true;

    @Autowired
    private AppEnvironment env;

    @Autowired
    private TaskScheduler scheduler;

    @Autowired(required = false)
    private MessageStore messageStore;

    private void defaults(Model model) {
        model.addAttribute("app", env);
        model.addAttribute("hc", healthChecked > 0);


        String greeting = System.getenv("GREETING");
        if (messageStore != null) {
            model.addAttribute("redis", true);
            greeting = messageStore.getMessage();
        } else {
            model.addAttribute("redis", false);
        }
        if (StringUtils.isEmpty(greeting)) {
            greeting = "Hello, world!";
        }
        model.addAttribute("greeting", greeting);
    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String index(Model model) {
        defaults(model);
        return "index";
    }

    @RequestMapping(value = "/hc", method = RequestMethod.GET)
    public ResponseEntity<String> healthcheck() {
        healthChecked = System.currentTimeMillis();
        if (healthy) {
            return ResponseEntity.ok("OK");
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @RequestMapping(value = "/set-unhealthy", method = RequestMethod.POST)
    public String infect(Model model) {
        healthy = false;
        return "redirect:/";
    }

    @RequestMapping(value = "/do-something", method = RequestMethod.POST)
    public String doSomething(Model model) {
        scheduler.schedule(new Runnable() {
            @Override
            public void run() {
                int size = ThreadLocalRandom.current().nextInt(50, 100) * 1000 * 1024;
                int load = ThreadLocalRandom.current().nextInt(40, 80);
                LOG.info("Consuming {} bytes memory and {}% CPU for 60000ms", size, load);
                char[] chars = new char[size / 2];
                Arrays.fill(chars, 'a');
                long end = System.currentTimeMillis() + (60 * 1000);
                while (System.currentTimeMillis() < end) {
                    if (System.currentTimeMillis() % 1000 == 0) {
                        try {
                            LOG.info("Consuming {} bytes memory and {}% CPU for {}ms", size, load, end - System.currentTimeMillis());
                            Thread.sleep(1000 - (load * 10));
                        } catch (Exception ex) {
                            // ignore
                        }
                    }
                }
                LOG.info("Consumed {} bytes memory and {}% CPU for 60s", size, load);
            }
        }, Instant.now().plusSeconds(3));
        LOG.info("Producing load in 3 seconds");
        return "redirect:/";
    }

    @RequestMapping(value = "/do-something-on-instance", method = RequestMethod.POST)
    public String doSomethingOnInstance(Model model) {
        scheduler.schedule(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 10; i++) {
                    try {
                        Thread.sleep(ThreadLocalRandom.current().nextInt(1, 5) * 1000);
                        LOG.info("working on {}/{}", Controller.this.env.getName(), Controller.this.env.getIndex());
                    } catch (Exception ex) {
                        // ignore
                    }
                }
                LOG.info("Done working on {}/{}", Controller.this.env.getName(), Controller.this.env.getIndex());
            }
        }, Instant.now().plusSeconds(3));
        LOG.info("Starting work in 3 seconds");
        return "redirect:/";
    }

    @RequestMapping(value = "/update-message", method = RequestMethod.POST)
    public String updateMessage(Model model, @RequestParam("message") String message) {
        messageStore.setMessage(message);
        return "redirect:/";
    }

    @RequestMapping(value = "/complete", method = RequestMethod.POST)
    public String complete(Model model) {
        defaults(model);
        if (1 + 3 == 4) {
            model.addAttribute("completed", Boolean.TRUE);
            return "index";
        } else {
            throw new RuntimeException("Math skills not implemented");
        }
    }
}
