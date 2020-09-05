package lk.ijse.dep.pos.business.custom.impl;

import lk.ijse.dep.pos.business.custom.ItemBO;
import lk.ijse.dep.pos.dao.custom.ItemDAO;
import lk.ijse.dep.pos.db.JPAUtil;
import lk.ijse.dep.pos.entity.Item;
import lk.ijse.dep.pos.util.ItemTM;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
@Component
@Transactional
public class ItemBOImpl implements ItemBO {
    @Autowired
    private ItemDAO itemDAO ;

    public List<ItemTM> getAllItems() throws Exception{
        List<Item> allItems = itemDAO.findAll();
        ArrayList<ItemTM> items = new ArrayList<>();
        for (Object i : allItems) {
            Item item = (Item) i;
            items.add(new ItemTM(item.getItemCode(),item.getDescription(),item.getQtyOnHand(),item.getUnitprice().doubleValue()));
        }
        return items;



    }

    public String getNewitemCode()throws Exception{
        String lastitemCode = itemDAO.getLastitemCode();
        if (lastitemCode == null){
            return "I001";
        }else{
            int maxId=  Integer.parseInt(lastitemCode.replace("I",""));
            maxId = maxId + 1;
            String id = "";
            if (maxId < 10) {
                id = "I00" + maxId;
            } else if (maxId < 100) {
                id = "I0" + maxId;
            } else {
                id = "I" + maxId;
            }
            return id;
        }
    }

    public void saveItem(String code, String description, int qtyOnHand, double unitPrice)throws Exception{
            itemDAO.save(new Item(code, description, BigDecimal.valueOf(unitPrice), qtyOnHand));

    }

    public void deleteItem(String itemCode) throws Exception{
            itemDAO.delete(itemCode);

    }

    public void updateItem(String description, int qtyOnHand, double unitPrice, String itemCode)throws Exception{

            itemDAO.update(new Item(itemCode, description, BigDecimal.valueOf(unitPrice),qtyOnHand));
        }

}
