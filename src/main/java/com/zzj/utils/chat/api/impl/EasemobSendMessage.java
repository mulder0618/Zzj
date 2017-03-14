package com.zzj.utils.chat.api.impl;


import com.zzj.utils.chat.api.EasemobRestAPI;
import com.zzj.utils.chat.api.SendMessageAPI;
import com.zzj.utils.chat.comm.constant.HTTPMethod;
import com.zzj.utils.chat.comm.helper.HeaderHelper;
import com.zzj.utils.chat.comm.wrapper.BodyWrapper;
import com.zzj.utils.chat.comm.wrapper.HeaderWrapper;

public class EasemobSendMessage extends EasemobRestAPI implements SendMessageAPI {
    private static final String ROOT_URI = "/messages";

    @Override
    public String getResourceRootURI() {
        return ROOT_URI;
    }

    public Object sendMessage(Object payload) {
        String  url = getContext().getSeriveURL() + getResourceRootURI();
        HeaderWrapper header = HeaderHelper.getDefaultHeaderWithToken();
        BodyWrapper body = (BodyWrapper) payload;

        return getInvoker().sendRequest(HTTPMethod.METHOD_POST, url, header, body, null);
    }
}
