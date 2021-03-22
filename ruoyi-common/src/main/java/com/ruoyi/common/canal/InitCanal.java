package com.ruoyi.common.canal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

//@Component
public class InitCanal implements ApplicationRunner {
    @Autowired
    private  SimpleCanalClientExample simpleCanalClientExample;
    @Override
    public void run(ApplicationArguments args) throws Exception {
        System.out.println("init canal========================================");
        new Thread(){
            @Override
            public void run() {
                simpleCanalClientExample.listen();
            }
        }.start();
    }
}
