package com.dictionary.service;

import com.dictionary.domain.DictionaryActions;
import org.apache.commons.collections.CollectionUtils;

import java.util.*;
import java.util.stream.Collectors;

public class DictionaryService<Key, DictionaryValue> {

    public static final String ERROR_KEY_DOES_NOT_EXIST = "ERROR, key does not exist";
    private final Map<Key, Set<DictionaryValue>> dictionaryMap;

    public DictionaryService() {
        this.dictionaryMap = new HashMap<>();
    }

    protected String add(Key key, DictionaryValue value) {
        return Optional.ofNullable(dictionaryMap.get(key))
                .map(values -> {
                    if (values.add(value)) {
                        return "Added";
                    } else {
                        return "ERROR, value already exists";
                    }
                })
                .orElseGet(() -> {
                    Set<DictionaryValue> values = new HashSet<>();
                    values.add(value);
                    dictionaryMap.putIfAbsent(key, values);
                    return "Added";
                });
    }

    protected Set<Key> keys() {
        return dictionaryMap.keySet();
    }

    protected Set<DictionaryValue> members(Key key) {
        return Optional.ofNullable(dictionaryMap.get(key)).orElseGet(() -> {
            System.out.println(ERROR_KEY_DOES_NOT_EXIST);
            return null;
        });
    }

    protected String clear() {
        dictionaryMap.clear();
        return "Cleared";
    }

    protected boolean keyExists(Key key) {
        return dictionaryMap.containsKey(key);
    }

    protected void valueExists(Key key, DictionaryValue value) {
        boolean isExists = false;
        if (keyExists(key)) {
            isExists = dictionaryMap.get(key).contains(value);
        }
        System.out.println(isExists);
    }

    protected void allMembers() {

        List<DictionaryValue> lst1 = dictionaryMap.values().stream()
                .flatMap(Collection::stream)
                .collect(Collectors.toList());

        for (int i = 0; i < lst1.size(); i++) {
            System.out.println(i + ") " + lst1.get(i));
        }

    }

    protected void items() {
        System.out.println(dictionaryMap.entrySet());
    }

    protected String remove(Key key, DictionaryValue value) {
        return Optional.ofNullable(dictionaryMap.get(key))
                .map(values -> {
                    if (values.remove(value)) {
                        if (CollectionUtils.isEmpty(values)) {
                            dictionaryMap.remove(key);
                        }
                        return "Removed.";
                    } else {
                        return "ERROR, value does not exist";
                    }
                })
                .orElse(ERROR_KEY_DOES_NOT_EXIST);
    }

    protected String removeAll(Key key) {
        if (dictionaryMap.keySet().remove(key)) {
            return "Removed";
        } else {
            return ERROR_KEY_DOES_NOT_EXIST;
        }
    }

    public void multivalueDictionary(String command, DictionaryService<String, String> dictionary) {

        String[] arrayCommand = command.split(" ");
        DictionaryActions dictionaryActions = DictionaryActions.getActionByName(arrayCommand[0]);

        switch (dictionaryActions) {
            case ADD:
                try {
                    System.out.println(dictionary.add(arrayCommand[1], arrayCommand[2]));
                } catch (ArrayIndexOutOfBoundsException ae) {
                    System.out.println("ERROR: Key & value must be provided!!");
                }
                return;
            case KEYS:
                System.out.println(dictionary.keys());
                return;
            case MEMBERS:
                try {
                    if (CollectionUtils.isNotEmpty(dictionary.members(arrayCommand[1]))) {
                        System.out.println(dictionary.members(arrayCommand[1]));
                    }
                } catch (ArrayIndexOutOfBoundsException ae) {
                    System.out.println("ERROR: Key must be provided!!");
                }
                return;
            case REMOVE:
                try {
                    System.out.println(dictionary.remove(arrayCommand[1], arrayCommand[2]));
                } catch (ArrayIndexOutOfBoundsException ae) {
                    System.out.println("ERROR: Key & value must be provided!!");
                }
                return;
            case REMOVEALL:
                try {
                    System.out.println(dictionary.removeAll(arrayCommand[1]));
                } catch (ArrayIndexOutOfBoundsException ae) {
                    System.out.println("ERROR: Key must be provided!!");
                }
                return;
            case CLEAR:
                System.out.println(dictionary.clear());
                return;
            case KEYEXISTS:
                System.out.println(dictionary.keyExists(arrayCommand[1]));;
                return;
            case VALUEEXISTS:
                try {
                    dictionary.valueExists(arrayCommand[1], arrayCommand[2]);
                } catch (ArrayIndexOutOfBoundsException ae) {
                    System.out.println("ERROR: Key & value must be provided!!");
                }
                return;
            case ALLMEMBERS:
                dictionary.allMembers();
                return;
            case ITEMS:
                dictionary.items();

        }
    }
}
