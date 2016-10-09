package redis.jedis.test;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import redis.clients.jedis.ShardedJedis;
import redis.clients.jedis.ShardedJedisPool;

public class JedisTest_1 {

	public static void main(String[] args) {
		// 加载spring配置文件xml
		ApplicationContext ap = new FileSystemXmlApplicationContext("classpath:spring/spring-redis.xml");
		// 获取定义的bean
		ShardedJedisPool jedisPool = (ShardedJedisPool) ap.getBean("shardedJedisPool");
		ShardedJedis jedis = null;
		try {
			jedis = jedisPool.getResource();
			jedis.set("rediskey3", "redisvalue1");
			jedis.set("rediskey4", "redisvalue2");
			System.out.println(jedis.get("rediskey3"));
			System.out.println(jedis.get("rediskey4"));
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (jedis != null)
				jedis.close();
		}
		// jedisPool.destroy();
	}

}
