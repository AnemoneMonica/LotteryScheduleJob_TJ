package com.hb.lottery.conf;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import redis.clients.jedis.Jedis;

public class RedisUtil {

	public static void setMap(String key, Map<String, String> map) {
		Jedis jedis = null;
		try {
			jedis = Redis.getInstance().getJedis();
			jedis.hmset(key, map);
		} catch (Exception e) {
			
		} finally {
			Redis.getInstance().returnResource(jedis);
		}
	}
	public static Map<String, String> getMap(String key) {
		Jedis jedis = null;
		try {
			jedis = Redis.getInstance().getJedis();
			return jedis.hgetAll(key);
		} catch (Exception e) {
			
		} finally {
			Redis.getInstance().returnResource(jedis);
		}
		return null;
	}

	public static void set(String key, String value) {
		Jedis jedis = null;
		try {
			jedis = Redis.getInstance().getJedis();
			jedis.set(key, value);
		} catch (Exception e) {
			
		} finally {
			Redis.getInstance().returnResource(jedis);
		}

	}

	public static String getValue(String key) {
		Jedis jedis = null;
		try {
			jedis = Redis.getInstance().getJedis();
			return jedis.get(key);
		} catch (Exception e) {
		} finally {
			Redis.getInstance().returnResource(jedis);
		}
		return null;
	}
      public static void setTicketNoticeMap(String packageId,String ticketNum)
      {
        Jedis jedis = null;
		try {
			jedis = Redis.getInstance().getJedis();
                       Map<String,String>map=jedis.hgetAll("ticketNoticeMap");
                       if(map==null){
                        map=new HashMap<String,String>();
                       }
                      map.put(packageId, ticketNum);
                    jedis.hmset("ticketNoticeMap",map);
		} catch (Exception e) {
			System.out.println(e);
		} finally {
			Redis.getInstance().returnResource(jedis);
		}
      
      }
      
        public static void setTicketInfoMap(String packageId,String ticketId,String dealTime,String ticketSerialNo,String statusCode)
      {
        Jedis jedis = null;
		try {
			jedis = Redis.getInstance().getJedis();
                       Map<String,String>map=jedis.hgetAll(packageId);
                       if(map!=null){
                        //map=new HashMap<String,String>();
                    	  map.put(ticketId, statusCode+"_"+dealTime+"_"+ticketSerialNo);
                    	  jedis.hmset(packageId,map);
                       }
		} catch (Exception e) {
			System.out.println(e);
		} finally {
			Redis.getInstance().returnResource(jedis);
		}
      
      }
        
        //del Map
        public static void detTicketNoticeMap(String packageId)
        {
        	Jedis jedis = null;
    		try {
    			jedis = Redis.getInstance().getJedis();
    		    
    			jedis.del(packageId);
    			//删除ticketNoticeMap里面的包号信息
    			jedis.hdel("ticketNoticeMap",packageId);
                      
    		} catch (Exception e) {
    			System.out.println(e);
    		} finally {
    			Redis.getInstance().returnResource(jedis);
    		}
        }
	
}
