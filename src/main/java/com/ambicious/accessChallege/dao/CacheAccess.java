package com.ambicious.accessChallege.dao;

import java.util.List;
import java.util.Map;

public interface CacheAccess {

    /**
     * @param key
     * @return
     */
    byte[] getObject(String key);

    /**
     * @param key
     * @param object
     */
    void saveObject(String key, byte[] object);

    /**
     * @param key
     * @param object
     * @param timeToLive
     */
    void saveObject(String key, byte[] object, int timeToLive);

    /**
     * @param key
     */
    void deleteObject(String key);

    /**
     * @param key
     */
    void containKey(String key);

    /**
     * @param timeToLive
     */
    void setTimeToLive(int timeToLive);

    /**
     * @param key
     */
    void getTimeToLive(String key);


    /**
     * Save the value if the key is absent.(I don't know the usage of it.)
     *
     * @param key
     * @param object
     * @param timeToLive
     * @return
     */
    Boolean saveObjectIfAbsent(byte[] key, byte[] object, int timeToLive);

    /**
     * Get fields from the specified hash object by key.
     *
     * @param key
     * @param fields
     * @return
     */
    List<String> getHashValues(String key, String[] fields);

    /**
     * Save fields to the specified hash object.
     *
     * @param key
     * @param map
     */
    void setHashValues(String key, Map<String, String> map);
}
