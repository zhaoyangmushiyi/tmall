package tmall.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import tmall.bean.User;
import tmall.util.DBUtil;

public class UserDAO implements DAO<User> {

	@Override
	public int getTotal() {
		// beanODO Auto-generated method stub
		int total = 0;
		String sql = "select count(*) from User";
		try (Connection c = DBUtil.getConnection(); Statement s = c.createStatement()) {
			ResultSet rs = s.executeQuery(sql);
			while (rs.next()) {
				total = rs.getInt(1);
			}
		} catch (SQLException e) {
			// beanODO Auto-generated catch block
			e.printStackTrace();
		}
		return total;
	}

	@Override
	public void add(User bean) {
		// beanODO Auto-generated method stub
		String sql = "insert into User values(null,?,?)";
		try (Connection c = DBUtil.getConnection(); PreparedStatement ps = c.prepareStatement(sql);) {
			ps.setString(1, bean.getName());
			ps.setString(2, bean.getPassword());
			ps.execute();
			ResultSet rs = ps.getGeneratedKeys();
			if (rs.next()) {
				int id = rs.getInt(1);
				bean.setId(id);
			}
		} catch (SQLException e) {
			// beanODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void update(User bean) {
		// beanODO Auto-generated method stub
		String sql = "update User set name= ?, password = ? where id = ?";
		try (Connection c = DBUtil.getConnection(); PreparedStatement ps = c.prepareStatement(sql);) {
			ps.setString(1, bean.getName());
			ps.setString(2, bean.getPassword());
			ps.setInt(3, bean.getId());
			ps.execute();
		} catch (SQLException e) {
			// beanODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void delete(int id) {
		// beanODO Auto-generated method stub
		try (Connection c = DBUtil.getConnection(); Statement s = c.createStatement();) {

			String sql = "delete from User where id = " + id;

			s.execute(sql);

		} catch (SQLException e) {

			e.printStackTrace();
		}
	}

	@Override
	public User get(int id) {
		// beanODO Auto-generated method stub
		User bean = null;

		try (Connection c = DBUtil.getConnection(); Statement s = c.createStatement();) {

			String sql = "select * from User where id = " + id;

			ResultSet rs = s.executeQuery(sql);

			if (rs.next()) {
				bean = new User();
				String name = rs.getString(2);
				String password = rs.getString(3);
				bean.setName(name);
				bean.setPassword(password);
				bean.setId(id);
			}

		} catch (SQLException e) {

			e.printStackTrace();
		}
		return bean;
	}

	@Override
	public List<User> list(int start, int count) {
		// beanODO Auto-generated method stub
		List<User> beans = new ArrayList<>();

		String sql = "select * from User order by id desc limit ?,?";

		try (Connection c = DBUtil.getConnection(); PreparedStatement ps = c.prepareStatement(sql);) {

			ps.setInt(1, start);
			ps.setInt(2, count);

			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				User bean = new User();
				int id = rs.getInt(1);
				String name = rs.getString(2);
				String password = rs.getString(3);
				bean.setId(id);
				bean.setName(name);
				bean.setPassword(password);
				beans.add(bean);
			}

		} catch (SQLException e) {
			// beanODO Auto-generated catch block
			e.printStackTrace();
		}
		return beans;
	}

	@Override
	public List<User> list() {
		// beanODO Auto-generated method stub
		return list(0, Short.MAX_VALUE);
	}

	public User get(String name) {
		User bean = null;
		String sql = "select * from User where name = ?";
		try (Connection c = DBUtil.getConnection(); PreparedStatement ps = c.prepareStatement(sql);) {
			ps.setString(1, name);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				bean = new User();
				int id = rs.getInt("id");
				String password = rs.getString(3);
				bean.setId(id);
				bean.setName(name);
				bean.setPassword(password);
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return bean;
	}

	public User get(String name, String password) {
		User bean = null;
		String sql = "select * from User where name = ? and password = ?";
		try (Connection c = DBUtil.getConnection(); PreparedStatement ps = c.prepareStatement(sql);) {
			ps.setString(1, name);
			ps.setString(1, password);

			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				bean = new User();
				int id = rs.getInt("id");
				bean.setId(id);
				bean.setName(name);
				bean.setPassword(password);
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return bean;
	}
}
