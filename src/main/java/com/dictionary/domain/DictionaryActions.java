package com.dictionary.domain;

import org.apache.commons.lang3.StringUtils;

public enum DictionaryActions {
    KEYS,
    MEMBERS,
    ADD,
    REMOVE,
    REMOVEALL,
    CLEAR,
    KEYEXISTS,
    VALUEEXISTS,
    ALLMEMBERS,
    ITEMS;


    public static DictionaryActions getActionByName(String name){
        return DictionaryActions.valueOf(StringUtils.upperCase(name));
    }
}
