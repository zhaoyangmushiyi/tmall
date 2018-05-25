package tmall.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import tmall.bean.Category;
import tmall.bean.Product;
import tmall.bean.ProductImage;
import tmall.util.DBUtil;
import tmall.util.DateUtil;

public class ProductDAO {

	public int getTotal(int cid) {
		// TODO Auto-generated method stub
		int total = 0;
		String sql = "select count(*) from Product where cid = " + cid;
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

	public void add(Product bean) {
		// TODO Auto-generated method stub
		String sql = "insert into Product values(null,?,?,?,?,?,?,?)";
		try (Connection c = DBUtil.getConnection(); PreparedStatement ps = c.prepareStatement(sql);) {
			ps.setString(1, bean.getName());
			ps.setString(2, bean.getSubTitle());
			ps.setFloat(3, bean.getOrignalPrice());
			ps.setFloat(4, bean.getPromotePrice());
			ps.setInt(5, bean.getStock());
			ps.setInt(6, bean.getCategory().getId());
			ps.setTimestamp(7, DateUtil.d2t(bean.getCreateDate()));
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

	public void update(Product bean) {
		// TODO Auto-generated method stub
		String sql = "update Product set name= ?, subTitle=?, orignalPrice=?,promotePrice=?,stock=?, cid = ?, createDate=? where id = ?";
		try (Connection c = DBUtil.getConnection(); PreparedStatement ps = c.prepareStatement(sql);) {
			ps.setString(1, bean.getName());
			ps.setString(2, bean.getSubTitle());
			ps.setFloat(3, bean.getOrignalPrice());
			ps.setFloat(4, bean.getPromotePrice());
			ps.setInt(5, bean.getStock());
			ps.setInt(6, bean.getCategory().getId());
			ps.setTimestamp(7, DateUtil.d2t(bean.getCreateDate()));
			ps.setInt(8, bean.getId());
			ps.execute();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void delete(int id) {
		// TODO Auto-generated method stub
		try (Connection c = DBUtil.getConnection(); Statement s = c.createStatement();) {

			String sql = "delete from Product where id = " + id;

			s.execute(sql);

		} catch (SQLException e) {

			e.printStackTrace();
		}
	}

	public Product get(int id) {
		// TODO Auto-generated method stub
		Product bean = null;

		try (Connection c = DBUtil.getConnection(); Statement s = c.createStatement();) {

			String sql = "select * from Product where id = " + id;

			ResultSet rs = s.executeQuery(sql);

			if (rs.next()) {
				bean = new Product();
				int cid = rs.getInt("cid");
				String name = rs.getString("name");
				String subTitle = rs.getString("subTitle");
				float orignalPrice = rs.getFloat("orignalPrice");
				float promotePrice = rs.getFloat("promotePrice");
				int stock = rs.getInt("stock");
				Date createDate = DateUtil.t2d(rs.getTimestamp("createDate"));
				bean.setName(name);
				bean.setSubTitle(subTitle);
				bean.setOrignalPrice(orignalPrice);
				bean.setPromotePrice(promotePrice);
				bean.setStock(stock);
				bean.setCreateDate(createDate);
				bean.setId(id);
				Category category = new CategoryDAO().get(cid);
				bean.setCategory(category);
				setFirstProductImage(bean);

			}

		} catch (SQLException e) {

			e.printStackTrace();
		}
		return bean;
	}

	public void setFirstProductImage(Product bean) {
		// TODO Auto-generated method stub
		List<ProductImage> productImages = new ProductImageDAO().list(bean, ProductImageDAO.type_single);
		if (!productImages.isEmpty()) {
			bean.setFirstProductImage(productImages.get(0));
		}
	}

	public void setSaleAndReviewNumber(List<Product> products) {
		for (Product product : products) {
			setSaleAndReviewNumber(product);
		}
	}

	public void setSaleAndReviewNumber(Product product) {
		// TODO Auto-generated method stub
		int saleCount = new OrderItemDAO().getSaleCount(product.getId());
		product.setSaleCount(saleCount);
		int reviewCount = new ReviewDAO().getCount(product.getId());
		product.setReviewCount(reviewCount);
	}

	public List<Product> list(int cid, int start, int count) {
		// TODO Auto-generated method stub
		List<Product> beans = new ArrayList<>();

		String sql = "select * from Product where cid = ? order by id desc limit ?,?";

		try (Connection c = DBUtil.getConnection(); PreparedStatement ps = c.prepareStatement(sql);) {

			ps.setInt(1, cid);
			ps.setInt(2, start);
			ps.setInt(3, count);

			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				Product bean = new Product();
				int id = rs.getInt("id");
				String name = rs.getString("name");
				String subTitle = rs.getString("subTitle");
				float orignalPrice = rs.getFloat("orignalPrice");
				float promotePrice = rs.getFloat("promotePrice");
				int stock = rs.getInt("stock");
				Date createDate = DateUtil.t2d(rs.getTimestamp("createDate"));

				bean.setName(name);
				bean.setSubTitle(subTitle);
				bean.setOrignalPrice(orignalPrice);
				bean.setPromotePrice(promotePrice);
				bean.setStock(stock);
				bean.setCreateDate(createDate);
				bean.setId(id);
				Category category = new CategoryDAO().get(cid);
				bean.setCategory(category);
				setFirstProductImage(bean);
				beans.add(bean);
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return beans;
	}

	public List<Product> list(int cid) {
		// TODO Auto-generated method stub
		return list(cid, 0, Short.MAX_VALUE);
	}

	public List<Product> list(int start, int count) {
		// TODO Auto-generated method stub
		List<Product> beans = new ArrayList<>();

		String sql = "select * from Product order by id desc limit ?,?";

		try (Connection c = DBUtil.getConnection(); PreparedStatement ps = c.prepareStatement(sql);) {

			ps.setInt(1, start);
			ps.setInt(2, count);

			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				Product bean = new Product();
				int id = rs.getInt("id");
				int cid = rs.getInt("cid");
				String name = rs.getString("name");
				String subTitle = rs.getString("subTitle");
				float orignalPrice = rs.getFloat("orignalPrice");
				float promotePrice = rs.getFloat("promotePrice");
				int stock = rs.getInt("stock");
				Date createDate = DateUtil.t2d(rs.getTimestamp("createDate"));

				bean.setName(name);
				bean.setSubTitle(subTitle);
				bean.setOrignalPrice(orignalPrice);
				bean.setPromotePrice(promotePrice);
				bean.setStock(stock);
				bean.setCreateDate(createDate);
				bean.setId(id);
				Category category = new CategoryDAO().get(cid);
				bean.setCategory(category);
				beans.add(bean);
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return beans;
	}

	public List<Product> list() {
		// TODO Auto-generated method stub
		return list(0, Short.MAX_VALUE);
	}

	public void fill(List<Category> cs) {
		for (Category category : cs) {
			fill(category);
		}
	}

	public void fill(Category category) {
		List<Product> products = this.list(category.getId());
		category.setProducts(products);
	}

	public void fillByRow(List<Category> cs) {
		int productNumberEachRow = 8;
		for (Category category : cs) {
			List<Product> products = this.list(category.getId());
			List<List<Product>> productsByRow = new ArrayList<>();
			for (int i = 0; i < products.size(); i += productNumberEachRow) {
				int size = i + productNumberEachRow;
				size = size > products.size() ? products.size() : size;
				List<Product> productsOfEachRow = products.subList(i, size);
				productsByRow.add(productsOfEachRow);
				category.setProductsByRow(productsByRow);
			}
		}
	}

	public List<Product> seach(String keyWord, int start, int count) {
		List<Product> products = new ArrayList<>();

		if (keyWord == null || keyWord.trim().length() == 0) {
			return products;
		}

		String sql = "select * from Product where name like ? limit ?,?";
		try (Connection c = DBUtil.getConnection(); PreparedStatement ps = c.prepareStatement(sql);) {
			ps.setString(1, "%" + keyWord.trim() + "%");
			ps.setInt(2, start);
			ps.setInt(3, count);

			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				Product product = new Product();
				int id = rs.getInt(1);
				int cid = rs.getInt("cid");
				String name = rs.getString("name");
				String subTitle = rs.getString("subTitle");
				float orignalPrice = rs.getFloat("orignalPrice");
				float promotePrice = rs.getFloat("promotePrice");
				int stock = rs.getInt("stock");
				Date createDate = DateUtil.t2d(rs.getTimestamp("createDate"));

				product.setName(name);
				product.setSubTitle(subTitle);
				product.setOrignalPrice(orignalPrice);
				product.setPromotePrice(promotePrice);
				product.setStock(stock);
				product.setCreateDate(createDate);
				product.setId(id);
				Category category = new CategoryDAO().get(cid);
				product.setCategory(category);
				setFirstProductImage(product);
				products.add(product);
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return products;
	}

}
