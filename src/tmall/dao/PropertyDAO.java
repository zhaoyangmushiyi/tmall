package tmall.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import tmall.bean.Category;
import tmall.bean.Property;
import tmall.util.DBUtil;

public class PropertyDAO {

	public int getTotal(int cid) {
		// TODO Auto-generated method stub
		int total = 0;
		String sql = "select count(*) from Property where cid = " + cid;
		try (Connection c = DBUtil.getConnection(); Statement s = c.createStatement()) {
			ResultSet rs = s.executeQuery(sql);
			while (rs.next()) {
				total = rs.getInt(1);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return total;
	}

	public void add(Property bean) {
		// TODO Auto-generated method stub
		String sql = "insert into Property values(null,?,?)";
		try (Connection c = DBUtil.getConnection(); PreparedStatement ps = c.prepareStatement(sql);) {
			ps.setInt(1, bean.getCategory().getId());
			ps.setString(2, bean.getName());
			ps.execute();
			ResultSet rs = ps.getGeneratedKeys();
			if (rs.next()) {
				int id = rs.getInt(1);
				bean.setId(id);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void update(Property bean) {
		// TODO Auto-generated method stub
		String sql = "update Property set cid = ?, name= ? where id = ?";
		try (Connection c = DBUtil.getConnection(); PreparedStatement ps = c.prepareStatement(sql);) {
			ps.setInt(1, bean.getCategory().getId());
			ps.setString(2, bean.getName());
			ps.setInt(3, bean.getId());
			ps.execute();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void delete(int id) {
		// TODO Auto-generated method stub
		try (Connection c = DBUtil.getConnection(); Statement s = c.createStatement();) {

			String sql = "delete from Property where id = " + id;

			s.execute(sql);

		} catch (SQLException e) {

			e.printStackTrace();
		}
	}

	public Property get(int id) {
		// TODO Auto-generated method stub
		Property bean = null;

		try (Connection c = DBUtil.getConnection(); Statement s = c.createStatement();) {

			String sql = "select * from Property where id = " + id;

			ResultSet rs = s.executeQuery(sql);

			if (rs.next()) {
				bean = new Property();
				int cid = rs.getInt("cid");
				String name = rs.getString("name");
				bean.setName(name);
				bean.setId(id);
				Category category = new CategoryDAO().get(cid);
				bean.setCategory(category);
			}

		} catch (SQLException e) {

			e.printStackTrace();
		}
		return bean;
	}

	public List<Property> list(int cid, int start, int count) {
		// TODO Auto-generated method stub
		List<Property> beans = new ArrayList<>();

		String sql = "select * from Property where cid = ? order by id desc limit ?,?";

		try (Connection c = DBUtil.getConnection(); PreparedStatement ps = c.prepareStatement(sql);) {
			ps.setInt(1, cid);
			ps.setInt(2, start);
			ps.setInt(3, count);

			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				Property bean = new Property();
				int id = rs.getInt("id");
				String name = rs.getString("name");
				Category category = new CategoryDAO().get(cid);
				bean.setCategory(category);
				bean.setId(id);
				bean.setName(name);
				beans.add(bean);
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return beans;
	}

	public List<Property> list(int cid) {
		// TODO Auto-generated method stub
		return list(cid, 0, Short.MAX_VALUE);
	}

}
