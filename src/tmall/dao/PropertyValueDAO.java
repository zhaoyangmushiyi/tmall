package tmall.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import tmall.bean.Product;
import tmall.bean.Property;
import tmall.bean.PropertyValue;
import tmall.util.DBUtil;

public class PropertyValueDAO {

	public int getTotal() {
		int total = 0;

		String sql = "select count(*) from PropertyValue";
		try (Connection c = DBUtil.getConnection(); Statement s = c.createStatement();) {

			ResultSet rs = s.executeQuery(sql);
			if (rs.next()) {
				total = rs.getInt(1);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return total;

	}

	public void add(PropertyValue bean) {
		String sql = "insert into PropertyValue values (null,?,?,?)";
		try (Connection c = DBUtil.getConnection(); PreparedStatement ps = c.prepareStatement(sql);) {

			ps.setString(1, bean.getValue());
			ps.setInt(2, bean.getProduct().getId());
			ps.setInt(3, bean.getProperty().getId());
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

	public PropertyValue get(int id) {
		PropertyValue bean = new PropertyValue();
		String sql = "select * from ProvertyValue where id = " + id;
		try (Connection c = DBUtil.getConnection(); Statement s = c.createStatement();) {
			ResultSet rs = s.executeQuery(sql);
			if (rs.next()) {
				int pid = rs.getInt("pid");
				int ptid = rs.getInt("pcid");
				String value = rs.getString("value");
				Product product = new ProductDAO().get(pid);
				Property property = new PropertyDAO().get(ptid);
				bean.setId(id);
				bean.setValue(value);
				bean.setProduct(product);
				bean.setProperty(property);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return bean;
	}

	public void update(PropertyValue bean) {

		String sql = "update PropertyValue set pid = ?, ptid = ?, value = ? where id = ?";

		try (Connection c = DBUtil.getConnection(); PreparedStatement ps = c.prepareStatement(sql);) {
			ps.setInt(1, bean.getProduct().getId());
			ps.setInt(2, bean.getProperty().getId());
			ps.setString(3, bean.getValue());
			ps.setInt(4, bean.getId());
			ps.execute();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void delete(int id) {
		String sql = "delete from PropertyValue where id = " + id;

		try (Connection c = DBUtil.getConnection(); Statement s = c.createStatement();) {
			s.execute(sql);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public PropertyValue get(int ptid, int pid) {

		PropertyValue bean = null;
		String sql = "select * from PropertyValue where ptid = " + ptid + "and pid = " + pid;
		try (Connection c = DBUtil.getConnection(); Statement s = c.createStatement();) {
			ResultSet rs = s.executeQuery(sql);
			while (rs.next()) {
				bean = new PropertyValue();
				int id = rs.getInt(1);
				String value = rs.getString("value");
				Product product = new ProductDAO().get(pid);
				Property property = new PropertyDAO().get(ptid);
				bean.setId(id);
				bean.setValue(value);
				bean.setProduct(product);
				bean.setProperty(property);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return bean;

	}

	public List<PropertyValue> list() {
		return list(0, Short.MAX_VALUE);
	}

	public List<PropertyValue> list(int start, int count) {

		List<PropertyValue> beans = new ArrayList<>();
		;

		String sql = "select * from PropertyValue order by id desc limit ?,?";

		try (Connection c = DBUtil.getConnection(); PreparedStatement ps = c.prepareStatement(sql);) {
			ps.setInt(1, start);
			ps.setInt(2, count);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				PropertyValue bean = new PropertyValue();
				int id = rs.getInt(1);
				String value = rs.getString("value");
				int pid = rs.getInt("pid");
				int ptid = rs.getInt("ptid");
				Product product = new ProductDAO().get(pid);
				Property property = new PropertyDAO().get(ptid);
				bean.setId(id);
				bean.setValue(value);
				bean.setProduct(product);
				bean.setProperty(property);

				beans.add(bean);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return beans;
	}

	public void init(Product product) {
		List<Property> pts = new PropertyDAO().list(product.getCategory().getId());
		for (Property property : pts) {
			PropertyValue ptv = get(property.getId(), product.getId());
			if (ptv == null) {
				ptv = new PropertyValue();
				ptv.setProduct(product);
				ptv.setProperty(property);
			}
		}
	}

	public List<PropertyValue> list(int pid) {
		List<PropertyValue> beans = new ArrayList<>();

		String sql = "select * from PropertyValue where pid = " + pid + "order by ptid desc";
		try (Connection c = DBUtil.getConnection(); Statement s = c.createStatement();) {
			ResultSet rs = s.executeQuery(sql);
			while (rs.next()) {
				PropertyValue bean = new PropertyValue();
				int id = rs.getInt(1);
				int ptid = rs.getInt("ptid");
				String value = rs.getString("value");
				Product product = new ProductDAO().get(pid);
				Property property = new PropertyDAO().get(ptid);
				bean.setId(id);
				bean.setValue(value);
				bean.setProduct(product);
				bean.setProperty(property);
				beans.add(bean);

			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return beans;

	}

}
