package tmall.dao;

import java.util.List;

public interface DAO<T> {
	public int getTotal();

	public void add(T t);

	public void update(T t);

	public void delete(int id);

	public T get(int id);

	public List<T> list(int start, int count);

	public List<T> list();
}
