package com.zzj.utils.chat.api;


import com.zzj.utils.chat.comm.wrapper.BodyWrapper;
import com.zzj.utils.chat.comm.wrapper.HeaderWrapper;
import com.zzj.utils.chat.comm.wrapper.QueryWrapper;
import com.zzj.utils.chat.comm.wrapper.ResponseWrapper;

import java.io.File;

public interface RestAPIInvoker {
	ResponseWrapper sendRequest(String method, String url, HeaderWrapper header, BodyWrapper body, QueryWrapper query);
	ResponseWrapper uploadFile(String url, HeaderWrapper header, File file);
    ResponseWrapper downloadFile(String url, HeaderWrapper header);
}
