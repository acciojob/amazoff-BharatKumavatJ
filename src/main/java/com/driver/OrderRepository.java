
package com.driver;
import org.springframework.stereotype.Component;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Component
public class OrderRepository {

    HashMap<String, Order> ordersListDB = new HashMap<>();

    HashMap<String, DeliveryPartner> deliveryPartnerListDB = new HashMap<>();

    HashMap<String, List<Order>> partnerOrderPairDB = new HashMap<>();

    HashMap<String, String> byDirectionalMapping = new HashMap<>();


    public void addOrder(Order order) {

        Order orderTobeAdded = new Order(order.getId(), Integer.toString(order.getDeliveryTime()));

        ordersListDB.put(order.getId(), orderTobeAdded);

    }

    public void addPartner(String partnerId) {

        DeliveryPartner deliveryPartnerToBeAdded = new DeliveryPartner(partnerId);
        deliveryPartnerListDB.put(partnerId, deliveryPartnerToBeAdded);

    }

    // size = all orders - assigned = ans
    // p1 p2 p3
    public void partnerPairOrder(String orderId, String partnerId) {

        if(!partnerOrderPairDB.containsKey(partnerId)){
            // no contains
            partnerOrderPairDB.put(partnerId, new ArrayList<>());
        }
        Order orderToBePaired = ordersListDB.get(orderId);
        partnerOrderPairDB.get(partnerId).add(orderToBePaired);

        DeliveryPartner partner = deliveryPartnerListDB.get(partnerId);
        int currOrder = partner.getNumberOfOrders();
        partner.setNumberOfOrders(currOrder + 1);

        byDirectionalMapping.put(orderId, partnerId);

    }

    public Order getOrderById(String orderId) {
        return ordersListDB.get(orderId);
    }

    public DeliveryPartner getPartnerById(String partnerId) {
        return deliveryPartnerListDB.get(partnerId);
    }


    public int getNumberOfOrderAssignedToPartner(String partnerId) {
        DeliveryPartner partner = deliveryPartnerListDB.get(partnerId);
        int currOrder = partner.getNumberOfOrders();
        return currOrder;


    }


    // 7th
    public List<String> getAllOrdersAssignedToPartner(String partnerId) {
        List<Order> orders = partnerOrderPairDB.get(partnerId);


        List<String> ordersName = new ArrayList<>();

        for(Order order : orders){
            ordersName.add(order.toString());
        }
        return ordersName;
    }

    // 8th
    public List<String> getAllOrders() {

        List<String> allOrders = new ArrayList<>();

        for(Order order: ordersListDB.values()){

            allOrders.add(order.toString());
        }
        return allOrders;

    }

    public Integer getCountOfUnassignedOrders() {
        int allOrders = ordersListDB.size();

        int assignedOrders = 0;
        for( String partnerId : partnerOrderPairDB.keySet()){
            assignedOrders += partnerOrderPairDB.get(partnerId).size();
        }
        return (allOrders - assignedOrders);

    }


    // bydirectio
    //  ordrid : partner id
    public void deletePartnerById(String partnerId) {
        if(deliveryPartnerListDB.containsKey(partnerId)) {
            deliveryPartnerListDB.remove(partnerId);
        }
        if(partnerOrderPairDB.containsKey(partnerId)){
            partnerOrderPairDB.remove(partnerId);
        }
        // removing assigned dependancy
        for(String orderId : byDirectionalMapping.keySet()){
            if(byDirectionalMapping.get(orderId) == partnerId){
                byDirectionalMapping.remove(orderId);
                break;
            }
        }
    }

    public void deleteOrderById(String orderId) {

        if(ordersListDB.containsKey(orderId))
            ordersListDB.remove(orderId);

        if(byDirectionalMapping.containsKey(orderId)){

            String partnerId = byDirectionalMapping.get(orderId);
            List<Order> allOrdersAssignedToPartner = partnerOrderPairDB.get(partnerId);

            for(Order order : allOrdersAssignedToPartner){
                if(order.getId() == orderId){
                    allOrdersAssignedToPartner.remove(order);
                    break;
                }
            }
            partnerOrderPairDB.put(partnerId, allOrdersAssignedToPartner);
            byDirectionalMapping.remove(orderId);
        }


    }
}

// p1  : o1 {time, id} o2 {time, id}
//  p2
