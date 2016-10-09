package redis.jedis;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import redis.clients.jedis.ShardedJedis;

@Repository("redisClientTemplate")
public class RedisClientTemplate {

	private static final Logger log = LoggerFactory.getLogger(RedisClientTemplate.class);
	@Autowired
	private RedisDataSource redisDataSource;

	private ShardedJedis shardedJedis;

	/**
	 * 断开连接
	 */
	public void disConnect() {
		if (shardedJedis != null)
			shardedJedis.disconnect();
	}

	public void getConnect() {
		if (shardedJedis != null)
			return;
		shardedJedis = redisDataSource.getRedisClient();
	}

	/**
	 * 判断键值是否存在
	 * 
	 * @param key
	 * @return
	 */
	private boolean judeKeyIsExists(String key) {
		return shardedJedis.exists(key.getBytes());
	}

	/**
	 * @param key
	 *            键值
	 * @param values
	 *            数据
	 * @param seconds
	 *            有效时间，单位秒
	 * @return
	 */
	public String setKeyValues(String key, Object values, int seconds) {

		if (key == null || "".equals(key) || values == null) {
			log.error("存入的redis的键值为空或者对象值为空");
			return "-1";
		}
		getConnect();
		if (shardedJedis == null) {
			log.error("无法获取redis仓库的链接");
			return "-2";
		}
		if (values instanceof String) {
			setValuesByString(key, (String) values, seconds);
		} else if (values instanceof Map) {
			setValuesByHash(key, (Map) values, seconds);
		} else if (values instanceof List) {
			setValuesByList(key, (List) values, seconds);
		} else {
			setValuesByAuto(key, values, seconds);
		}
		log.info("存入redis数据存储，键值：" + key);
		disConnect();
		return "1";
	}

	/**
	 * 字符串存值
	 * 
	 * @param key
	 * @param values
	 */
	private void setValuesByString(String key, String values, int seconds) {
		shardedJedis.setex(key.getBytes(), seconds, values.getBytes());
	}

	/**
	 * list集合
	 * 
	 * @param key
	 * @param values
	 */
	public String getValueByString(String key) {
		try {
			getConnect();
			if (!judeKeyIsExists(key)) {
				disConnect();
				return null;
			}
			String rs = new String(shardedJedis.get(key.getBytes()));
			return rs;
		} catch (Exception e) {
			log.error("获取getValueByString异常，key=" + key);

		} finally {
			disConnect();
		}
		return null;
	}

	private void setValuesByList(String key, List values, int seconds) {
		if (judeKeyIsExists(key)) {
			shardedJedis.del(key);
		}
		shardedJedis.setex(key.getBytes(), seconds, ObjectsTranscoder.serialize(values));
	}

	public List getValuesByList(String key) {
		try {
			getConnect();
			if (!judeKeyIsExists(key)) {
				disConnect();
				return null;
			}
			byte[] in = shardedJedis.get(key.getBytes());
			Object o = ObjectsTranscoder.deserialize(in);
			if (o != null && o instanceof List) {
				return (List) o;
			}
		} catch (Exception e) {
			log.error("获取getValuesByList异常，key=" + key);

		} finally {
			disConnect();
		}
		return null;
	}

	/**
	 * Map集合
	 * 
	 * @param key
	 * @param values
	 */
	private void setValuesByHash(String key, Map values, int seconds) {
		if (judeKeyIsExists(key)) {
			shardedJedis.del(key.getBytes());
		}
		shardedJedis.setex(key.getBytes(), seconds, ObjectsTranscoder.serialize(values));
	}

	public Map getValuesByHash(String key) {
		try {
			getConnect();
			if (!judeKeyIsExists(key)) {
				return null;
			}
			byte[] in = shardedJedis.get(key.getBytes());
			Object o = ObjectsTranscoder.deserialize(in);
			if (o != null && o instanceof Map) {
				return (Map) o;
			}
		} catch (Exception e) {
			log.error("获取getValuesByHash异常，key=" + key);

		} finally {
			disConnect();
		}
		return null;
	}

	/**
	 * 其他自定义对象
	 */

	private void setValuesByAuto(String key, Object values, int seconds) {
		if (judeKeyIsExists(key)) {
			shardedJedis.del(key);
		}
		shardedJedis.setex(key.getBytes(), seconds, ObjectsTranscoder.serialize(values));
	}

	public Object getValuesByAuto(String key) {
		try {
			getConnect();
			if (!judeKeyIsExists(key)) {
				return null;
			}
			byte[] in = shardedJedis.get(key.getBytes());
			return ObjectsTranscoder.deserialize(in);
		} catch (Exception e) {
			log.error("获取getValuesByAuto异常，key=" + key);

		} finally {
			disConnect();
		}
		return null;
	}
}
