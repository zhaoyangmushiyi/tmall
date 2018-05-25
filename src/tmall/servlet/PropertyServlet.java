package tmall.servlet;
import java.io.UnsupportedEncodingException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import tmall.bean.Category;
import tmall.bean.Property;
import tmall.util.Page;

public class PropertyServlet extends BaseBackServlet {

	@Override
	public String add(HttpServletRequest request, HttpServletResponse response, Page page) {
		// TODO Auto-generated method stub
		int cid = Integer.parseInt(request.getParameter("cid"));
		String name =null;
		try {
			name = new String((request.getParameter("name")).getBytes("ISO-8859-1"),"UTF-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Category category = categoryDAO.get(cid);
		Property property = new Property();
		property.setCategory(category);
		property.setName(name);
		propertyDAO.add(property);
		return "@admin_property_list?cid=" + cid;
	}

	@Override
	public String delete(HttpServletRequest request, HttpServletResponse response, Page page) {
		// TODO Auto-generated method stub
		int id = Integer.parseInt(request.getParameter("id"));
		Property property = propertyDAO.get(id);
		propertyDAO.delete(id);
		return "@admin_property_list?cid=" + property.getCategory().getId();
	}

	@Override
	public String edit(HttpServletRequest request, HttpServletResponse response, Page page) {

		int id = Integer.parseInt(request.getParameter("id"));
		Property property = propertyDAO.get(id);
		request.setAttribute("property", property);
		return "admin/editProperty.jsp";
	}

	@Override
	public String update(HttpServletRequest request, HttpServletResponse response, Page page) {
		int cid = Integer.parseInt(request.getParameter("cid"));
		int id = Integer.parseInt(request.getParameter("id"));
		Category category = categoryDAO.get(cid);
		Property property = propertyDAO.get(id);
		String name =null;
		try {
			name = new String((request.getParameter("name")).getBytes("ISO-8859-1"),"UTF-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		property.setName(name);
		property.setCategory(category);
		property.setId(id);
		propertyDAO.update(property);
		return "@admin_property_list?cid="+property.getCategory().getId();
	}

	@Override
	public String list(HttpServletRequest request, HttpServletResponse response, Page page) {
		int cid = Integer.parseInt(request.getParameter("cid"));
		Category category = categoryDAO.get(cid);
		List<Property> properties = propertyDAO.list(cid, page.getStart(), page.getCount());
		int total = propertyDAO.getTotal(cid);
		page.setTotal(total);
		page.setParam("&cid=" + category.getId());
		request.setAttribute("properties", properties);
		request.setAttribute("category", category);
		request.setAttribute("page", page);

		return "admin/listProperty.jsp";
	}

}
