package xju.web.mypetstore.service;

import xju.web.mypetstore.domain.Category;
import xju.web.mypetstore.domain.Item;
import xju.web.mypetstore.domain.Product;
import xju.web.mypetstore.persistence.CategoryDao;
import xju.web.mypetstore.persistence.Impl.CategoryDaoImpl;
import xju.web.mypetstore.persistence.Impl.ItemDaoImpl;
import xju.web.mypetstore.persistence.Impl.ProductDaoImpl;
import xju.web.mypetstore.persistence.ItemDao;
import xju.web.mypetstore.persistence.ProductDao;

import java.util.List;

public class CatalogService {
    private  ProductDao productDao;
    private ItemDao itemDao;
    private CategoryDao categoryDao;

    public CatalogService() {
        this.productDao = new ProductDaoImpl();
        this.itemDao = new ItemDaoImpl();
        this.categoryDao = new CategoryDaoImpl();
    }

    public List<Category> getCategoryList() {
        return categoryDao.getCategoryList();
    }

    public Category getCategory(String categoryId) {
        return categoryDao.getCategory(categoryId);
    }

    public Product getProduct(String productId) {
        return productDao.getProduct(productId);
    }

    public List<Product> getProductListByCategory(String categoryId) {
        return productDao.getProductListByCategory(categoryId);
    }

    // TODO enable using more than one keyword
    public List<Product> searchProductList(String keyword) {
        return productDao.searchProductList("%" + keyword.toLowerCase() + "%");
    }

    public List<Item> getItemListByProduct(String productId) {
        return itemDao.getItemListByProduct(productId);
    }

    public Item getItem(String itemId) {
        return itemDao.getItem(itemId);
    }

    public boolean isItemInStock(String itemId) {
        return itemDao.getInventoryQuantity(itemId) > 0;
    }
}
