package redis.jedis.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import redis.clients.jedis.ShardedJedis;
import redis.clients.jedis.ShardedJedisPool;

/**
 * 操作rdis啊啊
 * 
 * @author LAPLACE
 *
 */
@Service
public class RedisService {

	@Autowired(required = false) // 如果容器中有就注入，如果没有就忽略
	private ShardedJedisPool shardedJedisPool;

	public <T> T execute(Function<ShardedJedis, T> fun) {
		ShardedJedis shardedJedis = null;
		try {
			// 从连接池中获取到jedis 分片对象
			shardedJedis = shardedJedisPool.getResource();
			return fun.callback(shardedJedis);
		} finally {
			if (null != shardedJedis) {
				// 关闭，检测连接是否有效，有效则放回到连接池中，无效则重置状态
				shardedJedis.close();
			}
		}
	}

	/**
	 * 保存数据到Redis
	 * 
	 * @param key
	 * @param value
	 * @return
	 */
	public String set(final String key, final String value) {
		return this.execute(new Function<ShardedJedis, String>() {
			@Override
			public String callback(ShardedJedis e) {
				return e.set(key, value);
			}
		});
	}

	/**
	 * 从Redis中获取数据
	 * 
	 * @param key
	 * @return
	 */
	public String get(final String key) {
		return this.execute(new Function<ShardedJedis, String>() {
			@Override
			public String callback(ShardedJedis e) {
				return e.get(key);
			}
		});
	}

	/**
	 * 保存数据到Redis并且设置生存时间，单位为秒
	 * 
	 * @param key
	 * @param value
	 * @param seconds
	 * @return
	 */
	public String set(final String key, final String value, final Integer seconds) {
		return this.execute(new Function<ShardedJedis, String>() {
			@Override
			public String callback(ShardedJedis e) {
				String result = e.set(key, value);
				e.expire(key, seconds);
				return result;
			}
		});
	}

	/**
	 * 设置生存时间，单位为秒
	 * 
	 * @param key
	 * @param seconds
	 * @return
	 */
	public Long expire(final String key, final Integer seconds) {
		return this.execute(new Function<ShardedJedis, Long>() {
			@Override
			public Long callback(ShardedJedis e) {
				return e.expire(key, seconds);
			}
		});
	}

	/**
	 * 根据key删除数据
	 * 
	 * @param key
	 * @return
	 */
	public Long del(final String key) {
		return this.execute(new Function<ShardedJedis, Long>() {
			@Override
			public Long callback(ShardedJedis e) {
				return e.del(key);
			}
		});
	}

}
