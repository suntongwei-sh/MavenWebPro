package redis.jedis.service;

//接口规范
public interface Function<E, T> {

	public T callback(E e);

}
