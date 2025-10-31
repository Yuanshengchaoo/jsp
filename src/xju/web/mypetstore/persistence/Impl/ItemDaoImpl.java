package xju.web.mypetstore.persistence.Impl;

import xju.web.mypetstore.domain.Item;
import xju.web.mypetstore.domain.Product;
import xju.web.mypetstore.persistence.DBUtil;
import xju.web.mypetstore.persistence.ItemDao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ItemDaoImpl implements ItemDao {

    private static final String UPDATE_INVENTORY_QUANTITY=
            "UPDATE ITEM SET QTY = QTY - ? WHERE ITEMID = ?";
    private static final String GET_INVENTORY_QUANTITY=
            "SELECT QTY AS value FROM INVENTORY WHERE ITEMID = ?";
    private static final String GET_ITEM_LIST_BY_PRODUCT=
            " select I.ITEMID,LISTPRICE,UNITCOST,SUPPLIER AS supplierId,I.PRODUCTID AS `product.productId`," +
                    " NAME AS `product.name`,DESCN AS `product.description`,CATEGORY AS `product.categoryId`,STATUS, " +
                    " ATTR1 AS attribute1,ATTR2 AS attribute2,ATTR3 AS attribute3,ATTR4 AS attribute4,ATTR5 AS attribute5," +
                    " QTY AS quantity from ITEM I, INVENTORY V, PRODUCT P  WHERE P.PRODUCTID = I.PRODUCTID and I.ITEMID = V.ITEMID and I.PRODUCTID = ?";
    private static final String GET_ITEM_BY_ITEMID=
            " select I.ITEMID,LISTPRICE,UNITCOST,SUPPLIER AS supplierId,I.PRODUCTID AS `product.productId`," +
                    " NAME AS `product.name`,DESCN AS `product.description`,CATEGORY AS `product.categoryId`,STATUS, " +
                    " ATTR1 AS attribute1,ATTR2 AS attribute2,ATTR3 AS attribute3,ATTR4 AS attribute4,ATTR5 AS attribute5," +
                    " QTY AS quantity from ITEM I, INVENTORY V, PRODUCT P where P.PRODUCTID = I.PRODUCTID and I.ITEMID = V.ITEMID and I.ITEMID = ?";
    @Override
    public void updateInventoryQuantity(Map<String, Object> param) {
        try {
            Connection connection = DBUtil.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_INVENTORY_QUANTITY);
            preparedStatement.setInt(1, (int) param.get("qty"));
            preparedStatement.setString(2, (String) param.get("itemId"));
            preparedStatement.executeUpdate();
            DBUtil.closePreparedStatement(preparedStatement);
            DBUtil.closeConnection(connection);
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public int getInventoryQuantity(String itemId) {
        int value = 0;
        try {
            Connection connection = DBUtil.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(GET_INVENTORY_QUANTITY);
            preparedStatement.setString(1, itemId);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                value = resultSet.getInt("value");
            }
            DBUtil.closeResultSet(resultSet);
            DBUtil.closePreparedStatement(preparedStatement);
            DBUtil.closeConnection(connection);
        }catch(Exception e){
            e.printStackTrace();
        }
        return value;
    }

    @Override
    public List<Item> getItemListByProduct(String productId) {
        List<Item> itemList = new ArrayList<>();
        try{
            Connection connection = DBUtil.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(GET_ITEM_LIST_BY_PRODUCT);
            preparedStatement.setString(1, productId);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Item item = new Item();
                item.setItemId(resultSet.getString("itemId"));
                item.setListPrice(resultSet.getBigDecimal("listPrice"));
                item.setUnitCost(resultSet.getBigDecimal("unitCost"));
                item.setSupplierId(resultSet.getInt("supplierId"));
                item.setStatus(resultSet.getString("status"));
                item.setAttribute1(resultSet.getString("attribute1"));
                item.setAttribute2(resultSet.getString("attribute2"));
                item.setAttribute3(resultSet.getString("attribute3"));
                item.setAttribute4(resultSet.getString("attribute4"));
                item.setAttribute5(resultSet.getString("attribute5"));
                Product product = new Product();
                product.setProductId(resultSet.getString("product.productId"));
                product.setName(resultSet.getString("product.name"));
                item.setProduct(product);
                itemList.add(item);
            }
            DBUtil.closeResultSet(resultSet);
            DBUtil.closePreparedStatement(preparedStatement);
            DBUtil.closeConnection(connection);
        } catch (Exception e) {
            e.printStackTrace();
        }

       return itemList;
    }
    //1111
//@Override
//public List<Item> getItemListByProduct(String productId) {
//    List<Item> itemList = new ArrayList<Item>();
//    try {
//        Connection connection = DBUtil.getConnection();
//        PreparedStatement pStatement = connection
//                .prepareStatement(GET_ITEM_LIST_BY_PRODUCT);
//        pStatement.setString(1, productId);
//        ResultSet resultSet = pStatement.executeQuery();
//        while (resultSet.next()) {
//            Item item = new Item();
//            item.setItemId(resultSet.getString(1));
//            item.setListPrice(resultSet.getBigDecimal(2));
//            item.setUnitCost(resultSet.getBigDecimal(3));
//            item.setSupplierId(resultSet.getInt(4));
//            Product product = new Product();
//            product.setProductId(resultSet.getString(5));
//            product.setName(resultSet.getString(6));
//            product.setDescription(resultSet.getString(7));
//            product.setCategoryId(resultSet.getString(8));
//            item.setProduct(product);
//            item.setStatus(resultSet.getString(9));
//            item.setAttribute1(resultSet.getString(10));
//            item.setAttribute2(resultSet.getString(11));
//            item.setAttribute3(resultSet.getString(12));
//            item.setAttribute4(resultSet.getString(13));
//            item.setAttribute5(resultSet.getString(14));
//            itemList.add(item);
//        }
//        DBUtil.closeResultSet(resultSet);
//        DBUtil.closeStatement(pStatement);
//        DBUtil.closeConnection(connection);
//    } catch (Exception e) {
//        e.printStackTrace();
//    }
//
//    return itemList;
//}

    @Override
    public Item getItem(String itemId) {
        Item item = null;
        try{
            Connection connection = DBUtil.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(GET_ITEM_BY_ITEMID);
            preparedStatement.setString(1, itemId);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                item = new Item();
                item.setItemId(resultSet.getString("itemId"));
                item.setListPrice(resultSet.getBigDecimal("listPrice"));
                item.setUnitCost(resultSet.getBigDecimal("unitCost"));
                item.setSupplierId(resultSet.getInt("supplierId"));
                item.setStatus(resultSet.getString("status"));
                item.setAttribute1(resultSet.getString("attribute1"));
                item.setAttribute2(resultSet.getString("attribute2"));
                item.setAttribute3(resultSet.getString("attribute3"));
                item.setAttribute4(resultSet.getString("attribute4"));
                item.setAttribute5(resultSet.getString("attribute5"));
                Product product = new Product();
                product.setProductId(resultSet.getString("product.productId"));
                product.setName(resultSet.getString("product.name"));
                item.setProduct(product);
                item.setQuantity(resultSet.getInt("quantity"));
            }
            DBUtil.closeResultSet(resultSet);
            DBUtil.closePreparedStatement(preparedStatement);
            DBUtil.closeConnection(connection);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return item;
    }
}
