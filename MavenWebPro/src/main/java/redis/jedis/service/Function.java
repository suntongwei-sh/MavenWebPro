package redis.jedis.service;

//接口
public interface Function<E, T> {

	public T callback(E e);

}
