package tmall.servlet;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import tmall.bean.Product;
import tmall.bean.ProductImage;
import tmall.dao.ProductImageDAO;
import tmall.util.ImageUtil;
import tmall.util.Page;

public class ProductImageServlet extends BaseBackServlet {

	@Override
	public String add(HttpServletRequest request, HttpServletResponse response, Page page) {
		// TODO Auto-generated method stub
		InputStream is = null;
		Map<String, String> params = new HashMap<>();
		is = parseUpload(request, params);
		//根据上传的参数生成productImage对象
		String type = params.get("type");
		int pid = Integer.parseInt(params.get("pid"));
		Product product = productDAO.get(pid);
		ProductImage productImage = new ProductImage();
		productImage.setProduct(product);
		productImage.setType(type);
		productImageDAO.add(productImage);
		
		//生成文件
		String fileName = productImage.getId() + ".jpg";
		String imageFolder;
		String imageFolder_small = null;
		String imageFolder_middle = null;
		
		if (ProductImageDAO.type_single.equals(productImage.getType())) {
			imageFolder = request.getSession().getServletContext().getRealPath("img/productSingle");
			imageFolder_small= request.getSession().getServletContext().getRealPath("img/productSingle_small");
            imageFolder_middle= request.getSession().getServletContext().getRealPath("img/productSingle_middle");
		}else {
			imageFolder = request.getSession().getServletContext().getRealPath("img/productDetail");
		}
		
		File file = new File(imageFolder, fileName);
		//复制文件
		
		try {
			if (is != null && is.available() != 0) {
				try(FileOutputStream fos = new FileOutputStream(file);){
					byte b[] = new byte[1024 * 1024]; 
					int length = 0;
					while ((length = is.read()) != -1){
						fos.write(b, 0, length);
					}
					fos.close();

					BufferedImage bufferedImage = ImageUtil.change2jpg(file);
					ImageIO.write(bufferedImage, "jsp", file);
					if (ProductImageDAO.type_single.equals(productImage.getType())){
						File file_small = new File(imageFolder_small, fileName);
						File file_middle = new File(imageFolder_middle, fileName);
						
					}
				}catch (Exception e) {
					e.printStackTrace();
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return "admin_productImage_list?id=";
	}

	@Override
	public String delete(HttpServletRequest request, HttpServletResponse response, Page page) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String edit(HttpServletRequest request, HttpServletResponse response, Page page) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String update(HttpServletRequest request, HttpServletResponse response, Page page) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String list(HttpServletRequest request, HttpServletResponse response, Page page) {
		// TODO Auto-generated method stub
		int pid = Integer.parseInt(request.getParameter("pid"));
		Product product = productDAO.get(pid);
		List<ProductImage> pisSingle = productImageDAO.list(product,ProductImageDAO.type_single);
		List<ProductImage> pisDetail = productImageDAO.list(product,ProductImageDAO.type_detail);
		request.setAttribute("product", product);
		request.setAttribute("pisSingle", pisSingle);
		request.setAttribute("pisDetail", pisDetail);
		
		return "admin/listProductImage.jsp";
	}

}
