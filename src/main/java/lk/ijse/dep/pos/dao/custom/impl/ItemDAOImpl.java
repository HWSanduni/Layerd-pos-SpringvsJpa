package lk.ijse.dep.pos.dao.custom.impl;

import lk.ijse.dep.pos.dao.CrudDAOImpl;
import lk.ijse.dep.pos.dao.custom.ItemDAO;
import lk.ijse.dep.pos.entity.Item;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public class ItemDAOImpl extends CrudDAOImpl<Item,String> implements ItemDAO {


    public String getLastitemCode() throws Exception{
       List list = entityManager.createQuery("SELECT i.itemCode FROM lk.ijse.dep.pos.Item i ORDER BY i.itemCode DESC").setMaxResults(1).getResultList();
        return (list.size()>0) ? (String) list.get(0):null;
    }


}
