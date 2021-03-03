package com.alibaba.csp.sentinel.init;

/**
 * 程序启动时要执行的初始化功能的接口，典型的SPI
 */
public interface InitFunc {

    void init() throws Exception;
}
