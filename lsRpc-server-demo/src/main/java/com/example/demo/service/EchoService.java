package com.example.demo.service;

import com.example.demo.service.pb.Echo;

/**
 * Created By 朱立松 on 2020/12/14
 */
public interface EchoService {
    Echo.EchoRsp echo(Echo.EchoReq req);
}
