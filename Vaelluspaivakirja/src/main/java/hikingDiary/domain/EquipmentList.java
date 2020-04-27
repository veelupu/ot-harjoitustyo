/*
 * /*
 * Copyright (c) Veera Lupunen 2020. All rights reserved.
 */
package hikingdiary.domain;

import java.util.HashMap;

/**
 *
 * @author veeralupunen
 */
public class EquipmentList {

    //POISTA TÄMÄ - EI TARVITA!!!
    
    private HashMap<String, Item> items;

    public EquipmentList() {
        this.items = new HashMap<>();
    }

//    public void addAnItem(String item) {
//        this.items.put(item, new Item(item));
//    }
//
//    public void addAnItem(String item, double weight) {
//        if (!items.containsKey(item)) {
//            this.items.put(item, new Item(item, weight));
//        } else {
//            this.items.get(item).setCount(this.items.get(item).getCount() + 1);
//        }
//    }
}
