package com.zzj.utils.chat.api.impl;


import com.zzj.utils.chat.api.ChatMessageAPI;
import com.zzj.utils.chat.api.EasemobRestAPI;
import com.zzj.utils.chat.comm.constant.HTTPMethod;
import com.zzj.utils.chat.comm.helper.HeaderHelper;
import com.zzj.utils.chat.comm.wrapper.HeaderWrapper;
import com.zzj.utils.chat.comm.wrapper.QueryWrapper;

public class EasemobChatMessage extends EasemobRestAPI implements ChatMessageAPI {

    private static final String ROOT_URI = "/chatmessages";

    public Object exportChatMessages(Long limit, String cursor, String query) {
        String url = getContext().getSeriveURL() + getResourceRootURI();
        HeaderWrapper header = HeaderHelper.getDefaultHeaderWithToken();
        QueryWrapper queryWrapper = QueryWrapper.newInstance().addLimit(limit).addCursor(cursor).addQueryLang(query);

        return getInvoker().sendRequest(HTTPMethod.METHOD_GET, url, header, null, queryWrapper);
    }

    @Override
    public String getResourceRootURI() {
        return ROOT_URI;
    }
}
