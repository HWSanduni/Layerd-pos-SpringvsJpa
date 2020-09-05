package lk.ijse.dep.pos.business.custom;

import lk.ijse.dep.pos.business.SuperBO;
import lk.ijse.dep.pos.util.ItemTM;

import java.util.List;

public interface ItemBO extends SuperBO {
    public List<ItemTM> getAllItems() throws Exception;
    public String getNewitemCode()throws Exception;
    public void saveItem(String code, String description, int qtyOnHand, double unitPrice)throws Exception;
    public void deleteItem(String itemCode)throws Exception;
    public void updateItem(String description, int qtyOnHand, double unitPrice, String itemCode)throws Exception;

    }
